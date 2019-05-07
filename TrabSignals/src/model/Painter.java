package model;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author João Bolsson (jvmarques@inf.ufsm.br)
 * @version 2019, May 02.
 */
public abstract class Painter {

    /**
     * Text to draw.
     */
    protected String text;

    /**
     * Color to draw.
     */
    protected Color color = Color.BLACK;

    private boolean isToDraw = false;

    /**
     * All the horizontal lines drawn by the painter.
     */
    private final List<Line> horizontalLines = new ArrayList<>();

    private final Stroke stroke = new BasicStroke(3);

    /**
     * Default construct.
     *
     * Draw bits with 1 = 5V and 0 = 0.
     */
    public Painter() {
        // empty
    }

    /**
     * Sets if the painter is to draw.
     *
     * @param isToDraw Flag to set.
     */
    public void setIsToDraw(final boolean isToDraw) {
        this.isToDraw = isToDraw;
    }

    /**
     * @return True if the painter was 'checked' to draw.
     */
    public boolean isIsToDraw() {
        return isToDraw;
    }

    /**
     * Sets the text to be drawn.
     *
     * @param text Text with bits to be drawn.
     */
    public void setText(final String text) {
        this.text = text;
    }

    /**
     * Adds a horizontal line.
     *
     * @param line Horizontal line to be added.
     */
    protected void addLine(final Line line) {
        // checks if the line was already added and checks if the line if really horizontal
        if (!horizontalLines.contains(line) && line.getY1() == line.getY2()) {
            horizontalLines.add(line);
        }
    }

    /**
     * @return True if the painter is allowed to draw.
     */
    public boolean canDraw() {
        return isToDraw && text != null && !text.isEmpty();
    }

    /**
     * Draw the vertical lines.
     *
     * @param g Referenced graphic.
     */
    private void drawVerticalLines(final Graphics g) {
        if (horizontalLines.size() < 2) {
            return;
        }

        for (int i = 0; i < horizontalLines.size() - 1; i++) {
            Line line = horizontalLines.get(i);
            Line next = horizontalLines.get(i + 1);

            Line vert = new Line(line.getX2(), line.getY2(), next.getX1(), next.getY1());

            if (vert.getY1() != vert.getY2()) {
                drawLine(g, vert);
            }
        }
    }

    /**
     * Draw the bits.
     *
     * @param g Referenced graphics.
     * @param x X axis.
     * @param y Y axis.
     * @param yMax Max y.
     * @param yMin Min y.
     */
    public final void draw(final Graphics g, final Line x, final Line y, final int yMax, final int yMin) {
        if (!canDraw()) {
            return;
        }

        horizontalLines.clear();

        /**
         * calculates the size of X axis (only the useful size) is because that the '- y.getX1()' to recompense the
         * unuseful size.
         */
        int lengthX = x.length() - y.getX1();

        /**
         * Estimates the size of each "line block" to draw a signal
         */
        int blockSize = lengthX / text.length();

        Color oldColor = g.getColor();
        g.setColor(color);

        drawBits(g, x, y, yMax, yMin, blockSize);

        drawVerticalLines(g);

        g.setColor(oldColor);
    }

    /**
     * Draws a line.
     *
     * @param g Graphics to draw the line.
     * @param line Line to draw.
     */
    protected void drawLine(final Graphics g, final Line line) {
        Graphics2D g2 = (Graphics2D) g;
        Stroke oldStroke = g2.getStroke();
        Color oldColor = g2.getColor();

        g2.setColor(color);
        g2.setStroke(stroke);

        g2.drawLine(line.getX1(), line.getY1(), line.getX2(), line.getY2());

        g2.setStroke(oldStroke);
        g2.setColor(oldColor);
    }

    /**
     * Only draw the bits from text.
     *
     * @param g Referenced graphics.
     * @param x X axis.
     * @param y Y axis.
     * @param yMax Max y.
     * @param yMin Min y.
     * @param blockSize Block size where each bit will stay.
     */
    public abstract void drawBits(Graphics g, Line x, Line y, int yMax, int yMin, int blockSize);

}
