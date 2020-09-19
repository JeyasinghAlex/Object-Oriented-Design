package Task.Transaction.model;

import Task.Transaction.enums.AccountType;
import Task.Transaction.enums.TransactionType;

public class BusinessAccount extends Account {

    private static int debitProcessingPercentage = BranchConstants.BUSINESS_DEBIT_PROCESSING_PERCENTAGE;
    private static int creditProcessingPercentage = BranchConstants.BUSINESS_CREDIT_PROCESSING_PERCENTAGE;
    public BusinessAccount(String accountNumber) {
        super(accountNumber);
    }

    /**
     *
     * @param amount
     * @return
     */
    @Override
    public TransactionEntry withdraw(int amount) {
        Transaction transaction = new Transaction.Builder().from(this).amount(amount).type(TransactionType.WITH_DRAW).build();
        return ((TransactionHandler) amt -> {
            int count = 0;
            for (int i = 0; i < this.getEntries().size(); ++i) {
                if (this.getEntries().get(i).getTransaction().getType().equals(TransactionType.WITH_DRAW))
                    count++;
            }
            if (count >= 5)
                debitProcessingPercentage = (count + debitProcessingPercentage) / 2;
            return amt * debitProcessingPercentage / 100;
        }).processTransaction(transaction);
    }

    @Override
    public TransactionEntry deposit(int amount) {
        Transaction transaction = new Transaction.Builder().from(this).amount(amount).type(TransactionType.DEPOSIT).build();
        return ((TransactionHandler) amt -> {
            int count = 0;
            for (int i = 0; i < this.getEntries().size(); ++i) {
                if (this.getEntries().get(i).getTransaction().getType().equals(TransactionType.DEPOSIT))
                    count++;
            }
            if (count >= 5)
                creditProcessingPercentage = (count + creditProcessingPercentage / 2) / 2;
            return amt * creditProcessingPercentage / 100;
        }).processTransaction(transaction);
    }

    @Override
    public AccountType getType() {
        return AccountType.BUSINESS;
    }
}