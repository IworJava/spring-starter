ALTER TABLE users
ADD COLUMN created_at TIMESTAMP,
ADD COLUMN modified_at TIMESTAMP;

ALTER TABLE users
ADD COLUMN created_by VARCHAR(64),
ADD COLUMN modified_by VARCHAR(64);