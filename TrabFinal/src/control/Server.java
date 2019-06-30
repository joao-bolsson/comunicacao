/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

/**
 *
 * @author joao
 */
public class Server {

    public Server() {
    }

    public void start() {
        try {
            // Instancia o ServerSocket ouvindo a porta 12345
            ServerSocket servidor = new ServerSocket(12345);
            System.out.println("Servidor ouvindo a porta 12345");
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
