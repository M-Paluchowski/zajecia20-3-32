INSERT INTO user(username, password, enabled)
VALUES ('admin', '{noop}admin', true), ('user', '{noop}user', true);

INSERT INTO user_role(username, role)
VALUES ('admin', 'ROLE_ADMIN'), ('user', 'ROLE_USER');