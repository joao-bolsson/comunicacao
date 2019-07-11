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
        for (Frame frame : frames) {
            System.out.println("===============================");
            frame.simulateSending(i++ == frameWithError);

        }
    }

}
