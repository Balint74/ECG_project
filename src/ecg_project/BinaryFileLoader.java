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
                    
    public short[][] load(File file) {

        freq = 0;
        
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

        } catch (FileNotFoundException ex) {
            Logger.getLogger(BinaryFileLoader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(BinaryFileLoader.class.getName()).log(Level.SEVERE, null, ex);
        }

        short[][] Array = new short[64][8000000];
        int csatorna = 0;
        int ertek = 0;
        int counter = 0;
        numOfLines = 0;

        System.out.println("freq: " + freq);

        for (int i = 0; i < shortArray.length - 1; i++) {

            Array[csatorna][ertek + counter] = shortArray[i];

            

            if (ertek == freq - 1) {
                csatorna++;
                ertek = 0;
            }else
                ertek++;
            
            
            
            if (csatorna == 64) { // blokk valtas
                csatorna = 0;
                ertek = 0;
                counter = counter + freq ;
                numOfLines++;
            }
            
        }
        
        numOfLines = numOfLines * freq;
        return Array;

    }

    public int getNumOfLines() {
        return numOfLines;
    }

    public int getFreq() {
        return freq;
    }

    
    

}
