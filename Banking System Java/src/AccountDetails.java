import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountDetails extends Thread {
    private String id;

    public void setAccountNumber(String id) {
        this.id = id;
    }

    @Override
    public void run() {
        Connection connection = DBconnection.getInstance().getConnection();
        String selectQuery = "SELECT * FROM banking_system.accounts WHERE account_id=?";

        try {
            PreparedStatement selectStmt = connection.prepareStatement(selectQuery);
            selectStmt.setString(1, id);
            ResultSet resultSet = selectStmt.executeQuery();

            if (resultSet.next()) {
                displayAccountDetails(resultSet);
            } else {
                System.out.println("No account found with the given account number.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void displayAccountDetails(ResultSet resultSet) throws SQLException {
        String accountHolderName = resultSet.getString("account_holder_name");
        int balance = resultSet.getInt("balance");

        System.out.println("Account Holder Name: " + accountHolderName);
        System.out.println("Balance: " + balance);
    }

}
