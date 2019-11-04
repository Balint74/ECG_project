/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ecg_project;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ShortBuffer;
import java.nio.channels.FileChannel;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author feherbalint
 */
public class BinaryFileLoader {

    private int numOfLines;
    private int freq;
    private short[][] array;

    public short[][] load(File file) {

        freq = 0;
        int numOfChanel = 64;
        short[] shortArray = null;

        try {

            FileInputStream input = new FileInputStream(file);
            FileChannel fc = input.getChannel();

            ByteBuffer buf = ByteBuffer.allocate((int) fc.size());
            buf.order(ByteOrder.BIG_ENDIAN);

            fc.read(buf);
            buf.flip();

            Buffer buffer = buf.asShortBuffer();
            shortArray = new short[(int) fc.size() / 2];
            ((ShortBuffer) buffer).get(shortArray);

            freq = shortArray[shortArray.length - 1];

            input.close();
            fc.close();

        } catch (FileNotFoundException ex) {
            Logger.getLogger(BinaryFileLoader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(BinaryFileLoader.class.getName()).log(Level.SEVERE, null, ex);
        }

        //Array = new short[64][8000000];
        array = new short[numOfChanel][(shortArray.length - 2) / 64];
        int chanel = 0;
        int value = 0;
        int counter = 0;
        numOfLines = 0;
        double gain = 0.5;

        for (int i = 0; i < shortArray.length - 2; i++) {

            array[chanel][value + counter] = (short) (gain * shortArray[i]);

            if (value == freq - 1) {
                chanel++;
                value = 0;
            } else {
                value++;
            }

            if (chanel == 64) { // blokk valtas
                chanel = 0;
                value = 0;
                counter = counter + freq;
                numOfLines++;
            }

        }

        numOfLines = numOfLines * freq;
        return array;

    }

    public int getNumOfLines() {
        return numOfLines;
    }

    public int getFreq() {
        return freq;
    }

    public short[][] getArray() {
        return array;
    }

}
