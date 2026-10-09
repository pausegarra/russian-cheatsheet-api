drop trigger word_relations_values_before_insert on word_relations;
drop trigger word_relations_checksum_before_snapshot_update on word_relations;
drop trigger words_relation_checksum_after_russian_update on words;

drop function set_word_relation_values_before_insert();
drop function refresh_word_relation_checksum_from_snapshots();
drop function refresh_word_relation_russian_snapshots_after_word_update();

alter table word_relations
  drop column source_russian,
  drop column related_russian;

create function set_word_relation_checksum_from_words()
returns trigger
language plpgsql
as $$
declare
  source_word_russian text;
  related_word_russian text;
begin
  perform word.id
  from words word
  where word.id in (new.source_word_id, new.target_word_id)
  order by word.id
  for update;

  select source_word.russian, target_word.russian
  into source_word_russian, related_word_russian
  from words source_word, words target_word
  where source_word.id = new.source_word_id
    and target_word.id = new.target_word_id;

  new.checksum := calculate_word_relation_checksum(
    source_word_russian, related_word_russian, new.relation
  );
  return new;
end;
$$;

create trigger word_relations_checksum_before_write
before insert or update of source_word_id, target_word_id, relation on word_relations
for each row execute function set_word_relation_checksum_from_words();

create function refresh_word_relation_checksums_after_word_update()
returns trigger
language plpgsql
as $$
begin
  if new.russian is distinct from old.russian then
    perform relation.id
    from word_relations relation
    where relation.source_word_id = new.id
      or relation.target_word_id = new.id
    order by relation.id
    for update;

    update word_relations relation
    set checksum = calculate_word_relation_checksum(
      source_word.russian, target_word.russian, relation.relation
    )
    from words source_word, words target_word
    where relation.source_word_id = source_word.id
      and relation.target_word_id = target_word.id
      and (source_word.id = new.id or target_word.id = new.id);
  end if;
  return new;
end;
$$;

create trigger words_relation_checksum_after_russian_update
after update of russian on words
for each row execute function refresh_word_relation_checksums_after_word_update();
