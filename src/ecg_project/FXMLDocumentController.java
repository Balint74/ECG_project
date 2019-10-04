/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ecg_project;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
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
    private MenuItem Open;
    

    
    public void LoadFile(ActionEvent event){
        FileChooser fc = new FileChooser();
        File SelectedFile = fc.showOpenDialog(null);
        
        if (SelectedFile != null) {
            FileName.setText("File name: " + SelectedFile.getName());
            status.setText("Status: File loaded");
        }else{
            status.setText("Status: Wrong File");
              }
            
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        // bo
    }    
    
    
    
}
