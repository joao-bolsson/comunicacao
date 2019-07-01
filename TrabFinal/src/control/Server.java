package control;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

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
            // Instancia o ServerSocket ouvindo a porta 12345
            ServerSocket servidor = new ServerSocket(port);
            System.out.println("Servidor ouvindo a porta " + port);
            while (true) {
                // o método accept() bloqueia a execução até que
                // o servidor receba um pedido de conexão
                try (Socket cliente = servidor.accept()) {
                    System.out.println("Cliente conectado: " + cliente.getInetAddress().getHostAddress());
                    try (ObjectOutputStream saida = new ObjectOutputStream(cliente.getOutputStream())) {
                        saida.flush();
                        saida.writeObject(new Date());
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

}
