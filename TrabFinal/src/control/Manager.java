package control;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author João Bolsson (jvmarques@inf.ufsm.br)
 * @version 2019, Jul 10.
 */
public class Manager {

    private final ServerSocket server;

    /**
     * Creates a manager.
     *
     * @param port Port to manage the server.
     * @throws IOException
     */
    public Manager(int port) throws IOException {
        server = new ServerSocket(port);
    }

    private void waitForClients() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                while (true) {
                    System.out.println("Aguardando conexão...");
                    try {
                        Socket con = server.accept();
                        System.out.println("Cliente conectado...");
                        Thread t = new Server(con);
                        t.start();
                    } catch (final IOException e) {
                        System.out.println("Error: " + e);
                    }
                }
            }
        };

        thread.start();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Manager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void startClients() {
        Client client = new Client();
        Client otherClient = new Client();

//        client.init();
//        otherClient.init();
    }

    /**
     * Initializing the manager.
     */
    public void init() {
        waitForClients();
        startClients();
    }
}
