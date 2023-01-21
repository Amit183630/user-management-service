CREATE TABLE IF NOT EXISTS roles
(
    id uuid NOT NULL,
    role character varying(20) COLLATE pg_catalog."default",
    CONSTRAINT roles_pkey PRIMARY KEY (id)
);