-- Create ENUM types first
CREATE TYPE user_role AS ENUM ('STUDENT', 'INSTRUCTOR', 'ADMIN');
CREATE TYPE auth_provider AS ENUM ('EMAIL', 'GOOGLE', 'FACEBOOK', 'APPLE');
CREATE TYPE course_status AS ENUM ('DRAFT', 'PUBLISHED');
CREATE TYPE content_type AS ENUM ('VIDEO', 'ASSIGNMENT', 'ATTACHMENT', 'QUIZ');
CREATE TYPE question_type AS ENUM ('MCQ', 'TEXT', 'BOOLEAN');

-- Users Table
CREATE TABLE users
(
    id              BIGSERIAL PRIMARY KEY,
    email           VARCHAR(255) UNIQUE,
    role            user_role NOT NULL,
    profile_details JSONB,
    created_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create trigger for updated_at
CREATE
OR REPLACE FUNCTION update_timestamp()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at
= CURRENT_TIMESTAMP;
RETURN NEW;
END;
$$
language 'plpgsql';

CREATE TRIGGER update_users_timestamp
    BEFORE UPDATE
    ON users
    FOR EACH ROW
    EXECUTE FUNCTION update_timestamp();

-- Authentication Table
CREATE TABLE authentication
(
    id               BIGSERIAL PRIMARY KEY,
    user_id          BIGINT        NOT NULL,
    auth_provider    auth_provider NOT NULL,
    provider_user_id VARCHAR(255) UNIQUE,
    password_hash    VARCHAR(255),
    created_at       TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- RefreshTokens Table
CREATE TABLE refresh_tokens
(
    id            BIGSERIAL PRIMARY KEY,
    user_id       BIGINT              NOT NULL,
    refresh_token VARCHAR(255) UNIQUE NOT NULL,
    expires_at    TIMESTAMP           NOT NULL,
    created_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Courses Table
CREATE TABLE courses
(
    id            BIGSERIAL PRIMARY KEY,
    title         VARCHAR(255)   NOT NULL,
    description   TEXT,
    price         DECIMAL(10, 2) NOT NULL DEFAULT 0.00,
    instructor_id BIGINT         NOT NULL,
    status        course_status  NOT NULL,
    created_at    TIMESTAMP               DEFAULT CURRENT_TIMESTAMP,
    updated_at    TIMESTAMP               DEFAULT CURRENT_TIMESTAMP
);

CREATE TRIGGER update_courses_timestamp
    BEFORE UPDATE
    ON courses
    FOR EACH ROW
    EXECUTE FUNCTION update_timestamp();

-- Modules Table
CREATE TABLE modules
(
    id          BIGSERIAL PRIMARY KEY,
    course_id   BIGINT       NOT NULL,
    title       VARCHAR(255) NOT NULL,
    "order"     INT          NOT NULL,
    description TEXT,
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Content Table
CREATE TABLE content
(
    id         BIGSERIAL PRIMARY KEY,
    module_id  BIGINT       NOT NULL,
    type       content_type NOT NULL,
    title      VARCHAR(255) NOT NULL,
    url        VARCHAR(255),
    metadata   JSONB,
    "order"    INT          NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Assignments Table
CREATE TABLE assignments
(
    id           BIGSERIAL PRIMARY KEY,
    content_id   BIGINT NOT NULL,
    instructions TEXT   NOT NULL,
    due_date     TIMESTAMP,
    max_score    INT    NOT NULL,
    created_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Attachments Table
CREATE TABLE attachments
(
    id         BIGSERIAL PRIMARY KEY,
    content_id BIGINT       NOT NULL,
    file_url   VARCHAR(255) NOT NULL,
    file_name  VARCHAR(255),
    file_type  VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Quizzes Table
CREATE TABLE quizzes
(
    id              BIGSERIAL PRIMARY KEY,
    content_id      BIGINT       NOT NULL,
    title           VARCHAR(255) NOT NULL,
    total_questions INT          NOT NULL,
    created_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Questions Table
CREATE TABLE questions
(
    id             BIGSERIAL PRIMARY KEY,
    quiz_id        BIGINT        NOT NULL,
    question_text  TEXT          NOT NULL,
    type           question_type NOT NULL,
    options        JSONB,
    correct_answer JSONB         NOT NULL
);
