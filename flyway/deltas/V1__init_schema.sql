CREATE TABLE users (
  id                    BIGSERIAL PRIMARY KEY,
  username              VARCHAR     NOT NULL UNIQUE,
  password              VARCHAR(64) NOT NULL,
  firstname             VARCHAR,
  lastname              VARCHAR,
  birth_date            TIMESTAMP,
  gender                VARCHAR,
  phone                 VARCHAR,
  email                 VARCHAR,
  pull_up_date          TIMESTAMP   NOT NULL DEFAULT (current_timestamp AT TIME ZONE 'UTC'),
  gender_preferences    VARCHAR,
  relations_preferences VARCHAR
);

CREATE TABLE user_filters (
  user_id               BIGINT REFERENCES users (id),
  gender                VARCHAR,
  gender_preferences    VARCHAR,
  relations_preferences VARCHAR,
  age_lower             INT,
  age_higher            INT,
  PRIMARY KEY (user_id)
);

CREATE TABLE news_categories (
  id   BIGSERIAL PRIMARY KEY,
  name VARCHAR NOT NULL UNIQUE
);

CREATE TABLE news (
  id            BIGSERIAL PRIMARY KEY,
  title         VARCHAR   NOT NULL,
  body          TEXT,
  creation_date TIMESTAMP NOT NULL DEFAULT (current_timestamp AT TIME ZONE 'UTC'),
  location      VARCHAR,
  author        BIGSERIAL NOT NULL REFERENCES users (id),
  category_id   BIGSERIAL NOT NULL REFERENCES news_categories (id)
);

CREATE TABLE subscriptions (
  subscriber_id BIGSERIAL REFERENCES users (id),
  author_id     BIGSERIAL REFERENCES users (id),
  PRIMARY KEY (subscriber_id, author_id)
);

CREATE TABLE favourites (
  user_id BIGSERIAL REFERENCES users (id),
  news_id BIGSERIAL REFERENCES news (id),
  PRIMARY KEY (user_id, news_id)
);

CREATE TABLE likes (
  id      BIGSERIAL PRIMARY KEY,
  news_id BIGSERIAL REFERENCES news (id),
  liker   BIGSERIAL REFERENCES users (id)
);

CREATE TABLE comments (
  id            BIGSERIAL PRIMARY KEY,
  body          TEXT      NOT NULL,
  creation_date TIMESTAMP NOT NULL DEFAULT (current_timestamp AT TIME ZONE 'UTC'),
  news_id       BIGSERIAL NOT NULL REFERENCES news (id),
  author        BIGSERIAL NOT NULL REFERENCES users (id)
);

CREATE TABLE images (
  id           BIGSERIAL PRIMARY KEY,
  content_type VARCHAR NOT NULL,
  ordering     INT,
  news_id      BIGINT REFERENCES news (id),
  comment_id   BIGINT REFERENCES comments (id),
  user_id      BIGINT REFERENCES users (id)
);
