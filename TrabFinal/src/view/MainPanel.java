package view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

/**
 *
 * @author João Bolsson (jvmarques@inf.ufsm.br).
 * @version 2019, JuL 01.
 */
public class MainPanel extends JPanel {

    private final ChatPanel server, client;

    private final JButton btnStart;

    /**
     * Default construct.
     */
    public MainPanel() {
        super(new GridBagLayout());

        server = new ChatPanel("Servidor");
        client = new ChatPanel("Cliente");

        btnStart = new JButton("Iniciar");

        init();
    }

    private void init() {
        GridBagConstraints cons = new GridBagConstraints();
        cons.gridx = 0;
        cons.gridy = 0;
        cons.insets = new Insets(2, 2, 2, 2);
        cons.anchor = GridBagConstraints.NORTHWEST;
        cons.fill = GridBagConstraints.BOTH;

        add(server, cons);

        cons.gridx++;
        add(client, cons);

        cons.gridx = 0;
        cons.gridy++;
        cons.gridwidth = 2;
        add(btnStart, cons);
    }

    /**
     * Show this panel as a dialog.
     */
    public void showAsDialog() {
        JDialog dialog = new JDialog();

        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        dialog.add(this);
        dialog.pack();

        dialog.setVisible(true);
    }

    public static void main(final String[] args) {
        MainPanel main = new MainPanel();
        main.showAsDialog();
    }

}
