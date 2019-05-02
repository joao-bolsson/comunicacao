package model;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author João Bolsson (jvmarques@inf.ufsm.br)
 * @version 2019, May 02.
 */
public abstract class Painter {

    protected String text;

    protected Color color = Color.BLACK;

    /**
     * All the horizontal lines drawn by the painter.
     */
    protected final List<Line> horizontalLines = new ArrayList<>();

    /**
     * Default construct.
     *
     * Draw bits with 1 = 5V and 0 = 0.
     */
    public Painter() {
        // empty
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

    protected void drawVerticalLines(final Graphics g) {
        // TODO
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
    public abstract void draw(Graphics g, Line x, Line y, int yMax, int yMin);

}
