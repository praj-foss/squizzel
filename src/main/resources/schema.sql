CREATE TABLE IF NOT EXISTS Quizzes(
    id VARCHAR(10) NOT NULL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS QuestionTypes(
    id INT IDENTITY,
    name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS Questions(
    quiz_id VARCHAR(10) NOT NULL,
    id INT NOT NULL,
    type_id INT NOT NULL,
    content JSON NOT NULL,
    answer JSON NOT NULL,
    PRIMARY KEY (quiz_id, id),
    CONSTRAINT fk_quiz FOREIGN KEY (quiz_id)
        REFERENCES Quizzes(id) ON DELETE CASCADE,
    CONSTRAINT fk_type FOREIGN KEY (type_id)
        REFERENCES QuestionTypes(id) ON DELETE RESTRICT
);
