create table if not exists Pancake_Order (
    id identity,
    delivery_Name varchar(50) not null,
    delivery_Address varchar(100) not null,
    phone_Num varchar(12) not null,
    placed_at timestamp not null
);

create table if not exists Pancake (
    id identity,
    name varchar(50) not null,
    pancake_order bigint not null,
    pancake_order_key bigint not null,
    created_at timestamp not null
);

create table if not exists Ingredient_Ref (
    ingredient varchar(4) not null,
    pancake bigint not null,
    pancake_key bigint not null
);

create table if not exists Ingredient (
    id varchar(4) not null,
    name varchar(25) not null,
    type varchar(10) not null
);

alter table Pancake
    add foreign key (pancake_order) references Pancake_Order(id);
alter table Ingredient_Ref
    add foreign key (ingredient) references Ingredient(id);
