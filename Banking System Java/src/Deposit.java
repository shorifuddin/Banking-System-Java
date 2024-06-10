import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Deposit extends Thread {
    private String id;
    private int amount;

    public void setAccountNumber(String id) {
        this.id = id;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public void run() {
        Connection conn = DBconnection.getInstance().getConnection();
        String selectQuery = "SELECT * FROM banking_system.accounts WHERE account_id=?";
        String updateQuery = "UPDATE banking_system.accounts SET balance=? WHERE account_id=?";

        try {
            // Retrieve account details
            PreparedStatement selectStmt = conn.prepareStatement(selectQuery);
            selectStmt.setString(1, id);
            ResultSet resultSet = selectStmt.executeQuery();

            if (resultSet.next()) {
                int currentBalance = resultSet.getInt("balance");
                // Update the account balance
                currentBalance += amount;
                PreparedStatement updateStmt = conn.prepareStatement(updateQuery);
                updateStmt.setInt(1, currentBalance);
                updateStmt.setString(2, id);
                updateStmt.executeUpdate();
                System.out.println("Deposit successful");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
