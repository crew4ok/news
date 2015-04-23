CREATE TABLE "user" (
  id bigserial PRIMARY KEY,
  username VARCHAR UNIQUE,
  password VARCHAR(32)
);