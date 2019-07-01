package control;

import java.awt.HeadlessException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author João Bolsson (jvmarques@inf.ufsm.br).
 * @version 2019, Jun 30.
 */
public class Client {

    private final int port;

    /**
     * Creates a client socket to listen the given port.
     *
     * @param port Given port to listen.
     */
    public Client(final int port) {
        this.port = port;
    }

    /**
     * Starts the client to send messages.
     */
    public void start() {
        try {
            Socket cliente = new Socket("127.0.0.1", port);
            try (ObjectInputStream entrada = new ObjectInputStream(cliente.getInputStream())) {
                Date data_atual = (Date) entrada.readObject();
                JOptionPane.showMessageDialog(null, "Data recebida do servidor:" + data_atual.toString());
            }
            System.out.println("Conexão Encerrada");
        } catch (HeadlessException | IOException | ClassNotFoundException e) {
            System.out.println("Erro: " + e);
        }
    }

}
