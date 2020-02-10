/**
 * This file is licensed under the MIT license.
 * See the LICENSE file in project root for details.
 */

package in.praj.squizzel.model;

import lombok.Data;

/**
 * Model of a question. It stores the content of a
 * question as well as the correct answer.
 */
@Data
public class Question {
    private int id;
    private QuestionType type;
    private String content;
    private String answer;
}
