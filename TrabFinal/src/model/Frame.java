package model;

import java.util.Arrays;

/**
 *
 * @author João Bolsson (jvmarques@inf.ufsm.br)
 * @version 2019, Jul 10.
 */
public class Frame {

    /**
     * Stores all the bytes to send, and the checksum.
     */
    private final byte[] data;

    /**
     * Creates a frame with data to send.
     *
     * @param data Data to send
     */
    public Frame(final String data) {
        this.data = new byte[data.length()];

        init(data);
    }

    private void init(final String data) {
        byte[] bytes = data.getBytes();

        System.arraycopy(bytes, 0, this.data, 0, this.data.length);
        System.out.println("bytes to send: " + Arrays.toString(this.data));
    }

    /**
     * The checksum of this frame.
     *
     * @return The checksum.
     */
    public byte checksum() {
        short sum = 0;
        for (byte b : data) {
            sum += b;
        }

        return (byte) sum;
    }

    /**
     * The data of this frame with checksum
     *
     * @return The data with checksum on the last position.
     */
    public byte[] getData() {
        byte[] pack = new byte[data.length + 1];
        System.arraycopy(data, 0, pack, 0, data.length);

        pack[data.length] = checksum();

        return pack;
    }

}
