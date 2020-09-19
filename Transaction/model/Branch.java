package Task.Transaction.model;

import Task.Transaction.enums.AccountType;
import Task.Transaction.enums.TransactionType;


import java.util.ArrayList;
import java.util.List;

public class Branch {
    private String ifsc;
    private List<Account> accounts;
    private List<Customer> customers;


    public String getIfsc() {
        return ifsc;
    }

    public void setIfsc(String ifsc) {
        this.ifsc = ifsc;
    }

    private void addAccounts(Account account) {
        if (accounts == null) {
            accounts = new ArrayList<>();
        }
        this.accounts.add(account);
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void addCustomers(Customer customer) {
        if (customers == null) {
            customers = new ArrayList<>();
        }
        this.customers.add(customer);
    }

    public String createAccount(String panNumber, String accountType, int amount) {
        Account newAccount = null;
        String accountNumber = BranchConstants.BANK_ACCOUNT_NUMBER_PREFIX + panNumber
                + TransactionHandler.TRANSACTION_ID_GENERATOR.incrementAndGet();
        if (accountType.equals(AccountType.SAVING.toString())) {
            newAccount = new SavingAccount(accountNumber);
            newAccount.setMinimumBalance(BranchConstants.SAVING_ACCOUNT_MINIMUM_BALANCE);
            newAccount.setInterestRate(BranchConstants.SAVING_ACCOUNT_INTEREST);
        }else {
            newAccount = new BusinessAccount(accountNumber);
            newAccount.setMinimumBalance(BranchConstants.BUSINESS_ACCOUNT_MINIMUM_BALANCE);
            newAccount.setInterestRate(BranchConstants.BUSINESS_ACCOUNT_INTEREST);
        }
        newAccount.setAvailableBalance(amount);
        String transactionId = BranchConstants.DEPOSIT_OPERATION + TransactionHandler.TRANSACTION_ID_GENERATOR.incrementAndGet();
        Transaction transaction = new Transaction.Builder().from(newAccount).amount(amount).type(TransactionType.DEPOSIT).build();
        TransactionEntry entry = new TransactionEntry(transactionId, transaction);
        newAccount.addEntry(entry);

        Customer alreadyCustomer = customerPresentWithPanNumber(panNumber);

        if (alreadyCustomer != null) {
            System.out.println("* -----  Customer is already present with the pan number, added another account  ----- *");
            alreadyCustomer.addAccounts(newAccount);
        } else {
            Customer newCustomer = new Customer();
            newCustomer.setPanNumber(panNumber);
            newCustomer.addAccounts(newAccount);
            addCustomers(newCustomer);
        }
        addAccounts(newAccount);
        return accountNumber;
    }

    public Account getAccount(String accountNumber) {
        for (Account account : accounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                return account;
            }
        }
        return null;
    }

    public Customer getCustomerByPanNumber(String panNumber) {
        for (Customer customer : customers) {
            if (customer.getPanNumber().equals(panNumber)) {
                return customer;
            }
        }
        return null;
    }

    private Customer customerPresentWithPanNumber(String panNumber) {
        if (null != customers) {
            for (Customer customer : customers) {
                if (customer.getPanNumber().equals(panNumber)) {
                    return customer;
                }
            }
        }
        return null;
    }
}
