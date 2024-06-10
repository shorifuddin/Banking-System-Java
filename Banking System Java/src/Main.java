//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        // Create Account
        CreateAccount createAccount = new CreateAccount();
        createAccount.setAccountHolderName("John Doe");
        createAccount.setInitialBalance(1000);
        createAccount.start();

        // Deposit
        Deposit deposit = new Deposit();
        deposit.setAccountNumber("1");
        deposit.setAmount(300);
        deposit.start();

        // transfer money
        Transfer transfer = new Transfer();
        transfer.setFromAccountNumber("1");
        transfer.setToAccountNumber("1");
        transfer.setAmount(500);
        transfer.start();

        // Withdraw money
        Withdraw withdraw = new Withdraw();
        withdraw.setAccountNumber("1");
        withdraw.setAmount(200);
        withdraw.start();

        // Account Details
        AccountDetails accountDetails = new AccountDetails();
        accountDetails.setAccountNumber("1");
        accountDetails.start();

    }
}