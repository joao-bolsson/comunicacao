package model;

import java.awt.Color;
import java.awt.Graphics;

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
    public void drawBits(final Graphics g, final Line x, final Line y, final int yMax, final int yMin,
            final int blockSize) {
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

            Line line = new Line(x1, y1, x2, y1);
            addLine(line);
            drawLine(g, line);
            i++;
        }
    }

}
