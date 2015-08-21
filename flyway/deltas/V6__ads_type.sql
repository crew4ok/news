CREATE TABLE ads_types (
  id BIGSERIAL PRIMARY KEY,
  name VARCHAR,
  category_id BIGINT REFERENCES ads_categories (id)
);

ALTER TABLE ads
  ALTER COLUMN ads_type TYPE BIGINT USING NULL,
  ADD FOREIGN KEY (ads_type) REFERENCES ads_types (id);

ALTER TABLE ads
    ALTER COLUMN subcategory_id SET NOT NULL,
    ALTER COLUMN author SET NOT NULL;
