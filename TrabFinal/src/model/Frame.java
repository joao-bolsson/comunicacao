package model;

import java.util.Objects;

/**
 *
 * @author João Bolsson (jvmarques@inf.ufsm.br)
 * @version 2019, Jul 10.
 */
public class Frame {

    public static final String SEPARATOR = " ";

    /**
     * Stores all the bytes to send, and the checksum.
     */
    private final byte[] data;

    private final String msg;

    private final boolean withError;

    /**
     * Creates a frame with data to send.
     *
     * @param data Data to send
     */
    public Frame(final String data) {
        this(data, false);
    }

    public Frame(final String data, boolean withError) {
        this.msg = data;
        this.data = new byte[data.length()];

        this.withError = withError;
        init(data);
    }

    public String getMsg() {
        return msg;
    }

    @Override
    public String toString() {
        return "[" + msg + "]";
    }

    private void init(final String data) {
        byte[] bytes = data.getBytes();

        System.arraycopy(bytes, 0, this.data, 0, this.data.length);
    }

    /**
     * The checksum of this frame.
     *
     * @return The checksum.
     */
    public byte checksum() {
        return checksum(withError);
    }

    public byte checksum(boolean withError) {
        short sum = 0;
        for (byte b : data) {
            sum -= b;
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

    /**
     * Gets the char representation of the given bytes.
     *
     * @param data The bytes to gets the char.
     * @return An array of bytes.
     */
    public static char[] toCharArray(final byte[] data) {
        StringBuilder builder = new StringBuilder();

        for (byte b : data) {
            builder.append(b);
            builder.append(SEPARATOR);
        }

        String toString = builder.toString();
        int lastIndexOf = toString.lastIndexOf(SEPARATOR);

        return toString.substring(0, lastIndexOf).toCharArray();
    }

    /**
     *
     * @param str String like "49 50 -99", checksum will be: 49 + 50 - 99 = 0.
     * @return The checksum according with each value in the string.
     */
    public static byte checksumFromString(final String str) {
        byte sum = 0;
        String[] split = str.split(SEPARATOR);

        for (String s : split) {
            sum += Integer.parseInt(s);
        }

        return sum;
    }

    /**
     * Decode message with checksum
     *
     * @param str String to decode.
     * @return
     */
    public static String decodeMessage(final String str) {
        String[] split = str.split(SEPARATOR);

        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < split.length - 1; i++) {
            int parseInt = Integer.parseInt(split[i]);
            char a = (char) parseInt;

            builder.append(a).append(SEPARATOR);
        }
        String toString = builder.toString();
        int lastIndexOf = toString.lastIndexOf(SEPARATOR);

        return toString.substring(0, lastIndexOf);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.msg);
        return hash;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Frame)) {
            return false;
        }

        final Frame other = (Frame) obj;
        return Objects.equals(this.msg, other.msg);
    }

}
