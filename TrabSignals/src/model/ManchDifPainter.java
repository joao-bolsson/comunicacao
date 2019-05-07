package model;

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author João Bolsson (jvmarques@inf.ufsm.br)
 * @version 2019, May 02.
 */
public class ManchDifPainter extends Painter {

    /**
     * Default construct.
     */
    public ManchDifPainter() {
        color = Color.PINK;
    }

    @Override
    public void drawBits(final Graphics g, final Line x, final Line y, final int yMax, final int yMin,
            final int blockSize) {
        // TODO
    }

}
