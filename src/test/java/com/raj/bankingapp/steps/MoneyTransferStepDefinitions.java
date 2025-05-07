package com.raj.bankingapp.steps;

import com.raj.bankingapp.exception.InsufficientFundsException;
import com.raj.bankingapp.model.Account;
import com.raj.bankingapp.service.TransferService;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

@SpringBootTest
public class MoneyTransferStepDefinitions {

    @Autowired
    private TransferService transferService;

    private Exception lastException;

    @Given("the following accounts exist:")
    public void theFollowingAccountsExist(DataTable dataTable) {
        List<Map<String, String>> accounts = dataTable.asMaps();

        for (Map<String, String> account : accounts) {
            String accountNumber = account.get("Account Number");
            String ownerName = account.get("Owner Name");
            double initialBalance = Double.parseDouble(account.get("Initial Balance"));

            transferService.addAccount(new Account(accountNumber, ownerName, initialBalance));
        }
    }

    @When("I transfer ${double} from account {string} to account {string}")
    public void iTransferMoneyFromAccountToAccount(Double amount, String sourceAccountNumber, String targetAccountNumber) {
        transferService.transfer(sourceAccountNumber, targetAccountNumber, amount);
    }

    @When("I try to transfer ${double} from account {string} to account {string}")
    public void iTryToTransferMoneyFromAccountToAccount(Double amount, String sourceAccountNumber, String targetAccountNumber) {
        try {
            transferService.transfer(sourceAccountNumber, targetAccountNumber, amount);
        } catch (Exception e) {
            lastException = e;
        }
    }

    @Then("the balance of account {string} should be ${double}")
    public void theBalanceOfAccountShouldBe(String accountNumber, Double expectedBalance) {
        Account account = transferService.getAccount(accountNumber);
        Assertions.assertEquals(expectedBalance, account.getBalance(), 0.01);
    }

    @Then("the transfer should fail with message {string}")
    public void theTransferShouldFailWithMessage(String expectedMessage) {
        Assertions.assertEquals(InsufficientFundsException.class, lastException.getClass());
        Assertions.assertEquals(expectedMessage, lastException.getMessage());
    }

    @Then("the balance of account {string} should remain ${double}")
    public void theBalanceOfAccountShouldRemain(String accountNumber, Double expectedBalance) {
        theBalanceOfAccountShouldBe(accountNumber, expectedBalance);
    }
}
