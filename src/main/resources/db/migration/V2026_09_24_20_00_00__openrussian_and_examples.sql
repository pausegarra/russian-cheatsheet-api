drop index if exists words_russian_english_spanish_idx;
alter table words drop constraint if exists words_russian_key;
alter table words drop column if exists english;
alter table words drop column if exists spanish;

alter table words add column external_id varchar(255);
alter table words add column usage text;
alter table words add column audio_url varchar(2048);
alter table words add column checksum varchar(64);
alter table words add column aspect varchar(16);
alter table words drop column if exists conjugations;
alter table words drop column if exists declinations;
alter table words drop column if exists declination_matrix;
alter table words add column forms jsonb;
alter table words add column translations jsonb not null default '[]'::jsonb;
alter table words add constraint words_aspect_check check (
  (type = 'verb' and aspect is not null and aspect in ('imperfective', 'perfective', 'both'))
  or (type <> 'verb' and aspect is null)
);

create unique index words_external_id_uidx on words (external_id) where external_id is not null;
create index words_russian_idx on words (russian);

alter table words drop constraint if exists words_type_check;
alter table words alter column type set not null;
alter table words add constraint words_type_check check (type in (
  'adjective', 'adverb', 'expression', 'noun', 'other', 'pronoun', 'verb'
));

create table example_sentences
(
  id          uuid primary key,
  russian     text not null,
  translations jsonb not null default '[]'::jsonb,
  contributor varchar(255),
  audio_url   varchar(2048),
  checksum    varchar(64),
  created_at  timestamp(6) with time zone,
  updated_at  timestamp(6) with time zone,
  created_by  varchar(255),
  updated_by  varchar(255)
);

create table word_examples
(
  example_id uuid not null references example_sentences (id) on delete cascade,
  word_id    uuid not null references words (id) on delete cascade,
  primary key (example_id, word_id)
);
create index word_examples_word_idx on word_examples (word_id, example_id);

create table word_relations
(
  id             uuid primary key,
  source_word_id uuid not null references words (id) on delete cascade,
  target_word_id uuid not null references words (id) on delete cascade,
  relation       varchar(16) not null check (relation in ('related', 'synonym', 'antonym')),
  check (source_word_id <> target_word_id),
  unique (source_word_id, target_word_id, relation)
);
create index word_relations_target_idx on word_relations (target_word_id, source_word_id);
