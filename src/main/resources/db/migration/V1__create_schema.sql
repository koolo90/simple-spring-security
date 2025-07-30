create table roles (
    id bigserial primary key,
    name varchar(100) not null unique
);

create table users (
    id bigserial primary key,
    username varchar(50) not null unique,
    password varchar(255) not null
);

create table user_roles (
    id bigserial primary key,
    user_id bigint not null,
    role_id bigint not null
);