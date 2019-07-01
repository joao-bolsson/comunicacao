package control;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

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
            Socket socket = new Socket("127.0.0.1", port);
            System.out.println("Connected");

            // takes input from terminal
            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

            // sends output to the socket
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            // string to read message from input
            String line = "";

            // keep reading until "Over" is input
            while (!line.equals("Over")) {
                try {
                    line = input.readLine();

                    System.out.println("Mensagem Enviada: " + line);
                    out.writeUTF(line);
                } catch (IOException i) {
                    System.out.println(i);
                }
            }

            // close the connection
            try {
                input.close();
                out.close();
                socket.close();
            } catch (IOException i) {
                System.out.println(i);
            }
        } catch (UnknownHostException u) {
            System.out.println(u);
        } catch (IOException i) {
            System.out.println(i);
        }
    }

}
