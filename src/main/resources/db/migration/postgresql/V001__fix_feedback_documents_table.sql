-- Drop uuid column from feedback_documents table if it exists
ALTER TABLE feedback_documents DROP COLUMN IF EXISTS uuid;
