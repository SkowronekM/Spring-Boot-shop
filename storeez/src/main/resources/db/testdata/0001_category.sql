-- main category
insert into category (name) values ('Clothes');
insert into category (name) values ('Books');
insert into category (name) values ('Perfumes');

-- subcategory
insert into subcategory (name, category_id) values ('T-shirt', 1);
insert into subcategory (name, category_id) values ('Sweatshirt', 1);
insert into subcategory (name, category_id) values ('Hoodie', 1);
insert into subcategory (name, category_id) values ('Trousers', 1);
insert into subcategory (name, category_id) values ('Boots', 1);