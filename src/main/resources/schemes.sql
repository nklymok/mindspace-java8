CREATE TABLE IF NOT EXISTS tasks(
    id INT PRIMARY KEY AUTO_INCREMENT,
    header VARCHAR(80) NOT NULL,
    description VARCHAR(200),
    duedate TIMESTAMP,
    priority INT NOT NULL
);