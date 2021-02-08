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

insert into products_tbl (title_fld, cost_fld)
values ('Product 1', 10),
       ('Product 2', 15),
       ('Product 3', 65),
       ('Product 4', 155),
       ('Product 5', 70),
       ('Product 6', 95),
       ('Product 7', 255),
       ('Product 8', 1300),
       ('Product 9', 85),
       ('Product 10', 40),
       ('Product 11', 215),
       ('Product 12', 555),
       ('Product 13', 725),
       ('Product 14', 400),
       ('Product 15', 315),
       ('Product 16', 205),
       ('Product 17', 60),
       ('Product 18', 125),
       ('Product 19', 330),
       ('Product 20', 990),
       ('Product 21', 1950);

insert into users_tbl (username_fld, password_fld)
values ('Jackass', '$2y$12$fdgMU9AMXKxZ9Jur1eaO5O2gs9xfifsV7Wh52rJ4wjf.Acv4c8WuS'), //100
       ('Genius', '$2y$12$jfmri3OwhHT0RRrXz3Bho.ZisFYtXISc3DCrJK5/QAwiSU0yRfyzK'); //123

insert into roles_tbl (role_fld)
values ('ROLE_ADMIN'),
       ('ROLE_USER');

insert into users_roles_tbl(user_id_fld, role_id_fld)
values (1, 2),
       (2, 1)