import java.sql.Connection;
import java.sql.DriverManager;

public class DBconnection {
    private static DBconnection dBconnection;
    private Connection connection;
    private DBconnection(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/banking_system", "root", "password");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static DBconnection getInstance(){
        if(dBconnection == null){
            dBconnection = new DBconnection();
        }
        return dBconnection;
    }
    public Connection getConnection(){
        return connection;
    }
}