CREATE TABLE IF NOT EXISTS user_roles
(
    user_id uuid NOT NULL,
    roles_id uuid NOT NULL,
    CONSTRAINT user_roles_pkey PRIMARY KEY (user_id, roles_id),
    CONSTRAINT fkdbv8tdyltxa1qjmfnj9oboxse FOREIGN KEY (roles_id)
        REFERENCES roles (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fkhfh9dx7w3ubf1co1vdev94g3f FOREIGN KEY (user_id)
        REFERENCES users (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);