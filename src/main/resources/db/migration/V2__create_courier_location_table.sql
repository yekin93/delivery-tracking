CREATE TABLE courier_locations(
	id BIGSERIAL PRIMARY KEY,
	courier_id BIGINT NOT NULL,
	latitude NUMERIC(10,7) NOT NULL,
	longitude NUMERIC(10,7) NOT NULL,
	speed NUMERIC(6,2),
	heading NUMERIC(6,2),
	accuracy NUMERIC(6,2),
	recorded_at TIMESTAMP WITH TIME ZONE NOT NULL,
	created_at TIMESTAMP WITH TIME ZONE NOT NULL,
	
	CONSTRAINT FK_courier_locations_courier
			FOREIGN KEY (courier_id)
			REFERENCES couriers(id)
);


CREATE INDEX idx_courier_locations_courier_recorded_at
ON courier_locations(courier_id, recorded_at);