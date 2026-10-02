UPDATE words
SET checksum = NULL
WHERE external_id IS NOT NULL;
