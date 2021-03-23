create table users_tbl
(
    id_fld         bigint       not null auto_increment,
    username_fld   varchar(255) not null unique,
    password_fld   varchar(255) not null,
    created_at_fld datetime     not null default current_timestamp(),
    updated_at_fld datetime     not null default current_timestamp(),
    primary key (id_fld)
);

create table orders_tbl
(
    id_fld          bigint       not null auto_increment,
    owner_id_fld    bigint       not null,
    total_price_fld int          not null,
    address_fld     varchar(255) not null,
    created_at_fld  datetime     not null default current_timestamp(),
    updated_at_fld  datetime     not null default current_timestamp(),
    primary key (id_fld),
    foreign key (owner_id_fld) references users_tbl (id_fld)
);

create table products_tbl
(
    id_fld         bigint       not null auto_increment,
    title_fld      varchar(255) not null,
    cost_fld       int          not null,
    created_at_fld datetime     not null default current_timestamp(),
    updated_at_fld datetime     not null default current_timestamp(),
    primary key (id_fld)
);

create table order_items_tbl
(
    id_fld               bigint   not null auto_increment,
    product_id_fld       bigint   not null,
    count_fld            int      not null,
    cost_per_product_fld int      not null,
    order_id_fld         bigint            default null,
    created_at_fld       datetime not null default current_timestamp(),
    updated_at_fld       datetime not null default current_timestamp(),
    primary key (id_fld),
    foreign key (product_id_fld) references products_tbl (id_fld),
    foreign key (order_id_fld) references orders_tbl (id_fld)
);

create table roles_tbl
(
    id_fld         bigint      not null auto_increment,
    role_fld       varchar(45) not null,
    created_at_fld datetime    not null default current_timestamp(),
    updated_at_fld datetime    not null default current_timestamp(),
    primary key (id_fld)
);

create table users_roles_tbl
(
    user_id_fld bigint not null,
    role_id_fld bigint not null,
    foreign key (user_id_fld) references users_tbl (id_fld),
    foreign key (role_id_fld) references roles_tbl (id_fld)
);

create table carts_tbl
(
    id_fld         UUID     not null,
    cart_price_fld int,
    created_at_fld datetime not null default current_timestamp(),
    updated_at_fld datetime not null default current_timestamp(),
    primary key (id_fld)
);

create table cart_items_tbl
(
    id_fld               bigint   not null,
    product_id_fld       bigint   not null,
    count_fld            int      not null,
    cost_per_product_fld int      not null,
    cart_id_fld          UUID     not null,
    created_at_fld       datetime not null default current_timestamp(),
    updated_at_fld       datetime not null default current_timestamp(),
    primary key (id_fld),
    foreign key (product_id_fld) references products_tbl (id_fld),
    foreign key (cart_id_fld) references carts_tbl (id_fld)
);

insert into products_tbl (id_fld,title_fld, cost_fld)
values (1,'Product 1', 10),
       (2,'Product 2', 15),
       (3,'Product 3', 65);

insert into users_tbl (username_fld, password_fld)
values ('Jackass', '$2y$12$fdgMU9AMXKxZ9Jur1eaO5O2gs9xfifsV7Wh52rJ4wjf.Acv4c8WuS'), //100
       ('Genius', '$2y$12$jfmri3OwhHT0RRrXz3Bho.ZisFYtXISc3DCrJK5/QAwiSU0yRfyzK'); //123

insert into roles_tbl (role_fld)
values ('ROLE_ADMIN'),
       ('ROLE_USER');

insert into users_roles_tbl(user_id_fld, role_id_fld)
values (1, 2),
       (2, 1)