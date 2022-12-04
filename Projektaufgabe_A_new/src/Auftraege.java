import java.util.ArrayList;

public class Auftraege {
    private String auftraggeber;
    private String ort;
    private String adresse;
    private String aufgabe;
    private String start;
    private String ende;
    private int id;

    static ArrayList<Auftraege> auftragListe = new ArrayList<Auftraege>();


    //Konstruktor zum erstellen des Objekts
    public Auftraege(String auftraggeber, String ort, String adresse, String aufgabe, String start, String ende, int id) {
        this.auftraggeber = auftraggeber;
        this.ort = ort;
        this.adresse = adresse;
        this.aufgabe = aufgabe;
        this.start = start;
        this.ende = ende;
        this.id = id;
    }

    //Getter und Setter
    public String getAuftraggeber() {
        return auftraggeber;
    }

    public void setAuftraggeber(String auftraggeber) {
        this.auftraggeber = auftraggeber;
    }

    public String getOrt() {
        return ort;
    }

    public void setOrt(String ort) {
        this.ort = ort;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getAufgabe() {
        return aufgabe;
    }

    public void setAufgabe(String aufgabe) {
        this.aufgabe = aufgabe;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnde() {
        return ende;
    }

    public void setEnde(String ende) {
        this.ende = ende;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static ArrayList<Auftraege> getAuftragListe() {
        return auftragListe;
    }

    public static void setAuftragListe(ArrayList<Auftraege> auftragListe) {
        Auftraege.auftragListe = auftragListe;
    }
}
