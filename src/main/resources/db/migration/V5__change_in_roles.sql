-- organization-level roles
ALTER TABLE users DROP CONSTRAINT ck_users_role;
UPDATE users SET role = 'ORG_ADMIN' WHERE role = 'ADMIN';
ALTER TABLE users
    ADD CONSTRAINT ck_users_role CHECK (role IN ('EMPLOYEE', 'ORG_ADMIN'));

-- room-level roles
ALTER TABLE chat_room_members DROP CONSTRAINT ck_members_role;
UPDATE chat_room_members SET role = 'MODERATOR' WHERE role = 'ADMIN';
ALTER TABLE chat_room_members
    ADD CONSTRAINT ck_members_role CHECK (role IN ('MEMBER', 'MODERATOR'));