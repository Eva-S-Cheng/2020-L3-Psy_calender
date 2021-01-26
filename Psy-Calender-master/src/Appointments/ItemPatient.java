package packages.appointments;
public class ItemPatient {
    
    private String name;
    private String firstname;
    private Integer idPatient;

    public ItemPatient(String name,String firstname,Integer idPatient){
        this.name = name;
        this.firstname = firstname;
        this.idPatient = idPatient;
    }


    public String toString(){
       return "|ID:"+this.idPatient+"->"+this.firstname+" "+this.name;
    }
}