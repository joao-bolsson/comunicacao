package model;

import java.util.BitSet;

/**
 *
 * @author João Bolsson (jvmarques@inf.ufsm.br)
 * @version 2019, Jul 10.
 */
public class Frame {

    private final BitSet bits;

    /**
     * Creates a frame with specified size.
     *
     * @param size Frames size.
     * @param data Data to put on this frame. The data must be a binary string, like 011001, for example.
     */
    public Frame(byte size, final String data) {
        if (size <= data.length()) {
            throw new IllegalArgumentException("The data is too long for this frame");
        }
        bits = new BitSet(size);

        init(data);
    }

    private void init(final String data) {
        String[] split = data.split("");

        int i = 0;
        for (String s : split) {
            bits.set(i, s.equals("1"));
        }
    }

}
