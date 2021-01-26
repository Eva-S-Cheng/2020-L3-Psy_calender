package packages.signup;
import java.util.Base64;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;


public class ModeleSignUp {

    String familyName;
    String firstName;
    String email;
    Integer telephoneNb;
    String password;
    String classification;
    String wayOfConnection;
    String city;
    String adress;
    Integer cityCode;
    java.sql.Date dateOfBirth;
    String job;


    public interface SetValue {
        void setValueFor(String value);
    }

    public SetValue SetFamilyName =  (String value) -> {  this.familyName = value;};

    public SetValue SetFirstName =  (String value) -> {  this.firstName = value;};

    public SetValue SetEmail =  (String value) -> {  this.email = value;};

    public SetValue SetJob =  (String value) -> {  this.job = value;};

    public SetValue SetPassword =  (String value)  -> {  
         // hash of the password using sha-256 and base64 to compare it to passwd in db
        try{
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(value.getBytes(StandardCharsets.UTF_8));
        byte[] encodedBytes = Base64.getEncoder().encode(hash);
        this.password = new String(encodedBytes);
        }
        catch(NoSuchAlgorithmException e){
            System.out.println("no such algo");
        }
        
        
    };

    public SetValue SetTelephoneNumber =  (String value) -> {  this.telephoneNb = Integer.parseInt(value);};

    public SetValue SetCity =  (String value) -> {  this.city = value;};

    public SetValue SetAdress =  (String value) -> {  this.adress = value;};

    public SetValue SetCityCode =  (String value) -> {  this.cityCode = Integer.parseInt(value);};

    public SetValue SetDateOfBirth =  (String value) -> { 
        
        String[] date =  value.split(" ");
        java.sql.Date sqlDate = java.sql.Date.valueOf(date[2]+"-"+date[1]+"-"+date[0]);
        this.dateOfBirth = sqlDate;
     } ;

    

    public SetValue SetClassification =  (String value) -> { 
        int index = Integer.parseInt(value);
        String[] classifications = {"Woman","Man","Couple"};
        this.classification = classifications[index];
    };
       
    public SetValue SetWayOfConnection =  (String value) -> {  this.wayOfConnection= value;};


    public void createProfil(){
        
        try{
        DatabaseOracle.createProfilInDB(this.firstName,this.familyName,this.email,this.password,this.classification,this.wayOfConnection,this.telephoneNb,this.city,this.adress,this.cityCode,this.job,this.dateOfBirth);
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

}