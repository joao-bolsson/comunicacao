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
import java.util.ArrayList;
import java.util.List;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import model.Frame;

import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

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

    private static final byte SECONDS = 2;

    private final Timer timer = new Timer();

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

    private void connect() throws IOException {
        socket = new Socket(txtIP.getText(), Integer.parseInt(txtPorta.getText()));
        ou = socket.getOutputStream();
        ouw = new OutputStreamWriter(ou);
        bfw = new BufferedWriter(ouw);
        bfw.write(txtNome.getText() + "\r\n");
        bfw.flush();
    }

    private final List<Frame> okFrames = new ArrayList<>();

    private void sendMessage(final String message) throws IOException {
        String[] split = message.split("(?<=\\G.{2})"); // quebra a mensagem de 2 em 2 caracteres

        // apenas um contador para determinar em qual frame vai dar erro
        i = 0;
        System.out.println("Enviando mensagem: " + message);

        okFrames.clear();
        for (String msg : split) {
            // vai dar erro sempre no segundo
            Frame frame = new Frame(msg, ++i == 2);

            sendFrame(frame);
        }
    }

    private int i = 0;

    private void sendFrame(final Frame frame) throws IOException {
        // data with checksum
        char[] data = Frame.toCharArray(frame.getData());

        // removes all the canceled tasks
        timer.purge();
        // não reenvia ACK
        if (!frame.toString().contains("ok")) {
            System.out.println("vai verificar se o frame " + frame + " ja foi enviado daqui há " + SECONDS + "s");
            timer.schedule(new RemindTask(frame), SECONDS * 1000);
        }

        System.out.println("sending frame " + frame);
        bfw.write(data);
        bfw.newLine();
        txtChat.append(txtNome.getText() + ": " + txtMsg.getText() + "\r\n");

        bfw.flush();
        txtMsg.setText("");
    }

    private void listen() throws IOException {
        InputStream in = socket.getInputStream();
        InputStreamReader inr = new InputStreamReader(in);
        BufferedReader bfr = new BufferedReader(inr);
        String msg = "";

        while (!"Sair".equalsIgnoreCase(msg)) {
            if (bfr.ready()) {
                msg = bfr.readLine();

                int lastIndexOf = msg.lastIndexOf(">");
                // isso vem em formato de caracteres 65 66 sum
                String realMessage = msg.substring(lastIndexOf + 1);

                // ack frame
                Frame frame = new Frame("ok - " + realMessage);

                // data with checksum
                char[] data = Frame.toCharArray(frame.getData());

                String valueOf = String.valueOf(data);

                if (valueOf.equals(realMessage)) {
                    System.out.println("ACK do frame " + realMessage + " recebida");
                    okFrames.add(new Frame(realMessage));
                } else {
                    byte checksum = Frame.checksumFromString(realMessage);
                    if (checksum == 0) {
                        System.out.println("frame " + realMessage + " recebido sem erros");

                        okFrames.add(new Frame(realMessage));

                        // envia ACK
                        sendMessage("ok - " + Frame.decodeMessage(realMessage));
                        txtChat.append(msg + "\r\n");
                    } else {
                        System.out.println("frame " + realMessage + " recebido com erros, descarta");
                    }
                }
            }
        }
    }

    private void exit() throws IOException {
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

    /**
     * Task to execute when timer up.
     */
    private class RemindTask extends TimerTask {

        private final Frame frame;

        RemindTask(final Frame frame) {
            this.frame = frame;
        }

        @Override
        public void run() {
            if (!okFrames.contains(frame)) {

                System.out.println("Não recebeu confirmação do frame " + frame);

                // cancel this task
                cancel();

                try {
                    System.out.println("Enviando o frame novamente"); // sem erros
                    sendFrame(new Frame(frame.getMsg()));
                } catch (IOException ex) {
                    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

}
