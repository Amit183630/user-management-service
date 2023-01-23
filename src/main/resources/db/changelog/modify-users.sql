ALTER TABLE users 
ADD COLUMN created_at timestamp(6) without time zone NOT NULL,
ADD COLUMN updated_at timestamp(6) without time zone NOT NULL,
ADD COLUMN confirmation_token character varying(200) NOT NULL,
add COLUMN enabled boolean NOT NULL;
