

package packages.authentification;
import java.util.Base64;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;
import java.util.List; 

public class ModelAuthentification {

    private String email;
    private String passwd;
    private Integer id;

    public interface SetValue {
        void setValueFor(String value);
    }

    public SetValue SetEmail =  (String value) -> {  this.email = value;};

    public SetValue SetPasswd =  (String value) -> { 
        // hash of the password using sha-256 and base64 to compare it to passwd in db
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(value.getBytes(StandardCharsets.UTF_8));
            byte[] encodedBytes = Base64.getEncoder().encode(hash);
            this.passwd = new String(encodedBytes);
            }
            catch(NoSuchAlgorithmException e){
                System.out.println("no such algo");
            }
        
        
    };

    public void setId(Integer id){
        this.id = id;
    }

    public Integer getId(){
        return this.id;
    }

    public Integer verifyCredentialInDb(){
        Integer access = -1;
        try{
            access = DatabaseAuthentification.verifyCredentials(this.email,this.passwd);
        }
        catch(Exception e){
           
            System.out.println("");
           
        }

        return access;
    }

    public void setUserId(Integer id){
        this.id = id;
    }
}