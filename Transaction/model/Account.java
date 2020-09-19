package Task.Transaction.model;

import Task.Transaction.enums.AccountType;
import Task.Transaction.enums.TransactionType;

import java.util.*;

public abstract class Account {
    private String accountNumber;
    private int minimumBalance;
    private int availableBalance;
    private int interestRate;
    private List<TransactionEntry> entries;
    private Map<TransactionType, UPIhandler> upIhandlers;

    public Account(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public int getMinimumBalance() {
        return minimumBalance;
    }

    protected void setMinimumBalance(int minimumBalance) {
        this.minimumBalance = minimumBalance;
    }

    public int getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(int availableBalance) {
        this.availableBalance = availableBalance;
    }

    protected void setInterestRate(int interestRate) {
        this.interestRate = interestRate;
    }

    public int getInterestRate() {
        return interestRate;
    }

    public void addEntry(TransactionEntry entry) {
        if (entries == null) {
            entries = new ArrayList<>();
        }
        this.entries.add(entry);
    }

    public List<TransactionEntry> getEntries() {
        return entries;
    }

    public void addUpi(TransactionType type, UPIhandler upIhandler) {
        if (upIhandlers == null) {
            upIhandlers = new HashMap<>();
        }
        this.upIhandlers.put(type, upIhandler);
    }

    public Map<TransactionType, UPIhandler> getUpIhandlers() {
        return upIhandlers;
    }

    public List<TransactionEntry> getMiniStatement() {
        List<TransactionEntry> miniStatement = new ArrayList<>();
        int count = 0;
        ListIterator<TransactionEntry> listIter = entries.listIterator(entries.size());
        while (listIter.hasPrevious()) {
            miniStatement.add(listIter.previous());
            count++;
            if (count > BranchConstants.MINI_STATEMENT_COUNT) {
                break;
            }
        }
        return miniStatement;
    }

    /**
     * In these method purpose of connect the another application using interface
     * @param transaction
     * @return
     */
    public TransactionEntry transact(Transaction transaction) {
        UPIhandler upIhandler = upIhandlers.get(transaction.getType());
        return upIhandler.transact(transaction);
    }

    public abstract TransactionEntry deposit(int amount);

    public abstract TransactionEntry withdraw(int withdrawAmount);

    protected abstract AccountType getType();

    @Override
    public String toString() {
        return "Account{" +
                " accountNumber = '" + accountNumber +
                ", accountType = " + this.getType() +
                ", availableBalance = " + availableBalance +
                ", entries = " + entries +
                '}';
    }
}
