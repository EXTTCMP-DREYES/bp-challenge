CREATE TABLE persons (
    id VARCHAR(255) PRIMARY KEY,
    full_name VARCHAR(255) NOT NULL,
    gender VARCHAR(50),
    age INTEGER,
    address VARCHAR(255),
    phone_number VARCHAR(50)
);

CREATE TABLE customers (
    id VARCHAR(255) PRIMARY KEY,
    password VARCHAR(255) NOT NULL,
    status VARCHAR(50),
    FOREIGN KEY (id) REFERENCES persons(id) ON DELETE CASCADE
);