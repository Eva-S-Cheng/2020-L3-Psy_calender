
import packages.appointments.*;
import packages.signup.*;
import packages.authentification.*;
import packages.connectiondb.*;
public class Main {

    public static void main(String[] args) {
    ViewAppointments viewAppointments = new ViewAppointments();
    ModelAppointments modelAppointments = new ModelAppointments();
    ControllerAppointments controllerAppointments = new ControllerAppointments(viewAppointments,modelAppointments);

    ViewAuthentification viewAuthentification = new ViewAuthentification();
    ModelAuthentification modelAuthentification = new ModelAuthentification();
    ControllerAuthentification controllerAuthentification = new ControllerAuthentification(viewAuthentification,modelAuthentification);

    ViewSignUp viewSignUp = new ViewSignUp();
    ModeleSignUp modelSignUp = new ModeleSignUp();
    ControllerSignUp controllerSignUp = new ControllerSignUp(viewSignUp,modelSignUp);

    // DB credentials
    DatabaseConnection.ip = "jdbc:oracle:thin:@192.168.56.102:1521:orclcdb";
    DatabaseConnection.user = "system";
    DatabaseConnection.passwd = "oracle";

   
    
    
        controllerAuthentification.frontAuthentification();
        Boolean leave = false;
        while(!leave){
        // admin menu
       
        if (modelAuthentification.getId() == 0) {
            Integer result = controllerAppointments.handlerRequest();
            switch(result){
                case 1: controllerSignUp.formUser();
                        break;
                case 2: controllerAppointments.formAppointment();
                        break;  
                case 3: controllerAppointments.formCancelAppointment();
                        break;
                case 41: controllerAppointments.formAddPatientAppointment();
                        break;
                case 42: controllerAppointments.formRemovePatientAppointment();
                        break;
                case 43: controllerAppointments.formChangeDateAppointment();
                        break;
                case 5: controllerAppointments.formAddInfoAppointment();
                        break;
                case 61: controllerAppointments.formDisplayAppointmentsWeek();
                        break;
                case 62: controllerAppointments.formDisplaylAppointment();
                        break;
                case 71: controllerAppointments.formBill();
                        break;
                case 72:controllerAppointments.displayNoPayedBill();
                        break;
                case 73:controllerAppointments.formUpdateBill();
                        break;
                case 8: controllerAppointments.displayInfoPatient();
                        break;
                case -1:leave=true;
                        break;
                default: viewAppointments.errorInput();
                        break;
                        
                       

            }

        }
        // user menu
        else{
            viewAppointments.menuUser();
            controllerAppointments.displayAppointments(modelAuthentification.getId());
            leave=true;
        }


    }

    }


   
}