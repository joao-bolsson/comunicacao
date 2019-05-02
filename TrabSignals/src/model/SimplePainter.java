package model;

import java.awt.Color;
import java.awt.Graphics;
import model.Line;
import model.Painter;

/**
 *
 * @author João Bolsson (jvmarques@inf.ufsm.br)
 * @version 2019, May 02.
 */
public class SimplePainter extends Painter {

    /**
     * Creates a simple painter that draw the bits O and 1 with rule: 1 - 5V and 0 - 0V, by example.
     */
    public SimplePainter() {
        color = Color.RED;
    }

    @Override
    public void draw(final Graphics g, final Line x, final Line y, final int yMax, final int yMin) {
        if (text == null || text.isEmpty()) {
            return;
        }

        /**
         * calculates the size of X axis (only the useful size) is because that the '- y.getX1()' to recompense the
         * unuseful size
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
            int x2 = x1 + blockSize;

            int y1 = 0;
            if (bit == 0) {
                // desenha no zero

                y1 = x.getY1();
            } else if (bit == 1) {
                // desenha no 5
                y1 = yMax;
            }

            addLine(new Line(x1, y1, x2, y1, color));

            g.drawLine(x1, y1, x2, y1);
            i++;
        }

        g.setColor(oldColor);
    }

}
