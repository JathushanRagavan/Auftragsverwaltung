import javax.imageio.stream.ImageInputStream;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

public class AuftraegTabelle extends JFrame {

    private int count = 0;


    AuftraegTabelle() {
        //Tabelle
        //Tabelle wird erstellt
        String aHeaders[] = {"Auftraggeber", "Ort", "Adresse", "Aufgabe", "Start", "Ende", "ID"};
        DefaultTableModel model = new DefaultTableModel(aHeaders, 0);
        JTable auftragTable = new JTable(model);
        TableRowSorter<TableModel> trs = new TableRowSorter<>(model);
        auftragTable.setRowSorter(trs);
        JScrollPane scroll = new JScrollPane(auftragTable);
        setLayout(new BorderLayout());


        //Werte aus der Arraylist werden der Tabelle hinzugefügt, damit man werte hat
        for (int i = 0; i < Auftraege.auftragListe.size(); i++){
            model.addRow(new Object[]{Auftraege.auftragListe.get(i).getAuftraggeber(), Auftraege.auftragListe.get(i).getOrt(), Auftraege.auftragListe.get(i).getAdresse(), Auftraege.auftragListe.get(i).getAufgabe(), Auftraege.auftragListe.get(i).getStart(), Auftraege.auftragListe.get(i).getEnde(), Auftraege.auftragListe.get(i).getId()});
            //count++;
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

            //Methode die bei Event stattfinden soll - schaut nach ob um Suchfeld etwas steht oder nicht und zeigt uns dann gefilterte Tabelle
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

        //Label für einfügen/bearbeiten
        JLabel jlAuftraggeber = new JLabel("Auftraggeber");
        JLabel jlOrt = new JLabel("Ort");
        JLabel jlAdresse = new JLabel("Adresse");
        JLabel jlAufgabe = new JLabel("Aufgabe");
        JLabel jlStart = new JLabel("Start");
        JLabel jlEnde = new JLabel("Ende");

        //Textfelder für einfügen/bearbeiten
        JTextField tfAuftraggeber = new JTextField(7);
        JTextField tfOrt = new JTextField(7);
        JTextField tfAdresse = new JTextField(7);
        JTextField tfAufgabe = new JTextField(7);
        JTextField tfStart = new JTextField(7);
        JTextField tfEnde = new JTextField(7);
        JPanel textPanel = new JPanel(new FlowLayout());

        //Button
        JButton addButton = new JButton("Einfügen");
        JButton delButton = new JButton("Löschen");
        JButton editButton = new JButton("Bearbeiten");
        JButton clearButton = new JButton("Zurücksetzen");
        JButton infoButton = new JButton("Info");
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(delButton);
        buttonPanel.add(editButton);
        buttonPanel.add(clearButton);
        //buttonPanel.add(infoButton);




        //Schaut nach was die letzte die größte ID ist und speichert diese ab sodass sie in addButton benutzt werden kann für automatische ID
        ArrayList<Integer> counter = new ArrayList<Integer>();
        for (int i=0; i < Auftraege.auftragListe.size(); i++){
            counter.add(Auftraege.auftragListe.get(i).getId());
        }

        if (counter.size() != 0) {
            count = Collections.max(counter);
        }

        //ButtonEvents
        //Füg eine Neue Zeile ein, wenn Textfeld vollständig ausgefüllt
        //gibt Fehler aus wenn nicht alle Textfelder ausgefüllt sind
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                count++;
                if (!tfAuftraggeber.getText().equals("") && !tfOrt.getText().equals("") && !tfAdresse.getText().equals("") && !tfAufgabe.getText().equals("") && !tfStart.getText().equals("") && !tfEnde.getText().equals("")){
                    //änderung in Tabelle
                    model.addRow(new Object[]{tfAuftraggeber.getText().trim(), tfOrt.getText().trim(), tfAdresse.getText().trim(), tfAufgabe.getText().trim(), tfStart.getText().trim(), tfEnde.getText().trim(), count});

                    //änderung in der Arrayliste
                    Auftraege.auftragListe.add((new Auftraege(tfAuftraggeber.getText().trim(), tfOrt.getText().trim(), tfAdresse.getText().trim(), tfAufgabe.getText().trim(), tfStart.getText().trim(), tfEnde.getText().trim(), count)));

                }
                else{
                    Popup pu = new Popup("Bitte alle Textfelder ausfüllen!");
                }
            }
        });

        //Löscht ausgewählte Zeile
        //gibt Fehler aus wenn keine Zeile ausgewählt wurde
        delButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(auftragTable.getSelectedRow() != -1) {
                    int index = auftragTable.getSelectedRow();

                    //änderung in der ArrayList
                    for (int i = 0; i < Auftraege.auftragListe.size(); i++){
                        if(Auftraege.auftragListe.get(i).getAuftraggeber().equals(auftragTable.getValueAt(index, 0))
                                && Auftraege.auftragListe.get(i).getOrt().equals(auftragTable.getValueAt(index, 1))
                                && Auftraege.auftragListe.get(i).getAdresse().equals(auftragTable.getValueAt(index, 2))
                                && Auftraege.auftragListe.get(i).getAufgabe().equals(auftragTable.getValueAt(index, 3))
                                && Auftraege.auftragListe.get(i).getStart().equals(auftragTable.getValueAt(index, 4))
                                && Auftraege.auftragListe.get(i).getEnde().equals(auftragTable.getValueAt(index, 5))){
                            Auftraege.auftragListe.remove(i);

                        }
                    }

                    ////änderung in der Tabelle
                    model.removeRow(auftragTable.getSelectedRow());

                }
                else{
                    Popup pu = new Popup("Bitte die zu löschende Zeile auswählen");
                }
            }
        });


        //Bearbeitet ausgewählte Zeile indem man in Textfeld neue Werte eingibt, PRoblem mann muss immer alles eingeben um zuändern - umständlich wenn man nur einen Wert ändern will -- gefixt
        //gibt Fehler aus wenn keine Zeile ausgewählt wurde
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //if (!tfAuftraggeber.getText().equals("") && !tfOrt.getText().equals("") && !tfAdresse.getText().equals("") && !tfAufgabe.getText().equals("") && !tfStart.getText().equals("") && !tfEnde.getText().equals("")) {
                if(auftragTable.getSelectedRow() != -1){
                    int index = auftragTable.getSelectedRow();
                    int idNumber = (Integer) model.getValueAt(index, 6);

                    //änderung in der Tabelle
                    model.setValueAt(tfAuftraggeber.getText().trim(), index, 0);
                    model.setValueAt(tfOrt.getText().trim(), index, 1);
                    model.setValueAt(tfAdresse.getText().trim(), index, 2);
                    model.setValueAt(tfAufgabe.getText().trim(), index, 3);
                    model.setValueAt(tfStart.getText().trim(), index, 4);
                    model.setValueAt(tfEnde.getText().trim(), index, 5);

                    //Änderung in Arraylist
                    Auftraege.auftragListe.set(index, new Auftraege(tfAuftraggeber.getText().trim(), tfOrt.getText().trim(), tfAdresse.getText().trim(), tfAufgabe.getText().trim(), tfStart.getText().trim(), tfEnde.getText().trim(), idNumber));


                }
                else{
                    Popup pu = new Popup("Bitte die zu bearbeitende Zeile auswählen!");
                }
            }
        });


        //Tabellen Klick Event - Werte der Zeile die angeklickt wurden,  werden in Textfeld geschrieben - damit wir bearbeiten können
        auftragTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(auftragTable.getSelectedRow() != -1){
                    int index = auftragTable.getSelectedRow();
                    tfAuftraggeber.setText((String) model.getValueAt(index, 0));
                    tfOrt.setText((String) model.getValueAt(index, 1));
                    tfAdresse.setText((String) model.getValueAt(index, 2));
                    tfAufgabe.setText((String) model.getValueAt(index, 3));
                    tfStart.setText((String) model.getValueAt(index, 4));
                    tfEnde.setText((String) model.getValueAt(index, 5));
                }
            }
        });

        //löscht Werte aus den Textfeldern
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tfAuftraggeber.setText("");
                tfOrt.setText("");
                tfAdresse.setText("");
                tfAufgabe.setText("");
                tfStart.setText("");
                tfEnde.setText("");

            }
        });

        //nicht benutzt
        infoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });



        //Komponenten in Panel einfügen
        textPanel.add(jlAuftraggeber);
        textPanel.add(tfAuftraggeber);
        textPanel.add(jlOrt);
        textPanel.add(tfOrt);
        textPanel.add(jlAdresse);
        textPanel.add(tfAdresse);
        textPanel.add(jlAufgabe);
        textPanel.add(tfAufgabe);
        textPanel.add(jlStart);
        textPanel.add(tfStart);
        textPanel.add(jlEnde);
        textPanel.add(tfEnde);

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
        setTitle("Administration - Auftraege");
        setLocation(500,0);
        setSize(850, 700);
        setResizable(false);
        setVisible(true);


    }
}
