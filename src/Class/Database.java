package Class;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

public class Database {

    public static Connection openConnection() {
        String url = "jdbc:mysql://localhost:3306/vsms_local";
        String user = "root";
        String password = "12345678";

        Connection connect = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connect = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Database Connected Unsuccessful");
        }

        return connect;
    }
    
    public static Connection openCloudConnection() {
        String url = "jdbc:mysql://localhost:3306/vsms_cloud";
        String user = "root";
        String password = "12345678";

        Connection connect = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connect = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Database Connected Unsuccessful");
        }

        return connect;
    }
}
