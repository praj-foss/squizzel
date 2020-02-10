/**
 * This file is licensed under the MIT license.
 * See the LICENSE file in project root for details.
 */

package in.praj.squizzel.model;

import lombok.Data;

/**
 * Model of the type of content in a question.
 */
@Data
public class QuestionType {
    private int id;
    private String name;
}
