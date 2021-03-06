/**
 * This file is licensed under the MIT license.
 * See the LICENSE file in project root for details.
 */

package in.praj.squizzel.config;

import in.praj.squizzel.data.JdbcQuizDao;
import in.praj.squizzel.data.QuizDao;
import in.praj.squizzel.service.IdService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Handle JDBC-related configurations. This supplies beans
 * for the data source as well as all the DAOs.
 */
@Configuration
public class JdbcConfig {
    private static final Logger LOG = LoggerFactory.getLogger(JdbcConfig.class);
    private static final String APP_DIR = ".squizzel";

    @Bean
    public DataSource dataSource() {
        try {
            final String dbPath = appHome().resolve("local").toString();
            final DriverManagerDataSource source = new DriverManagerDataSource();
            source.setDriverClassName("org.h2.Driver");
            source.setUrl("jdbc:h2:" + dbPath);
            source.setUsername("sa");
            source.setPassword("password");

            return source;
        } catch (Exception e) {
            LOG.error("Failed to initialize DataSource bean", e);
            return null;
        }
    }

    @Bean
    @Autowired
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    @Autowired
    public QuizDao quizDao(JdbcTemplate jdbc, IdService idService) {
        return new JdbcQuizDao(jdbc, idService);
    }

    private Path appHome() {
        return Paths.get(System.getProperty("user.home"), APP_DIR);
    }
}
