import javax.swing.*;

public class Popup extends JFrame {

    //Konstruktor der Fehlermeldung ausgeben soll
    public Popup(String s){
        JOptionPane.showMessageDialog(null, s,
                "Achtung!", JOptionPane.ERROR_MESSAGE);

    }


}
