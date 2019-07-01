package control;

/**
 *
 * @author João Bolsson (jvmarques@inf.ufsm.br).
 * @version 2019, Jun 30.
 */
public class App {

    private static final int PORT = 12345;

    // TODO: temporary code
    public static final boolean USE_DATAGRAM = true;

    private final Client client;

    private final Server server;

    public App() {
        client = new Client(PORT);
        server = new Server(PORT);
    }

    /**
     * Starts the application.
     */
    public void start() {
        Thread thServer = new Thread() {
            @Override
            public void run() {
                server.start();
            }
        };

        Thread thClient = new Thread() {
            @Override
            public void run() {
                client.start();
            }

        };

        thServer.start();
        thClient.start();
    }

    /**
     * Main method to run the APP.
     *
     * @param args Command line arguments.
     */
    public static void main(final String[] args) {
        App app = new App();
        app.start();
    }

}
