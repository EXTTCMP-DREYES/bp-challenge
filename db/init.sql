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
    status VARCHAR(50),
    FOREIGN KEY (id) REFERENCES persons(id) ON DELETE CASCADE
);

CREATE TABLE public.accounts (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    number VARCHAR NOT NULL,
    type VARCHAR, -- Puede cambiar a un tipo específico si es necesario
    balance DECIMAL NOT NULL,
    status VARCHAR, -- Puede cambiar a un tipo específico si es necesario
    client_id UUID NOT NULL
);

CREATE TABLE public.transactions (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    executed_at TIMESTAMP NOT NULL,
    type VARCHAR, -- Puede cambiar a un tipo específico si es necesario
    amount DECIMAL NOT NULL,
    balance DECIMAL NOT NULL,
    account_id UUID NOT NULL,
    FOREIGN KEY (account_id) REFERENCES public.accounts(id) ON DELETE CASCADE
);