CREATE TABLE user (
  user_id INT AUTO_INCREMENT  NOT NULL ,
  login VARCHAR(50) UNIQUE,
  email VARCHAR(50) UNIQUE,
  first_name VARCHAR(50) NOT NULL,
  last_name VARCHAR(50) NOT NULL,
  password VARCHAR(100) NOT NULL,
  enabled TINYINT NOT NULL DEFAULT 1,
  PRIMARY KEY (user_id)
);

CREATE TABLE roles (
  role VARCHAR(50) UNIQUE,
  PRIMARY KEY (role)
);

CREATE TABLE user_role (
    id INT AUTO_INCREMENT,
	user_id INT NOT NULL,
	role VARCHAR(50) NOT NULL,
    PRIMARY KEY (id),
	FOREIGN KEY (user_id) REFERENCES user(user_id)
	ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (role) REFERENCES roles(role)
	ON DELETE CASCADE ON UPDATE CASCADE,
    UNIQUE `user_role_index`(`user_id`, `role`)
);

insert into roles
(role)
values
('ADMIN'),('USER');

insert into user
(login, email, first_name, last_name, password)
values
('admin', 'admin@test.com','Admin', 'Admin', '$2a$12$v7Sk5Hbf1jxA2tw.3hOahONs0DEzYOo4bqAYdIJJMILAZNt.VQk0q'),
('user', 'user@test.com', 'User', 'User', '$2a$12$v7Sk5Hbf1jxA2tw.3hOahONs0DEzYOo4bqAYdIJJMILAZNt.VQk0q');

insert into user_role
(user_id, role)
values
((select user.user_id from user where login = 'admin' LIMIT 1), 'ADMIN'),
((select user.user_id from user where login = 'user' LIMIT 1), 'USER');

