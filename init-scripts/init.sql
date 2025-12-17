CREATE DATABASE auth;
CREATE DATABASE inventory_write;
CREATE DATABASE inventory_read;

\c auth;

CREATE TABLE USERS (
    id UUID PRIMARY KEY,
    username VARCHAR(255) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    is_enabled BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE ROLES (
    id INT PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL
);

INSERT INTO ROLES (id, name) VALUES (1, 'USER');
INSERT INTO ROLES (id, name) VALUES (2, 'ADMIN');

CREATE TABLE USERS_ROLES (
    user_id UUID,
    role_id INT,
    PRIMARY KEY (user_id, role_id),
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES USERS(id) ON DELETE CASCADE,
    CONSTRAINT fk_role FOREIGN KEY (role_id) REFERENCES ROLES(id) ON DELETE CASCADE
);

\c inventory_write;

CREATE TABLE PRODUCTS (
    id UUID PRIMARY KEY,
    code VARCHAR(255) UNIQUE NOT NULL,
    name VARCHAR(255),
    category VARCHAR(100),
    price DECIMAL(10, 2)
);

CREATE TABLE INVENTORY (
    id UUID PRIMARY KEY,
    product_id UUID UNIQUE NOT NULL,
    stock_local INTEGER,
    version BIGINT,
    updated_at TIMESTAMP,
    CONSTRAINT fk_product FOREIGN KEY (product_id) REFERENCES PRODUCTS(id)
);

CREATE TABLE OUTBOX (
    id UUID PRIMARY KEY,
    occurred_on TIMESTAMP,
    aggregate_type VARCHAR(255),
    aggregate_id VARCHAR(255),
    type VARCHAR(255),
    payload JSONB,
    topic VARCHAR(255)
);

\c inventory_read;

CREATE TABLE INVENTORY_PROJECTION (
    product_id VARCHAR(255) PRIMARY KEY,
    product_name VARCHAR(255),
    current_stock INTEGER,
    last_updated_by_event_id VARCHAR(255)
);
