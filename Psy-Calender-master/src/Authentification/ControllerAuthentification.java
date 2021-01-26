
package packages.authentification;
import java.util.Scanner;
public class ControllerAuthentification {

    Scanner input = new Scanner(System.in);
    private final ModelAuthentification model;
    private final ViewAuthentification view;

    public ControllerAuthentification(ViewAuthentification view,ModelAuthentification model){
        this.model = model;
        this.view = view;
    }

    public void authentificationProcess(ViewAuthentification.AskPatient displayer,ModelAuthentification.SetValue setter){
        String value;
        
        displayer.askFor();
        value = input.nextLine();
        setter.setValueFor(value);
            
        }
    

    public Integer verifyCredential(){

        return model.verifyCredentialInDb();        
    }

    public void frontAuthentification(){
        System.out.println("--------------");
        System.out.println("- Connection -");
        System.out.println("--------------\n");
        
        Integer verificationCrendentials = -1;
        while(verificationCrendentials == -1){
           
            this.authentificationProcess(view.Email,model.SetEmail);
    
            this.authentificationProcess(view.Passwd,model.SetPasswd);
    
            verificationCrendentials = this.verifyCredential();
            
            if (verificationCrendentials == -1) {System.out.println("\nNous n'avons pas réussie à vous connecter \n"); }

            model.setId(verificationCrendentials);
    
            
        }
    }
}