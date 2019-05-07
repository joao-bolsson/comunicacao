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
    public void drawBits(final Graphics g, final Line x, final Line y, final int yMax, final int yMin,
            final int blockSize) {
        String[] split = text.split("");

        int i = 0;
        for (String s : split) {
            int bit = Integer.parseInt(s);

            int x1 = x.getX1() + blockSize * i + y.getX1();
            int x2 = x1 + blockSize / 2;

            int yA = (bit == 0) ? yMax : yMin; // y of the first line
            int yB = (bit == 0) ? yMin : yMax; // y of the second line

            Line a = new Line(x1, yA, x2, yA);
            Line b = new Line(x2, yB, x2 + blockSize / 2, yB);

            addLine(a);
            addLine(b);

            g.drawLine(a.getX1(), a.getY1(), a.getX2(), a.getY2());
            g.drawLine(b.getX1(), b.getY1(), b.getX2(), b.getY2());

            i++;
        }
    }

}
