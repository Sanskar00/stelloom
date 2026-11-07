ALTER TABLE organizations
    ADD COLUMN owner_id   BIGINT REFERENCES users(id),
    ADD COLUMN status     VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
    ADD COLUMN deleted_at TIMESTAMPTZ;

ALTER TABLE organizations
    ADD CONSTRAINT ck_org_status CHECK (status IN ('ACTIVE', 'PENDING_DELETION'));