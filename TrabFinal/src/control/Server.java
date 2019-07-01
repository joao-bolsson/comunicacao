package control;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author João Bolsson (jvmarques@inf.ufsm.br).
 * @version 2019, Jun 30.
 */
public class Server {

    private final int port;

    /**
     * Creates a server to listen the given port.
     *
     * @param port Given port to listen.
     */
    public Server(final int port) {
        this.port = port;
    }

    /**
     * Starts the server to receive messages.
     */
    public void start() {
        try {
            ServerSocket server = new ServerSocket(port);
            System.out.println("Server started");

            System.out.println("Waiting for a client ...");

            DataInputStream in;
            try (Socket socket = server.accept()) {
                System.out.println("Client accepted");
                // takes input from the client socket
                in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
                String line = "";
                // reads message from client until "Over" is sent
                while (!line.equalsIgnoreCase("over")) {
                    try {
                        line = in.readUTF();
                        System.out.println("Mensagem recebida: " + line);

                    } catch (IOException i) {
                        System.out.println(i);
                    }
                }
                System.out.println("Closing connection");
            }
            in.close();
        } catch (IOException i) {
            System.out.println(i);
        }
    }

}
