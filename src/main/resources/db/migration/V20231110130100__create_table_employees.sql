CREATE TABLE employees
(
    id SMALLSERIAL PRIMARY KEY,
    first_name VARCHAR(20) NOT NULL,
    last_name VARCHAR(20) NOT NULL,
    email VARCHAR(50) NOT NULL UNIQUE,
    phone_number VARCHAR(20) NOT NULL UNIQUE,
    salary DECIMAL(10, 2) CHECK (salary >= 1.0),
    department_id INTEGER REFERENCES departments(id) ON DELETE SET NULL
)