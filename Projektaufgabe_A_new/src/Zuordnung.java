import java.util.ArrayList;

public class Zuordnung {
    public String id;
    public String name;
    static ArrayList<Zuordnung> zuordnungListe = new ArrayList<Zuordnung>();

    //konstruktor zum erstellen des Objekts
    public Zuordnung(String id, String name ){
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static ArrayList<Zuordnung> getZuordnungListe() {
        return zuordnungListe;
    }

    public static void setZuordnungListe(ArrayList<Zuordnung> zuordnungListe) {
        Zuordnung.zuordnungListe = zuordnungListe;
    }
}
