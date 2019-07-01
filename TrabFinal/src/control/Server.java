package control;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author João Bolsson (jvmarques@inf.ufsm.br).
 * @version 2019, Jun 30.
 */
public class Server {

    public Server() {
    }

    public void start() {
        if (1 > 0) {
            start2();
            return;
        }
        ServerSocket servidor;
        try {
            servidor = new ServerSocket(12345);

            System.out.println("Porta 12345 aberta!");

            try (Socket cliente = servidor.accept()) {
                System.out.println("Nova conexão com o cliente " + cliente.getInetAddress().getHostAddress());

                try (Scanner s = new Scanner(cliente.getInputStream())) {
                    while (s.hasNextLine()) {
                        System.out.println(s.nextLine());
                    }
                }
                servidor.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void start2() {
        try (DatagramSocket ds = new DatagramSocket(3000)) {
            byte[] buf = new byte[1024];

            DatagramPacket dp = new DatagramPacket(buf, 1024);
            ds.receive(dp);

            String strRecv = new String(dp.getData(), 0, dp.getLength());
            System.out.println(strRecv);
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
