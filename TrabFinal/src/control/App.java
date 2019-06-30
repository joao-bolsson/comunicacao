/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

/**
 *
 * @author joao
 */
public class App {

    private final Client client;

    private final Server server;

    public App() {
        client = new Client();
        server = new Server();
    }

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

    public static void main(String[] args) {
        App app = new App();
        app.start();
    }

}
