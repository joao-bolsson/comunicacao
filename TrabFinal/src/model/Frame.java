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
            builder.append('|');
        }

        return builder.toString().toCharArray();
    }

    /**
     * Gets the original message.
     *
     * @param str The received message in char values, like 49|50, will be transformed in -> "12"
     * @return
     */
    public static String charToString(final String str) {
        StringBuilder builder = new StringBuilder();

        String[] split = str.split("|");

        for (String s : split) {
            System.out.println("s: " + s);
        }

        return builder.toString();
    }

    // TODO: temporario, somente para testes
    public static void main(final String[] args) {
        InputStreamReader input = new InputStreamReader(System.in);
        BufferedReader buf = new BufferedReader(input);

        System.out.println("dado: ");
        try {
            String line = buf.readLine();

            Frame frame = new Frame(line);

            System.out.println("frame checksum: " + frame.checksum());
            System.out.println("frame bytes to send: " + Arrays.toString(frame.getData()));
            System.out.println("data to send: " + Arrays.toString(Frame.toCharArray(frame.getData())));

            System.out.println("chars received: " + Frame.charToString("49|50"));

        } catch (IOException ex) {
            Logger.getLogger(Frame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
