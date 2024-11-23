CREATE DATABASE IF NOT EXISTS orbit;

USE orbit;

DROP TABLE IF EXISTS interest_job;
DROP TABLE IF EXISTS resume;
DROP TABLE IF EXISTS quest;
DROP TABLE IF EXISTS member_goal;
DROP TABLE IF EXISTS goal;
DROP TABLE IF EXISTS schedule;
DROP TABLE IF EXISTS job;
DROP TABLE IF EXISTS member;

-- member 테이블을 먼저 생성
CREATE TABLE member
(
    member_id       BIGINT        NOT NULL AUTO_INCREMENT PRIMARY KEY,
    nickname        VARCHAR(100)  NOT NULL,
    kakao_nickname        VARCHAR(100)  NOT NULL,
    image_url       VARCHAR(500)  NOT NULL,
    known_prompt    VARCHAR(1000) NOT NULL DEFAULT '',
    help_prompt     VARCHAR(1000) NOT NULL DEFAULT '',
    is_notification BOOLEAN       NOT NULL DEFAULT FALSE,
    is_profile      BOOLEAN       NOT NULL DEFAULT FALSE,
    ai_feedback     TEXT
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

-- 그 후 다른 테이블을 생성
CREATE TABLE job
(
    job_id   BIGINT      NOT NULL AUTO_INCREMENT PRIMARY KEY,
    category VARCHAR(50) NOT NULL,
    name     VARCHAR(50) NOT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

CREATE TABLE interest_job
(
    job_id    BIGINT NOT NULL,
    member_id BIGINT NOT NULL,
    PRIMARY KEY (job_id, member_id),
    CONSTRAINT fk_interest_job_job FOREIGN KEY (job_id) REFERENCES job (job_id),
    CONSTRAINT fk_interest_job_member FOREIGN KEY (member_id) REFERENCES member (member_id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

CREATE TABLE resume
(
    resume_id  BIGINT      NOT NULL AUTO_INCREMENT PRIMARY KEY,
    title      VARCHAR(50) NOT NULL,
    category   VARCHAR(50) NOT NULL,
    content    VARCHAR(50) NOT NULL,
    start_date DATE,
    end_date   DATE,
    member_id  BIGINT      NOT NULL,
    CONSTRAINT fk_resume_member FOREIGN KEY (member_id) REFERENCES member (member_id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

CREATE TABLE goal
(
    goal_id  BIGINT      NOT NULL AUTO_INCREMENT PRIMARY KEY,
    title    VARCHAR(50) NOT NULL,
    category VARCHAR(50) NOT NULL,
    count    INT         NOT NULL DEFAULT 0
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

CREATE TABLE member_goal
(
    member_goal_id BIGINT  NOT NULL AUTO_INCREMENT PRIMARY KEY,
    member_id      BIGINT  NOT NULL,
    created_at  DATETIME    NOT NULL,
    goal_id        BIGINT  NOT NULL,
    is_complete    BOOLEAN NOT NULL DEFAULT FALSE,
    CONSTRAINT member_goal_member FOREIGN KEY (member_id) REFERENCES member (member_id),
    CONSTRAINT member_goal_goal FOREIGN KEY (goal_id) REFERENCES goal (goal_id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

CREATE TABLE quest
(
    quest_id    BIGINT      NOT NULL AUTO_INCREMENT PRIMARY KEY,
    title       VARCHAR(50) NOT NULL,
    is_complete BOOLEAN     NOT NULL DEFAULT FALSE,
    created_at  DATETIME    NOT NULL,
    deadline    DATE        NOT NULL,
    sequence    INT         NOT NULL,
    member_goal_id   BIGINT      NOT NULL,
    CONSTRAINT fk_quest_member_goal FOREIGN KEY (member_goal_id) REFERENCES member_goal (member_goal_id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

CREATE TABLE schedule
(
    schedule_id BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY,
    content     VARCHAR(100) NOT NULL,
    start_date  DATE         NOT NULL,
    end_date    DATE         NOT NULL,
    member_id   BIGINT       NOT NULL,
    CONSTRAINT schedule_member FOREIGN KEY (member_id) REFERENCES member (member_id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;
