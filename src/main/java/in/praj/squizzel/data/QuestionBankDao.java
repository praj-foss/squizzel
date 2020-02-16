/**
 * This file is licensed under the MIT license.
 * See the LICENSE file in project root for details.
 */

package in.praj.squizzel.data;

import in.praj.squizzel.model.QuestionBank;

import java.util.List;

/**
 * Data access object (DAO) to handle {@link QuestionBank} objects.
 */
public interface QuestionBankDao {
    List<QuestionBank> findAll();
    List<QuestionBank> findById(Integer id);
    long insert(QuestionBank bank);
    void update(QuestionBank bank);
    void delete(QuestionBank bank);
}
