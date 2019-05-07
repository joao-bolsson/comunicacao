package model;

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author João Bolsson (jvmarques@inf.ufsm.br)
 * @version 2019, May 02.
 */
public class ManchDifPainter extends Painter {

    private Point lastPoint;

    /**
     * Default construct.
     */
    public ManchDifPainter() {
        color = Color.PINK;
    }

    @Override
    public void drawBits(final Graphics g, final Line x, final Line y, final int yMax, final int yMin,
            final int blockSize) {
        String[] split = text.split("");

        lastPoint = new Point(x.getX1() + y.getX1(), yMax);

        int i = 0;
        for (String s : split) {
            int bit = Integer.parseInt(s);

            int x1 = x.getX1() + blockSize * i + y.getX1();
            int x2 = x1 + blockSize / 2;

            int yA = lastPoint.getY(); // y of the first line

            if (bit == 0) {
                yA = (lastPoint.getY() == yMax) ? yMin : yMax;

                drawLine(g, new Line(lastPoint.getX(), lastPoint.getY(), lastPoint.getX(), yA));
            }

            int yB = (yA == yMax) ? yMin : yMax; // transition

            Line a = new Line(x1, yA, x2, yA);
            Line b = new Line(x2, yB, x2 + blockSize / 2, yB);

            lastPoint = new Point(b.getX2(), b.getY1());

            addLine(a);
            addLine(b);

            drawLine(g, a);
            drawLine(g, b);

            i++;
        }
    }

}
