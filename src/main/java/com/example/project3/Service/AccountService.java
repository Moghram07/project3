package com.example.project3.Service;

import com.example.project3.ApiException.ApiException;
import com.example.project3.Model.Account;
import com.example.project3.Model.Customer;
import com.example.project3.Repository.AccountRepository;
import com.example.project3.Repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public void addAccount(Account account, Integer customerId) {
        Customer c = customerRepository.findCustomerById(customerId);
        if (c == null) {
            throw new ApiException("Customer with id " + customerId + " not found");
        }
        account.setCustomer(c);
        accountRepository.save(account);
    }

    public void updateAccount( Integer customerId, Integer id, Account account) {
        Customer c = customerRepository.findCustomerById(customerId);
        Account a = accountRepository.findAccountById(id);
        if (a == null) {
            throw new ApiException("Account with id " + id + " not found");
        } else if (account.getCustomer().getId() != customerId) {
            a.setCustomer(account.getCustomer());
        }
        a.setAccountNumber(account.getAccountNumber());
        a.setBalance(account.getBalance());
        accountRepository.save(a);
    }

    public void deleteAccount(Integer customerId, Integer id) {
        Customer c = customerRepository.findCustomerById(customerId);
        Account a = accountRepository.findAccountById(id);
        if (a == null) {
            throw new ApiException("Account with id " + id + " not found");
        } else if (a.getCustomer().getId() != customerId) {
            throw new ApiException("Account not linked to customer");
        }
        accountRepository.delete(a);
    }
    // Method to deposit money into an account
    public void deposit(Integer customerId, Integer accountId, double amount) {
        Account account = accountRepository.findAccountById(accountId);
        if (account == null) {
            throw new ApiException("Account with id " + accountId + " not found");
        } else if (!account.getCustomer().getId().equals(customerId)) {
            throw new ApiException("Account not linked to customer");
        }
        if (amount <= 0) {
            throw new ApiException("Deposit amount must be greater than zero");
        }
        account.setBalance(account.getBalance() + amount);
        accountRepository.save(account);
    }

    // Method to withdraw money from an account
    public void withdraw(Integer customerId, Integer accountId, double amount) {
        Account account = accountRepository.findAccountById(accountId);
        if (account == null) {
            throw new ApiException("Account with id " + accountId + " not found");
        } else if (!account.getCustomer().getId().equals(customerId)) {
            throw new ApiException("Account not linked to customer");
        }
        if (amount <= 0) {
            throw new ApiException("Withdrawal amount must be greater than zero");
        }
        if (account.getBalance() < amount) {
            throw new ApiException("Insufficient balance");
        }
        account.setBalance(account.getBalance() - amount);
        accountRepository.save(account);
    }

    // Method to transfer funds between two accounts
    public void transferFunds(Integer fromCustomerId, Integer fromAccountId, Integer toAccountId, double amount) {
        Account fromAccount = accountRepository.findAccountById(fromAccountId);
        Account toAccount = accountRepository.findAccountById(toAccountId);

        if (fromAccount == null) {
            throw new ApiException("From account with id " + fromAccountId + " not found");
        } else if (!fromAccount.getCustomer().getId().equals(fromCustomerId)) {
            throw new ApiException("From account not linked to customer");
        }
        if (toAccount == null) {
            throw new ApiException("To account with id " + toAccountId + " not found");
        }
        if (amount <= 0) {
            throw new ApiException("Transfer amount must be greater than zero");
        }
        if (fromAccount.getBalance() < amount) {
            throw new ApiException("Insufficient balance in the source account");
        }

        // Perform the transfer
        fromAccount.setBalance(fromAccount.getBalance() - amount);
        toAccount.setBalance(toAccount.getBalance() + amount);

        // Save the updated accounts
        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);
    }

        // Method to block a bank account
        public void blockAccount(Integer accountId, Integer customerId) {
        Customer customer = customerRepository.findCustomerById(customerId);
        Account account = accountRepository.findAccountById(accountId);
        if (account == null) {
            throw new ApiException("Account with id " + accountId + " not found");
        } else if(account.getCustomer().getId() != customerId) {
            throw new ApiException("Account not linked to customer");
        }
        account.setActive(false);  // Set the account to inactive, indicating it's blocked
        accountRepository.save(account);
    }

}
