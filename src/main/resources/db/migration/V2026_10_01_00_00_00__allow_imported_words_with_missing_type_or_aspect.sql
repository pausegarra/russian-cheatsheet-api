ALTER TABLE words
  ALTER COLUMN type DROP NOT NULL;

ALTER TABLE words
  DROP CONSTRAINT IF EXISTS words_type_check;

ALTER TABLE words
  ADD CONSTRAINT words_type_check CHECK (
    CASE
      WHEN type IS NULL THEN external_id IS NOT NULL
      ELSE type IN ('adjective', 'adverb', 'expression', 'noun', 'other', 'pronoun', 'verb')
    END
  );

ALTER TABLE words
  DROP CONSTRAINT IF EXISTS words_aspect_check;

ALTER TABLE words
  ADD CONSTRAINT words_aspect_check CHECK (
    CASE
      WHEN type IS NULL THEN external_id IS NOT NULL AND aspect IS NULL AND forms IS NULL
      WHEN type = 'verb' THEN
        (aspect IS NULL AND external_id IS NOT NULL)
        OR (aspect IS NOT NULL AND aspect IN ('imperfective', 'perfective', 'both'))
      ELSE aspect IS NULL
    END
  );
