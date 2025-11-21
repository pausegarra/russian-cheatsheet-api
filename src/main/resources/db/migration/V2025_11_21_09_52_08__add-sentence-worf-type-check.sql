ALTER TABLE words
    DROP CONSTRAINT IF EXISTS words_type_check;

ALTER TABLE words
    ADD CONSTRAINT words_type_check
        CHECK (type IN ('NOUN', 'PRONOUN', 'VERB', 'ADJECTIVE', 'ADVERB', 'NUMERAL', 'ORDINAL',
                        'PREPOSITION', 'CONJUNCTION', 'PARTICLE', 'INTERJECTION', 'GERUND',
                        'PARTICIPLE', 'PREFIX', 'SUFFIX', 'ROOT', 'LETTER', 'SENTENCE', 'OTHER'));