CREATE TABLE roles (
	id BIGSERIAL PRIMARY KEY,
	name VARCHAR(50) NOT NULL UNIQUE
);

INSERT INTO roles (name) VALUES ('ADMIN'),
							('COURIER'),
							('RESTAURANT'),
							('CUSTOMER');


CREATE TABLE user_roles(
	user_id BIGINT NOT NULL,
	role_id BIGINT NOT NULL,
	
	CONSTRAINT pk_user_roles PRIMARY KEY (user_id, role_id),
	
	CONSTRAINT FK_user_roles_user_id 
		FOREIGN KEY (user_id)
		REFERENCES users(id)
		ON DELETE CASCADE,
	
	CONSTRAINT FK_user_roles_role_id
		FOREIGN KEY (role_id)
		REFERENCES roles(id)
		ON DELETE RESTRICT
);
