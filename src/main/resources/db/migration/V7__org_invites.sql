-- V7__org_invites.sql
CREATE TABLE org_invites (
    id               BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    organization_id  BIGINT       NOT NULL REFERENCES organizations(id) ON DELETE CASCADE,
    code_hash        VARCHAR(64)  NOT NULL,
    created_by       BIGINT       NOT NULL REFERENCES users(id),
    expires_at       TIMESTAMPTZ  NOT NULL,
    max_uses         INTEGER,
    use_count        INTEGER      NOT NULL DEFAULT 0,
    revoked          BOOLEAN      NOT NULL DEFAULT FALSE,
    created_at       TIMESTAMPTZ  NOT NULL DEFAULT now(),
    CONSTRAINT uq_org_invites_code_hash UNIQUE (code_hash),
    CONSTRAINT ck_org_invites_max_uses  CHECK (max_uses IS NULL OR max_uses > 0),
    CONSTRAINT ck_org_invites_use_count CHECK (use_count >= 0)
);

CREATE INDEX idx_org_invites_org ON org_invites (organization_id, created_at DESC);