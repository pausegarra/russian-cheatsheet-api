ALTER TABLE words ADD COLUMN russian_accented TEXT;
UPDATE words SET checksum = NULL WHERE external_id IS NOT NULL;
