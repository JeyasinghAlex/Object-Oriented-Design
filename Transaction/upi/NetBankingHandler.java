package Task.Transaction.upi;

import Task.Transaction.enums.TransactionType;
import Task.Transaction.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NetBankingHandler implements UPIhandler {

    @Override
    public TransactionEntry transact(Transaction transaction) {

        List<TransactionEvent> event = new ArrayList<>();
        Random rn = new Random();
        int answer = rn.nextInt(10) + 1;
        TransactionEntry entry = null;
        try {
            if (transaction.getFrom().getAvailableBalance() - transaction.getAmount() < transaction.getFrom().getMinimumBalance()) {
                System.out.println("* -----   Transaction failed, Please maintain minimum balance   ----- *");
                return null;
            }
            event.add(new TransactionEvent(transaction.getFrom(), TransactionType.WITH_DRAW, transaction.getAmount()));
            transaction.getFrom().setAvailableBalance(transaction.getFrom().getAvailableBalance() - transaction.getAmount());
            event.add(new TransactionEvent(transaction.getTo(), TransactionType.DEPOSIT, transaction.getAmount()));
            transaction.getTo().setAvailableBalance(transaction.getTo().getAvailableBalance() + transaction.getAmount());
            entry = addEntry(transaction);
        } catch (Exception ex) {
            for (int i = 0; i < event.size(); ++i) {
                if (event.get(i).getType().equals(TransactionType.WITH_DRAW)) {
                    transaction.getFrom().setAvailableBalance(transaction.getFrom().getAvailableBalance() + event.get(i).getAmount());
                    String transactionId = BranchConstants.DEPOSIT_OPERATION + TransactionHandler.TRANSACTION_ID_GENERATOR.incrementAndGet();
                    TransactionEntry entry1 = new TransactionEntry(transactionId, transaction);
                    transaction.getFrom().addEntry(entry1);
                } else {
                    event.get(i).getAccount().setAvailableBalance(event.get(i).getAccount().getAvailableBalance() - event.get(i).getAmount());
                    String transactionId = BranchConstants.DEPOSIT_OPERATION + TransactionHandler.TRANSACTION_ID_GENERATOR.incrementAndGet();
                    TransactionEntry entry1 = new TransactionEntry(transactionId, transaction);
                    transaction.getFrom().addEntry(entry1);
                }
                System.out.println("Transaction failed, due to some technical issue ");
                return null;
            }
        }
        return entry;
    }

    public TransactionEntry addEntry(Transaction transaction) {
        String transactionId = BranchConstants.TRANSFER_OPERATION + TransactionHandler.TRANSACTION_ID_GENERATOR.incrementAndGet();
        TransactionEntry entryFrom = new TransactionEntry(transactionId, transaction);
        String transactionId1 = BranchConstants.TRANSFER_OPERATION + TransactionHandler.TRANSACTION_ID_GENERATOR.incrementAndGet();
        TransactionEntry entryTo = new TransactionEntry(transactionId1, transaction);
        transaction.getFrom().addEntry(entryFrom);
        transaction.getTo().addEntry(entryTo);
        return entryFrom;
    }
}
