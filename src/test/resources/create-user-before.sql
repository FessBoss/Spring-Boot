delete from user_role;
delete from users;

insert into users(id, active, password, username) values
    (1, true, '$2y$08$OBTd9t8K1SfSqaO/oFiPSOgp/Bxdm7HaNfRwkYh5uLHGF7rlMzldu', 'admin'),
    (2, true, '$2a$08$v8lEmxuq4JLbQhGnbuFJ5ekVyraBnkKMKBBmCIh8MF1g3QF0uHQNa', 'user');

insert into user_role(user_id, roles) values
    (1, 'USER'), (1, 'ADMIN'),
    (2, 'USER');