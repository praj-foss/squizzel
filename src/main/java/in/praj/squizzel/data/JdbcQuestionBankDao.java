/**
 * This file is licensed under the MIT license.
 * See the LICENSE file in project root for details.
 */

package in.praj.squizzel.data;

import in.praj.squizzel.model.Question;
import in.praj.squizzel.model.QuestionBank;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of {@link QuestionBankDao} connected to the
 * database using JDBC.
 */
public class JdbcQuestionBankDao implements QuestionBankDao {
    private final JdbcTemplate jdbc;
    private final ResultSetExtractor<List<QuestionBank>> bankExtractor = (rs) -> {
        final List<QuestionBank> banks = new ArrayList<>();
        QuestionBank current = null;
        while (rs.next()) {
            final long id = rs.getLong("id");
            if (current == null || current.getId() != id) {
                current = new QuestionBank();
                current.setId(id);
                current.setName(rs.getString("name"));
                current.setQuestions(new ArrayList<>());
                banks.add(current);
            }

            final Question question = new Question();
            question.setId(rs.getLong("qid"));
            question.setType(rs.getString("type"));
            question.setContent(rs.getString("content"));
            question.setAnswer(rs.getString("answer"));
            current.getQuestions().add(question);
        }
        return banks;
    };

    public JdbcQuestionBankDao(final JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public List<QuestionBank> findAll() {
        final String sql =
                "SELECT B.id, B.name, Q.id AS qid, T.name AS type, Q.content, Q.answer "
              + "FROM QuestionBanks B, QuestionTypes T "
              + "INNER JOIN Questions Q "
              + "ON B.id = Q.bank_id "
              + "WHERE T.id = Q.type_id";
        return jdbc.query(sql, bankExtractor);
    }

    @Override
    public List<QuestionBank> findById(Integer id) {
        // TODO: Implement this
        return null;
    }

    @Override
    public long insert(QuestionBank bank) {
        // TODO: Make transaction and insert questions
        final KeyHolder holder = new GeneratedKeyHolder();
        final String INSERT_BANK = "INSERT INTO QuestionBanks(name) VALUES (?)";

        jdbc.update(con -> {
            final PreparedStatement ps = con.prepareStatement(
                    INSERT_BANK,
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, bank.getName());
            return ps;
        }, holder);

        return holder.getKey().longValue();
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
