/**
 * This file is licensed under the MIT license.
 * See the LICENSE file in project root for details.
 */

package in.praj.squizzel.data;

import in.praj.squizzel.model.QuestionBank;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;

/**
 * Implementation of {@link QuestionBankDao} connected to the
 * database using JDBC.
 */
public class JdbcQuestionBankDao implements QuestionBankDao {
    private final JdbcTemplate jdbc;
    private final QuestionDao questionDao;
    private final RowMapper<QuestionBank> mapper;

    public JdbcQuestionBankDao(final JdbcTemplate jdbc,
                               final QuestionDao questionDao) {
        this.jdbc = jdbc;
        this.questionDao = questionDao;
        mapper = (rs, rowNum) -> {
            final QuestionBank bank = new QuestionBank();
            bank.setId(rs.getInt("id"));
            bank.setName(rs.getString("name"));
            bank.setQuestions(questionDao.findByBank(bank));
            return bank;
        };
    }

    @Override
    public List<QuestionBank> findAll() {
        final String sql = "SELECT * FROM QuestionBanks";
        return jdbc.query(sql, mapper);
    }

    @Override
    public List<QuestionBank> findByName(String name) {
        // TODO: Implement this
        return null;
    }

    @Override
    public void insert(QuestionBank bank) {
        final String sql = "INSERT INTO QuestionBanks VALUES (?, ?)";
        jdbc.update(sql, mapper, bank.getId(), bank.getName());
        bank.getQuestions().forEach(q -> questionDao.insert(q, bank));
    }

    @Override
    public void update(QuestionBank bank) {
        // TODO: Implement this
    }

    @Override
    public void delete(QuestionBank bank) {
        // TODO: Implement this
    }
}
