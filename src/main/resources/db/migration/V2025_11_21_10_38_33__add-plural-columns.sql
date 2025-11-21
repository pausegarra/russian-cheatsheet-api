alter table if exists word_declinations
    add column nominative_plural varchar(255),
    add column accusative_plural varchar(255),
    add column genitive_plural varchar(255),
    add column dative_plural varchar(255),
    add column instrumental_plural varchar(255),
    add column prepositional_plural varchar(255);