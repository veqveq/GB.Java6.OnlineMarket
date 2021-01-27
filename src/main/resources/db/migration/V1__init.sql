create table products_tbl
(
    id_fld         bigint       not null auto_increment,
    title_fld      varchar(255) not null,
    cost_fld       int          not null,
    created_at_fld datetime     not null default current_timestamp(),
    updated_at_fld datetime     not null default current_timestamp(),
    primary key (id_fld)
);

create table orders_tbl
(
    id_fld         bigint   not null auto_increment,
    product_id_fld bigint   not null,
    count_fld      int      not null,
    created_at_fld datetime not null,
    updated_at_fld datetime not null,
    primary key (id_fld),
    foreign key (product_id_fld) references products_tbl (id_fld)
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
       ('Product 21', 1950)