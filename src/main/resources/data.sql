delete from Ingredient_Ref;
delete from Pancake;
delete from Pancake_Order;
delete from Ingredient;

insert into Ingredient (id, name, type)
values ('CP', 'Crispy Pancake', 'DOUGH');
insert into Ingredient (id, name, type)
values ('SC', 'Soft Pancake', 'DOUGH');
insert into Ingredient (id, name, type)
values ('SS', 'Sweet Strawberries', 'FILLING');
insert into Ingredient (id, name, type)
values ('CM', 'Condensed Milk', 'FILLING');
insert into Ingredient (id, name, type)
values ('HC', 'Ham and Cheese', 'FILLING');
insert into Ingredient (id, name, type)
values ('SCR', 'Sour Cream', 'FILLING');
insert into Ingredient (id, name, type)
values ('SB', 'Sweet Blueberries', 'FILLING');
