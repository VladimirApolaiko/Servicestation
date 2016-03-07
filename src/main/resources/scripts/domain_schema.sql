drop table if exists station;
drop table if exists mechanic cascade;
drop table if exists "order" cascade;
drop table if exists mechanic_order;
drop type if exists mechanic_profile;
drop type if exists order_status;
create table station(
	id serial primary key,
	name varchar(255) not null,
	address varchar(255) not null,
	description varchar(255),
	latitude double precision,
	longitude double precision
);

create type mechanic_profile as(
	name varchar(255),
	surname varchar(255),
	patronymic varchar(255),
	passport_number varchar(255),
	passport_id varchar(255),
	address varchar(1000),
	phone_number varchar(255)
);

create table mechanic(
	id serial primary key,
	nickname varchar(255) unique not null,
	profile mechanic_profile,
	station_id serial
);

create type order_status as enum ('INIT', 'ACCEPTED', 'IN_PROGRESS', 'DONE');

create table "order"(
	id bigserial primary key,
	initial_date timestamp with time zone default now() not null,
	work_description varchar(10000),
	status order_status not null,
	planned_cost money,
	planned_end_date timestamp with time zone,
	total_cost money,
	end_date timestamp with time zone,
	station_id bigint
);

create table mechanic_order(
	id bigserial primary key,
	order_id bigint not null,
	mechanic_id bigint not null
);

alter table mechanic_order add constraint order_fkey foreign key (order_id) references "order"(id) on delete cascade;
alter table mechanic_order add constraint mechanic_fkey foreign key (mechanic_id) references mechanic(id) on delete cascade;
