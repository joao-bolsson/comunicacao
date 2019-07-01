package control;

import java.io.IOException;
import java.io.PrintStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author João Bolsson (jvmarques@inf.ufsm.br).
 * @version 2019, Jun 30.
 */
public class Client {

    public Client() {
    }

    public void start() {
        if (1 > 0) {
            start2();
            return;
        }
        try {
            try (Socket cliente = new Socket("127.0.0.1", 12345)) {
                System.out.println("O cliente se conectou ao servidor!");

                try (Scanner teclado = new Scanner(System.in);
                        PrintStream saida = new PrintStream(cliente.getOutputStream())) {

                    while (teclado.hasNextLine()) {
                        saida.println(teclado.nextLine());
                    }

                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void start2() {
        try {
            DatagramSocket ds = new DatagramSocket();
            String str = "hello world";
            InetAddress ia;
            try {
                ia = InetAddress.getByName("127.0.0.1");
                DatagramPacket dp = new DatagramPacket(str.getBytes(), str.length(), ia, 3000);
                try {
                    ds.send(dp);
                } catch (IOException ex) {
                    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                }
                ds.close();
            } catch (UnknownHostException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SocketException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
