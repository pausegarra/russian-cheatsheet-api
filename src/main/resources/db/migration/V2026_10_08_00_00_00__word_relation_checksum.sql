create extension if not exists pgcrypto;

alter table word_relations add column checksum varchar(64);

update word_relations relation
set checksum = encode(
  digest(convert_to(source_word.russian || '|' || target_word.russian || '|' || relation.relation, 'UTF8'), 'sha256'),
  'hex'
)
from words source_word, words target_word
where source_word.id = relation.source_word_id
  and target_word.id = relation.target_word_id;

alter table word_relations alter column checksum set not null;
