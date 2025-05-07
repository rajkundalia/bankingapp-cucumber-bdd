package com.raj.bankingapp.service;

import com.raj.bankingapp.exception.InsufficientFundsException;
import com.raj.bankingapp.model.Account;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class TransferService {
    private Map<String, Account> accounts = new HashMap<>();

    public void addAccount(Account account) {
        accounts.put(account.getAccountNumber(), account);
    }

    public Account getAccount(String accountNumber) {
        return accounts.get(accountNumber);
    }

    public void transfer(String sourceAccountNumber, String targetAccountNumber, double amount) {
        Account sourceAccount = accounts.get(sourceAccountNumber);
        Account targetAccount = accounts.get(targetAccountNumber);

        if (sourceAccount == null) {
            throw new IllegalArgumentException("Source account not found");
        }
        if (targetAccount == null) {
            throw new IllegalArgumentException("Target account not found");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("Transfer amount must be positive");
        }
        if (sourceAccount.getBalance() < amount) {
            throw new InsufficientFundsException("Insufficient funds for transfer");
        }

        sourceAccount.withdraw(amount);
        targetAccount.deposit(amount);
    }
}