drop table if exists station;
drop table if exists mechanic;
drop table if exists "order";
drop table if exists mechanic_order;
create table station(
	id serial primary key,
	name varchar(255) not null,
	address varchar(255) not null,
	description varchar(255),
	latitude double precision,
	longitude double precision
);

create type work_profile as(
	mobile_phone_number varchar(255)
);

create table mechanic(
	id serial primary key,
	name varchar(255) not null,
	surname varchar(255) not null,
	patronymic varchar(255) not null,
	profile work_profile not null
);

create type order_status as enum ('init', 'accepted', 'in_progress', 'done');

create table "order"(
	id bigserial primary key,
	initial_date timestamp with time zone default now() not null,
	work_description varchar(10000),
	status order_status not null,
	planned_cost money,
	planned_end_data timestamp with time zone, 
	total_cost money,
	end_date timestamp with time zone
);

create table mechanic_order(
	id bigserial primary key,
	order_id bigint not null,
	mechanic_id bigint not null
);

alter table mechanic_order add constraint order_fkey foreign key (order_id) references "order"(id) on delete cascade;
alter table mechanic_order add constraint mechanic_fkey foreign key (mechanic_id) references mechanic(id) on delete cascade;
