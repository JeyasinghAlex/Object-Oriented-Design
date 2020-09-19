package Task.Transaction;

import Task.Transaction.enums.AccountType;
import Task.Transaction.enums.TransactionType;
import Task.Transaction.model.*;
import Task.Transaction.upi.GooglePayHandler;
import Task.Transaction.upi.NetBankingHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {

    private Scanner scan = new Scanner(System.in);
    private Branch branch = new Branch();
    private Map<String, String> login = new HashMap<>();

    public static void main(String[] args) {
        System.out.println("Welcome to bank website :- ");
        new Main().indexPage();
    }


    public void indexPage() {
        boolean isEnd = true;
        do {
            System.out.println();
            System.out.println("1 ) User  - ");
            System.out.println("2 ) Admin - ");
            System.out.println(" Otherwise Exit program");
            System.out.print("Enter your option - ");
            int option = scan.nextInt();
            if (option == 1) {
//                showUserOption();
                validation();
            } else if (option == 2) {
                showAdminOption();
            } else {
                isEnd = false;
            }
        } while (isEnd);
    }

    public void validation() {
        System.out.println("1 ) Sign up : ");
        System.out.println("2 ) Sign in : ");
        System.out.println("Otherwise exit program : ");
        System.out.print("Enter your option -  ");
        int option = scan.nextInt();
        scan.nextLine();
        if (option == 1) {
            System.out.print("Enter your user name - ");
            String userName = scan.nextLine();
            System.out.print("Enter your password - ");
            String pwd = scan.nextLine();
            login.put(userName, pwd);
        } else if (option == 2) {
            System.out.print("Enter your username - ");
            String userName = scan.nextLine();
            System.out.print("Enter your password - ");
            String pwd = scan.nextLine();
            boolean ans = login.containsKey(userName);
            if (ans) {
                String pass = login.get(userName);
                if (pwd.equals(pass)) {
                    System.out.println("* -----  login successfully  ----- *");
                    showUserOption();
                }
            }
            System.out.println("* -----  Incorrect user name or password  ----- *");
            indexPage();
        }
    }

    private void showAdminOption() {
        System.out.println();
        System.out.println("1 ) Search account by account number : ");
        System.out.println("2 ) Search account by pan number :");
        System.out.println("3 ) See all accounts : ");
        System.out.println("4 ) Show bank balance : ");
        System.out.print("Enter your option - ");
        int option = scan.nextInt();
        scan.nextLine();
        String accountNumber = "";
        switch (option) {
            case 1:
                System.out.print("Enter the account number - ");
                accountNumber = scan.nextLine();
                Account user = branch.getAccount(accountNumber);
                if (user != null) {
                    System.out.println(user.toString());
                } else {
                    System.out.println("Account not found ");
                }
                break;
            case 2:
                System.out.print("Enter the pan number - ");
                String panNumber = scan.nextLine();
                Customer customer = branch.getCustomerByPanNumber(panNumber);
                if (customer != null) {
                    System.out.println(customer.toString());
                } else {
                    System.out.println("Customer not found ");
                }
                break;
            case 3:
                List<Customer> customers = branch.getCustomers();
                for (Customer customer1 : customers) {
                    System.out.println(customer1);
                }
                break;
            case 4:
                int amount = HeadOffice.getInstance().getAmount();
                System.out.println("Bank balance is - " + amount);
                break;
            default:
                break;
        }
    }

    private void showUserOption() {
        System.out.println();
        System.out.println("1 ) Create Account :- ");
        System.out.println("2 ) Add UPI :- ");
        System.out.println("3 ) Balance Enquiry :- ");
        System.out.println("4 ) Withdraw :- ");
        System.out.println("5 ) Deposit :- ");
        System.out.println("6 ) Transfer :- ");
        System.out.println("7 ) Show mini statement :- ");
        System.out.println("8 ) Exit :- ");
        System.out.print("Please enter your  option - ");
        int option = scan.nextInt();
        scan.nextLine();
        switch (option) {
            case 1:
                createAccount();
                break;
            case 2:
                addUpi();
                break;
            case 3:
                balanceEnquiry();
                break;
            case 4:
                withdraw();
                break;
            case 5:
                deposit();
                break;
            case 6:
                transfer();
                break;
            case 7:
                getMiniStatement();
                break;
            default:
                System.out.println("Please enter the valid input :");
                break;
        }
        indexPage();
    }

    private String chooseAccountType() {
        boolean isEnd = true;
        do {
            System.out.println("1 ) Saving account :");
            System.out.println("2 ) Business account :");
            System.out.print("Enter your option - ");
            int option = scan.nextInt();
            switch (option) {
                case 1:
                    return AccountType.SAVING.toString();
                case 2:
                    return AccountType.BUSINESS.toString();
                default:
                    System.out.println("Please enter valid option :");
                    break;
            }
        } while (true);
    }

    private void createAccount() {
        System.out.print("Enter your panNumber - ");
        String panNumber = scan.nextLine();
        String accountType = chooseAccountType();
        System.out.print("Enter your open amount - ");
        int amount = scan.nextInt();
        String accountNumber = branch.createAccount(panNumber, accountType, amount);
        System.out.print("=> Your account number is - " + accountNumber);
    }

    private void balanceEnquiry() {
        System.out.print("Enter your account number - ");
        String accountNumber = scan.nextLine();
        Account account = branch.getAccount(accountNumber);
        if (account != null) {
            System.out.print("Your available balance is - " + account.getAvailableBalance());
        } else {
            System.out.println("* -----  Account not found  ----- *");
        }
    }

    private void withdraw() {
        System.out.print("Enter your account number - ");
        String accountNumber = scan.nextLine();
        Account account1 = branch.getAccount(accountNumber);
        System.out.print("Enter the withdraw amount - ");
        int amount = scan.nextInt();
        if (account1 != null) {
            TransactionEntry transaction = account1.withdraw(amount);
            if (transaction != null)
                System.out.print("Transaction receipt - " + transaction);
        } else {
            System.out.println("* -----  Account not found ----  *");
        }
    }

    private void deposit() {
        System.out.print("Enter your account number - ");
        String accountNumber = scan.nextLine();
        Account account2 = branch.getAccount(accountNumber);
        System.out.print("Enter the deposit amount - ");
        int amount = scan.nextInt();
        if (account2 != null) {
            TransactionEntry transaction = account2.deposit(amount);
            if (transaction != null)
                System.out.print("Transaction receipt - " + transaction);
        } else {
            System.out.println("Account not found");
        }
    }

    private void getMiniStatement() {
        System.out.print("Enter the account number -  ");
        String accountNumber = scan.nextLine();
        Account ac = branch.getAccount(accountNumber);
        if (ac != null) {
            List<TransactionEntry> entries = ac.getMiniStatement();
            for (TransactionEntry tr : entries) {
                System.out.println(tr.toString());
            }
        } else {
            System.out.println("* -----  Account not found  ----- *");
        }
    }

    private void transfer() {
        System.out.println("1 ) Net banking  transaction :- ");
        System.out.println("2 ) Google pay transaction :- ");
        System.out.print("Enter your option - ");
        int option = scan.nextInt();
        switch (option) {
            case 1:
                makeTransferNetBanking(TransactionType.NET_BANKING);
                break;
            case 2:
                makeTransferUPI(TransactionType.GOOGLE_PAY);
                break;
            default:
                break;
        }
    }

    private void makeTransferNetBanking(TransactionType type) {
        scan.nextLine();
        System.out.print("Enter the FROM account number - ");
        String acc1 = scan.nextLine();
        Account from = branch.getAccount(acc1);
        System.out.print("Enter the TO account number - ");
        String acc2 = scan.nextLine();
        Account to = branch.getAccount(acc2);
        if (from == null || to == null || from.getUpIhandlers() == null || !from.getUpIhandlers().containsKey(type)) {
            System.out.println("* -----   Account not fount   ----- *");
            return;
        }
        System.out.print("Enter the transaction amount - ");
        int amount = scan.nextInt();
        Transaction transaction = new Transaction.Builder().from(from).to(to).amount(amount).type(type).build();
        TransactionEntry entry = from.transact(transaction);
        if (entry != null)
            System.out.println(entry);
    }

    private void makeTransferUPI(TransactionType type) {
        scan.nextLine();
        System.out.print("Enter the FROM account number - ");
        String acc1 = scan.nextLine();
        Account from = branch.getAccount(acc1);
        System.out.print("Enter the TO account number - ");
        String acc2 = scan.nextLine();
        Account to = branch.getAccount(acc2);
        if (from == null || to == null || from.getUpIhandlers() == null || to.getUpIhandlers() == null
                ||!from.getUpIhandlers().containsKey(type) || !to.getUpIhandlers().containsKey(type)) {
            System.out.println("* -----   Account not fount   ----- *");
            return;
        }
        System.out.print("Enter the transaction amount - ");
        int amount = scan.nextInt();
        Transaction transaction = new Transaction.Builder().from(from).to(to).amount(amount).type(type).build();
        TransactionEntry entry = from.transact(transaction);
        if (entry != null)
            System.out.println(entry);
    }

    private void addUpi() {
        System.out.println("1 ) NetBanking :- ");
        System.out.println("2 ) GooglePay :-");
        System.out.print("Enter your option - ");
        int option = scan.nextInt();
        scan.nextLine();
        switch(option) {
            case 1:
                System.out.print("Enter your account number - ");
                String ac = scan.nextLine();
                Account account = branch.getAccount(ac);
                if (account != null) {
                    NetBankingHandler googlePay = new NetBankingHandler();
                    account.addUpi(TransactionType.NET_BANKING, googlePay);
                    System.out.println(" ---  Successfully added UPI ---  ");
                }else {
                    System.out.println("* -----  account not found  ----- *");
                }
                break;
            case 2:
                System.out.print("Enter your account number - ");
                String ac1 = scan.nextLine();
                Account account1 = branch.getAccount(ac1);

                if (account1 != null) {
                    GooglePayHandler googlePay = new GooglePayHandler();
                    account1.addUpi(TransactionType.GOOGLE_PAY, googlePay);
                    System.out.println(" ---  Successfully added UPI ---  ");
                }else {
                    System.out.println("* -----  account not found  ----- *");
                }
                break;
            default:
                System.out.println("* -----  Please, enter the valid option :  ----- *");
                break;
        }
    }
}