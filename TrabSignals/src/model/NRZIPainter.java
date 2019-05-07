package model;

import java.awt.Color;
import java.awt.Graphics;


//boolean flag;
/**
 *
 * @author João Bolsson (jvmarques@inf.ufsm.br)
 * @version 2019, May 02.
 */
public class NRZIPainter extends Painter {
    
   public NRZIPainter() {
        color = Color.BLUE;
        isToDraw = true;
    }

    @Override
    public void draw(final Graphics g, final Line x, final Line y, final int yMax, final int yMin) {
        if (!isToDraw) {
            return;
        }
        if (text == null || text.isEmpty()) {
            return;
        }

        horizontalLines.clear();

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
        
        boolean flag = false;
        int i = 0;
        for (String s : split) {
            int bit = Integer.parseInt(s);

            int x1 = x.getX1() + blockSize * i + y.getX1();
            int x2 = x1 + blockSize;

            int y1 = 0;
            if (bit == 0) {
                // desenha no zero
                if(flag == false){
                    y1 = yMax;
                }
                else if(flag == true){
                    y1 = yMin;
                }
                
            } else if (bit == 1) {
                // desenha no 5
                if(flag == false){
                    y1 = yMin;
                    flag = true;
                }
                else if (flag == true){
                    y1 = yMax;
                    flag = false;
                }
            }

            addLine(new Line(x1, y1, x2, y1, color));

            g.drawLine(x1, y1, x2, y1);
            i++;
        }

        drawVerticalLines(g);

        g.setColor(oldColor);
    }

}
