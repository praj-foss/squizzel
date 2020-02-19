/**
 * This file is licensed under the MIT license.
 * See the LICENSE file in project root for details.
 */

package in.praj.squizzel.controller;

import in.praj.squizzel.data.QuizDao;
import in.praj.squizzel.model.Quiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/**
 * Endpoints for managing quizzes.
 */
@RestController
public class QuizController {
    @Autowired
    private QuizDao quizDao;

    @GetMapping("/quizzes")
    List<Quiz> quizzes() {
        return quizDao.findAll();
    }

    @GetMapping("/quizzes/{id}")
    Optional<Quiz> quizById(@PathVariable String id) {
        return quizDao.findById(id);
    }

    @PostMapping("/quizzes")
    Quiz createQuiz(@RequestBody Quiz quiz) {
        quiz.setId(quizDao.insert(quiz));
        return quiz;
    }
}
