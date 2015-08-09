CREATE TABLE ads_categories (
  id   BIGSERIAL PRIMARY KEY,
  name VARCHAR NOT NULL
);

CREATE TABLE ads (
  id            BIGSERIAL PRIMARY KEY,
  title         VARCHAR   NOT NULL,
  description   TEXT      NOT NULL,
  ads_type      VARCHAR   NOT NULL,
  creation_date TIMESTAMP NOT NULL DEFAULT (current_timestamp AT TIME ZONE 'UTC'),
  phone         VARCHAR,
  email         VARCHAR,
  city          VARCHAR,
  price         BIGINT,
  author        BIGINT REFERENCES users (id),
  category_id   BIGINT REFERENCES ads_categories (id)
);

ALTER TABLE images
ADD COLUMN ads_id BIGINT REFERENCES ads (id);
