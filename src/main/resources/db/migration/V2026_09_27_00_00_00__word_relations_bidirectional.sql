with inserted_relations as (
  insert into word_relations (id, source_word_id, target_word_id, relation)
  select gen_random_uuid(), relation.target_word_id, relation.source_word_id, relation.relation
  from word_relations relation
  where not exists (
    select 1
    from word_relations inverse_relation
    where inverse_relation.source_word_id = relation.target_word_id
      and inverse_relation.target_word_id = relation.source_word_id
      and inverse_relation.relation = relation.relation
  )
  returning source_word_id, target_word_id
)
update words as word
set checksum = null
where word.external_id is not null
  and word.id in (
    select source_word_id from inserted_relations
    union
    select target_word_id from inserted_relations
  );
