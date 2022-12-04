import javax.sound.midi.MidiDeviceTransmitter;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class ZuordnungTabelle extends JFrame{


    ZuordnungTabelle(){


        //Tabelle
        //Komponenten werden erstellt für Tabelle
        String mHeaders[] = {"Auftragsnummer", "Mitarbeiter"};
        DefaultTableModel model = new DefaultTableModel(mHeaders, 0);
        JTable zuordnungTable = new JTable(model);
        TableRowSorter<TableModel> trs = new TableRowSorter<>(model);
        zuordnungTable.setRowSorter(trs);
        JScrollPane scroll = new JScrollPane(zuordnungTable);
        setLayout(new BorderLayout());


        //Defaultwert werden wenn vorhanden bzw. Werte aus der Arraylist werden der Tabelle hinzugefügt
        for (int i = 0; i < Zuordnung.zuordnungListe.size(); i++) {
            model.addRow(new Object[]{Zuordnung.zuordnungListe.get(i).getId(), Zuordnung.zuordnungListe.get(i).getName()});
        }






        //SEARCHBAR grafische komponenten werden erzeugt
        JLabel searchLabel = new JLabel("Suche");
        JTextField tfSearch = new JTextField(15);
        JPanel searchPanel = new JPanel(new FlowLayout());
        searchPanel.add(searchLabel);
        searchPanel.add(tfSearch);


        //funktion/event für die searchbar
        //ist ein Interface und beobachtet ob im Textfeld irgendwelche veränderungen Stattfinden und was dann getan werden muss
        tfSearch.getDocument().addDocumentListener(new DocumentListener() {

            //Methode die bei Event stattfinden soll - schaut nach ob um Suchfeld etwas steht oder nicht und was getan werden soll
            public void search(String text){
                if(text.length() == 0) {
                    trs.setRowFilter(null);
                }
                else{
                    //trs.setRowFilter(RowFilter.regexFilter(text));//Case Sensitive
                    trs.setRowFilter(RowFilter.regexFilter("(?i)" + text)); //Case Insesitive
                }
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                search(tfSearch.getText());
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                search(tfSearch.getText());
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
                search(tfSearch.getText());
            }
        });


        //Liste mit Mitarbeiternamen und IDs für Combobox
        String[] mitarbeiterNamen = new String[Mitarbeiter.mitarbeiterListe.size()+1];
        for (int i = 1; i <= Mitarbeiter.mitarbeiterListe.size(); i++){
            mitarbeiterNamen[i] = Mitarbeiter.mitarbeiterListe.get(i-1).getName();
        }

        String[] auftragId = new String[Auftraege.auftragListe.size()+1];
        for (int i = 1; i <= Auftraege.auftragListe.size(); i++){
            auftragId[i] = String.valueOf(Auftraege.auftragListe.get(i-1).getId());
        }



        //Label für Combobox
        JLabel jlId = new JLabel("ID");
        JLabel jlName = new JLabel("Name");

        //Textfelder
        JComboBox cbId = new JComboBox(auftragId);
        JComboBox cbNamen = new JComboBox(mitarbeiterNamen);
        cbNamen.setSize(new Dimension(1,25));
        JPanel textPanel = new JPanel(new FlowLayout());

        //Button
        JButton addButton = new JButton("Einfügen");
        JButton delButton = new JButton("Löschen");
        JButton infoButton = new JButton("Datenblatt");
        JPanel buttonPanel = new JPanel();




        //ButtonEvents
        //Füg eine Neue Zeile ein, wenn Textfeld vollständig ausgefüllt
        //gibt Fehler aus wenn nicht alle Textfelder ausgefüllt sind
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //ComboBox werte
                if(cbId.getSelectedItem() != null || cbNamen.getSelectedItem() != null) {
                    String wertId = cbId.getSelectedItem().toString();
                    String wertName = cbNamen.getSelectedItem().toString();

                    //in Tabelle einfügen
                    model.addRow(new Object[]{wertId, wertName});

                    //in Arrayliste einfügen
                    Zuordnung.zuordnungListe.add(new Zuordnung(wertId, wertName));
                }
                else {
                    Popup pu = new Popup("Bitte Daten in der ComboBox aussuchen");
                }
            }
        });

        //Löscht ausgewählte Zeile
        //gibt Fehler aus wenn keine Zeile ausgewählt wurde
        delButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(zuordnungTable.getSelectedRow() != -1){
                    int index = zuordnungTable.getSelectedRow();

                    //ComboBox werte


                    //löschen im arraay
                    for (int i = 0; i < Zuordnung.zuordnungListe.size(); i++){
                        if(Zuordnung.zuordnungListe.get(i).getId().equals(zuordnungTable.getValueAt(index, 0)) && Zuordnung.zuordnungListe.get(i).getName().equals(zuordnungTable.getValueAt(index, 1))){
                            Zuordnung.zuordnungListe.remove(i);
                        }
                    }

                    //löschen in tabelle
                    model.removeRow(zuordnungTable.getSelectedRow());
                }
                else{
                    Popup pu = new Popup("Bitte die zulöschene Zeile auswählen");
                }

            }
        });

        //erstellt ein neues Object Datenblatt mit allen wichtigen informationen
        infoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(zuordnungTable.getSelectedRow() != -1) {
                    int index = zuordnungTable.getSelectedRow();
                    String wertId = zuordnungTable.getValueAt(index, 0).toString();
                    String wertName = zuordnungTable.getValueAt(index, 1).toString();
                    Datenblatt db = new Datenblatt(wertId);
                }
                else{
                    Popup pu = new Popup("Bitte gewünschten Auftrag in Tabelle auswählen!");
                }

            }
        });




        //Komponenten in Panel einfügen
        buttonPanel.add(addButton);
        buttonPanel.add(delButton);
        buttonPanel.add(infoButton);

        textPanel.add(jlId);
        textPanel.add(cbId);
        textPanel.add(jlName);
        textPanel.add(cbNamen);

        JPanel processingPanel= new JPanel(new BorderLayout());
        processingPanel.add(textPanel, BorderLayout.NORTH);
        processingPanel.add(buttonPanel, BorderLayout.SOUTH);


        //Komponenten für MAIN FRAME
        add(searchPanel, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        add(processingPanel, BorderLayout.SOUTH);




        //Standard JFrame sachen
        //setLayout(new FlowLayout(FlowLayout.CENTER));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Administration - Zuordnung");
        setLocation(500,0);
        setSize(850, 700);
        setResizable(false);
        setVisible(true);
    }
}
