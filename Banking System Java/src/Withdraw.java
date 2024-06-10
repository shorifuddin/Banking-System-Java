import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Withdraw extends Thread {

    private String accountNumber;
    private int amount;

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public void run() {
        Connection conn = DBconnection.getInstance().getConnection();
        String selectQuery = "SELECT * FROM banking_system.accounts WHERE accountNumber=?";
        String updateQuery = "UPDATE banking_system.accounts SET balance=? WHERE accountNumber=?";

        try {
            // Retrieve account details
            PreparedStatement selectStmt = conn.prepareStatement(selectQuery);
            selectStmt.setString(1, accountNumber);
            ResultSet resultSet = selectStmt.executeQuery();

            if (resultSet.next()) {
                int currentBalance = resultSet.getInt("balance");
                if (currentBalance < amount) {
                    System.out.println("Insufficient funds");
                } else {
                    // Update the account balance
                    currentBalance -= amount;
                    PreparedStatement updateStmt = conn.prepareStatement(updateQuery);
                    updateStmt.setInt(1, currentBalance);
                    updateStmt.setString(2, accountNumber);
                    updateStmt.executeUpdate();
                    System.out.println("Withdrawal successful");
                }
            }
        } catch (Exception e) {
            System.out.println(e.getCause());
        }
    }

}
