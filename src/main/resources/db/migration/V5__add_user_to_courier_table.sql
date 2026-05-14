ALTER TABLE couriers ADD COLUMN user_id BIGINT NOT NULL;

ALTER TABLE couriers
ADD CONSTRAINT fk_couriers_user
FOREIGN KEY (user_id)
REFERENCES users(id)