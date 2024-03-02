CREATE TABLE IF NOT EXISTS accounts
(
    id              SERIAL PRIMARY KEY,
    balance         NUMERIC(15, 2) NOT NULL,
    initial_balance NUMERIC(15, 2) NOT NULL
);

CREATE TABLE IF NOT EXISTS users
(
    id         SERIAL PRIMARY KEY,
    username   VARCHAR(255) NOT NULL,
    full_name  VARCHAR(255),
    birth_date DATE,
    password   VARCHAR(255)        NOT NULL,
    account_id BIGINT REFERENCES accounts (id),
    role       VARCHAR(20)         NOT NULL
);

CREATE TABLE IF NOT EXISTS phone_numbers
(
    id      SERIAL PRIMARY KEY,
    phone   VARCHAR(20) NOT NULL,
    user_id BIGINT REFERENCES users (id)
);

CREATE TABLE IF NOT EXISTS emails
(
    id      SERIAL PRIMARY KEY,
    email   VARCHAR(255) NOT NULL,
    user_id BIGINT REFERENCES users(id)
);

INSERT INTO accounts(balance, initial_balance)
VALUES (100, 100),
       (105, 105);

INSERT INTO users(username, full_name, birth_date, password, role, account_id)
VALUES ('test_user1', 'surname1 name1', '1990-05-15', 'password1', 'ROLE_USER', 1),
       ('test_user2', 'surname2 name2', '1999-05-15', 'password2', 'ROLE_USER', 2);



