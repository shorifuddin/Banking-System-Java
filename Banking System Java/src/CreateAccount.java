import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

public class CreateAccount extends Thread {
    private String accountHolderName;
    private int initialBalance;

    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }

    public void setInitialBalance(int initialBalance) {
        this.initialBalance = initialBalance;
    }

    @Override
    public void run() {
        Connection conn = DBconnection.getInstance().getConnection();
        String insertQuery = "INSERT INTO accounts (account_holder_name, balance) VALUES (?, ?)";

        try {
            PreparedStatement insertStmt = conn.prepareStatement(insertQuery);
            insertStmt.setString(1, accountHolderName);
            insertStmt.setInt(2, initialBalance);
            insertStmt.executeUpdate();
            System.out.println("Account successfully created.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
