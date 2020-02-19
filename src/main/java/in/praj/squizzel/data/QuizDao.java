/**
 * This file is licensed under the MIT license.
 * See the LICENSE file in project root for details.
 */

package in.praj.squizzel.data;

import in.praj.squizzel.model.Quiz;

import java.util.List;
import java.util.Optional;

/**
 * Data access object (DAO) to handle {@link Quiz} objects.
 */
public interface QuizDao {
    List<Quiz> findAll();
    Optional<Quiz> findById(String id);
    String insert(Quiz quiz);
    void update(Quiz quiz);
    void delete(Quiz quiz);
}
