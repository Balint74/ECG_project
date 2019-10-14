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
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.stage.FileChooser;

/**
 *
 * @author feherbalint
 */

public class FXMLDocumentController implements Initializable {
    private Double upperBound;
    
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
    
    @FXML
    private NumberAxis xAxis;
    
    @FXML
    private NumberAxis yAxis;
    
    @FXML
    private Slider slider;
    
    @FXML
    private Button zoomButton;
    
    
    public void loadFile(ActionEvent event){
        FileChooser fc = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Text Files", "*.txt");
        fc.getExtensionFilters().add(extFilter);
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
            System.out.println("File catch");
        } catch (IOException ex) {
            Logger.getLogger(ECG_Project.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("I/O catch");
        }
               
        XYChart.Series series = new XYChart.Series();
        
        double frekvencia = 256.0;
        int counter = 0;
        for(int i = 0; i < sorokszama-1;i = i+4){

            series.getData().add(new XYChart.Data(counter/frekvencia,array.get(i)));
            counter++;
        }
        
        
        
        lineChart.getData().add(series);
        series.getNode().setStyle("-fx-stroke-width: 1px;");
        
        
        upperBound = Double.valueOf(sorokszama)/1024;
        zoomButton.setDisable(false);
    }

    public void reset(ActionEvent event){
        lineChart.getData().clear();
        xAxis.setAutoRanging(true);
        yAxis.setAutoRanging(true);
        zoomButton.setDisable(true);
        status.setText("Status: Graph reset");
        FileName.setText("File name:");
        FilePath.setText("File path:");
        FileLength.setText("File length:");
    }
    
    public void exit(ActionEvent event){
        System.exit(0);
    }
    
    public void zoom(ActionEvent event){

        xAxis.setAutoRanging(false);
        yAxis.setAutoRanging(false);
        xAxis.setUpperBound(upperBound*(slider.getValue()/100));
        yAxis.setUpperBound(600);
    }

    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    
    
    
    
}
