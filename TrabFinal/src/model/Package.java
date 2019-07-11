/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Arrays;

/**
 *
 * @author joao
 */
public class Package {

    private final Frame[] frames;

    public Package(final Frame[] frames) {
        this.frames = frames;
    }

    public void simulateSending(byte frameWithError) {
        byte i = 0;

        System.out.println("frames: " + Arrays.toString(frames));

        while (i < frames.length) {
            System.out.println("===============================");
            Frame frame = frames[i];
            frame.simulateSending(i == frameWithError);

            // se não teve erro no envio avança, se não tenta novamente.
            if (frame.wasSend()) {
                i++;
            } else {
                frameWithError = -1; // vai enviar certo na segunda tentativa
            }
        }
    }

}
