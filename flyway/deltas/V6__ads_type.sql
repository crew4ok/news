CREATE TABLE ads_types (
  id BIGSERIAL PRIMARY KEY,
  name VARCHAR,
  category_id BIGINT REFERENCES ads_categories (id)
);

DELETE FROM ads;

ALTER TABLE ads
  ALTER COLUMN ads_type DROP NOT NULL,
  ALTER COLUMN ads_type TYPE BIGINT USING NULL,
  ALTER COLUMN ads_type SET NOT NULL,
  ADD FOREIGN KEY (ads_type) REFERENCES ads_types (id);