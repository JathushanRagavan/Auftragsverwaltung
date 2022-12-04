import javax.swing.*;
import javax.swing.border.Border;
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
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class MitarbeiterTabelle extends JFrame {


    MitarbeiterTabelle() {
        //Tabelle
        //Komponenten werden erstellt für Tabelle
        String mHeaders[] = {"Name", "Berufsbezeichnung", "Einstellungsdatum", "Jahresgehalt"};
        DefaultTableModel model = new DefaultTableModel(mHeaders, 0);
        JTable mitarbeiterTable = new JTable(model);
        TableRowSorter<TableModel> trs = new TableRowSorter<>(model);
        mitarbeiterTable.setRowSorter(trs);
        JScrollPane scroll = new JScrollPane(mitarbeiterTable);
        setLayout(new BorderLayout());


        //Werte aus der Arraylist werden der Tabelle hinzugefügt
        for (int i = 0; i < Mitarbeiter.mitarbeiterListe.size(); i++) {
            model.addRow(new Object[]{Mitarbeiter.mitarbeiterListe.get(i).getName(), Mitarbeiter.mitarbeiterListe.get(i).getBerufsbezeichnung(), Mitarbeiter.mitarbeiterListe.get(i).getEinstellungsdatum(), Mitarbeiter.mitarbeiterListe.get(i).getJahresgehalt()});
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



        //Label für einfügen/bearbeiten
        JLabel jlName = new JLabel("Name");
        JLabel jlBerufsbezeichung = new JLabel("Berufsbezeichnung");
        JLabel jlEinstellungsdatum = new JLabel("Einstellungsdatum");
        JLabel jlJahresgehalt = new JLabel("Jahresgehalt");

        //Textfelder für einfügen/bearbeiten
        JTextField tfName = new JTextField(7);
        JTextField tfBerufsbezeichung = new JTextField(7);
        JTextField tfEinstellungsdatum = new JTextField(7);
        JTextField tfJahresgehalt = new JTextField(7);
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


        //ButtonEvents
        //Füg eine Neue Zeile ein, wenn Textfeld vollständig ausgefüllt
        //gibt Fehler aus wenn nicht alle Textfelder ausgefüllt sind
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!tfName.getText().equals("") && !tfBerufsbezeichung.getText().equals("") && !tfEinstellungsdatum.getText().equals("") && !tfJahresgehalt.getText().equals("")){
                    //änderung im Tabelle
                    model.addRow(new Object[]{tfName.getText().trim(), tfBerufsbezeichung.getText().trim(), tfEinstellungsdatum.getText().trim(), tfJahresgehalt.getText().trim()});

                    //änderung in der Arrayliste
                    Mitarbeiter.mitarbeiterListe.add(new Mitarbeiter(tfName.getText().trim(), tfBerufsbezeichung.getText().trim(), tfEinstellungsdatum.getText().trim(), tfJahresgehalt.getText().trim()));
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
                if(mitarbeiterTable.getSelectedRow() != -1) {
                    int index = mitarbeiterTable.getSelectedRow();

                    //veränderung in der Arrayliste, jede spalte muss verglichen werden, im fall das 2 Mitarbeiter mit selben Namen/Berufsbezeichung/Einstellungsdatum/Gehalt
                    for (int i = 0; i < Mitarbeiter.mitarbeiterListe.size(); i++){
                        if(Mitarbeiter.mitarbeiterListe.get(i).getName().equals(mitarbeiterTable.getValueAt(index, 0))
                                && Mitarbeiter.mitarbeiterListe.get(i).getBerufsbezeichnung().equals(mitarbeiterTable.getValueAt(index, 1))
                                && Mitarbeiter.mitarbeiterListe.get(i).getEinstellungsdatum().equals(mitarbeiterTable.getValueAt(index, 2))
                                && Mitarbeiter.mitarbeiterListe.get(i).getJahresgehalt().equals(mitarbeiterTable.getValueAt(index, 3))){
                            Mitarbeiter.mitarbeiterListe.remove(i);
                        }
                    }

                    //Veränderung in der Tabelle
                    model.removeRow(mitarbeiterTable.getSelectedRow());

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
                //if(mitarbeiterTable.getSelectedRow() != -1 && (!tfName.getText().equals("") && !tfBerufsbezeichung.getText().equals("") && !tfEinstellungsdatum.getText().equals("") && !tfJahresgehalt.getText().equals(""))) {
                if(mitarbeiterTable.getSelectedRow() != -1){
                    int index = mitarbeiterTable.getSelectedRow();

                    //änderung in der Tabelle
                    model.setValueAt(tfName.getText().trim(), index, 0);
                    model.setValueAt(tfBerufsbezeichung.getText().trim(), index, 1);
                    model.setValueAt(tfEinstellungsdatum.getText().trim(), index, 2);
                    model.setValueAt(tfJahresgehalt.getText().trim(), index, 3);

                    //Änderungen in der Arrayliste
                    Mitarbeiter.mitarbeiterListe.set(index, new Mitarbeiter(tfName.getText().trim(), tfBerufsbezeichung.getText().trim(), tfEinstellungsdatum.getText().trim(), tfJahresgehalt.getText().trim()));


                }
                else{
                    Popup pu = new Popup("Bitte die zu bearbeitende Zeile auswählen");
                }
            }
        });

        //Tabellen Klick Event - Werte der Zeile die angeklickt wurden,  werden in Textfeld geschrieben - damit wir bearbeiten können
        mitarbeiterTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(mitarbeiterTable.getSelectedRow() != -1){
                    int index = mitarbeiterTable.getSelectedRow();
                    tfName.setText((String) model.getValueAt(index, 0));
                    tfBerufsbezeichung.setText((String) model.getValueAt(index, 1));
                    tfEinstellungsdatum.setText((String) model.getValueAt(index, 2));
                    tfJahresgehalt.setText((String) model.getValueAt(index, 3));
                }
            }
        });

        //löscht Werte aus den Textfeldern
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tfName.setText("");
                tfBerufsbezeichung.setText("");
                tfEinstellungsdatum.setText("");
                tfJahresgehalt.setText("");

            }
        });

        //nicht benutzt
        infoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


            }
        });




        //Komponenten in Panel einfügen
        textPanel.add(jlName);
        textPanel.add(tfName);
        textPanel.add(jlBerufsbezeichung);
        textPanel.add(tfBerufsbezeichung);
        textPanel.add(jlEinstellungsdatum);
        textPanel.add(tfEinstellungsdatum);
        textPanel.add(jlJahresgehalt);
        textPanel.add(tfJahresgehalt);

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
        setTitle("Administration - Mitarbeiter");
        setLocation(400,0);
        setSize(850, 700);
        setResizable(false);
        setVisible(true);
    }
}
