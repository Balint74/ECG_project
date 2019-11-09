/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ecg_project;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.stage.FileChooser;


/**
 *
 * @author feherbalint
 */
public class Filter {

    public double[] load(short[][] array, int x, int numOfLines) {
        double[] aValues = {1.0, 3.991983031580645, 5.975981215005254, -3.976013259934955, 0.992015076598585};;
        double[] bValues = {0.995999536444965, -3.983998145779859, 5.975997218669789, -3.983998145779859, 0.995999536444965};
        double[] filteredArray = new double[numOfLines];

        for (int n = 0; n < numOfLines; n++) {
            switch (n) {
                case 0:
                    filteredArray[n] = (bValues[0] * array[x][n]);
                    break;
                case 1:
                    filteredArray[n] = ((bValues[0] * array[x][n]) + (bValues[1] * array[x][n - 1]) + (aValues[1] * filteredArray[n - 1]));
                    break;
                case 2:
                    filteredArray[n] = (bValues[0] * array[x][n] + bValues[1] * array[x][n - 1] + bValues[2] * array[x][n - 2] + aValues[1] * filteredArray[n - 1] - aValues[2] * filteredArray[n - 2]);
                    break;
                case 3:
                    filteredArray[n] = (bValues[0] * array[x][n] + bValues[1] * array[x][n - 1] + bValues[2] * array[x][n - 2] + bValues[3] * array[x][n - 3] + aValues[1] * filteredArray[n - 1] - aValues[2] * filteredArray[n - 2] - aValues[3] * filteredArray[n - 3]);
                    break;
                default:
                    filteredArray[n] = (bValues[0] * array[x][n] + bValues[1] * array[x][n - 1] + bValues[2] * array[x][n - 2] + bValues[3] * array[x][n - 3] + bValues[4] * array[x][n - 4] + aValues[1] * filteredArray[n - 1] - aValues[2] * filteredArray[n - 2] - aValues[3] * filteredArray[n - 3] - aValues[4] * filteredArray[n - 4]);
                    break;
            }

        }

        return filteredArray;

    }

    public static void write(double [] array) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Save File");
        fc.setInitialFileName("Filter");
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text file", "*.txt"));
        
        File file = fc.showSaveDialog(null);
        
        if(file != null){
            try {
                PrintWriter pw = new PrintWriter(file,"UTF-8");
                
                for(int i = 0;i<array.length;i++){
                    pw.println(array[i]);
                }
                pw.close();
                
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Filter.class.getName()).log(Level.SEVERE, null, ex);
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(Filter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
}
