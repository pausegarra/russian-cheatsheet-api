create table words
(
    created_at         timestamp(6) with time zone,
    published_at       timestamp(6) with time zone,
    updated_at         timestamp(6) with time zone,
    id                 uuid not null,
    conjugations       jsonb,
    created_by         varchar(255),
    declination_matrix jsonb,
    declinations       jsonb,
    english            varchar(255),
    russian            varchar(255) unique,
    spanish            varchar(255),
    type               varchar(255) check ((type in
                                            ('NOUN', 'PRONOUN', 'VERB', 'ADJECTIVE', 'ADVERB', 'NUMERAL', 'ORDINAL',
                                             'PREPOSITION', 'CONJUNCTION', 'PARTICLE', 'INTERJECTION', 'GERUND',
                                             'PARTICIPLE', 'PREFIX', 'SUFFIX', 'ROOT', 'LETTER', 'SENTENCE', 'OTHER', 'SHORT_ADJECTIVE', 'COMPARATIVE', 'PREDICATIVE'))),
    updated_by         varchar(255),
    primary key (id)
);
create index words_russian_english_spanish_idx on words (russian, spanish, english);
