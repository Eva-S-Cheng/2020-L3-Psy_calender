

package packages.authentification;  
import packages.connectiondb.*;
 import java.sql.*;
 import java.sql.CallableStatement;
 import java.text.DateFormat;
 import java.text.SimpleDateFormat;


public class DatabaseAuthentification {

        public static Integer verifyCredentials(String email,String passwd) throws Exception {
               
            Connection conn = DatabaseConnection.getConnection();
                   
            CallableStatement request= conn.prepareCall("{ ? = call AUTHENTIFICATION(?,?) }");
            
            
            
            request.registerOutParameter(1, Types.INTEGER);
            request.setString(2,email);
            request.setString(3,passwd);
           
            request.executeUpdate();

            Integer result = request.getInt(1);
    
            request.close();
            conn.close();

            return result;
        }
    
       
    
}