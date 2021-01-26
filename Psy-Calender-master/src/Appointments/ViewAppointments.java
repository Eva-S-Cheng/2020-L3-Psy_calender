package packages.appointments;
public class ViewAppointments {

    public interface AskPatient {
        void askFor();
     }

    public AskPatient NbPatients = () -> { System.out.println("Combien de patients ?");};

    public AskPatient FamilyName = () -> { System.out.println("Nom de famille ?");};

    public AskPatient FirstName = () -> { System.out.println("Prenom ?");};

    public AskPatient Date = () -> { 
        System.out.println("Entrez une date ?");
        System.out.println("Veuillez entrer une date avec le format suivant: hh mm dd mm yyyy ");
    };

    public AskPatient TypeAppointment = () -> { 
        System.out.println("Choissiez le motif de consultation ? (Entrez le chiffre correspond)");
        System.out.println("1. Travail");
        System.out.println("2. Personnel");
    
    };

    public void displayErrorMessageInput(){
        System.out.println("Format invalide");
    }


    public void askIdConsult(){
        System.out.println("Entrez l'ID de la consultation, si vous ne l'avez pas, veuillez vous refferer à la fonction affichage des rendez-vous");
    }

    public void askHowManyPAtients(){
        System.out.println("Enter the number of patient who should pay (max 3)");
    }

    public void askIdPatient(){
        System.out.println("Entrez l'ID du patient (Veuillez consulter la liste des rendez-vous si vous ne connaisait pas l'ID)");
    }

    public void askStatus(){
        System.out.println("Est ce que le patient paye maintenant ?");
        System.out.println("1. Oui");
        System.out.println("0. Non");
    }

    public void askTheAmount(){
        System.out.println("Entrez le prix de la consultation");
    }

    public void askTheWayOfPayment(){
        System.out.println("Comment le patient paye");
        System.out.println("1./Espèce");
        System.out.println("2./Chèque");
        System.out.println("2./Carte Bleue");
    }

    public  void menuUser(){
        System.out.println("-------------------");
        System.out.println("- Vos Rendez-vous -");
        System.out.println("-------------------\n");   
    }

    public void menuAdmin(){
        System.out.println("-------------------");
        System.out.println("- Bienvenue Admin -");
        System.out.println("------------------\n");

        System.out.println("Que souhaitez-vous faire?");
        System.out.println("1/ Enregister un nouveau patient");
        System.out.println("2/ Ajouter un rendez-vous");
        System.out.println("3/ Annuler un rendez-vous");
        System.out.println("4/ Modifier un rendez-vous");
        System.out.println("|-1 Ajouter un patient d'un rendez-vous");
        System.out.println("|-2 Retirer un patient d'un rendez-vous");
        System.out.println("|-3 Changer la date d'un rendez-vous");
        System.out.println("5/ Ajouter des notes à un rendez-vous");
        System.out.println("6/ Afficher rendez-vous");
        System.out.println("|-1 Afficher les rendez-vous sur une semaine donnée");
        System.out.println("|-2 Afficher les rendez-vous d'un patient");
        System.out.println("7/ Facture");
        System.out.println("|-1 Creer une facture");
        System.out.println("|-2 Affichier les factures non-payés");
        System.out.println("|-3 Mettre à jour une facture");
        System.out.println("8/ Afficher la liste des patients avec leur ID");
        System.out.println("-1 to quit");

        System.out.println("Veuillez entrer un numéro:");
    }

    public void errorInput(){
        System.out.println("Format invalide");
    }

    public void askIdAppointment(){
        System.out.println("Entrez l'ID du rendez-vous (Veuillez consulter la liste des rendez-vous si vous ne connaisait pas l'ID)");
    }
    public void askCodeFacture(){
        System.out.println("Entrez l'ID de la facture (Veuillez consulter la liste des factures si vous ne connaisait pas l'ID)");
    }

    public void askDate(){
        System.out.println("Entrez une date");
        System.out.println("La date doit suive le format suivant: hh mm dd mm yyyy ");
    }

    public void displayAppointment(ItemAppointment item){
        System.out.println("-------------");
        System.out.println("| "+item.getIdAppointment()+" "+item.getDateBegin()+" |");
        System.out.println("-------------\n");
    }
}