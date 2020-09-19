package Task.Transaction.model;

import Task.Transaction.enums.AccountType;
import Task.Transaction.enums.TransactionType;

public class SavingAccount extends Account {

    private static int debitProcessingPercentage = BranchConstants.SAVING_DEBIT_PROCESSING_PERCENTAGE;
    private static int creditProcessingPercentage = BranchConstants.SAVING_CREDIT_PROCESSING_PERCENTAGE;

    public SavingAccount(String accountNumber) {
        super(accountNumber);
    }
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
        return AccountType.SAVING;
    }

//    @Override
//    public boolean transfer(Account to, int amount) {
//        return ((TransactionHandler) amt -> {
//            return amt * DEBIT_PROCESSING_PERCENTAGE / 100;
//        }).processTransaction(this, to, amount, TransactionType.TRANSFER);
//    }
}


