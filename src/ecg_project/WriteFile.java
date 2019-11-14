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
public class WriteFile {
        public static void write(double[] array) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Save File");
        fc.setInitialFileName("Filter");
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text file", "*.txt"));

        File file = fc.showSaveDialog(null);

        if (file != null) {
            try {
                PrintWriter pw = new PrintWriter(file, "UTF-8");

                for (int i = 0; i < array.length; i++) {
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
