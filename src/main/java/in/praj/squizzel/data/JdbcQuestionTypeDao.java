/**
 * This file is licensed under the MIT license.
 * See the LICENSE file in project root for details.
 */

package in.praj.squizzel.data;

import in.praj.squizzel.model.QuestionType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;

/**
 * Implementation of {@link QuestionTypeDao} connected to the
 * database using JDBC.
 */
public class JdbcQuestionTypeDao implements QuestionTypeDao {
    private final JdbcTemplate jdbc;
    private final RowMapper<QuestionType> mapper;

    public JdbcQuestionTypeDao(final JdbcTemplate jdbc) {
        this.jdbc = jdbc;
        mapper = (rs, rowNum) -> {
            final QuestionType type = new QuestionType();
            type.setId(rs.getInt("id"));
            type.setName(rs.getString("name"));
            return type;
        };
    }

    @Override
    public List<QuestionType> findAll() {
        final String sql = "SELECT * FROM QuestionTypes";
        return jdbc.query(sql, mapper);
    }
    
    @Override
    public QuestionType findById(final int id) {
        final String sql = "SELECT * FROM QuestionTypes WHERE id = ?";
        return jdbc.queryForObject(sql, mapper, id);
    }
}
