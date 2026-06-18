CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE tb_users (
    id UUID PRIMARY KEY,
    full_name VARCHAR(100) NOT NULL,
    cpf VARCHAR(11) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP
);

CREATE TABLE tb_accounts (
    id UUID PRIMARY KEY,
    account_number VARCHAR(10) NOT NULL UNIQUE,
    agency VARCHAR(4) NOT NULL,
    balance NUMERIC(15, 2) NOT NULL DEFAULT 0.00,
    account_type VARCHAR(20) NOT NULL,
    user_id UUID NOT NULL,
    created_at TIMESTAMP NOT NULL,
    CONSTRAINT fk_accounts_user FOREIGN KEY (user_id) REFERENCES tb_users(id) ON DELETE RESTRICT
);