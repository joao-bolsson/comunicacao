/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Arrays;
import java.util.List;

/**
 *
 * @author joao
 */
public class Package {

    private final Frame[] frames;

    private final boolean[] results;

    public Package(final Frame[] frames) {
        this.frames = frames;
        results = new boolean[frames.length];

        for (int i = 0; i < results.length; i++) {
            results[i] = false;
        }
    }

    public void simulateSending(final List<Byte> withErrors) {
        System.out.println("frames: " + Arrays.toString(frames));

        for (byte i = 0; i < frames.length; i++) {
            System.out.println("===============================");
            Frame frame = frames[i];
            frame.simulateSending(withErrors.contains(i));

            results[i] = frame.wasSend();
        }

        System.out.println("------enviando novamente -----");
        for (int i = 0; i < results.length; i++) {
            if (!results[i]) {
                Frame frame = frames[i];
                // vai enviar certo na segunda tentativa
                frame.simulateSending(false);

                results[i] = frame.wasSend();
            }
        }

    }

}
