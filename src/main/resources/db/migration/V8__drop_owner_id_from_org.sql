-- PostgreSQL automatically names the constraint based on the table and column
ALTER TABLE organizations DROP CONSTRAINT organizations_owner_id_fkey;

-- Now drop the column to break the circular dependency
ALTER TABLE organizations DROP COLUMN owner_id;