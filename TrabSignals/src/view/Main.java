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
import model.Plane;

/**
 *
 * @author João Bolsson (jvmarques@inf.ufsm.br)
 * @version 2019, Apr 27.
 */
public class Main extends JPanel {

    private static final short WIDTH_PANEL = 320, HEIGHT_PANEL = 200;

    private final Plane plane = new Plane(-5, 0, 5);

    private final JButton btnOk;

    private final JTextField textField = new JTextField(20);

    /**
     * Default construct.
     */
    public Main() {
        super(new BorderLayout());
        btnOk = new JButton("OK");

        addListeners();
        init();
    }

    private void init() {
        setPreferredSize(new Dimension(WIDTH_PANEL, HEIGHT_PANEL));

        JPanel controlPanel = new JPanel(new GridBagLayout());

        GridBagConstraints cons = new GridBagConstraints();
        cons.gridx = 0;
        cons.gridy = 0;
        cons.anchor = GridBagConstraints.NORTHWEST;
        cons.fill = GridBagConstraints.BOTH;
        cons.insets = new Insets(5, 5, 5, 5);
        controlPanel.add(textField, cons);

        cons.gridx++;
        controlPanel.add(btnOk, cons);

        add(plane, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);
    }

    private void addListeners() {
        btnOk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                plane.clear();

                String text = textField.getText();
                plane.drawSimple(text);
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
