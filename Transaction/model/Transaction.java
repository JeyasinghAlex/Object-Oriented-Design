package Task.Transaction.model;

import Task.Transaction.enums.TransactionType;

public class Transaction {

    private Account from;
    private Account to;
    private int amount;
    private TransactionType type;

    private Transaction(Builder builder) {
        this.from = builder.from;
        this.to = builder.to;
        this.amount = builder.amount;
        this.type = builder.type;
    }

    public Account getFrom() {
        return from;
    }

    public Account getTo() {
        return to;
    }

    public int getAmount() {
        return amount;
    }

    public TransactionType getType() {
        return type;
    }

    @Override
    public String toString() {

        if (to != null)
            return "Transaction{" + ", from = " +
                    from.getAccountNumber() + ", to = " + to.getAccountNumber() + ", amount = " + amount + ", type = " + type + '}';

        return "Transaction{" + ", from = " +
                from.getAccountNumber() + ", amount = " + amount + ", type = " + type + '}';
    }


    public static class Builder {
        private String transactionId;
        private Account from;
        private Account to;
        private int amount;
        private TransactionType type;

        public Builder from(Account from) {
            this.from = from;
            return this;
        }

        public Builder to(Account to) {
            this.to = to;
            return this;
        }

        public Builder amount(int amount) {
            this.amount = amount;
            return this;
        }

        public Builder type(TransactionType type) {
            this.type = type;
            return this;
        }

        public Transaction build() {
            Transaction transaction = new Transaction(this);
            /* To check some validation :- */
            return transaction;
        }
    }
}
