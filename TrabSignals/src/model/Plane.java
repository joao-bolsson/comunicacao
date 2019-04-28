package model;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComponent;

/**
 *
 * @author João Bolsson (jvmarques@inf.ufsm.br)
 * @version 2019, Apr 27.
 */
public class Plane extends JComponent {

    private final List<Line> lines = new ArrayList<>();

    /**
     * Default construct.
     */
    public Plane() {
        // empty
    }

    /**
     * Adds to the plane a given line.
     *
     * @param line Line to add.
     */
    public void add(final Line line) {
        lines.add(line);
        repaint();
    }

    /**
     * Clear the plane.
     */
    public void clear() {
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

}
