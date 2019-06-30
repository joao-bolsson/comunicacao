/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author joao
 */
public class Server {

    public Server() {
    }

    public void start() {
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

}
