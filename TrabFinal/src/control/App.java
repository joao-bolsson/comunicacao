package control;

import java.io.IOException;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author João Bolsson (jvmarques@inf.ufsm.br)
 * @version 2019, Jul 10.
 */
public class App {

    private final Manager manager;

    public App(int port) throws IOException {
        manager = new Manager(port);
    }

    private void init() {
        manager.init();
    }

    /**
     * Main method to execute the application.
     *
     * @param args Command line arguments.
     */
    public static void main(final String[] args) {
        //Cria os objetos necessário para instânciar o servidor
        JLabel lblMessage = new JLabel("Porta do Servidor:");
        JTextField txtPorta = new JTextField("12345");
        Object[] texts = {lblMessage, txtPorta};
        JOptionPane.showMessageDialog(null, texts);

        try {
            App app = new App(Integer.parseInt(txtPorta.getText()));
            app.init();
        } catch (final IOException ex) {
            System.out.println("Error: " + ex);
        }
    }

}
