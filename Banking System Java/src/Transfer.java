import java.lang.Thread;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Transfer extends Thread {
    private String fromAccountNumber;
    private String toAccountNumber;
    private int amount;
    private int senderId;
    private int receiverId;

    public void setFromAccountNumber(String fromAccountNumber) {
        this.fromAccountNumber = fromAccountNumber;
    }

    public void setToAccountNumber(String toAccountNumber) {
        this.toAccountNumber = toAccountNumber;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public void run() {
        Connection conn = DBconnection.getInstance().getConnection();
        String selectQuery = "SELECT * FROM banking_system.accounts WHERE accountNumber=?";
        String updateQuery = "UPDATE banking_system.accounts SET balance = ? WHERE accountNumber=?";

        try {
            // Deduct amount from sender's account
            PreparedStatement fromAccountStmt = conn.prepareStatement(selectQuery);
            fromAccountStmt.setString(1, fromAccountNumber);
            ResultSet fromAccountResult = fromAccountStmt.executeQuery();
            if (fromAccountResult.next()) {
                int senderBalance = fromAccountResult.getInt("balance");
                this.senderId = fromAccountResult.getInt("account_id");
                senderBalance -= amount;

                PreparedStatement updateSenderStmt = conn.prepareStatement(updateQuery);
                updateSenderStmt.setInt(1, senderBalance);
                updateSenderStmt.setString(2, fromAccountNumber);
                updateSenderStmt.executeUpdate();

            }

            // Add amount to receiver's account
            PreparedStatement toAccountStmt = conn.prepareStatement(selectQuery);
            toAccountStmt.setString(1, toAccountNumber);
            ResultSet toAccountResult = toAccountStmt.executeQuery();
            if (toAccountResult.next()) {
                int receiverBalance = toAccountResult.getInt("balance");
                this.receiverId = toAccountResult.getInt("account_id");
                receiverBalance += amount;

                PreparedStatement updateReceiverStmt = conn.prepareStatement(updateQuery);
                updateReceiverStmt.setInt(1, receiverBalance);
                updateReceiverStmt.setString(2, toAccountNumber);
                updateReceiverStmt.executeUpdate();

            }

            System.out.println("Transfer successful");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
