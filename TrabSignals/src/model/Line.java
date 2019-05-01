package model;

import java.awt.Color;

/**
 *
 * @author João Bolsson (jvmarques@inf.ufsm.br)
 * @version 2019, Apr 27.
 */
public class Line {

    private final Point start, end;
    private final Color color;

    public Line(final Point start, final Point end, Color color) {
        this.start = start;
        this.end = end;
        this.color = color;
    }

    public Line(final int x1, final int y1, final int x2, final int y2, final Color color) {
        this(new Point(x1, y1), new Point(x2, y2), color);
    }

    public int getX1() {
        return start.getX();
    }

    public int getY1() {
        return start.getY();
    }

    public int getX2() {
        return end.getX();
    }

    public int getY2() {
        return end.getY();
    }

    public Point getStart() {
        return start;
    }

    public Point getEnd() {
        return end;
    }

    public Color getColor() {
        return color;
    }

}
