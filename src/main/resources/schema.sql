CREATE TABLE IF NOT EXISTS QuestionBanks(
    id INT IDENTITY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS QuestionTypes(
    id INT IDENTITY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS Questions(
    bank_id INT NOT NULL,
    id INT NOT NULL,
    type_id INT NOT NULL,
    content JSON NOT NULL,
    answer JSON NOT NULL,
    PRIMARY KEY (bank_id, id),
    CONSTRAINT fk_bank FOREIGN KEY (bank_id) REFERENCES QuestionBanks(id),
    CONSTRAINT fk_type FOREIGN KEY (type_id) REFERENCES QuestionTypes(id)
);
