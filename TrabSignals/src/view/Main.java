package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import model.Line;
import model.Plane;

/**
 *
 * @author João Bolsson (jvmarques@inf.ufsm.br)
 * @version 2019, Apr 27.
 */
public class Main extends JPanel {

    private static final short WIDTH_PANEL = 320, HEIGHT_PANEL = 200;

    private final Plane plane = new Plane();

    private final JButton newLineButton, clearButton;

    /**
     * Default construct.
     */
    public Main() {
        super(new BorderLayout());
        newLineButton = new JButton("New Line");
        clearButton = new JButton("Clear");

        addListeners();
        init();
    }

    private void init() {
        setPreferredSize(new Dimension(WIDTH_PANEL, HEIGHT_PANEL));
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(newLineButton);
        buttonsPanel.add(clearButton);

        add(plane, BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);
    }

    private void addListeners() {
        newLineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                int x1 = (int) (Math.random() * WIDTH_PANEL);
                int x2 = (int) (Math.random() * WIDTH_PANEL);
                int y1 = (int) (Math.random() * HEIGHT_PANEL);
                int y2 = (int) (Math.random() * HEIGHT_PANEL);
                Color randomColor = new Color((float) Math.random(), (float) Math.random(), (float) Math.random());
                plane.add(new Line(x1, y1, x2, y2, randomColor));
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                plane.clear();
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
