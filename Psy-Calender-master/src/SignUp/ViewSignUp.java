package packages.signup;
public class ViewSignUp {

    public interface AskPatient {
        void askFor();
     }

    public AskPatient FamilyName = () -> { System.out.println("Entrez un nom de famille");};
    
    public AskPatient FirstName = () -> {System.out.println("Entrez un prenom");};
    
    public AskPatient Email = () -> {System.out.println("Entrez un email");};

    public AskPatient Password = () -> {  System.out.println("Entrez un mot de passe"); };
  
    public AskPatient Classification = () -> { 
    System.out.println("Quelle est la situation du patient ? (Entrez un numéro)");
    System.out.println("1/ Femme");
    System.out.println("2/ Homme");
    System.out.println("3/ Couple");};
   
    public AskPatient WayOfConnection = () -> {System.out.println("Comment avez-vous connnu la psychologue ? ");};

    public AskPatient  TelephoneNumber = () -> {
        System.out.println("Entrez un numéro de télphone ? ");
        System.out.println("le format est le suivant: 0623123423");
    };

    public AskPatient  City = () -> {System.out.println("Entrez une ville ");};

    public AskPatient  Adress = () -> {System.out.println("Entrez un numero de rue ");};

    public AskPatient  CityCode = () -> {System.out.println("Entrez le code postal de la ville ");};

    public AskPatient  Job = () -> {System.out.println("Entrez un travail ");};

    public AskPatient  DateOfBirth = () -> {
        System.out.println("Entrez une date de naissance ");
        System.out.println("Veuillez respecter le format suivant: dd mm yyyy ");
    
    };
    

    public void askPatientConfirmation() {
        System.out.println("enter 1 to create the account else any key");
    }

    public void displayErrorMessageInput() {
        System.out.println("Format invalid");
        System.out.println("Veuillez vérifier le format  ");
    }
    
    

}