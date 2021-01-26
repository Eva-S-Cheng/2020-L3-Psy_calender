

package packages.authentification;
public class ViewAuthentification {


    public interface AskPatient {
        void askFor();
     }


    public AskPatient Email = () -> { System.out.println("Entrez un mail");};
    
    public AskPatient Passwd = () -> {System.out.println("Entrez un password");};

    public void accessDenied(){
        System.out.println("Identifiants refusés");
    }

    public void accessGranted(){
        System.out.println("Authentification réussie");
    }
}