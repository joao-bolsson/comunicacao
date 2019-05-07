package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import control.Plane;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;

/**
 *
 * @author João Bolsson (jvmarques@inf.ufsm.br)
 * @version 2019, Apr 27.
 */
public class Main extends JPanel {

    private static final short WIDTH_PANEL = 500, HEIGHT_PANEL = 460;

    private final Plane plane = new Plane(-5, 0, 5);

    private final JButton btnOk;

    private final JTextField textField = new JTextField(20);

    private final JCheckBox checkNRZL = new JCheckBox("NRZ-L"),
            checkNRZI = new JCheckBox("NRZ-I"),
            checkAMI = new JCheckBox("AMI"),
            checkPseudo = new JCheckBox("Pseudoternário"),
            checkManch = new JCheckBox("Manchester"),
            checkManchDif = new JCheckBox("Manchester Diferencial");

    /**
     * Default construct.
     */
    public Main() {
        super(new BorderLayout());
        btnOk = new JButton("OK");

        addListeners();
        init();
    }

    private JPanel buildOptPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Opções"));

        GridBagConstraints cons = new GridBagConstraints();
        cons.gridx = 0;
        cons.gridy = 0;
        cons.anchor = GridBagConstraints.NORTHWEST;
        cons.fill = GridBagConstraints.BOTH;
        cons.insets = new Insets(5, 5, 5, 5);

        panel.add(checkNRZL, cons);

        cons.gridx++;
        panel.add(checkNRZI, cons);

        cons.gridx++;
        panel.add(checkAMI, cons);

        cons.gridx = 0;
        cons.gridy++;
        panel.add(checkPseudo, cons);

        cons.gridx++;
        panel.add(checkManch, cons);

        cons.gridx++;
        panel.add(checkManchDif, cons);

        return panel;
    }

    private void init() {
        setPreferredSize(new Dimension(WIDTH_PANEL, HEIGHT_PANEL));

        JPanel optPanel = buildOptPanel();

        JPanel controlPanel = new JPanel(new GridBagLayout());

        GridBagConstraints cons = new GridBagConstraints();
        cons.gridx = 0;
        cons.gridy = 0;
        cons.anchor = GridBagConstraints.NORTHWEST;
        cons.fill = GridBagConstraints.BOTH;
        cons.insets = new Insets(5, 5, 5, 5);
        cons.gridwidth = 2;
        controlPanel.add(optPanel, cons);

        cons.gridwidth = 1;
        cons.gridy++;
        controlPanel.add(textField, cons);

        cons.gridx++;
        controlPanel.add(btnOk, cons);

        add(plane, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);
    }

    private void addListeners() {
        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(final KeyEvent e) {
                char c = e.getKeyChar();

                if (!(c != KeyEvent.VK_BACK_SPACE && (c == '0' || c == '1'))) {
                    e.consume();
                }
            }
        });

        checkNRZL.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                plane.drawNRZL(checkNRZL.isSelected());
            }
        });

        checkNRZI.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                plane.drawNRZI(checkNRZI.isSelected());
            }
        });

        checkAMI.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                plane.drawAMI(checkAMI.isSelected());
            }
        });

        checkPseudo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                plane.drawPseudo(checkPseudo.isSelected());
            }
        });

        checkManch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                plane.drawManch(checkManch.isSelected());
            }
        });

        checkManchDif.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                plane.drawManchDif(checkManchDif.isSelected());
            }
        });

        btnOk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                plane.clear();

                String text = textField.getText();
                plane.draw(text);
            }
        });
    }

    private void showFrame() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        final Main comp = new Main();
        frame.getContentPane().add(comp, BorderLayout.CENTER);

        frame.pack();
        frame.setVisible(true);
    }

    /**
     * Main function.
     *
     * @param args Command line arguments (ignore).
     */
    public static void main(final String[] args) {
        Main main = new Main();
        main.showFrame();
    }

}
