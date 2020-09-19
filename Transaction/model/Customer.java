package Task.Transaction.model;

import java.util.ArrayList;
import java.util.List;

public class Customer {

    private List<Account> accounts;
    private String panNumber;

    public List<Account> getAccounts() {
        return accounts;
    }

    public void addAccounts(Account account) {
        if (accounts == null) {
            accounts = new ArrayList<>();
        }
        this.accounts.add(account);
    }

    public String getPanNumber() {
        return panNumber;
    }

    public void setPanNumber(String panNumber) {
        this.panNumber = panNumber;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "accounts=" + accounts +
                ", panNumber='" + panNumber + '\'' +
                '}';
    }
}
