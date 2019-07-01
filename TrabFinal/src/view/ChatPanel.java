package view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author João Bolsson (jvmarques@inf.ufsm.br).
 * @version 2019, Jul 01.
 */
public class ChatPanel extends JPanel {

    private final String title;

    private final JTextArea txtArea;
    private final JTextField txtMessage;
    private final JButton btnSend;

    /**
     * Creates a chat panel.
     *
     * @param title Title of this panel.
     */
    public ChatPanel(final String title) {
        super(new GridBagLayout());
        this.title = title;

        txtArea = new JTextArea(10, 10);
        txtMessage = new JTextField(20);
        btnSend = new JButton("Enviar");

        init();
    }

    private void init() {
        setBorder(BorderFactory.createTitledBorder(title));

        GridBagConstraints cons = new GridBagConstraints();
        cons.gridx = 0;
        cons.gridy = 0;
        cons.insets = new Insets(2, 2, 2, 2);
        cons.anchor = GridBagConstraints.NORTHWEST;
        cons.fill = GridBagConstraints.BOTH;
        cons.gridwidth = 3;

        add(txtArea, cons);

        cons.gridy++;
        cons.gridwidth = 2;
        add(txtMessage, cons);

        cons.gridx += 2;
        cons.gridwidth = 1;
        add(btnSend, cons);
    }

}
