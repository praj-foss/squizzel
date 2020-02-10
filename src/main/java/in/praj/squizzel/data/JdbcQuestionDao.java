/**
 * This file is licensed under the MIT license.
 * See the LICENSE file in project root for details.
 */

package in.praj.squizzel.data;

import in.praj.squizzel.model.Question;
import in.praj.squizzel.model.QuestionBank;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;

/**
 * Implementation of {@link QuestionDao} connected to the
 * database using JDBC.
 */
public class JdbcQuestionDao implements QuestionDao {
    private final JdbcTemplate jdbc;
    private final QuestionTypeDao typeDao;
    private final RowMapper<Question> mapper;

    public JdbcQuestionDao(final JdbcTemplate jdbc,
                           final QuestionTypeDao typeDao) {
        this.jdbc = jdbc;
        this.typeDao = typeDao;
        mapper = (rs, rowNum) -> {
            final Question q = new Question();
            q.setId(rs.getInt("id"));
            q.setType(typeDao.findById(rs.getInt("type_id")));
            q.setContent(rs.getString("content"));
            q.setAnswer(rs.getString("answer"));
            return q;
        };
    }

    @Override
    public List<Question> findByBank(final QuestionBank bank) {
        final String sql =
                "SELECT id, type_id, content, answer "
              + "FROM Questions WHERE bank_id = ?";

        return jdbc.query(sql, mapper, bank.getId());
    }

    @Override
    public void insert(Question question, QuestionBank bank) {
        final String sql =
                "INSERT INTO Questions(bank_id, type_id, content, answer) "
              + "VALUES (?, ?, ? FORMAT JSON, ? FORMAT JSON)";
        jdbc.update(
                sql,
                bank.getId(),
                question.getType().getId(),
                question.getContent(),
                question.getAnswer());
    }
}
