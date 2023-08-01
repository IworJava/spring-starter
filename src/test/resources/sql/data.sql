INSERT INTO company (id, name)
VALUES (1, 'Google'),
       (2, 'Meta'),
       (3, 'Amazon');
SELECT SETVAL('company_id_seq', (SELECT MAX(id) FROM company));

INSERT INTO company_locales (company_id, lang, description)
VALUES ((SELECT id FROM company WHERE name = 'Google'), 'en', 'Google description'),
       ((SELECT id FROM company WHERE name = 'Google'), 'ru', 'Google описание'),
       ((SELECT id FROM company WHERE name = 'Meta'), 'en', 'Meta description'),
       ((SELECT id FROM company WHERE name = 'Meta'), 'ru', 'Meta описание'),
       ((SELECT id FROM company WHERE name = 'Amazon'), 'en', 'Amazon description'),
       ((SELECT id FROM company WHERE name = 'Amazon'), 'ru', 'Amazon описание');

INSERT INTO users (id, birth_date, firstname, lastname, role, username, company_id)
VALUES (1, '1990-01-10', 'Ivan', 'Ivanov', 'ADMIN', 'ivan@gmail.com', (SELECT id FROM company WHERE name = 'Google')),
       (2, '1995-10-19', 'Petr', 'Petrov', 'USER', 'petr@gmail.com', (SELECT id FROM company WHERE name = 'Google')),
       (3, '2001-12-23', 'Sveta', 'Svetikova', 'USER', 'sveta@gmail.com', (SELECT id FROM company WHERE name = 'Meta')),
       (4, '1984-03-14', 'Vlad', 'Vladikov', 'USER', 'vlad@gmail.com', (SELECT id FROM company WHERE name = 'Amazon')),
       (5, '1984-03-14', 'Kate', 'Smith', 'ADMIN', 'kate@gmail.com', (SELECT id FROM company WHERE name = 'Amazon'));
SELECT SETVAL('users_id_seq', (SELECT MAX(id) FROM users));

INSERT INTO payment (id, amount, receiver_id)
VALUES (1, 100, (SELECT id FROM users WHERE username = 'ivan@gmail.com')),
       (2, 300, (SELECT id FROM users WHERE username = 'ivan@gmail.com')),
       (3, 500, (SELECT id FROM users WHERE username = 'ivan@gmail.com')),
       (4, 250, (SELECT id FROM users WHERE username = 'petr@gmail.com')),
       (5, 600, (SELECT id FROM users WHERE username = 'petr@gmail.com')),
       (6, 500, (SELECT id FROM users WHERE username = 'petr@gmail.com')),
       (7, 400, (SELECT id FROM users WHERE username = 'sveta@gmail.com')),
       (8, 300, (SELECT id FROM users WHERE username = 'sveta@gmail.com')),
       (9, 500, (SELECT id FROM users WHERE username = 'vlad@gmail.com')),
       (10, 700, (SELECT id FROM users WHERE username = 'vlad@gmail.com')),
       (11, 340, (SELECT id FROM users WHERE username = 'vlad@gmail.com')),
       (12, 440, (SELECT id FROM users WHERE username = 'kate@gmail.com')),
       (13, 510, (SELECT id FROM users WHERE username = 'kate@gmail.com')),
       (14, 630, (SELECT id FROM users WHERE username = 'kate@gmail.com'));
SELECT SETVAL('payment_id_seq', (SELECT MAX(id) FROM payment));

INSERT INTO chat (id, name)
VALUES (1, 'dmdev'),
       (2, 'java'),
       (3, 'database');
SELECT SETVAL('chat_id_seq', (SELECT MAX(id) FROM chat));

INSERT INTO users_chat(user_id, chat_id)
VALUES ((SELECT id FROM users WHERE username = 'ivan@gmail.com'), (SELECT id FROM chat WHERE name = 'dmdev')),
       ((SELECT id FROM users WHERE username = 'petr@gmail.com'), (SELECT id FROM chat WHERE name = 'dmdev')),
       ((SELECT id FROM users WHERE username = 'sveta@gmail.com'), (SELECT id FROM chat WHERE name = 'dmdev')),
       ((SELECT id FROM users WHERE username = 'petr@gmail.com'), (SELECT id FROM chat WHERE name = 'java')),
       ((SELECT id FROM users WHERE username = 'sveta@gmail.com'), (SELECT id FROM chat WHERE name = 'java')),
       ((SELECT id FROM users WHERE username = 'vlad@gmail.com'), (SELECT id FROM chat WHERE name = 'java')),
       ((SELECT id FROM users WHERE username = 'kate@gmail.com'), (SELECT id FROM chat WHERE name = 'java')),
       ((SELECT id FROM users WHERE username = 'petr@gmail.com'), (SELECT id FROM chat WHERE name = 'database')),
       ((SELECT id FROM users WHERE username = 'kate@gmail.com'), (SELECT id FROM chat WHERE name = 'database'));
