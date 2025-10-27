create table word_conjugations
(
    created_at                                  timestamp(6) with time zone,
    updated_at                                  timestamp(6) with time zone,
    id                                          uuid not null,
    word_id                                     uuid unique,
    created_by                                  varchar(255),
    imperfective_future_first_person_plural     varchar(255),
    imperfective_future_first_person_singular   varchar(255),
    imperfective_future_second_person_plural    varchar(255),
    imperfective_future_second_person_singular  varchar(255),
    imperfective_future_third_person_plural     varchar(255),
    imperfective_future_third_person_singular   varchar(255),
    imperfective_past_feminine                  varchar(255),
    imperfective_past_masculine                 varchar(255),
    imperfective_past_neuter                    varchar(255),
    imperfective_past_plural                    varchar(255),
    imperfective_present_first_person_plural    varchar(255),
    imperfective_present_first_person_singular  varchar(255),
    imperfective_present_second_person_plural   varchar(255),
    imperfective_present_second_person_singular varchar(255),
    imperfective_present_third_person_plural    varchar(255),
    imperfective_present_third_person_singular  varchar(255),
    perfective_future_first_person_plural       varchar(255),
    perfective_future_first_person_singular     varchar(255),
    perfective_future_second_person_plural      varchar(255),
    perfective_future_second_person_singular    varchar(255),
    perfective_future_third_person_plural       varchar(255),
    perfective_future_third_person_singular     varchar(255),
    perfective_past_feminine                    varchar(255),
    perfective_past_masculine                   varchar(255),
    perfective_past_neuter                      varchar(255),
    perfective_past_plural                      varchar(255),
    updated_by                                  varchar(255),
    primary key (id)
);
create table word_declination_matrix
(
    created_at              timestamp(6) with time zone,
    updated_at              timestamp(6) with time zone,
    id                      uuid not null,
    word_id                 uuid unique,
    accusative_feminine     varchar(255),
    accusative_masculine    varchar(255),
    accusative_neuter       varchar(255),
    accusative_plural       varchar(255),
    created_by              varchar(255),
    dative_feminine         varchar(255),
    dative_masculine        varchar(255),
    dative_neuter           varchar(255),
    dative_plural           varchar(255),
    genitive_feminine       varchar(255),
    genitive_masculine      varchar(255),
    genitive_neuter         varchar(255),
    genitive_plural         varchar(255),
    instrumental_feminine   varchar(255),
    instrumental_masculine  varchar(255),
    instrumental_neuter     varchar(255),
    instrumental_plural     varchar(255),
    nominative_feminine     varchar(255),
    nominative_masculine    varchar(255),
    nominative_neuter       varchar(255),
    nominative_plural       varchar(255),
    prepositional_feminine  varchar(255),
    prepositional_masculine varchar(255),
    prepositional_neuter    varchar(255),
    prepositional_plural    varchar(255),
    updated_by              varchar(255),
    primary key (id)
);
create table word_declinations
(
    created_at    timestamp(6) with time zone,
    updated_at    timestamp(6) with time zone,
    id            uuid not null,
    word_id       uuid unique,
    accusative    varchar(255),
    created_by    varchar(255),
    dative        varchar(255),
    genitive      varchar(255),
    instrumental  varchar(255),
    nominative    varchar(255),
    prepositional varchar(255),
    updated_by    varchar(255),
    primary key (id)
);
create table words
(
    created_at timestamp(6) with time zone,
    updated_at timestamp(6) with time zone,
    id         uuid not null,
    created_by varchar(255),
    english    varchar(255),
    russian    varchar(255),
    spanish    varchar(255),
    type       varchar(255) check (type in ('NOUN', 'PRONOUN', 'VERB', 'ADJECTIVE', 'ADVERB', 'NUMERAL', 'ORDINAL',
                                            'PREPOSITION', 'CONJUNCTION', 'PARTICLE', 'INTERJECTION', 'GERUND',
                                            'PARTICIPLE', 'PREFIX', 'SUFFIX', 'ROOT', 'OTHER')),
    updated_by varchar(255),
    primary key (id)
);
alter table if exists word_conjugations
    add constraint FKa00s4dyjsttk7c100ra17p4eq foreign key (word_id) references words;
alter table if exists word_declination_matrix
    add constraint FKf8l0b6js0rxa3mma74n351bje foreign key (word_id) references words;
alter table if exists word_declinations
    add constraint FKjogrlkrten7h4g5i8xm0fxiet foreign key (word_id) references words;
