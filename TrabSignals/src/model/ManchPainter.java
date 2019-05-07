package model;

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author João Bolsson (jvmarques@inf.ufsm.br)
 * @version 2019, May 02.
 */
public class ManchPainter extends Painter {

    /**
     * Default construct.
     */
    public ManchPainter() {
        color = Color.CYAN;
    }

    @Override
    public void draw(final Graphics g, final Line x, final Line y, final int yMax, final int yMin) {
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
        String[] split = text.split("");

        int i = 0;
        for (String s : split) {
            int bit = Integer.parseInt(s);

            int x1 = x.getX1() + blockSize * i + y.getX1();
            int x2 = x1 + blockSize / 2;

            Line a = null, b = null;

            if (bit == 0) {
                // first line on 5
                a = new Line(x1, yMax, x2, yMax, color);
                // second line on -5
                b = new Line(x2, yMin, x2 + blockSize / 2, yMin, color);
            } else if (bit == 1) {
                // first line on -5
                a = new Line(x1, yMin, x2, yMin, color);
                // second line on 5
                b = new Line(x2, yMax, x2 + blockSize / 2, yMax, color);
            }

            if (a != null && b != null) {
                addLine(a);
                addLine(b);

                g.drawLine(a.getX1(), a.getY1(), a.getX2(), a.getY2());
                g.drawLine(b.getX1(), b.getY1(), b.getX2(), b.getY2());
            }

            i++;
        }

        drawVerticalLines(g);

        g.setColor(oldColor);
    }

}
