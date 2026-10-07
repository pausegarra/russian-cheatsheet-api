alter table example_sentences add column if not exists external_id varchar(255);
alter table example_sentences alter column external_id drop not null;
alter table example_sentences drop constraint if exists example_sentences_external_id_key;
create unique index if not exists example_sentences_external_id_uidx
  on example_sentences (external_id)
  where external_id is not null;
