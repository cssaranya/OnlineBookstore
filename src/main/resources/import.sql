INSERT INTO books (title, author, price) VALUES ('Angles & Demons', 'Dan Brown', 10);
INSERT INTO books (title, author, price) VALUES ('The Hades Factor', 'Robert Ludlum', 11);

INSERT INTO users (username, password, email, address, phonenumber) VALUES ('Saranya', '$2a$10$aFDYm09llyfJ.EJhPJnjcujZabQ5H1FJxqu7bDZgjruRN7V5Z.Xfe', 'saranya@test.com', 'brussels', '01234');
INSERT INTO users (username, password, email, address, phonenumber) VALUES ('Suptha', '$2a$10$pTeBQ1gLaotCaf9/OcueWOkxG6Jw3r1K/ktKw5me3cwdzNqN/bZi2', 'suptha@test.com', 'bangalore', '91222');

INSERT INTO shoppingcart (user_id) VALUES (1);

INSERT INTO cartitem (book_id, shoppingcart_id, quantity) VALUES (1, 1, 2);
INSERT INTO cartitem (book_id, shoppingcart_id, quantity) VALUES (2, 1, 1);

//INSERT INTO orders (user_id, shoppingcart_id, orderamount, order_date, status) VALUES (1, 1, 18, '2024-11-14T10:00:00', 'PLACED');