create table "user" (
    id serial primary key,
    name        varchar,
    second_name varchar,
    email       varchar,
    pass        varchar,
    phone       varchar,
    token       varchar,
    is_admin    boolean default false
);

create table product (
    id serial primary key,
    name  varchar,
    price real,
    image varchar
);

create table cart (
   id serial primary key ,
   user_id int,
   product_id int,
   count int check (count > 0) default 1,
   foreign key (user_id) references "user" (id),
   foreign key (product_id) references product (id),
   unique (user_id, product_id)
);
