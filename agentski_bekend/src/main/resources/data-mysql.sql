#insert into agentski_bekend.privilege_table (id, name) values (1, 'pr1');
insert into role_table (id, name) values(4, 'agent');
insert into role_table (id, name) values(3, 'user');
insert into role_table (id, name) values(1, 'admin');
insert into role_table (id, name) values(2, 'secure_admin');


#insert into roles_privileges (role_id, privilege_id) values (1, 1);
insert into user_table (user_id, email, password) values (0, 'mail@gmail.com', 'sifra');
insert into user_table (user_id, email, password) values (0, 'mailm@gmail.com', 'ssifra');


insert into user_roles (user_id, role_id) values (0, 1);

commit;