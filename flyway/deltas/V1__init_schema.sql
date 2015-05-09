CREATE TABLE "user" (
  id bigserial PRIMARY KEY,
  username VARCHAR UNIQUE,
  password VARCHAR(32),
  firstname VARCHAR,
  lastname VARCHAR,
  birthdate TIMESTAMP,
  gender VARCHAR,
  phonenumber VARCHAR,
  email VARCHAR
);