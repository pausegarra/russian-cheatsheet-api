create extension if not exists pgcrypto;

create function calculate_word_relation_checksum(source_russian text, related_russian text, relation_type text)
returns text
language sql
immutable
strict
as $$
  select encode(
    digest(convert_to(source_russian || '|' || related_russian || '|' || relation_type, 'UTF8'), 'sha256'),
    'hex'
  )
$$;

alter table word_relations add column checksum varchar(64);

update word_relations relation
set checksum = calculate_word_relation_checksum(source_word.russian, target_word.russian, relation.relation)
from words source_word, words target_word
where source_word.id = relation.source_word_id
  and target_word.id = relation.target_word_id;

create function set_legacy_word_relation_checksum()
returns trigger
language plpgsql
as $$
declare
  source_russian text;
  related_russian text;
begin
  if new.checksum is not null then
    return new;
  end if;

  perform word.id
  from words word
  where word.id in (new.source_word_id, new.target_word_id)
  order by word.id
  for update;

  select source_word.russian, target_word.russian
  into source_russian, related_russian
  from words source_word
  join words target_word on target_word.id = new.target_word_id
  where source_word.id = new.source_word_id;

  new.checksum := calculate_word_relation_checksum(source_russian, related_russian, new.relation);
  return new;
end;
$$;

create trigger word_relations_checksum_before_insert
before insert on word_relations
for each row execute function set_legacy_word_relation_checksum();

create function refresh_word_relation_checksums_after_russian_update()
returns trigger
language plpgsql
as $$
begin
  if new.russian is distinct from old.russian then
    update word_relations relation
    set checksum = calculate_word_relation_checksum(
      source_word.russian, target_word.russian, relation.relation
    )
    from words source_word, words target_word
    where source_word.id = relation.source_word_id
      and target_word.id = relation.target_word_id
      and (relation.source_word_id = new.id or relation.target_word_id = new.id);
  end if;
  return new;
end;
$$;

create trigger words_relation_checksum_after_russian_update
after update of russian on words
for each row execute function refresh_word_relation_checksums_after_russian_update();

alter table word_relations alter column checksum set not null;
