CREATE TABLE public.persons (
    id VARCHAR(255) PRIMARY KEY,
    full_name VARCHAR(255) NOT NULL,
    gender VARCHAR(50),
    age INTEGER,
    address VARCHAR(255),
    phone_number VARCHAR(50)
);

CREATE TABLE public.customers (
    id VARCHAR(255) PRIMARY KEY,
    password VARCHAR(255) NOT NULL,
    identity_number VARCHAR(13) UNIQUE NOT NULL,
    status VARCHAR(50),
    FOREIGN KEY (id) REFERENCES persons(id) ON DELETE CASCADE
);

CREATE TABLE public.accounts (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    number VARCHAR NOT NULL,
    type VARCHAR,
    balance DECIMAL NOT NULL,
    status VARCHAR,
    customer_id UUID NOT NULL
);

CREATE TABLE public.transactions (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    executed_at TIMESTAMP NOT NULL,
    type VARCHAR,
    amount DECIMAL NOT NULL,
    balance DECIMAL NOT NULL,
    account_id UUID NOT NULL,
    FOREIGN KEY (account_id) REFERENCES public.accounts(id) ON DELETE CASCADE
);

CREATE TABLE public.reports_history (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    customer_id UUID NOT NULL,
    generated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);