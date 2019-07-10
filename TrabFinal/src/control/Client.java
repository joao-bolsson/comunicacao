package control;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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
public class Client extends JFrame {

    private static final long serialVersionUID = 1L;
    private final JTextArea txtChat = new JTextArea(10, 20);
    private final JTextField txtMsg = new JTextField(20);
    private final JButton btnSend = new JButton("Enviar");
    private final JButton btnExit = new JButton("Sair");

    private final JLabel lblChat = new JLabel("Histórico");
    private final JLabel lblMsg = new JLabel("Mensagem");
    private final JPanel pnlContent = new JPanel();
    private Socket socket;
    private OutputStream ou;
    private Writer ouw;
    private BufferedWriter bfw;
    private final JTextField txtIP = new JTextField("127.0.0.1");
    private final JTextField txtPorta = new JTextField("12345");
    private final JTextField txtNome = new JTextField("Cliente");

    /**
     * Creates a client panel.
     */
    public Client() {
        JLabel lblMessage = new JLabel("Verificar!");
        Object[] texts = {lblMessage, txtIP, txtPorta, txtNome};
        JOptionPane.showMessageDialog(null, texts);

        addListeners();
        init();
    }

    private void addListeners() {
        btnSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                try {
                    sendMessage(txtMsg.getText());
                } catch (final IOException ex) {
                    JOptionPane.showMessageDialog(Client.this, "Erro ao enviar mensagem: " + ex);
                }
            }
        });
        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                try {
                    exit();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(Client.this, "Erro ao sair: " + ex);
                }
            }
        });
        txtMsg.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(final KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    System.out.println("enter");
                    try {
                        sendMessage(txtMsg.getText());
                    } catch (final IOException e1) {
                        System.out.println(e1);
                    }
                }
            }
        });
    }

    private void init() {
        txtChat.setEditable(false);
        txtChat.setBackground(new Color(240, 240, 240));
        btnExit.setToolTipText("Sair do Chat");

        JScrollPane scroll = new JScrollPane(txtChat);
        txtChat.setLineWrap(true);
        pnlContent.add(lblChat);
        pnlContent.add(scroll);
        pnlContent.add(lblMsg);
        pnlContent.add(txtMsg);
        pnlContent.add(btnExit);
        pnlContent.add(btnSend);
        pnlContent.setBackground(Color.LIGHT_GRAY);
        txtChat.setBorder(BorderFactory.createEtchedBorder(Color.BLUE, Color.BLUE));
        txtMsg.setBorder(BorderFactory.createEtchedBorder(Color.BLUE, Color.BLUE));

        setTitle(txtNome.getText());
        setContentPane(pnlContent);
        setLocationRelativeTo(null);
        setResizable(false);
        setSize(250, 300);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    /**
     * Connects to the server.
     *
     * @throws IOException
     */
    public void connect() throws IOException {
        socket = new Socket(txtIP.getText(), Integer.parseInt(txtPorta.getText()));
        ou = socket.getOutputStream();
        ouw = new OutputStreamWriter(ou);
        bfw = new BufferedWriter(ouw);
        bfw.write(txtNome.getText() + "\r\n");
        bfw.flush();
    }

    /**
     * Sends the message.
     *
     * @param msg Message to send.
     * @throws IOException
     */
    public void sendMessage(final String msg) throws IOException {
        if (msg.equals("Sair")) {
            bfw.write("Desconectado \r\n");
            txtChat.append("Desconectado \r\n");
        } else {
            bfw.write(msg + "\r\n");
            txtChat.append(txtNome.getText() + " diz -> " + txtMsg.getText() + "\r\n");
        }
        bfw.flush();
        txtMsg.setText("");
    }

    /**
     * Listen the channel.
     *
     * @throws IOException
     */
    public void listen() throws IOException {
        InputStream in = socket.getInputStream();
        InputStreamReader inr = new InputStreamReader(in);
        BufferedReader bfr = new BufferedReader(inr);
        String msg = "";

        while (!"Sair".equalsIgnoreCase(msg)) {
            if (bfr.ready()) {
                msg = bfr.readLine();
                if (msg.equals("Sair")) {
                    txtChat.append("Servidor caiu! \r\n");
                } else {
                    txtChat.append(msg + "\r\n");
                }
            }
        }
    }

    /**
     * Exit from communication channel.
     *
     * @throws IOException
     */
    public void exit() throws IOException {
        sendMessage("Sair");
        bfw.close();
        ouw.close();
        ou.close();
        socket.close();
    }

    /**
     * Main method to show the client panel.
     *
     * @param args Command line arguments.
     * @throws IOException
     */
    public static void main(final String[] args) throws IOException {
        Client app = new Client();
        app.connect();
        app.listen();
    }

}
