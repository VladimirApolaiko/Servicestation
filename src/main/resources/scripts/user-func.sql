CREATE TABLE or_user (
  id         bigserial primary key,
  email      varchar(250)           not null,
  password   varchar(120),
  created    timestamp default now() NOT NULL,
  first_name varchar(50),
  last_name  varchar(50)
);

create table role (
  id          bigserial primary key,
  name        varchar(50) not null,
  description varchar(1024)
);

create table nn_user_role (
  user_id bigint not null,
  role_id bigint not null
);

alter table only nn_user_role add constraint role_fkey foreign key (role_id) references role (id) on delete cascade;
alter table only nn_user_role add constraint user_fkey foreign key (user_id) references or_user (id) on delete cascade;