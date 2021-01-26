
package packages.appointments;
import java.util.Calendar;
import java.util.Scanner;
import java.text.DateFormat;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;

public class ControllerAppointments {

    private final ModelAppointments model;
    private final ViewAppointments view;

    Scanner input = new Scanner(System.in);
    public ControllerAppointments(ViewAppointments view,ModelAppointments model){
        this.model = model;
        this.view = view;
    }

    public interface CheckValue {
        boolean checkValueFor(String value);
    }

    //
    // VERIFICATION INPUT 
    //
    public CheckValue NameCheck = (String value) -> { return  value.length() > 25 ||  value == null;};

    public CheckValue TypeAppointmentCheck = (String value) -> { 
        Boolean index = false;
        try{
            Integer  nb = Integer.parseInt(value);   
            if (nb > 2 || nb < 0){
                index = true;
            }
        }
        catch(Exception e){
            index = true;
        }
        
        return (value.length() != 1 && index != true ) ? true:false;
    };

    public CheckValue NbPatientsCheck = (String value) -> { 
        Boolean index = false;
        try{
            Integer  nb = Integer.parseInt(value);   
            if (nb > 3 || nb < 0){
                index = true;
            }
        }
        catch(Exception e){
            index = true;
        }
        
        return (value.length() != 1 && index != true ) ? true:false;
    };

    public CheckValue DateCheck = (String value) -> { 
        
        //dd mm yyyy
        boolean index = false;
        try{
            String[] date =  value.split(" ");
       

            Integer year = Integer.parseInt(date[4]);
            Integer month = Integer.parseInt(date[3]);
            Integer day = Integer.parseInt(date[2]);
            Integer hour = Integer.parseInt(date[0]);
            Integer minute = Integer.parseInt(date[1]);
            Calendar cal = Calendar.getInstance();
            cal.set(year, month, day,hour,minute);
            
            java.sql.Date sqlDate =  new java.sql.Date(cal.getTime().getTime());
       
        }
        catch(Exception f){
            index = true;
        }
            
        
        return index ;
        
        
       };

    //
    // ASSEMBLER VIEW MODEL CONTROLLEr
    public void operate(ViewAppointments.AskPatient displayer,CheckValue checker,ModelAppointments.SetValue setter){
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


    public void pushAppointment(){
        model.addAppointment();
        
    }


    public void displayAppointments(int id_patient){
        List<ItemAppointment> list = model.fetchAppointments(id_patient);
        list.forEach(System.out::println);
    }


    public void askIdConsult(){
        String value = input.nextLine();
        model.setIdConsult(Integer.parseInt(value));
    }

    public void askHowManyPAtients(){
       String value = input.nextLine();
        model.setHowManyPAtients(Integer.parseInt(value));
    }

    public void askIdPatient(){
        String value = input.nextLine();
        model.setIdPatient(Integer.parseInt(value));
    }

    public void askTheAmount(){
        String value = input.nextLine();
        model.setTheAmount(Double.parseDouble(value));
    }

    public void askTheWayOfPayment(){
        String value = input.nextLine();
        model.setTheWayOfPayment(value);
    }

    public void askCodeFacture(){
        String value = input.nextLine();
        try{
        Integer code = Integer.valueOf(value);
        model.setCodeBill(code);
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    public Integer askStatus(){
        String value = input.nextLine();
        try{
        Integer status = Integer.valueOf(value);
        model.setStatus(status);
        return status;
        }
        catch(Exception e){
            System.out.println(e);
        }
        return null;
    }

    public void createBill(){
        model.createBillinDb();
    }

    public void updateBill(){
        model.updateBill();
       
    }

    public void deleteAppointment(Integer idAppointment){
        try{
            DatabaseAppointment.deleteAppointment(idAppointment);
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    public void modifyAppointment(Integer idAppointment,Integer idPatient){
        try{
            DatabaseAppointment.removePatientFromAnAppointment(idAppointment,idPatient);
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    public void addPatientToAppointment(Integer idAppointment,Integer idPatient){
        try{
            DatabaseAppointment.addPatientToAnAppointment(idAppointment,idPatient);
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    public void changeDateAppointment(Integer idAppointment,java.sql.Date date){
        try{
            DatabaseAppointment.updateDateAppointment(idAppointment,date);
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    public void addInfoAppointment(Integer idAppointment,String behaviour,String gesture,String posture,Double anxiety){
        try{
            DatabaseAppointment.updateAppointmentData(idAppointment,behaviour, gesture, posture,anxiety);
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    public void displayAppointmentOverAWeek(java.sql.Date beg,java.sql.Date end){
        try{
            List<ItemAppointment> list = DatabaseAppointment.displayAppointmentOverAWeek(beg,end);
            for (ItemAppointment item : list) {
                item.displayAppointmentOverAWeek();
            }
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    public void displayAppointment(Integer idPatient){
        try{
            List<ItemAppointment> list = DatabaseAppointment.selectAppointmentInDB(idPatient);
            for (ItemAppointment item : list) {
                item.displayAppointmentForAPatient();
            }
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    public void displayInfoPatient(){
        try{
            List<ItemPatient> list = DatabaseAppointment.selectInfoPatients();
            list.forEach(System.out::println);
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    //
    //  CONTROLLER FUNCTINO FROM MAIN 
    //
    public Integer handlerRequest(){
        boolean valueNotCorrect;
        valueNotCorrect = true;
        String value;
        while(valueNotCorrect){
            view.menuAdmin();
            value = input.nextLine();
            Integer result;
            try{
            result = Integer.valueOf(value);
            return result;
            }
            catch(Exception e){
                view.errorInput();
            }

           
            
        }

        return null;
    }

    public void formAppointment(){
        this.operate(view.NbPatients,this.NbPatientsCheck,model.SetNbPatients);

        this.operate(view.FirstName,this.NameCheck,model.SetPatientName1);
        this.operate(view.FamilyName ,this.NameCheck,model.SetPatientFamilyName1);

        if (model.getNbPatients() > 1){
            this.operate(view.FirstName,this.NameCheck,model.SetPatientName2);
            this.operate(view.FamilyName,this.NameCheck,model.SetPatientFamilyName2);
            if (model.getNbPatients() > 2){
            this.operate(view.FirstName,this.NameCheck,model.SetPatientName3);
            this.operate(view.FamilyName,this.NameCheck,model.SetPatientFamilyName3);
            }
        }

        this.operate(view.TypeAppointment ,this.TypeAppointmentCheck,model.SetTypeAppointment);
        this.operate(view.Date ,this.DateCheck,model.SetDate);

        this.pushAppointment();
    }

    public void formCancelAppointment(){
        boolean valueNotCorrect;
        valueNotCorrect = true;
        String value;
        while(valueNotCorrect){
            view.askIdAppointment();
            value = input.nextLine();
            Integer result;
            try{
            result = Integer.valueOf(value);
            this.deleteAppointment(result);
            valueNotCorrect = false;
            }
            catch(Exception e){
                view.errorInput();
            }
            
            

            
            
        }
       
    }

    public void formAddPatientAppointment(){
        boolean valueNotCorrect;
        valueNotCorrect = true;
        String appointment;
        String patient;
        while(valueNotCorrect){
            view.askIdAppointment();
            appointment = input.nextLine();
            view.askIdPatient();
            patient = input.nextLine();
            Integer  appointmentId;
            Integer patientId;
            try{
            patientId = Integer.valueOf(patient);
            appointmentId = Integer.valueOf(appointment);
            this.addPatientToAppointment( appointmentId,patientId);
            valueNotCorrect = false;
            }
            catch(Exception e){
                view.errorInput();
            }
                
        }
       
    }

    public void formRemovePatientAppointment(){
        boolean valueNotCorrect;
        valueNotCorrect = true;
        String appointment;
        String patient;
        while(valueNotCorrect){
            view.askIdAppointment();
            appointment = input.nextLine();
            view.askIdPatient();
            patient = input.nextLine();
            Integer  appointmentId;
            Integer patientId;
            try{
            patientId = Integer.valueOf(patient);
            appointmentId = Integer.valueOf(appointment);
            this.modifyAppointment( appointmentId,patientId);
            valueNotCorrect = false;
            }
            catch(Exception e){
                view.errorInput();
            }
                
        }
       
    }

    public void formChangeDateAppointment(){
        boolean valueNotCorrect;
        valueNotCorrect = true;
        String appointment;
        String dateInput;
        while(valueNotCorrect){
            view.askIdAppointment();
            appointment = input.nextLine();
            view.askDate();
            dateInput = input.nextLine();
           
            Integer  appointmentId;
            
            try{
            appointmentId = Integer.valueOf(appointment);
            // create timestamp according to input
            String[] date = dateInput.split(" ");
    
            Integer year = Integer.parseInt(date[4]);
            Integer month = Integer.parseInt(date[3]);
            month -=1;
            Integer day = Integer.parseInt(date[2]);
            Integer hour = Integer.parseInt(date[0]);
            Integer minute = Integer.parseInt(date[1]);
            Calendar cal = Calendar.getInstance();
            cal.set(year, month, day,hour,minute);
            
            java.sql.Date sqlDate =  new java.sql.Date(cal.getTime().getTime());
             
            this.changeDateAppointment( appointmentId,sqlDate);
            valueNotCorrect = false;
            }
            catch(Exception e){
                view.errorInput();
            }
                
        }

    
       
    }

    public void formAddInfoAppointment(){
        boolean valueNotCorrect;
        valueNotCorrect = true;
        String appointment;
        String behaviour;
        String gesture;
        String conport;
        String anxiety;
        while(valueNotCorrect){
            view.askIdAppointment();
            appointment = input.nextLine();
            System.out.println("Entrez une note pour le comportement");
            behaviour = input.nextLine();
            System.out.println("Entrez une note pour le gestes");
            gesture = input.nextLine();
            System.out.println("Entrez une note pour la posture");
            conport = input.nextLine();
            System.out.println("Entrez une note pour l'anxiété si nécessaire (entre 0 et 10)");
            anxiety = input.nextLine();
           
            Double anxietyD;
            Integer  appointmentId;
            try{
            anxietyD = Double.valueOf(anxiety);
            appointmentId = Integer.valueOf(appointment);
            this.addInfoAppointment( appointmentId, behaviour,gesture,conport,anxietyD);
            valueNotCorrect = false;
            }
            catch(Exception e){
                view.errorInput();
            }
                
        }
        
    }

    public void formDisplayAppointmentsWeek(){
        boolean valueNotCorrect;
        valueNotCorrect = true;
        String appointment;
        String dateInput;
        while(valueNotCorrect){
           
            view.askDate();
            dateInput = input.nextLine();
           
            Integer  appointmentId;
            
            try{
            
            String[] date = dateInput.split(" ");
    
            Integer year = Integer.parseInt(date[2]);
            Integer month = Integer.parseInt(date[1]);
            month -=1;
            Integer day = Integer.parseInt(date[0]);
            
            Calendar cal = Calendar.getInstance();
            cal.set(year, month, day);
            Calendar cal2 = Calendar.getInstance();
           
            // check if adding seven day doesn't overflow
            day +=7;
            if ((day/31) > 0){
                month+=1;
                if(month/12 > 0){
                    year+=1;
                }
            }

            day = day%31;
            month = month%31;

            cal2.set(year, month, day);
            
            java.sql.Date sqlDate =  new java.sql.Date(cal.getTime().getTime());
            java.sql.Date sqlDate2 =  new java.sql.Date(cal2.getTime().getTime());
             
            this.displayAppointmentOverAWeek( sqlDate,sqlDate2);
            valueNotCorrect = false;
            }
            catch(Exception e){
                System.out.println(e);
                view.errorInput();
            }
                
        }  
    }

    public void formDisplaylAppointment(){
        boolean valueNotCorrect;
        valueNotCorrect = true;
        String value;
        while(valueNotCorrect){
            view.askIdPatient();
            value = input.nextLine();
            Integer result;
            try{
            result = Integer.valueOf(value);
            this.displayAppointment(result);
            valueNotCorrect = false;
            }
            catch(Exception e){
                view.errorInput();
            }
            
            

            
            
        }
       
    }

    public void formBill(){
        view.askIdConsult();
        this.askIdConsult();
        view.askIdPatient();
        this.askIdPatient();
        view.askTheAmount();
        this.askTheAmount();
        //if the patient pay now we add payement information
        view.askStatus();
        if (this.askStatus()==1){
            view.askTheWayOfPayment();
            this.askTheWayOfPayment();     
        }
        this.createBill();
    }

    public void displayNoPayedBill(){
        try{
            List<ItemBill> list = DatabaseAppointment.selectBiilsNotPayed();
            list.forEach(System.out::println);

        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    public void formUpdateBill(){
        view.askIdConsult();
        this.askIdConsult();
        view.askIdPatient();
        this.askIdPatient();
        view.askCodeFacture();
        this.askCodeFacture();
        view.askTheWayOfPayment();
        this.askTheWayOfPayment();
        this.updateBill();
    }
   

    
}