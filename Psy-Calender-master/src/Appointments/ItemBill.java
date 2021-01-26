package packages.appointments;
import java.util.Date;
public class ItemBill {
    
    private String name;
    private String firstName;
    private Date date;
    private Double price;
    private Integer idAppointment;
    private Integer codeFacture;

    public ItemBill( String name,String firstName,java.sql.Date date,Double price,Integer idAppointment,Integer codeFacture){
        this.name = name;
        this.firstName = firstName;
        this.date =  new Date(date.getTime());;
        this.price = price;
        this.idAppointment = idAppointment;
        this.codeFacture = codeFacture;
    }

    public String getDateBegin(){
        Integer year = this.date.getYear();
        Integer month = this.date.getMonth();
        Integer d = this.date.getDay();
        Integer m = this.date.getMinutes();
        Integer h = this.date.getHours();
        return d+"/"+month+"/"+year+" à "+ h + "h" + m;
    }



    public String toString(){
        return "Rendez-vous numero "+this.idAppointment +" le " +getDateBegin() + " par le patient "+this.firstName +" "+this.name+ " d'un montant de "+this.price+"€  (Code facture:"+this.codeFacture+")";
    }
}