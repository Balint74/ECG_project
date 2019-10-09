/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ecg_project;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;

/**
 *
 * @author feherbalint
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private Label status;
    
    @FXML
    private Label FileName;
    
    @FXML
    private Label FilePath;
    
    @FXML
    private Label FileLength;
    
    @FXML
    private LineChart<Number, Number> lineChart;


    
    public void loadFile(ActionEvent event){
        FileChooser fc = new FileChooser();
        File SelectedFile = fc.showOpenDialog(null);
        
        if (SelectedFile != null) {
            FileName.setText("File name: " + SelectedFile.getName());
            FilePath.setText("File path: " + SelectedFile.getAbsolutePath());
            status.setText("Status: File loaded");
        }else{
            status.setText("Status: Wrong File");
              }
            
        
        
        File file = new File(SelectedFile.getAbsolutePath());
        List<Double> array = new ArrayList<Double>();
        int sorokszama = 0;
        
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String sor;


            
            while ((sor = br.readLine()) != null) { 
                Double db = Double.parseDouble(sor);
                array.add(db);
                //System.out.println(db);
                sorokszama = sorokszama + 1;
                
            }
            
             System.out.println("sorokszama: " + sorokszama);
             FileLength.setText("File length: " + sorokszama);
             
             br.close();
             
                     
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ECG_Project.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("catch");
        } catch (IOException ex) {
            Logger.getLogger(ECG_Project.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("catch");
        }
        


        XYChart.Series series = new XYChart.Series();
        /*
        series.getData().add(new XYChart.Data(1, 23));
        series.getData().add(new XYChart.Data(2, 14));
        series.getData().add(new XYChart.Data(3, 15));
        series.getData().add(new XYChart.Data(4, 24));
        series.getData().add(new XYChart.Data(5, 34));
        series.getData().add(new XYChart.Data(6, 36));
        series.getData().add(new XYChart.Data(7, 22));
        series.getData().add(new XYChart.Data(8, 45));
        series.getData().add(new XYChart.Data(9, 43));
        series.getData().add(new XYChart.Data(10, 17));
        series.getData().add(new XYChart.Data(11, 29));
        series.getData().add(new XYChart.Data(12, 25));
        */
        for(int i = 0; i < 10000;i++){
            
            series.getData().add(new XYChart.Data(i/1,array.get(i)));
        }
        lineChart.getData().add(series);
    }

    
    public void exit(ActionEvent event){
        System.exit(0);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    
    
}
