package control;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.JPanel;
import model.AMIPainter;
import model.Line;
import model.ManchDifPainter;
import model.ManchPainter;
import model.NRZIPainter;
import model.NRZLPainter;
import model.Painter;
import model.PseudoTernPainter;

/**
 *
 * @author João Bolsson (jvmarques@inf.ufsm.br)
 * @version 2019, Apr 27.
 */
public class Plane extends JPanel {

    private static final byte FONT_INSET = 10;

    private final int yStart, yMiddle, yEnd;

    private final List<Line> lines = new ArrayList<>();

    private final Painter nrzlPainter = new NRZLPainter();
    private final Painter nrziPainter = new NRZIPainter();
    private final Painter amiPainter = new AMIPainter();
    private final Painter pseudoPainter = new PseudoTernPainter();
    private final Painter manchPainter = new ManchPainter();
    private final Painter manchDifPainter = new ManchDifPainter();

    private final List<Painter> painters = Arrays.asList(nrzlPainter, nrziPainter, amiPainter, pseudoPainter,
            manchPainter, manchDifPainter);

    private String text;

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
        drawBlocksSeparator(g);

        for (Painter painter : painters) {
            painter.draw(g, getAxisX(), getAxisY(), getYMax(), getYMin());
        }
    }

    private void resetPainters() {
        for (Painter p : painters) {
            p.setIsToDraw(false);
        }
    }

    /**
     * Draws the NRZ-L representation.
     */
    public void drawNRZL() {
        resetPainters();
        nrzlPainter.setIsToDraw(true);
        repaint();
    }

    /**
     * Draws the NRZ-I representation.
     */
    public void drawNRZI() {
        resetPainters();
        nrziPainter.setIsToDraw(true);
        repaint();
    }

    /**
     * Draws the AMI representation.
     */
    public void drawAMI() {
        resetPainters();
        amiPainter.setIsToDraw(true);
        repaint();
    }

    /**
     * Draws the Pseudoternary representation.
     */
    public void drawPseudo() {
        resetPainters();
        pseudoPainter.setIsToDraw(true);
        repaint();
    }

    /**
     * Draws the Manchester representation.
     */
    public void drawManch() {
        resetPainters();
        manchPainter.setIsToDraw(true);
        repaint();
    }

    /**
     * Draws the Differential Manchester representation.
     */
    public void drawManchDif() {
        resetPainters();
        manchDifPainter.setIsToDraw(true);
        repaint();
    }

    /**
     * Draw the text.
     *
     * @param text Text to draw.
     */
    public void draw(final String text) {
        this.text = text;
        for (Painter painter : painters) {
            painter.setText(text);
        }
    }

    /**
     * @return The line that represents the X axis.
     */
    private Line getAxisX() {
        return new Line(0, (int) (getHeight() * 0.5), getWidth(), (int) (getHeight() * 0.5));
    }

    /**
     * @return The line that represents the Y axis.
     */
    private Line getAxisY() {
        // if we draw the y axis starting at point (0, 0) we can't see it. So, we push a litle bit to the right (40).
        return new Line(40, 0, 40, getHeight());
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

    private void drawBlocksSeparator(final Graphics g) {
        if (text != null && !text.isEmpty()) {
            Color oldColor = g.getColor();
            g.setColor(Color.BLACK);

            Line x = getAxisX();
            Line y = getAxisY();

            /**
             * calculates the size of X axis (only the useful size) is because that the '- y.getX1()' to recompense the
             * unuseful size.
             */
            int lengthX = x.length() - y.getX1();

            /**
             * Estimates the size of each "line block" to draw a signal
             */
            int blockSize = lengthX / text.length();

            // x1, y1, x2, y2
            int[] coord = new int[4];

            String[] split = text.split("");

            int i = 0;
            for (String bit : split) {
                coord[0] = x.getX1() + blockSize * i + y.getX1();
                coord[2] = coord[0] + blockSize;

                coord[1] = y.getY1();
                coord[3] = y.getY2();

                g.drawLine(coord[2], coord[1], coord[2], coord[3]);

                g.drawString(bit, (coord[0] + coord[2]) / 2, coord[3]);
                i++;
            }

            g.setColor(oldColor);
        }
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
