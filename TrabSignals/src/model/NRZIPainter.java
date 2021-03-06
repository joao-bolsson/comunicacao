package model;

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author Gabriel Righi (gfrighi@inf.ufsm.br)
 * @version 2019, May 06.
 */
public class NRZIPainter extends Painter {

    /**
     * Default construct.
     */
    public NRZIPainter() {
        color = Color.BLUE;
    }

    @Override
    public void drawBits(final Graphics g, final Line x, final Line y, final int yMax, final int yMin,
            final int blockSize) {
        String[] split = text.split("");

        boolean flag = false;
        int i = 0;
        for (String s : split) {
            int bit = Integer.parseInt(s);

            int x1 = x.getX1() + blockSize * i + y.getX1();
            int x2 = x1 + blockSize;

            int y1 = 0;
            if (bit == 0) {
                // desenha no zero
                if (flag == false) {
                    y1 = yMax;
                } else if (flag == true) {
                    y1 = yMin;
                }

            } else if (bit == 1) {
                // desenha no 5
                if (flag == false) {
                    y1 = yMin;
                    flag = true;
                } else if (flag == true) {
                    y1 = yMax;
                    flag = false;
                }
            }

            Line line = new Line(x1, y1, x2, y1);
            addLine(line);
            drawLine(g, line);
            i++;
        }
    }

}
