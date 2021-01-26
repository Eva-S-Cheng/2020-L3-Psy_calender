package packages.appointments;
import java.util.Date;
public class ItemAppointment {

    
    private Integer idPatient;
    private Date beg;
    private Date end;
    private Integer idAppointment;
    private String name;
    private String firstname;
    private String comportement;
    private String geste;
    private String posture;
    private Double anxiety;
    private String titre;
    

    public ItemAppointment(Integer idPatient, java.sql.Date beg,java.sql.Date end,Integer idAppointment, String comportement,String geste,String posture,Double anxiety,String titre,String firstname,String name){
        this.idPatient = idPatient;
        this.idAppointment = idAppointment;
        this.beg = new Date(beg.getTime());
        this.end = new Date(beg.getTime());
        this.comportement = comportement;
        this.geste = geste;
        this.posture = posture;
        this.anxiety = anxiety;
        this.titre = titre;
        this.firstname = firstname;
        this.name = name;
    }

    public ItemAppointment(String name,String firstname, java.sql.Date beg,java.sql.Date end,Integer idAppointment){
        this.name = name;
        this.firstname = firstname;
        this.idAppointment = idAppointment;
        this.beg = new Date(beg.getTime());
        this.end = new Date(beg.getTime());
    }

    public String getDateBegin(){
        Integer year = this.beg.getYear();
        Integer month = this.beg.getMonth();
        Integer d = this.beg.getDay();
        Integer m = this.beg.getMinutes();
        Integer h = this.beg.getHours();
        return d+"/"+month+"/"+year+" à "+ h + "h" + m;
    }

    public Integer getIdAppointment(){
        return this.idAppointment;
    }

    
    public String toString(){
        return "Rendez-vous numero "+this.idAppointment +" le " +getDateBegin();
    }

    public void displayAppointmentOverAWeek(){
        System.out.println("ID:"+this.idAppointment+" "+this.getDateBegin());
        System.out.println("Avec "+this.firstname+" "+this.name+"\n");
    }

    public void displayAppointmentForAPatient(){
        this.displayAppointmentOverAWeek();
        System.out.println("Info rendez-vous:");
        System.out.println("Postures: "+this.posture);
        System.out.println("Comportments: "+this.comportement);
        System.out.println("Gestes: "+this.geste);
        System.out.println("Anxieté: "+this.anxiety);
        System.out.println("Titre: "+this.titre);
    }

}