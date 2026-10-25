-- USERS
CREATE TABLE users (
    id             BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    username       VARCHAR(50)  NOT NULL,
    email          VARCHAR(255) NOT NULL,
    password_hash  VARCHAR(255) NOT NULL,
    full_name      VARCHAR(100) NOT NULL,
    avatar_key     VARCHAR(500),
    role           VARCHAR(20)  NOT NULL DEFAULT 'EMPLOYEE',
    created_at     TIMESTAMPTZ  NOT NULL DEFAULT now(),
    updated_at     TIMESTAMPTZ  NOT NULL DEFAULT now(),
    CONSTRAINT uq_users_username UNIQUE (username),
    CONSTRAINT uq_users_email    UNIQUE (email),
    CONSTRAINT ck_users_role     CHECK (role IN ('EMPLOYEE', 'ADMIN'))
);

-- CHAT ROOMS (direct and group)
CREATE TABLE chat_rooms (
    id          BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    type        VARCHAR(10)  NOT NULL,
    name        VARCHAR(100),            -- null for DIRECT rooms
    direct_key  VARCHAR(50),             -- "smallerUserId_largerUserId" for DIRECT rooms
    created_by  BIGINT       NOT NULL REFERENCES users(id),
    created_at  TIMESTAMPTZ  NOT NULL DEFAULT now(),
    CONSTRAINT ck_rooms_type   CHECK (type IN ('DIRECT', 'GROUP')),
    CONSTRAINT ck_rooms_direct CHECK (
        (type = 'DIRECT' AND direct_key IS NOT NULL) OR
        (type = 'GROUP'  AND direct_key IS NULL AND name IS NOT NULL)
    ),
    CONSTRAINT uq_rooms_direct_key UNIQUE (direct_key)
);

-- ROOM MEMBERS
CREATE TABLE chat_room_members (
    room_id       BIGINT      NOT NULL REFERENCES chat_rooms(id) ON DELETE CASCADE,
    user_id       BIGINT      NOT NULL REFERENCES users(id)      ON DELETE CASCADE,
    role          VARCHAR(10) NOT NULL DEFAULT 'MEMBER',
    joined_at     TIMESTAMPTZ NOT NULL DEFAULT now(),
    last_read_at  TIMESTAMPTZ,           -- for unread counts / read receipts later
    PRIMARY KEY (room_id, user_id),
    CONSTRAINT ck_members_role CHECK (role IN ('MEMBER', 'ADMIN'))
);
CREATE INDEX idx_members_user ON chat_room_members (user_id);

-- MESSAGES
CREATE TABLE messages (
    id                  BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    room_id             BIGINT      NOT NULL REFERENCES chat_rooms(id) ON DELETE CASCADE,
    sender_id           BIGINT      NOT NULL REFERENCES users(id),
    content             TEXT,
    media_key           VARCHAR(500),    -- S3 object key, never a full URL
    media_content_type  VARCHAR(100),
    created_at          TIMESTAMPTZ NOT NULL DEFAULT now(),
    CONSTRAINT ck_messages_body CHECK (content IS NOT NULL OR media_key IS NOT NULL)
);
-- serves "latest N messages in a room" and cursor pagination
CREATE INDEX idx_messages_room_cursor ON messages (room_id, created_at DESC, id DESC);

-- POSTS (feed)
CREATE TABLE posts (
    id                  BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    author_id           BIGINT      NOT NULL REFERENCES users(id),
    type                VARCHAR(20) NOT NULL DEFAULT 'POST',
    content             TEXT        NOT NULL,
    media_key           VARCHAR(500),
    media_content_type  VARCHAR(100),
    pinned              BOOLEAN     NOT NULL DEFAULT FALSE,
    like_count          INTEGER     NOT NULL DEFAULT 0,
    comment_count       INTEGER     NOT NULL DEFAULT 0,
    created_at          TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at          TIMESTAMPTZ NOT NULL DEFAULT now(),
    CONSTRAINT ck_posts_type CHECK (type IN ('POST', 'ANNOUNCEMENT'))
);
CREATE INDEX idx_posts_feed   ON posts (created_at DESC, id DESC);
CREATE INDEX idx_posts_author ON posts (author_id);
CREATE INDEX idx_posts_pinned ON posts (created_at DESC) WHERE pinned;

-- COMMENTS
CREATE TABLE comments (
    id          BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    post_id     BIGINT      NOT NULL REFERENCES posts(id) ON DELETE CASCADE,
    user_id     BIGINT      NOT NULL REFERENCES users(id),
    content     TEXT        NOT NULL,
    created_at  TIMESTAMPTZ NOT NULL DEFAULT now()
);
CREATE INDEX idx_comments_post ON comments (post_id, created_at, id);

-- LIKES
CREATE TABLE likes (
    post_id     BIGINT      NOT NULL REFERENCES posts(id) ON DELETE CASCADE,
    user_id     BIGINT      NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    created_at  TIMESTAMPTZ NOT NULL DEFAULT now(),
    PRIMARY KEY (post_id, user_id)       -- one like per user per post
);
CREATE INDEX idx_likes_user ON likes (user_id);