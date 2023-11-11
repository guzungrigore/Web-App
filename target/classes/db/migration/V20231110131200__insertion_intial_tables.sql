INSERT INTO departments(name ,location)
VALUES ('Finance', 'Paris'),
      ('Marketing', 'Berlin'),
      ('Operations_management', 'Bucharest'),
      ('Human_resources', 'London'),
      ('Information_technology', 'Chisinau');

INSERT INTO employees(first_name, last_name, email, phone_number, salary, department_id)
VALUES ('John', 'Smith', 'john.smith@company.com', '492-236-124-6379', 2500, 3),
       ('Robert', 'Davis', 'robert.davis@company.com', '555-567-8901', 1250, 1),
       ('Sarah', 'Brown', 'sarah.brown@company.com', '455-678-9012', 1800, 2),
       ('Gregory', 'Goose', 'gregory.goose@company.com', '373-78-105-373', 9000, 5),
       ('John', 'Doe', 'john.doe@company.com', '505-123-4567', 5000, null),
       ('Mark', 'Johnson', 'mark.johnson@company.com', '555-345-6789', 4300, 4),
       ('Mary', 'Wilson', 'mary.wilson@company.com', '246-456-7890', 4000, 3),
       ('Robertson', 'Davison', 'robertson.davison@company.com', '777-567-8901', 7900, 5),
       ('Emily', 'Lee', 'emily.lee@company.com', '255-890-1234', 4700, 2),
       ('David', 'Lopez', 'david.lopez@company.com', '956-901-2345', 6900, 2);