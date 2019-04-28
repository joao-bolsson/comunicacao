package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import model.Line;

/**
 *
 * @author João Bolsson (jvmarques@inf.ufsm.br)
 * @version 2019, Apr 27.
 */
public class LinesComponent extends JComponent {

    private static final short WIDTH_PANEL = 320, HEIGHT_PANEL = 200;

    private final List<Line> lines = new ArrayList<>();

    public void addLine(final int x1, final int x2, final int x3, final int x4, final Color color) {
        lines.add(new Line(x1, x2, x3, x4, color));
        repaint();
    }

    public void clearLines() {
        lines.clear();
        repaint();
    }

    @Override
    protected void paintComponent(final Graphics g) {
        super.paintComponent(g);
        for (Line line : lines) {
            g.setColor(line.getColor());
            g.drawLine(line.getX1(), line.getY1(), line.getX2(), line.getY2());
        }
    }

    /**
     * Main function.
     *
     * @param args Command line arguments (ignore).
     */
    public static void main(final String[] args) {
        JFrame testFrame = new JFrame();
        testFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        final LinesComponent comp = new LinesComponent();
        comp.setPreferredSize(new Dimension(WIDTH_PANEL, HEIGHT_PANEL));
        testFrame.getContentPane().add(comp, BorderLayout.CENTER);
        JPanel buttonsPanel = new JPanel();
        JButton newLineButton = new JButton("New Line");
        JButton clearButton = new JButton("Clear");
        buttonsPanel.add(newLineButton);
        buttonsPanel.add(clearButton);
        testFrame.getContentPane().add(buttonsPanel, BorderLayout.SOUTH);
        newLineButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent e) {
                int x1 = (int) (Math.random() * WIDTH_PANEL);
                int x2 = (int) (Math.random() * WIDTH_PANEL);
                int y1 = (int) (Math.random() * HEIGHT_PANEL);
                int y2 = (int) (Math.random() * HEIGHT_PANEL);
                Color randomColor = new Color((float) Math.random(), (float) Math.random(), (float) Math.random());
                comp.addLine(x1, y1, x2, y2, randomColor);
            }
        });
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                comp.clearLines();
            }
        });
        testFrame.pack();
        testFrame.setVisible(true);
    }

}
