package model;

/**
 *
 * @author João Bolsson (jvmarques@inf.ufsm.br)
 * @version 2019, Apr 27.
 */
public class Line {

    private final Point start, end;

    /**
     * Creates a line with start point P(x1, y1) and end point P(x2, y2).
     *
     * @param x1 x1.
     * @param y1 y1.
     * @param x2 x2.
     * @param y2 y2.
     */
    public Line(final int x1, final int y1, final int x2, final int y2) {
        this.start = new Point(x1, y1);
        this.end = new Point(x2, y2);
    }

    /**
     * @return The x1.
     */
    public int getX1() {
        return start.getX();
    }

    /**
     * @return The y1.
     */
    public int getY1() {
        return start.getY();
    }

    /**
     * @return The x2.
     */
    public int getX2() {
        return end.getX();
    }

    /**
     * @return The y2.
     */
    public int getY2() {
        return end.getY();
    }

    /**
     * @return The line length.
     */
    public int length() {
        return (int) Math.hypot(start.getX() - end.getX(), start.getY() - end.getY());
    }

}
