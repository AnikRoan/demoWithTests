create table if not exists public.users
(
    id         serial
        primary key,
    name       varchar(255),
    email      varchar(255),
    country    varchar(255),
    is_deleted boolean default false
);

create table if not exists public.addresses
(
    id                 serial
        primary key,
    address_has_active boolean default false,
    city               varchar(255),
    country            varchar(255),
    street             varchar(255),
    employee_id        integer
        constraint fk_address_employee references public.users

);

create table if not exists public.documents
(
    id          serial
        primary key,
    expire_date timestamp(6),
    is_handled  boolean,
    number      varchar(255) not null,
    uuid        varchar(255)
);



