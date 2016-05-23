truncate table "order" cascade;
truncate table users cascade;
truncate table station;
alter sequence cars_id_seq restart with 1;
alter sequence mechanic_id_seq restart with 1;
alter sequence mechanic_order_id_seq restart with 1;
alter sequence mechanic_station_id_seq restart with 1;
alter sequence order_id_seq restart with 1;
alter sequence station_id_seq restart with 1;


/*Users*/
insert into users values('test_change_user', 'test_change_user', true, 'Test1', 'User1');
insert into users values('test_delete_user', 'test_delete_user', true, 'Test2', 'User2');
insert into users values('test_user', 'test_user', true, 'Test3', 'User3');
insert into users values('test_user_foreign_1', 'test_user_foreign_1', true, 'Test4', 'User4');
insert into users values('test_user_foreign_2', 'test_user_foreign_2', true, 'Test5', 'User5');
insert into users values('test_user_foreign_3', 'test_user_foreign_3', true, 'Test7', 'User7');

insert into users values('test_user_foreign_4', 'test_user_foreign_4', true, 'Test8', 'User8');
insert into users values('test_user_foreign_5', 'test_user_foreign_5', true, 'Test9', 'User9');


/*Stations*/
insert into station (station_name, address, description, latitude, longitude)
values('test_station_foreign_1', 'address1', 'description1', 1.0, 1.0);

insert into station (station_name, address, description, latitude, longitude)
values('test_station_foreign_2', 'address2', 'description2', 2.0, 2.0);

insert into station (station_name, address, description, latitude, longitude)
values('test_station', 'address3', 'description3', 2.0, 2.0);

insert into station (station_name, address, description, latitude, longitude)
values('test_change_station', 'address4', 'description4', 3.0, 3.0);

insert into station (station_name, address, description, latitude, longitude)
values('test_delete_station', 'address5', 'description5', 4.0, 4.0);

/*Mechanics*/

/*Mechanic to get*/
insert into mechanic (station_id, username) values (
(select id from station where station_name ='test_station_foreign_1'),
'test_user_foreign_1');

/*Mechanic to change*/
insert into mechanic (station_id, username) values (
(select id from station where station_name ='test_station_foreign_1'),
'test_user_foreign_2');

/*Mechanic to delete*/
insert into mechanic (station_id, username) values (
(select id from station where station_name ='test_station_foreign_1'),
'test_user_foreign_3');

/*Mechanics to test get all mechanics by station_id*/
insert into mechanic (station_id, username) values (
(select id from station where station_name ='test_station_foreign_2'),
'test_user_foreign_4');

insert into mechanic (station_id, username) values (
(select id from station where station_name ='test_station_foreign_2'),
'test_user_foreign_5');

/*Authorities*/

insert into authorities values ('test_user_foreign_1', 'ROLE_USER');
insert into authorities values ('test_user_foreign_1', 'ROLE_MECHANIC');

/*Authorities below for test get all authorities by username*/
insert into authorities values ('test_user_foreign_2', 'ROLE_USER');
insert into authorities values ('test_user_foreign_2', 'ROLE_MECHANIC');

/*Car to change*/
insert into car (brand, model, engine_volume, vin, registration_number, username)
values ('brand1', 'model1', 1.0, '322ed2d323e23fg34', 'TH33343', 'test_user_foreign_1');

/*Car to delete*/
insert into car (brand, model, engine_volume, vin, registration_number, username)
values ('brand2', 'model2', 1.0, '322ed2d323e23fg34', 'TH33343', 'test_user_foreign_1');

/*Cars to get all cars*/
insert into car (brand, model, engine_volume, vin, registration_number, username)
values ('brand3', 'model3', 1.0, '322ed2d323e23fg34', 'TH33343', 'test_user_foreign_2');

insert into car (brand, model, engine_volume, vin, registration_number, username)
values ('brand4', 'model4', 1.0, '322ed2d323e23fg34', 'TH33343', 'test_user_foreign_2');

insert into car (brand, model, engine_volume, vin, registration_number, username)
values ('brand5', 'model5', 1.0, '322ed2d323e23fg34', 'TH33343', 'test_user_foreign_2');

/*Orders*/
insert into "order" (initial_date, work_description, status, planned_cost, planned_end_date, total_cost, end_date, station_id)
values ('2016-01-01', 'test_change_order', cast('INIT' as order_status), 40000, '2016-01-03', 0.0, '2016-01-03',
(select id from station where station_name = 'test_station_foreign_1'));

insert into "order" (initial_date, work_description, status, planned_cost, planned_end_date, total_cost, end_date, station_id)
values ('2016-01-01', 'test_delete_order', cast('INIT' as order_status), 40000, '2016-01-03', 0.0, '2016-01-03',
(select id from station where station_name = 'test_station_foreign_1'));

insert into "order" (initial_date, work_description, status, planned_cost, planned_end_date, total_cost, end_date, station_id)
values ('2016-01-01', 'test_order', cast('INIT' as order_status), 40000.0, '2016-01-03', 0.0, '2016-01-03',
(select id from station where station_name = 'test_station_foreign_1'));

insert into "order" (initial_date, work_description, status, planned_cost, planned_end_date, total_cost, end_date, station_id)
values ('2016-01-01', 'test_order_1', cast('INIT' as order_status), 40000.0, '2016-01-03', 0.0, '2016-01-03',
(select id from station where station_name = 'test_station_foreign_2'));

insert into "order" (initial_date, work_description, status, planned_cost, planned_end_date, total_cost, end_date, station_id)
values ('2016-01-01', 'test_order_2', cast('INIT' as order_status), 40000.0, '2016-01-03', 0.0, '2016-01-03',
(select id from station where station_name = 'test_station_foreign_2'));

/*Mechanic Orders*/
insert into mechanic_order (order_id, mechanic_id) values (
(select id from "order" where work_description = 'test_order'),
(select id from mechanic where username = 'test_user_foreign_1'));

insert into mechanic_order (order_id, mechanic_id) values (
(select id from "order" where work_description = 'test_order_1'),
(select id from mechanic where username = 'test_user_foreign_1'));


insert into mechanic_order (order_id, mechanic_id) values (
(select id from "order" where work_description = 'test_order_2'),
(select id from mechanic where username = 'test_user_foreign_2'));








