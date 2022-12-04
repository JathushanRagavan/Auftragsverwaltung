import java.util.ArrayList;
import java.util.List;

public class Mitarbeiter {
    public String name;
    public String vorname;
    public String berufsbezeichnung;
    public String einstellungsdatum;
    public String jahresgehalt;
    static ArrayList<Mitarbeiter> mitarbeiterListe = new ArrayList<Mitarbeiter>() ;


    //Konstruktor zum erstellen des Objekts
    public Mitarbeiter(String name, String berufsbezeichnung, String einstellungsdatum, String jahresgehalt) {
        this.name = name;
        this.berufsbezeichnung = berufsbezeichnung;
        this.einstellungsdatum = einstellungsdatum;
        this.jahresgehalt = jahresgehalt;
    }


    //Getter und Setter falls notwendig

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getBerufsbezeichnung() {
        return berufsbezeichnung;
    }

    public void setBerufsbezeichnung(String berufsbezeichnung) {
        this.berufsbezeichnung = berufsbezeichnung;
    }

    public String getEinstellungsdatum() {
        return einstellungsdatum;
    }

    public void setEinstellungsdatum(String einstellungsdatum) {
        this.einstellungsdatum = einstellungsdatum;
    }

    public String getJahresgehalt() {
        return jahresgehalt;
    }

    public void setJahresgehalt(String jahresgehalt) {
        this.jahresgehalt = jahresgehalt;
    }

    public static ArrayList<Mitarbeiter> getMitarbeiterListe() {
        return mitarbeiterListe;
    }

    public static void setMitarbeiterListe(ArrayList<Mitarbeiter> mitarbeiterListe) {
        Mitarbeiter.mitarbeiterListe = mitarbeiterListe;
    }
}

