CREATE TABLE IF NOT EXISTS "events"
(
    ID          BIGINT PRIMARY KEY AUTO_INCREMENT,
    LOCATION    VARCHAR(50) NOT NULL,
    TITLE       VARCHAR(50) NOT NULL,
    "WHEN"      VARCHAR(50) NOT NULL
);
