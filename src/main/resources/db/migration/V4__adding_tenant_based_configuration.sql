CREATE TABLE organizations (
    id          BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name        VARCHAR(100) NOT NULL,
    slug        VARCHAR(50)  NOT NULL UNIQUE,
    created_at  TIMESTAMPTZ  NOT NULL DEFAULT now()
);

-- Backfill: put all existing rows into one default organization
INSERT INTO organizations (name, slug) VALUES ('Default', 'default');

ALTER TABLE users      ADD COLUMN organization_id BIGINT REFERENCES organizations(id);
ALTER TABLE chat_rooms ADD COLUMN organization_id BIGINT REFERENCES organizations(id);
ALTER TABLE posts      ADD COLUMN organization_id BIGINT REFERENCES organizations(id);

UPDATE users      SET organization_id = (SELECT id FROM organizations WHERE slug = 'default');
UPDATE chat_rooms SET organization_id = (SELECT id FROM organizations WHERE slug = 'default');
UPDATE posts      SET organization_id = (SELECT id FROM organizations WHERE slug = 'default');

ALTER TABLE users      ALTER COLUMN organization_id SET NOT NULL;
ALTER TABLE chat_rooms ALTER COLUMN organization_id SET NOT NULL;
ALTER TABLE posts      ALTER COLUMN organization_id SET NOT NULL;

-- Indexes that lead with the tenant
CREATE INDEX idx_users_org ON users (organization_id);
CREATE INDEX idx_posts_org_feed ON posts (organization_id, created_at DESC, id DESC);
CREATE INDEX idx_rooms_org ON chat_rooms (organization_id);