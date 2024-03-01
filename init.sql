CREATE TABLE IF NOT EXISTS users
(
    id         SERIAL PRIMARY KEY,
    full_name  VARCHAR(255),
    birth_date DATE,
    password   VARCHAR(255),
    role       VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS accounts
(
    id              SERIAL PRIMARY KEY,
    balance         NUMERIC(15, 2),
    initial_balance NUMERIC(15, 2),
    user_id         BIGINT REFERENCES users (id)
);

CREATE TABLE IF NOT EXISTS phone_numbers
(
    id      SERIAL PRIMARY KEY,
    phone   VARCHAR(20),
    user_id BIGINT REFERENCES users (id)
);

CREATE TABLE IF NOT EXISTS emails
(
    id      SERIAL PRIMARY KEY,
    email   VARCHAR(255),
    user_id BIGINT REFERENCES users (id)
);