DROP TABLE IF EXISTS event;

CREATE TABLE event (
    id INTEGER NOT NULL AUTO_INCREMENT,
    title VARCHAR(128) NOT NULL COLLATE 'utf8mb4_unicode_ci',
    quantity INT NOT NULL, 
    created_date DATETIME(6) NOT NULL,
    PRIMARY KEY (id)
);