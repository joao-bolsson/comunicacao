package model;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author João Bolsson (jvmarques@inf.ufsm.br).
 * @version 2019, Jun 30.
 */
public abstract class CustomSocket {

    private DataOutputStream out;
    private DataInputStream in;

    private ServerSocket server; // server

    private Socket socket; // client

    protected final int port;

    protected final String host;

    Thread thServer = new Thread() {
        @Override
        public void run() {
            try {
                server = new ServerSocket(port);
                System.out.println("Server started");
                waitForAClient();
            } catch (final IOException ex) {
                System.out.println("Could'n start the server side: " + ex);
            }
        }
    };

    Thread thClient = new Thread() {
        @Override
        public void run() {
            if (!host.isEmpty()) {
                try {
                    socket = new Socket(host, port);
                    System.out.println("Client Connected");
                } catch (final IOException ex) {
                    System.out.println("Could'n start the client side: " + ex);
                }
            }
        }

    };

    /**
     * Creates a custom socket.
     *
     * @param port Port to listen.
     */
    public CustomSocket(int port) {
        this(port, "");
    }

    /**
     * Creates a custom socket.
     *
     * @param port Socket port.
     * @param host Socket host.
     */
    public CustomSocket(int port, final String host) {
        this.port = port;
        this.host = host;
    }

    /**
     * Starts the socket.
     */
    public void start() {
        thServer.start();
        thClient.start();
    }

    /**
     * Sends a message to the server.
     *
     * @param message Message to send.
     */
    public void sendMessage(final String message) {
        try {
            // sends output to the socket
            out = new DataOutputStream(socket.getOutputStream());
            if (!"over".equalsIgnoreCase(message)) {
                out.writeUTF(message);
            }
        } catch (IOException ex) {
            System.out.println("Couldn't write message to the output to send for the server: " + ex);
        }

    }

    /**
     * Method to receive and treat a message.
     *
     * @param txt Mess
     */
    public abstract void receiveMessage(String txt);

    private void waitForAClient() {
        System.out.println("Waiting for a client ...");
        try (Socket client = server.accept()) {
            System.out.println("Client accepted");
            // takes input from the client socket
            in = new DataInputStream(new BufferedInputStream(client.getInputStream()));
            String line = "";
            // reads message from client until "Over" is sent
            while (!line.equalsIgnoreCase("over")) {
                try {
                    line = in.readUTF();

                    receiveMessage(line);
                } catch (final IOException i) {
                    System.out.println("Can't reading the message from client: " + i);
                    break;
                }
            }
        } catch (final IOException i) {
            System.out.println("Can't accept the client: " + i);
        }
    }

}
