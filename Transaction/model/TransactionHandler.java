package Task.Transaction.model;

import Task.Transaction.enums.TransactionType;

import java.util.concurrent.atomic.AtomicInteger;

public interface TransactionHandler {

    AtomicInteger TRANSACTION_ID_GENERATOR = new AtomicInteger(0);

    /**
     * In this method purpose of withdraw or deposit amount ....
     * During this transaction process something happen wrong catch block will execute and process will reset......
     * @param transaction
     * @return
     */
    default TransactionEntry processTransaction(Transaction transaction) {
        int balance = transaction.getFrom().getAvailableBalance();
        TransactionEntry entry = null;
        try {
            if (transaction.getType().equals(TransactionType.WITH_DRAW)) {
                if (transaction.getFrom().getAvailableBalance() - transaction.getAmount() < transaction.getFrom().getMinimumBalance()) {
                    System.out.println("* -----   Transaction failed, please maintain minimum balance   ----- *");
                    return null;
                }
                int availableBalance = transaction.getFrom().getAvailableBalance() - transaction.getAmount();
                int transactionCharge = calculateProcessingAmount(transaction.getAmount());

                /*Added Bank Charge*/
                HeadOffice.getInstance().setAmount(transactionCharge);
                transaction.getFrom().setAvailableBalance(availableBalance - transactionCharge);
                String transactionId = BranchConstants.WITHDRAW_OPERATION + TRANSACTION_ID_GENERATOR.incrementAndGet();
                entry = new TransactionEntry(transactionId, transaction, transactionCharge);
                transaction.getFrom().addEntry(entry);
            } else if (transaction.getType().equals(TransactionType.DEPOSIT)) {
                if (transaction.getAmount() < 0) {
                    System.out.println("* ------  Deposit amount should be greater than 0, deposit amount:    ----- *");
                    return null;
                }
                int availableBalance = transaction.getFrom().getAvailableBalance() + transaction.getAmount();
                int transactionCharge = calculateProcessingAmount(transaction.getAmount());
                transaction.getFrom().setAvailableBalance(availableBalance - transactionCharge);
                String transactionId = BranchConstants.DEPOSIT_OPERATION + TRANSACTION_ID_GENERATOR.incrementAndGet();
                 entry = new TransactionEntry(transactionId, transaction, transactionCharge);
                transaction.getFrom().addEntry(entry);
            }
        } catch (Exception ex) {
            transaction.getFrom().setAvailableBalance(balance);
            System.out.println("* -----  Transaction failed due to technical issue :  ----- *");
            ex.printStackTrace();
        }
        return entry;
    }
    public int calculateProcessingAmount(int amount);
}


