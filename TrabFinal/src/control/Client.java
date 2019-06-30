/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.awt.HeadlessException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author joao
 */
public class Client {

    public Client() {
    }

    public void start() {
        try {
            // localhost
            Socket cliente = new Socket("127.0.0.1", 12345);
            try (ObjectInputStream entrada = new ObjectInputStream(cliente.getInputStream())) {
                Date data_atual = (Date) entrada.readObject();
                JOptionPane.showMessageDialog(null, "Data recebida do servidor:" + data_atual.toString());
            }
            System.out.println("Conexão encerrada");
        } catch (HeadlessException | IOException | ClassNotFoundException e) {
            System.out.println("Erro: " + e);
        }
    }

}
