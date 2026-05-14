CREATE TABLE applications (
	id BIGSERIAL NOT NULL PRIMARY KEY,
	user_id BIGINT NOT NULL,
	type VARCHAR(50) NOT NULL,
	status VARCHAR(30) NOT NULL,
	applicant_name VARCHAR(100),
	business_name VARCHAR(100),
	email VARCHAR(150) NOT NULL,
	phone VARCHAR(30) NOT NULL,
	message TEXT,
	reviewed_by BIGINT,
	reviewed_at TIMESTAMP WITH TIME ZONE,
	created_at TIMESTAMP WITH TIME ZONE NOT NULL,
	updated_at TIMESTAMP WITH TIME ZONE NOT NULL,

	CONSTRAINT fk_applications_user
				FOREIGN KEY (user_id)
				REFERENCES users(id),
				
	CONSTRAINT fk_applications_reviwer
				FOREIGN KEY (reviewed_by)
				REFERENCES users(id)
	
);