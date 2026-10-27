-- V4__username_format_check.sql
ALTER TABLE users
    ADD CONSTRAINT ck_users_username
    CHECK (username ~ '^[A-Za-z][A-Za-z0-9_]{2,49}$');