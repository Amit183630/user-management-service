ALTER TABLE roles RENAME COLUMN role TO name;
ALTER TABLE roles ADD CONSTRAINT role_name_unique_const UNIQUE (name);