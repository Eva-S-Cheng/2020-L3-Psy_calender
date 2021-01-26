package packages.signup;
import java.util.Scanner;
import java.text.DateFormat;
import java.util.Date;
import java.text.SimpleDateFormat;

public class ControllerSignUp {

    private final ModeleSignUp model;
    private final ViewSignUp view;
    
    Scanner input = new Scanner(System.in);

    public ControllerSignUp(ViewSignUp view,ModeleSignUp model){
        this.model = model;
        this.view = view;
    }


    public interface CheckValue {
        boolean checkValueFor(String value);
    }

    public CheckValue FamilyNameCheck = (String value) -> { return  value.length() > 25 ||  value == null;};
    public CheckValue FirstNameCheck = (String value) -> { return  value.length() > 25 ||  value == null;};
    public CheckValue EmailCheck = (String value) -> { return  value.length() > 50 ||  value == null;};
    public CheckValue PasswordCheck = (String value) -> { return  value.length() < 4 ||  value == null;};
    public CheckValue ClassificationCheck = (String value) -> { return ( value.equals("1") || value.equals("2") || value.equals("3") ) ?  false : true;};
    public CheckValue WayOfConnectionCheck = (String value) -> { return  value.length() > 60 ||  value == null;};
    public CheckValue TelephoneNumberCheck = (String value) -> { 
        Boolean index = false;
        try{
            Integer  nb = Integer.parseInt(value);

            
        }
        catch(Exception e){
            index = true;
        }
        
        return value.length() != 10 ? true:false;
    };

    public CheckValue CityCheck = (String value) -> { return  value.length() > 25 ||  value == null;};

    public CheckValue CityCodeCheck = (String value) -> {   
    
    Boolean index = false;
    try{
        Integer  nb = Integer.parseInt(value);   
    }
    catch(Exception g){
        index = true;
    }

    return (index || (value.length() != 5) )? true : false;
  

    };

    public CheckValue AdressCheck = (String value) -> { return  value.length() > 30 ||  value == null;};

    public CheckValue JobCheck = (String value) -> { return  value.length() > 60 ||  value == null;};

    public CheckValue DateOfBirthCheck = (String value) -> { 
        
        //dd mm yyyy
        

        DateFormat formatter = new SimpleDateFormat("dd MM yyyy");

        Boolean index = false;
        try{
            Date date = formatter.parse(value);
        }
        catch(Exception f){
            index = true;
        }
            
        
        return index ;
        
        
       };
    
    public void operate(ViewSignUp.AskPatient displayer,CheckValue checker,ModeleSignUp.SetValue setter){
        boolean valueNotCorrect;
        valueNotCorrect = true;
        String value;
        while(valueNotCorrect){
            displayer.askFor();
            value = input.nextLine();
           
            if (checker.checkValueFor(value)){
                this.view.displayErrorMessageInput();
            }
            else{
                valueNotCorrect = false;
                setter.setValueFor(value);
            }
            
        }
    }

    public void pushDataToDB(){
        this.model.createProfil();
    }


    public void formUser(){
        this.operate(view.FirstName,this.FirstNameCheck,model.SetFirstName);

        this.operate(view.FamilyName,this.FamilyNameCheck,model.SetFamilyName);

        this.operate(view.Email,this.EmailCheck,model.SetEmail);

        this.operate(view.Password,this.PasswordCheck,model.SetPassword);

        this.operate(view.TelephoneNumber,this.TelephoneNumberCheck,model.SetTelephoneNumber);
        
        this.operate(view.City,this.CityCheck,model.SetCity);

        this.operate(view.Adress,this.AdressCheck,model.SetAdress);

        this.operate(view.CityCode,this.CityCodeCheck,model.SetCityCode);

        this.operate(view.DateOfBirth,this.DateOfBirthCheck,model.SetDateOfBirth);

        this.operate(view.Classification,this.ClassificationCheck,model.SetClassification);

        this.operate(view.WayOfConnection,this.WayOfConnectionCheck,model.SetWayOfConnection);

        this.operate(view.Job,this.JobCheck,model.SetJob);

        // create profil user in DB
        this.pushDataToDB();
    }

    
    

}