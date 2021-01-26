
package packages.appointments;
import java.util.Calendar;
import java.util.List;
public class ModelAppointments {

    private String patientName1;
    private String patientName2;
    private String patientName3;
    private String patientFamilyName1;
    private String patientFamilyName2;
    private String patientFamilyName3;
    private Integer typeAppointment;
    private java.sql.Date date;
    private Integer nbPatients;
    
    private Integer idConsult;
    private Integer idPatient;
    private Double amountToPay;
    private String wayofPayment;
    private Integer status;
    private Integer code;



    public ModelAppointments(){
        this.patientName2 = "";
        this.patientName3 = "";
        this.patientFamilyName2 = "";
        this.patientFamilyName3 = "";
        this.wayofPayment ="";

    }

    public Integer getNbPatients(){
        return this.nbPatients;
    }

    public interface SetValue {
        void setValueFor(String value);
    }

    public SetValue SetNbPatients =  (String value) -> {  this.nbPatients = Integer.parseInt(value);};

    public SetValue SetPatientName1 =  (String value) -> {  this.patientName1 = value;};
    public SetValue SetPatientName2 =  (String value) -> {  this.patientName2 = value;};
    public SetValue SetPatientName3 =  (String value) -> {  this.patientName3 = value;};

    public SetValue SetPatientFamilyName1 =  (String value) -> {  this.patientFamilyName1 = value;};
    public SetValue SetPatientFamilyName2 =  (String value) -> {  this.patientFamilyName2 = value;};
    public SetValue SetPatientFamilyName3 =  (String value) -> {  this.patientFamilyName3 = value;};

    public SetValue SetTypeAppointment =  (String value) -> {  this.typeAppointment = Integer.parseInt(value);};
    public SetValue SetDate =  (String value) -> {  
        // create timestamp from input
        String[] date =  value.split(" ");
       

        Integer year = Integer.parseInt(date[4]);
        Integer month = Integer.parseInt(date[3]);
        month -=1;
        Integer day = Integer.parseInt(date[2]);
        Integer hour = Integer.parseInt(date[0]);
        Integer minute = Integer.parseInt(date[1]);
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, day,hour,minute);
        
        java.sql.Date sqlDate =  new java.sql.Date(cal.getTime().getTime());
        
        this.date = sqlDate;    
    };

        

    public void addAppointment(){
    
        try{
           DatabaseAppointment.addAppointmentInDB(this.patientName1,this.patientFamilyName1,this.patientName2,this.patientFamilyName2,this.patientName3,this.patientFamilyName3,this.typeAppointment,this.date);
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    public List<ItemAppointment> fetchAppointments(int id_patient ){
        try{
            return DatabaseAppointment.selectAppointmentInDB(id_patient);
         }
         catch(Exception e){
             System.out.println(e);
         }

         return null;
    }

   


    public void setIdConsult(int idConsult){
        this.idConsult = idConsult;
    }

    public void setHowManyPAtients(int nbPatients){
        this.nbPatients = nbPatients;
    }

    public void setIdPatient(int idPatient){
        this.idPatient = idPatient;
    }

    public void setTheAmount(Double amountToPay){
        this.amountToPay = amountToPay;
    }

    public void setTheWayOfPayment(String wayOfPayment){
        this.wayofPayment = wayOfPayment;
    }

    public void setStatus(Integer status){
        this.status = status;
    }

    public void setCodeBill(Integer code){
        this.code = code;
    }

    public void createBillinDb(){
        try{
        DatabaseAppointment.createBill(this.amountToPay,this.idPatient,this.idConsult,this.wayofPayment,this.status);
        }
        catch(Exception e){
            System.out.println(e);
        }
        
    }

    public void updateBill(){
        try{
        DatabaseAppointment.updateBill(this.idPatient,this.idConsult,this.code,this.wayofPayment,1);
        }
        catch(Exception e){
            System.out.println(e);
        }
        
    }

}