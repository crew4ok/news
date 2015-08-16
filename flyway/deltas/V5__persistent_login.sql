CREATE TABLE persistent_user_sessions (
  id BIGSERIAL PRIMARY KEY,
  token VARCHAR(64) NOT NULL UNIQUE,
  user_id BIGINT NOT NULL REFERENCES users (id)
);