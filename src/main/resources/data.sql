insert into APP_USER(ID, PASSWORD, USERNAME) values(1, 'test', 'sean');
insert into APP_USER(ID, PASSWORD, USERNAME) values(2, 'test', 'sean1');
insert into USER_ROLE(APP_USER_ID, ROLE) values(1, 'ROLE_ADMIN');
insert into USER_ROLE(APP_USER_ID, ROLE) values(1, 'ROLE_USER');
insert into USER_ROLE(APP_USER_ID, ROLE) values(2, 'ROLE_ADMIN');
insert into USER_ROLE(APP_USER_ID, ROLE) values(2, 'ROLE_USER');