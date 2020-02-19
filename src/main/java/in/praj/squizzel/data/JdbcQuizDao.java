/**
 * This file is licensed under the MIT license.
 * See the LICENSE file in project root for details.
 */

package in.praj.squizzel.data;

import in.praj.squizzel.model.Question;
import in.praj.squizzel.model.Quiz;
import in.praj.squizzel.service.IdService;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of {@link QuizDao} connected to the
 * database using JDBC.
 */
public class JdbcQuizDao implements QuizDao {
    private final JdbcTemplate jdbc;
    private final IdService idService;
    private final ResultSetExtractor<List<Quiz>> bankExtractor;

    public JdbcQuizDao(final JdbcTemplate jdbc,
                       final IdService idService) {
        this.jdbc = jdbc;
        this.idService = idService;

        bankExtractor = (ResultSet rs) -> {
            final List<Quiz> quizzes = new ArrayList<>();
            Quiz current = null;
            while (rs.next()) {
                final String id = rs.getString("id");
                if (current == null || !current.getId().equals(id)) {
                    current = new Quiz();
                    current.setId(id);
                    current.setName(rs.getString("name"));
                    current.setQuestions(new ArrayList<>());
                    quizzes.add(current);
                }

                final Question question = new Question();
                question.setId(rs.getLong("nid"));
                question.setType(rs.getString("type"));
                question.setContent(rs.getString("content"));
                question.setAnswer(rs.getString("answer"));
                current.getQuestions().add(question);
            }
            return quizzes;
        };
    }

    private List<Quiz> find(final String id) {
        final String SELECT_QUIZ =
                "SELECT Q.id, Q.name, N.id AS nid, T.name AS type, N.content, N.answer " +
                "FROM Quizzes Q, QuestionTypes T " +
                "INNER JOIN Questions N " +
                "ON Q.id = N.quiz_id " +
                "WHERE T.id = N.type_id ";

        return (id == null)
                ? jdbc.query(SELECT_QUIZ, bankExtractor)
                : jdbc.query(SELECT_QUIZ + "AND Q.id = ?", bankExtractor, id);
    }

    @Override
    public List<Quiz> findAll() {
        return find(null);
    }

    @Override
    public Optional<Quiz> findById(String id) {
        final List<Quiz> result = find(id);
        return Optional.ofNullable(
                (result.size() > 0) ? result.get(0) : null);
    }

    @Override
    public String insert(Quiz quiz) {
        // TODO: Make transaction and insert question types
        final String INSERT_QUIZ =
                "INSERT INTO Quizzes VALUES (?, ?)";

        final String INSERT_QUESTION =
                "INSERT INTO Questions VALUES " +
                "(?, ?, " +
                " (WITH S(name) AS (VALUES ?) " +
                "   SELECT id FROM FINAL TABLE " +
                "     (MERGE INTO QuestionTypes T USING S ON T.name = S.name " +
                "      WHEN NOT MATCHED THEN INSERT (name) VALUES (S.name)) " +
                "   UNION SELECT id FROM QuestionTypes " +
                "     JOIN S ON QuestionTypes.name = S.name), " +
                "? FORMAT JSON, ? FORMAT JSON)";

        final String quizId = idService.generate();
        jdbc.update(INSERT_QUIZ, quizId, quiz.getName());

        jdbc.batchUpdate(INSERT_QUESTION, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                final Question q = quiz.getQuestions().get(i);
                ps.setString(1, quizId);
                ps.setLong(2, q.getId());
                ps.setString(3, q.getType());
                ps.setString(4, q.getContent());
                ps.setString(5, q.getAnswer());
            }

            @Override
            public int getBatchSize() {
                return quiz.getQuestions().size();
            }
        });

        return quizId;
    }

    @Override
    public void update(Quiz quiz) {
        // TODO: Implement this
    }

    @Override
    public void delete(Quiz quiz) {
        // TODO: Implement this
    }
}
