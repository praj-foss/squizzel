/**
 * This file is licensed under the MIT license.
 * See the LICENSE file in project root for details.
 */

package in.praj.squizzel.model;

import lombok.Data;

import java.util.List;

/**
 * Model of a question bank. This is used to represent
 * a set of related questions.
 */
@Data
public class Quiz {
    private String id;
    private String name;
    private List<Question> questions;
}
