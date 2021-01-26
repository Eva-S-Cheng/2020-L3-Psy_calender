package packages.connectiondb;
import java.sql.*;
public class DatabaseConnection {
    
    private static Connection connection = null;

    public static String ip = "jdbc:oracle:thin:@192.168.56.102:1521:orclcdb";
    public static String user = "system";
    public static String passwd = "oracle";
    
    private DatabaseConnection(){
      
       
    };

    public static Connection getConnection() throws Exception{
        Class.forName("oracle.jdbc.driver.OracleDriver");
    
        return DriverManager.getConnection(ip, user,
            passwd);
    }

     
}