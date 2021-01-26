package packages.appointments;
import packages.connectiondb.*;
import java.sql.*;
import java.sql.CallableStatement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ArrayList;
import java.util.Calendar;

public class DatabaseAppointment {

 

    public static void addAppointmentInDB(String patientName1,String patientFamilyName1,String patientName2,String patientFamilyName2,String patientName3,String patientFamilyName3,Integer typeAppointment,Date date) throws Exception {
        

       
        Connection conn =  DatabaseConnection.getConnection();
               
        CallableStatement request= conn.prepareCall("{CALL AJOUTRDV(?,?,?,?,?,?,?,?)}");
        
       
        
        request.setString(2,patientName1);
        request.setString(1,patientFamilyName1);
        request.setString(4,patientName2);
        request.setString(3,patientFamilyName2);
        request.setString(6,patientName3);
        request.setString(5,patientFamilyName3);
        request.setInt(7,typeAppointment);
        request.setDate(8,date);
        

        request.executeUpdate();
        request.close();
        conn.close();
    }

    public static void deleteAppointment(Integer idAppointment) throws Exception{
       

       
        Connection conn =  DatabaseConnection.getConnection();
               
        CallableStatement request= conn.prepareCall("{CALL SUPPRDV(?)}");
        request.setInt(1,idAppointment);

        request.executeUpdate();
        request.close();
        conn.close();

    }

    public static List<ItemAppointment> selectAppointmentInDB(Integer id_patient ) throws Exception {
       

        Connection conn =  DatabaseConnection.getConnection();
                       
       
        PreparedStatement request = conn.prepareStatement("SELECT * FROM DISPLAYAPPOINTMENTSPATIENT WHERE ID_Patient = ?");
        request.setInt(1,id_patient);
        ResultSet rset = request.executeQuery();
        

        List<ItemAppointment> list = new ArrayList<ItemAppointment>();
        while (rset.next()) {
            java.sql.Date beg = rset.getDate("Date_Debut_RDV");
            java.sql.Date end = rset.getDate("Fin_RDV");
            Integer idPatient = rset.getInt("ID_Patient");
            Integer idAppointment = rset.getInt("ID_Consultation");
            String posture = rset.getString("Postures");
            String geste = rset.getString("Gestes");
            String comp = rset.getString("Comportements");
            String titre = rset.getString("TITRE");
            String firstname = rset.getString("PRENOM");
            String name = rset.getString("NOM");
            Double anxiety = rset.getDouble("Note_Anxiete");
            
            
            ItemAppointment appointment = new ItemAppointment(idPatient,beg,end,idAppointment,comp,geste,posture,anxiety,titre,firstname,name);
            list.add(appointment);
        }
      
        request.close();
        conn.close();
        return list;
    }


    public static void createBill(Double price,Integer idPatient1,Integer idConsult,String wayOfPayment,Integer paymentStatus) throws Exception {
       

       
        Connection conn =  DatabaseConnection.getConnection();
               
        CallableStatement request= conn.prepareCall("{CALL CREATIONDEFACTURE(?,?,?,?,?)}");
        
       
        
        request.setDouble(1,price);
        request.setInt(2,idPatient1);
        request.setInt(3,idConsult);
        request.setString(5,wayOfPayment);
        request.setInt(4,paymentStatus);

        request.executeUpdate();
        request.close();
        conn.close();
    }

    public static void updateBill(Integer idPatient1,Integer idConsult,Integer idFacture,String wayOfPayment,Integer paymentStatus) throws Exception {
       

       
        Connection conn =  DatabaseConnection.getConnection();
               
        CallableStatement request= conn.prepareCall("{CALL MAJPaiement(?,?,?,?,?)}");
        
       
        
        request.setInt(1,idPatient1);
        request.setInt(2,idConsult);
        request.setInt(3,idFacture);
        request.setString(5,wayOfPayment);
        request.setInt(4,paymentStatus);

        request.executeUpdate();
        request.close();
        conn.close();
    }

    public static List<ItemBill> selectBiilsNotPayed() throws Exception {
       

       
        Connection conn =  DatabaseConnection.getConnection();
               
        PreparedStatement request = conn.prepareStatement("SELECT * FROM AFFICHENONPAYE");
        ResultSet rset = request.executeQuery();

        List<ItemBill> listBill = new ArrayList<ItemBill>();
        while (rset.next()) {
            String name = rset.getString("NOM");
            String firstname = rset.getString("PRENOM");
            java.sql.Date date = rset.getDate("DATE_DEBUT_RDV");
            Integer idAppointment = rset.getInt("ID_Consultation");
            Double price = rset.getDouble("PRIX_EN_EUROS");
            Integer codeFacture = rset.getInt("CODE_FACTURE");

            ItemBill bill = new ItemBill(name,firstname,date,price,idAppointment,codeFacture);
            listBill.add(bill);
        }
        request.close();
        conn.close();
        return listBill;


    }

    public static void updateDateAppointment(Integer idAppointment,Date date)throws Exception {
       
        Connection conn =  DatabaseConnection.getConnection();
               
        CallableStatement request= conn.prepareCall("{CALL UPDATEDATE(?,?)}");
        request.setInt(1,idAppointment);
        request.setDate(2,date);

        request.executeUpdate();
        request.close();
        conn.close();

    }

    public static void updateAppointmentData(Integer idAppointment,String behaviour,String gesture,String posture,Double anxiety)throws Exception {
       

       
        Connection conn =  DatabaseConnection.getConnection();
               
        CallableStatement request= conn.prepareCall("{CALL UPDATECONSULT(?,?,?,?,?)}");
        request.setString(1,behaviour);
        request.setString(2,gesture);
        request.setString(3,posture);
        request.setDouble(4,anxiety);
        request.setInt(5,idAppointment);

        request.executeUpdate();
        request.close();
        conn.close();
    }

    public static void removePatientFromAnAppointment(Integer idAppointment,Integer idPatient) throws Exception {
       
        Connection conn =  DatabaseConnection.getConnection();
               
        CallableStatement request= conn.prepareCall("{CALL SUPPPATRDV(?,?)}");
        request.setInt(1,idAppointment);
        request.setInt(2,idPatient);
       
        request.executeUpdate();
        request.close();
        conn.close();
    }

    public static void addPatientToAnAppointment(Integer idAppointment,Integer idPatient) throws Exception {
       
        Connection conn =  DatabaseConnection.getConnection();
               
        CallableStatement request= conn.prepareCall("{CALL ADDPATIENTTOAPP(?,?)}");
        request.setInt(1,idAppointment);
        request.setInt(2,idPatient);
       
        request.executeUpdate();
        request.close();
        conn.close();
    }

    public static List<ItemAppointment> displayAppointmentOverAWeek(Date begOfTheWeek,Date endOfTheWeek)  throws Exception {
       
       
        Connection conn =  DatabaseConnection.getConnection();
               
        PreparedStatement request = conn.prepareStatement(" SELECT * FROM DisplayAppointmentsWeek WHERE Date_Debut_RDV Between ? AND ?");
        request.setDate(1,begOfTheWeek);
        request.setDate(2,endOfTheWeek);
       
        ResultSet rset = request.executeQuery();

        List<ItemAppointment> list = new ArrayList<ItemAppointment>();
        while (rset.next()) {
            java.sql.Date beg = rset.getDate("Date_Debut_RDV");
            java.sql.Date end = rset.getDate("Fin_RDV");
            String name = rset.getString("NOM");
            String firstname = rset.getString("PRENOM");
            Integer idAppointment = rset.getInt("ID_Consultation");
            String title = rset.getString("TITRE");
            
            ItemAppointment appointment = new ItemAppointment(name,firstname,beg,end,idAppointment);
            list.add(appointment);
        }
        request.close();
        conn.close();
        return list;
    }

    public static List<ItemPatient> selectInfoPatients()  throws Exception {
       

        Connection conn =  DatabaseConnection.getConnection();
                       
       
        PreparedStatement request = conn.prepareStatement("SELECT * FROM INFO_PATIENT");
       
        ResultSet rset = request.executeQuery();
        

        List<ItemPatient> list = new ArrayList<ItemPatient>();
        while (rset.next()) {
            Integer idPatient = rset.getInt("ID_Patient");
            String name = rset.getString("NOM");
            String prenom = rset.getString("PRENOM");
            
            ItemPatient patient = new ItemPatient(name,prenom,idPatient);
            list.add(patient);
        }
      
        request.close();
        conn.close();
        return list;
    }


   



   

}