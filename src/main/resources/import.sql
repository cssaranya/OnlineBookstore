INSERT INTO books (title, author, price) VALUES ('Angles & Demons', 'Dan Brown', 10.99);
INSERT INTO books (title, author, price) VALUES ('The Lost Symbol', 'Dan Brown', 8.99);
INSERT INTO books (title, author, price) VALUES ('And Then There Were None', 'Agatha Christie', 12.99);
INSERT INTO books (title, author, price) VALUES ('Pride and Prejudice', 'Jane Austen', 9.99);
INSERT INTO books (title, author, price) VALUES ('The Hades Factor', 'Robert Ludlum', 11.99);

INSERT INTO shoppingcart DEFAULT VALUES;


INSERT INTO cartitem (book_id, shopping_cart_id, quantity) VALUES (1, 1, 2);
INSERT INTO cartitem (book_id, shopping_cart_id, quantity) VALUES (2, 1, 1);


INSERT INTO orders (shopping_cart_id, order_date, status) VALUES (1, '2024-11-14T10:00:00', 'PLACED');