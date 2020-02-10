/**
 * This file is licensed under the MIT license.
 * See the LICENSE file in project root for details.
 */

package in.praj.squizzel.data;

import in.praj.squizzel.model.Question;
import in.praj.squizzel.model.QuestionBank;

import java.util.List;

/**
 * Data access object (DAO) to handle {@link Question} objects.
 */
public interface QuestionDao {
    List<Question> findByBank(QuestionBank bank);
    void insert(Question question, QuestionBank bank);
}
