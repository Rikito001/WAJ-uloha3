-- Ensure employee.full_time can store Slovak strings
ALTER TABLE employee
    MODIFY COLUMN full_time VARCHAR(50)
        CHARACTER SET utf8mb4
        COLLATE utf8mb4_unicode_ci
        NOT NULL;

