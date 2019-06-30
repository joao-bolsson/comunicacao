/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author joao
 */
public class Client {

    public Client() {
    }

    public void start() {
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

}
