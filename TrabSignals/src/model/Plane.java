package model;

import control.SimplePainter;
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

    private static final byte FONT_INSET = 10;

    private final int yStart, yMiddle, yEnd;

    private final List<Line> lines = new ArrayList<>();

    private final SimplePainter simplePainter = new SimplePainter();

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
        drawMarks(g);
        simplePainter.draw(g, getAxisX(), getAxisY(), getYMax(), getYMin());
    }

    /**
     * Draw with simple painter.
     *
     * @param text Text to be drawn.
     */
    public void drawSimple(final String text) {
        simplePainter.setText(text);
    }

    /**
     * @return The line that represents the X axis.
     */
    private Line getAxisX() {
        return new Line(0, (int) (getHeight() * 0.5), getWidth(), (int) (getHeight() * 0.5), Color.BLACK);
    }

    /**
     * @return The line that represents the Y axis.
     */
    private Line getAxisY() {
        // if we draw the y axis starting at point (0, 0) we can't see it. So, we push a litle bit to the right (40).
        return new Line(40, 0, 40, getHeight(), Color.BLACK);
    }

    private void drawPlane(final Graphics g) {
        Color oldColor = g.getColor();
        g.setColor(Color.BLACK);

        Line lineX = getAxisX();
        Line lineY = getAxisY();

        List<Line> axis = Arrays.asList(lineX, lineY);

        for (Line line : axis) {
            g.drawLine(line.getX1(), line.getY1(), line.getX2(), line.getY2());
        }

        g.setColor(oldColor);
    }

    private void drawMarks(final Graphics g) {
        Color oldColor = g.getColor();
        g.setColor(Color.BLACK);

        Line axisX = getAxisX();
        Line axisY = getAxisY();

        final int x = axisY.getX1() - FONT_INSET;

        g.drawString(Integer.toString(yEnd), x, getYMax());
        g.drawString(Integer.toString(yMiddle), x, axisX.getY1());
        g.drawString(Integer.toString(yStart), x, getYMin());

        g.setColor(oldColor);
    }

    private int getYMax() {
        Line axisX = getAxisX();
        Line axisY = getAxisY();

        return (int) (axisX.getY1() - axisY.getY1()) / 2;
    }

    private int getYMin() {
        Line axisX = getAxisX();
        Line axisY = getAxisY();

        return (int) (axisY.getY2() + axisX.getY1()) / 2;
    }

}
