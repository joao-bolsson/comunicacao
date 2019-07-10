package control;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.Socket;
import javax.swing.*;
import static javax.swing.JFrame.EXIT_ON_CLOSE;

/**
 *
 * @author João Bolsson (jvmarques@inf.ufsm.br)
 * @version 2019, Jul 10.
 */
public class Client extends JFrame implements ActionListener, KeyListener {

    private static final long serialVersionUID = 1L;
    private final JTextArea texto;
    private final JTextField txtMsg;
    private final JButton btnSend;
    private final JButton btnSair;
    private final JLabel lblHistorico;
    private final JLabel lblMsg;
    private final JPanel pnlContent;
    private Socket socket;
    private OutputStream ou;
    private Writer ouw;
    private BufferedWriter bfw;
    private final JTextField txtIP;
    private final JTextField txtPorta;
    private final JTextField txtNome;

    public Client() throws IOException {
        JLabel lblMessage = new JLabel("Verificar!");
        txtIP = new JTextField("127.0.0.1");
        txtPorta = new JTextField("12345");
        txtNome = new JTextField("Cliente");
        Object[] texts = {lblMessage, txtIP, txtPorta, txtNome};
        JOptionPane.showMessageDialog(null, texts);
        pnlContent = new JPanel();
        texto = new JTextArea(10, 20);
        txtMsg = new JTextField(20);
        lblHistorico = new JLabel("Histórico");
        lblMsg = new JLabel("Mensagem");
        btnSend = new JButton("Enviar");
        btnSend.setToolTipText("Enviar Mensagem");
        btnSair = new JButton("Sair");

        init();
    }

    private void init() {
        texto.setEditable(false);
        texto.setBackground(new Color(240, 240, 240));
        btnSair.setToolTipText("Sair do Chat");
        btnSend.addActionListener(this);
        btnSair.addActionListener(this);
        btnSend.addKeyListener(this);
        txtMsg.addKeyListener(this);
        JScrollPane scroll = new JScrollPane(texto);
        texto.setLineWrap(true);
        pnlContent.add(lblHistorico);
        pnlContent.add(scroll);
        pnlContent.add(lblMsg);
        pnlContent.add(txtMsg);
        pnlContent.add(btnSair);
        pnlContent.add(btnSend);
        pnlContent.setBackground(Color.LIGHT_GRAY);
        texto.setBorder(BorderFactory.createEtchedBorder(Color.BLUE, Color.BLUE));
        txtMsg.setBorder(BorderFactory.createEtchedBorder(Color.BLUE, Color.BLUE));

        setTitle(txtNome.getText());
        setContentPane(pnlContent);
        setLocationRelativeTo(null);
        setResizable(false);
        setSize(250, 300);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void conectar() throws IOException {

        socket = new Socket(txtIP.getText(), Integer.parseInt(txtPorta.getText()));
        ou = socket.getOutputStream();
        ouw = new OutputStreamWriter(ou);
        bfw = new BufferedWriter(ouw);
        bfw.write(txtNome.getText() + "\r\n");
        bfw.flush();
    }

    public void enviarMensagem(String msg) throws IOException {

        if (msg.equals("Sair")) {
            bfw.write("Desconectado \r\n");
            texto.append("Desconectado \r\n");
        } else {
            bfw.write(msg + "\r\n");
            texto.append(txtNome.getText() + " diz -> " + txtMsg.getText() + "\r\n");
        }
        bfw.flush();
        txtMsg.setText("");
    }

    public void escutar() throws IOException {

        InputStream in = socket.getInputStream();
        InputStreamReader inr = new InputStreamReader(in);
        BufferedReader bfr = new BufferedReader(inr);
        String msg = "";

        while (!"Sair".equalsIgnoreCase(msg)) {
            if (bfr.ready()) {
                msg = bfr.readLine();
                if (msg.equals("Sair")) {
                    texto.append("Servidor caiu! \r\n");
                } else {
                    texto.append(msg + "\r\n");
                }
            }
        }
    }

    public void sair() throws IOException {

        enviarMensagem("Sair");
        bfw.close();
        ouw.close();
        ou.close();
        socket.close();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        try {
            if (e.getActionCommand().equals(btnSend.getActionCommand())) {
                enviarMensagem(txtMsg.getText());
            } else if (e.getActionCommand().equals(btnSair.getActionCommand())) {
                sair();
            }
        } catch (final IOException e1) {
            System.out.println(e1);
        }
    }

    @Override
    public void keyPressed(final KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            try {
                enviarMensagem(txtMsg.getText());
            } catch (final IOException e1) {
                System.out.println(e1);
            }
        }
    }

    @Override
    public void keyReleased(final KeyEvent arg0) {
    }

    @Override
    public void keyTyped(final KeyEvent arg0) {
    }

    public static void main(final String[] args) throws IOException {
        Client app = new Client();
        app.conectar();
        app.escutar();
    }

}
