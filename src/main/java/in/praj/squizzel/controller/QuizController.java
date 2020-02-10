/**
 * This file is licensed under the MIT license.
 * See the LICENSE file in project root for details.
 */

package in.praj.squizzel.controller;

import in.praj.squizzel.data.QuestionBankDao;
import in.praj.squizzel.model.QuestionBank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Endpoints for question banks.
 */
@Controller
public class QuizController {
    @Autowired
    private QuestionBankDao bankDao;

    @RequestMapping("/banks")
    @ResponseBody
    List<QuestionBank> banks() {
        return bankDao.findAll();
    }
}
