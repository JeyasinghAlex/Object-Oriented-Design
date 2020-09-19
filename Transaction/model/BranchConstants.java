package Task.Transaction.model;

public class BranchConstants {

    public static final int SAVING_ACCOUNT_MINIMUM_BALANCE = 500;
    public static final int BUSINESS_ACCOUNT_MINIMUM_BALANCE = 5000;
    public static final int SAVING_ACCOUNT_INTEREST = 5;
    public static final int BUSINESS_ACCOUNT_INTEREST = 0;
    public static final int SAVING_DEBIT_PROCESSING_PERCENTAGE = 2;
    public static final int SAVING_CREDIT_PROCESSING_PERCENTAGE = 1;
    public static final int BUSINESS_DEBIT_PROCESSING_PERCENTAGE = 20;
    public static final int BUSINESS_CREDIT_PROCESSING_PERCENTAGE = 10;
    public static final String BANK_ACCOUNT_NUMBER_PREFIX = "IOBIN";
    public static final String WITHDRAW_OPERATION = "IO_DE_";
    public static final String DEPOSIT_OPERATION = "IO_CR_";
    public static final String TRANSFER_OPERATION = "IO_TR_";
    public static final int MINI_STATEMENT_COUNT = 10;
}
