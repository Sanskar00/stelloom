ALTER TABLE chat_room_members DROP CONSTRAINT chat_room_members_pkey;
ALTER TABLE chat_room_members
    ADD COLUMN id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY;

ALTER TABLE chat_room_members
    ADD CONSTRAINT uq_members_room_user UNIQUE (room_id, user_id);

ALTER TABLE likes DROP CONSTRAINT likes_pkey;

ALTER TABLE likes
    ADD COLUMN id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY;

ALTER TABLE likes
    ADD CONSTRAINT uq_likes_post_user UNIQUE (post_id, user_id);

CREATE INDEX idx_likes_post_created ON likes (post_id, created_at DESC);
