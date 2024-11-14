INSERT INTO books (book_id, title, author, price) VALUES (1, 'Angles & Demons', 'Dan Brown', 10);
INSERT INTO books (book_id, title, author, price) VALUES (2, 'The Lost Symbol', 'Dan Brown', 8);
INSERT INTO books (book_id, title, author, price) VALUES (3, 'And Then There Were None', 'Agatha Christie', 12);
INSERT INTO books (book_id, title, author, price) VALUES (4, 'Pride and Prejudice', 'Jane Austen', 9);
INSERT INTO books (book_id, title, author, price) VALUES (5, 'The Hades Factor', 'Robert Ludlum', 11);

INSERT INTO users (user_id, username, password, email, address, phonenumber) VALUES (1, 'Saranya', 'Saranya', 'saranya@test.com', 'brussels', '01234');
INSERT INTO users (user_id, username, password, email, address, phonenumber) VALUES (2, 'Suptha', 'Suptha', 'suptha@test.com', 'bangalore', '91222');

INSERT INTO shoppingcart (shoppingcart_id, user_id) VALUES (1, 1);

INSERT INTO cartitem (cartitem_id, book_id, shoppingcart_id, quantity) VALUES (1, 1, 1, 2);
INSERT INTO cartitem (cartitem_id, book_id, shoppingcart_id, quantity) VALUES (2, 2, 1, 1);

INSERT INTO orders (order_id, user_id, shoppingcart_id, orderamount, order_date, status) VALUES (1, 1, 1, 18, '2024-11-14T10:00:00', 'PLACED');