/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author joao
 */
public class Demo {

    private static final byte POS_ERROR = 3;

    public static void main(String[] args) {
        Frame frame1 = new Frame("12");
        Frame frame2 = new Frame("34");
        Frame frame3 = new Frame("56");
        Frame frame4 = new Frame("78");
        Frame frame5 = new Frame("91");
        Frame frame6 = new Frame("87");

        Package pack = new Package(new Frame[]{frame1, frame2, frame3, frame4, frame5, frame6});
        pack.simulateSending(POS_ERROR);
    }

}
