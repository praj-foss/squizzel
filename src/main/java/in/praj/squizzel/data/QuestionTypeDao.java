/**
 * This file is licensed under the MIT license.
 * See the LICENSE file in project root for details.
 */

package in.praj.squizzel.data;

import in.praj.squizzel.model.QuestionType;

import java.util.List;

/**
 * Data access object (DAO) to handle {@link QuestionType} objects.
 */
public interface QuestionTypeDao {
    List<QuestionType> findAll();
    QuestionType findById(int id);
}
