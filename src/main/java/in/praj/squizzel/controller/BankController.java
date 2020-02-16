/**
 * This file is licensed under the MIT license.
 * See the LICENSE file in project root for details.
 */

package in.praj.squizzel.controller;

import in.praj.squizzel.data.QuestionBankDao;
import in.praj.squizzel.model.QuestionBank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Endpoints for question banks.
 */
@RestController
public class BankController {
    @Autowired
    private QuestionBankDao bankDao;

    @GetMapping("/banks")
    List<QuestionBank> banks() {
        return bankDao.findAll();
    }

    @PostMapping("/banks")
    QuestionBank createBank(@RequestBody QuestionBank bank) {
        bank.setId(bankDao.insert(bank));
        return bank;
    }
}
