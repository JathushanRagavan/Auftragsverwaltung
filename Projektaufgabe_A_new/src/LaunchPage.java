import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.io.*;

public class LaunchPage{

    JFrame frame;
    JButton mitarbeiterButton;
    JButton auftraegButton;
    JButton zuordnungButton;

    LaunchPage(){

        //initialisierung Frame - GRAFISCHE DARSTELLUNG
        frame = new JFrame();


        //Button
        mitarbeiterButton = new JButton("Mitarbeiter");
        mitarbeiterButton.setBounds(100,160,200,40);
        mitarbeiterButton.setFocusable(false);
        frame.add(mitarbeiterButton);

        auftraegButton = new JButton("Aufträge");
        auftraegButton.setBounds(200,320,200,40);
        auftraegButton.setFocusable(false);
        frame.add(auftraegButton);

        zuordnungButton = new JButton("Zuordnung");
        zuordnungButton.setBounds(200,320,200,40);
        zuordnungButton.setFocusable(false);
        frame.add(zuordnungButton);

        //Menu
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Menu");
        JMenuItem importCSV = new JMenuItem("import CSV");
        JMenuItem exportCSV = new JMenuItem(("export CSV"));

        menu.add(importCSV);
        menu.add(exportCSV);
        menuBar.add(menu);
        frame.setJMenuBar(menuBar);



        //Standard JFrame sachen
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Administration");
        frame.setSize(420,420);
        frame.setLayout(new FlowLayout());
        frame.setVisible(true);


        //Button funktionen
        //erstellt beim Klick ein neues Object vom MitarbeiterTablle/AuftraegTabelle/Zuordnungstabelle
        mitarbeiterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MitarbeiterTabelle mTabelle = new MitarbeiterTabelle();
            }
        });

        auftraegButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AuftraegTabelle aTabelle = new AuftraegTabelle();
            }
        });

        zuordnungButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ZuordnungTabelle zTabelle = new ZuordnungTabelle();
            }
        });


        //Menufunktionen
        //beim klick auf export CSV werden die Daten in eine CSV abgespeichert
        exportCSV.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    File file = new File("admin.csv");
                    FileWriter fw = new FileWriter(file);
                    BufferedWriter bw = new BufferedWriter(fw);

                    //für Mitarbeiter
                    for (int i = 0; i < Mitarbeiter.mitarbeiterListe.size(); i++){
                        bw.write("Mitarbeiter" + ";"
                                + Mitarbeiter.mitarbeiterListe.get(i).getName()
                                + ";" +Mitarbeiter.mitarbeiterListe.get(i).getBerufsbezeichnung()
                                + ";" + Mitarbeiter.mitarbeiterListe.get(i).getEinstellungsdatum()
                                + ";" + Mitarbeiter.mitarbeiterListe.get(i).getJahresgehalt());
                        bw.newLine();
                    }

                    //Für Auftrag
                    for (int i = 0; i < Auftraege.auftragListe.size(); i++){
                        bw.write("Auftrag" + ";"
                                + Auftraege.auftragListe.get(i).getAuftraggeber()
                                + ";" + Auftraege.auftragListe.get(i).getOrt()
                                + ";" + Auftraege.auftragListe.get(i).getAdresse()
                                + ";" + Auftraege.auftragListe.get(i).getAufgabe()
                                + ";" + Auftraege.auftragListe.get(i).getStart()
                                + ";" + Auftraege.auftragListe.get(i).getEnde()
                                + ";" + Auftraege.auftragListe.get(i).getId());
                        bw.newLine();
                    }

                    //für Zuordnung
                    for (int i = 0; i < Zuordnung.zuordnungListe.size(); i++){
                        bw.write("Zuordnung" + ";"
                                + Zuordnung.zuordnungListe.get(i).getId()
                                + ";" +Zuordnung.zuordnungListe.get(i).getName());
                        bw.newLine();
                    }


                    bw.close();
                    fw.close();

                } catch (Exception d){
                    System.out.println(d);
                    System.out.println("Fehler beim Auslesen");
                }

            }
        });

        //daten werden von einer CSV in das Programm importiert
        importCSV.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    //Damit die Daten nicht mehrfach angezeigt werden
                    Mitarbeiter.mitarbeiterListe.removeAll(Mitarbeiter.mitarbeiterListe);
                    Auftraege.auftragListe.removeAll(Auftraege.auftragListe);
                    Zuordnung.zuordnungListe.removeAll(Zuordnung.zuordnungListe);
                    FileReader fr = new FileReader("admin.csv");
                    BufferedReader in = new BufferedReader(fr);
                    String line = "";
                    while((line = in.readLine()) != null){

                        String[] s = line.split(";");
                        if(s[0].equals("Mitarbeiter")){
                            Mitarbeiter.mitarbeiterListe.add(new Mitarbeiter(s[1], s[2], s[3], s[4]));
                        }
                        else if(s[0].equals("Auftrag")){
                            Auftraege.auftragListe.add(new Auftraege(s[1], s[2], s[3], s[4], s[5], s[6], Integer.parseInt(s[7])));
                        }
                        else if(s[0].equals("Zuordnung")){
                            Zuordnung.zuordnungListe.add(new Zuordnung(s[1], s[2]));
                        }
                    }

                }catch (Exception d){
                    System.out.println(d);
                }
            }
        });


    }
}
