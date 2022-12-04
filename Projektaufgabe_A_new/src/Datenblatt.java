import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;

public class Datenblatt extends JFrame {
    private int id;
    public String auftraggeber;
    public String ort;
    public String adresse;
    public String aufgabe;
    public String start;
    public String ende;

    public String name;
    public String berufsbezeichnung;

    ArrayList<String> mitarbeiterNamen = new ArrayList<String>();



    //Konstruktor der einen String übergeben bekommt und ein neuen Frame mit den oben genannten Variablen erstellt
    public Datenblatt(String id) {
        for (int i = 0; i < Auftraege.auftragListe.size(); i++){
            this.id = Integer.parseInt(id);
            if(Auftraege.auftragListe.get(i).getId() == this.id){
                auftraggeber = Auftraege.auftragListe.get(i).getAuftraggeber();
                ort = Auftraege.auftragListe.get(i).getOrt();
                adresse = Auftraege.auftragListe.get(i).getAdresse();
                aufgabe = Auftraege.auftragListe.get(i).getAufgabe();
                start = Auftraege.auftragListe.get(i).getStart();
                ende = Auftraege.auftragListe.get(i).getEnde();
            }
        }

        for(int i = 0; i < Zuordnung.zuordnungListe.size(); i++){
            if(Zuordnung.zuordnungListe.get(i).getId().equals(id)){
                mitarbeiterNamen.add(Zuordnung.zuordnungListe.get(i).getName().trim());

            }
        }



        //Standardwerte um frame zu erzeugen
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(Color.WHITE);
        setTitle("Administration");
        setLocation(500,0);
        setSize(900, 600);
        setResizable(true);
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        //panel.setLayout(new BorderLayout());



        //Der Text der angezeigt werden soll
        String Auftraggeber_Beschreibung = "Datenblatt zum Auftrag " + id + "\n\n\n";
        Auftraggeber_Beschreibung += "Auftraggeber:  " + auftraggeber + "\n";
        Auftraggeber_Beschreibung += "Auftragsbeginn:  " + start + "\n";
        Auftraggeber_Beschreibung += "Auftragsende: " + ende + "\n";
        Auftraggeber_Beschreibung += "Auftragsbeschreibung: \n";
        Auftraggeber_Beschreibung += aufgabe;
        Auftraggeber_Beschreibung += "\nAuftragsadresse: " + adresse + ", " + ort + "\n";
        Auftraggeber_Beschreibung += "Zugeordnette Mitarbeiter: ";
        Auftraggeber_Beschreibung += mitarbeiterNamen.get(0);
        if(mitarbeiterNamen.size() > 1){
            for (int i = 1; i < mitarbeiterNamen.size()-1; i++){
                Auftraggeber_Beschreibung += ", " + mitarbeiterNamen.get(i);
            }
            Auftraggeber_Beschreibung += " und " + mitarbeiterNamen.get(mitarbeiterNamen.size()-1) ;

        }




        for (int i = 0; i < Mitarbeiter.mitarbeiterListe.size(); i++) {
            for (int j = 0; j < mitarbeiterNamen.size(); j++){
                if(Mitarbeiter.mitarbeiterListe.get(i).getName().equals(mitarbeiterNamen.get(j))){
                    Auftraggeber_Beschreibung += "\n";
                    Auftraggeber_Beschreibung += "Der Mitarbeiter " + Mitarbeiter.mitarbeiterListe.get(i).getName();
                    Auftraggeber_Beschreibung += " ist, seit dem " + Mitarbeiter.mitarbeiterListe.get(i).getEinstellungsdatum();
                    Auftraggeber_Beschreibung += ", beschäfftigt als " + Mitarbeiter.mitarbeiterListe.get(i).getBerufsbezeichnung();
                    Auftraggeber_Beschreibung += " und Verdient jährlich " + Mitarbeiter.mitarbeiterListe.get(i).getJahresgehalt() + "€";

                }
            }
        }




        //Der erzeugte Text wird in einer TextArea abgespeichert
        JTextArea jtAuftraggeber_Beschreibung = new JTextArea(Auftraggeber_Beschreibung);
        jtAuftraggeber_Beschreibung.setFont(new Font("Monospaced", Font.PLAIN, 14));
       //JTextArea jtMitarbeiter_StartEnde = new JTextArea(Mitarbeiter_StartEnde);

        jtAuftraggeber_Beschreibung.setEditable(false);
        jtAuftraggeber_Beschreibung.setLineWrap(true);
        jtAuftraggeber_Beschreibung.setWrapStyleWord(true);


        panel.add(jtAuftraggeber_Beschreibung);

        //falls der Text zu lang ist, damit mit scrollen kann
        JScrollPane scroll = new JScrollPane(panel);

        add(scroll);
        setVisible(true);



    }
}
