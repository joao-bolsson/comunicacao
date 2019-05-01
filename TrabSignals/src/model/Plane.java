package model;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.JPanel;

/**
 *
 * @author João Bolsson (jvmarques@inf.ufsm.br)
 * @version 2019, Apr 27.
 */
public class Plane extends JPanel {

    private final int yStart, yMiddle, yEnd;

    private final List<Line> lines = new ArrayList<>();

    /**
     * Default construct.
     *
     * Creates a Cartesian plane.<code>
     *      |-yEnd
     *      |
     *      |
     *      |
     * _____|-yMiddle__________________
     *      |
     *      |
     *      |
     *      |-yStart
     * </code>
     *
     * @param yStart
     * @param yMiddle
     * @param yEnd
     */
    public Plane(int yStart, int yMiddle, int yEnd) {
        this.yStart = yStart;
        this.yMiddle = yMiddle;
        this.yEnd = yEnd;
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

        drawPlane(g);
    }

    private void drawPlane(final Graphics g) {
        Color oldColor = g.getColor();
        g.setColor(Color.BLACK);

        Line lineX = new Line(0, (int) (getHeight() * 0.5), getWidth(), (int) (getHeight() * 0.5), Color.BLACK);
        Line lineY = new Line(40, 0, 40, getHeight(), Color.BLACK);

        List<Line> axis = Arrays.asList(lineX, lineY);

        for (Line line : axis) {
            g.drawLine(line.getX1(), line.getY1(), line.getX2(), line.getY2());
        }

        g.setColor(oldColor);
    }

}
