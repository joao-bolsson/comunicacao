package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    /**
     * Creates a frame with data to send.
     *
     * @param data Data to send
     */
    public Frame(final String data) {
        this.msg = data;
        this.data = new byte[data.length()];

        init(data);
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
        return checksum(false);
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

    // TODO: temporario, somente para testes
    public static void main(final String[] args) {
        InputStreamReader input = new InputStreamReader(System.in);
        BufferedReader buf = new BufferedReader(input);

        System.out.println("dado: ");
        try {
            String line = buf.readLine();

            Frame frame = new Frame(line);

            frame.simulateSending(false);
        } catch (IOException ex) {
            Logger.getLogger(Frame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void simulateSending(boolean withError) {
        System.out.println("SIMULATION FOR frame: " + msg);
        System.out.println("frame checksum: " + checksum(withError));
        System.out.println("frame to send: " + Arrays.toString(getData()));
        char[] dt = Frame.toCharArray(getData());
        String msgReceived = String.valueOf(dt);

        if (withError) {
            msgReceived += "11";
        }

        System.out.println("msg received: " + msgReceived);
        byte received = Frame.checksumFromString(msgReceived);
        System.out.println("checksum received: " + received);

        System.out.println("Send ok: " + (received == 0));
    }

}
