package packages.signup;
import packages.connectiondb.*;
import java.sql.*;
import java.sql.CallableStatement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;



public class DatabaseOracle {

    public static void createProfilInDB(String firstName,String familyName,String email,String password,String classification,String wayOfConnection,Integer telephoneNb,String city,String adress,Integer cityCode,String job,java.sql.Date dateOfBirth) throws Exception {
       

        Connection conn = DatabaseConnection.getConnection();
               
        CallableStatement request= conn.prepareCall("{CALL CREATEUSER1(?,?,?,?,?,?,?,?,?,?,?,?)}");
        
        
        request.setString(1,firstName);
        request.setString(2,familyName);
        request.setString(3,email);
        request.setString(4,password);
        request.setString(5,classification);
        request.setString(6,wayOfConnection);
        request.setInt(7,telephoneNb);
        request.setString(8,city);
        request.setString(9,adress);
        request.setInt(10,cityCode);
        request.setString(11,job);
        request.setDate(12,dateOfBirth);

        request.executeUpdate();
        request.close();
        conn.close();
    }

   

}