CREATE TABLE user(
    id                   INT NOT NULL AUTO_INCREMENT,
    email                VARCHAR(256) NOT NULL,
    monthly_salary       FLOAT(12, 2) NOT NULL,
    monthly_expenses     FLOAT(12, 2) NOT NULL,
    PRIMARY KEY          (id)
);

CREATE TABLE account(
    id              INT NOT NULL AUTO_INCREMENT,
    user_id         INT NOT NULL,
    username        VARCHAR(256) NOT NULL,
    password        VARCHAR(256) NOT NULL,
    PRIMARY KEY     (id),
    FOREIGN KEY     (user_id) REFERENCES user(id)
)