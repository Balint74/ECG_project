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
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Line;
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
    private Label fileName;

    @FXML
    private Label filePath;

    @FXML
    private Label fileLength;

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

    @FXML
    private Label interval;

    @FXML
    private ChoiceBox choiceBox;

    private BinaryFileLoader fileLoader;

    @FXML
    private CheckBox checkBox;

    @FXML
    private Label labelChanel;

    @FXML
    private Label labelFreq;

    @FXML
    private Button buttonFilter;

    @FXML
    private Button chanelButton;

    private double[] filterArray;

    @FXML
    private MenuItem saveMenuItem;

    @FXML
    private Label labelCursorCords;

    private XYChart.Series series = new XYChart.Series();

    public void loadFile(ActionEvent event) {
        FileChooser fc = new FileChooser();

        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Binary Files", "*.bsp"),
                new FileChooser.ExtensionFilter("Text Files", "*.txt"));

        File SelectedFile = fc.showOpenDialog(null);

        String selectedFileName = SelectedFile.getName();
        String selectedFileExtension = selectedFileName.substring(selectedFileName.lastIndexOf("."), selectedFileName.length());

        if (selectedFileExtension.equals(".txt")) {

            chanelButton.setDisable(true);
            fileName.setText("File name: " + SelectedFile.getName());
            filePath.setText("File path: " + SelectedFile.getAbsolutePath());
            status.setText("Status: File loaded");

            File file = new File(SelectedFile.getAbsolutePath());
            List<Double> array = new ArrayList<Double>();
            int numOfLines = 0;

            try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String line;

                while ((line = br.readLine()) != null) {
                    double db = Double.parseDouble(line);
                    array.add(db);
                    numOfLines = numOfLines + 1;
                }

                fileLength.setText("File length: " + numOfLines);
                labelFreq.setText("Frequency: 1024hz");

                br.close();

            } catch (FileNotFoundException ex) {
                Logger.getLogger(ECG_Project.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("File catch");
            } catch (IOException ex) {
                Logger.getLogger(ECG_Project.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("I/O catch");
            }

            series = new XYChart.Series();
            series.setName("ECG");

            double frequency = 256.0;
            int counter = 0;
            for (int i = 0; i < numOfLines - 1; i = i + 4) {

                series.getData().add(new XYChart.Data(counter / frequency, array.get(i)));
                counter++;
            }

            lineChart.getData().add(series);

            series.getNode().setStyle("-fx-stroke-width: 1px;");

            interval.setText("Interval: " + numOfLines / 4 / frequency + "s");

            upperBound = Double.valueOf(numOfLines) / 1024;
            zoomButton.setDisable(false);

//Binaris resz
        } else if (selectedFileExtension.equals(".bsp")) {

            fileName.setText("File name: " + SelectedFile.getName());
            filePath.setText("File path: " + SelectedFile.getAbsolutePath());
            status.setText("Status: File loaded");
            chanelButton.setDisable(false);

            fileLoader = new BinaryFileLoader();
            short[][] Array = new short[64][fileLoader.getNumOfLines()];

            Array = fileLoader.load(SelectedFile);

            series = new XYChart.Series<Double,Double>();
            int counter = 0;
            double frequency = fileLoader.getFreq();
            for (int i = 0; i < fileLoader.getNumOfLines(); i = i + 4) {

                series.getData().add(new XYChart.Data(counter / (frequency / 4), Array[0][i]));
                counter++;
            }

            lineChart.getData().add(series);
            series.getNode().setStyle("-fx-stroke-width: 1px;");
            series.setName("Chanel 1");

            interval.setText("Interval: " + fileLoader.getNumOfLines() / frequency + "s");
            fileLength.setText("File length: " + fileLoader.getNumOfLines());
            labelFreq.setText("Frequency: " + fileLoader.getFreq() + "hz");
            labelChanel.setText("Selected chanel: 1");

            upperBound = Double.valueOf(fileLoader.getNumOfLines()) / fileLoader.getFreq();
            zoomButton.setDisable(false);
            buttonFilter.setDisable(false);

            for (int i = 0; i < 64; i++) {
                choiceBox.getItems().add("Chanel: " + (i + 1));
            }

            choiceBox.getSelectionModel().selectFirst();
            //lineChart.getPlugins().addAll(new Zoomer(), new Panner(), new CrosshairIndicator<>(), new DataPointTooltip<>());

            double dsor = 0.1;
            String sor= series.getData().get(1).toString();
            System.out.println(sor);
            System.out.println(sor.substring(sor.indexOf(",") + 1, sor.lastIndexOf(",")));

        } else {
            status.setText("Status: Wrong File");
        }

    }

    public void reset(ActionEvent event) {
        lineChart.getData().clear();
        choiceBox.getSelectionModel().clearSelection();
        choiceBox.getItems().clear();
        xAxis.setAutoRanging(true);
        yAxis.setAutoRanging(true);
        zoomButton.setDisable(true);
        status.setText("Status: Graph reset");
        fileName.setText("File name:");
        filePath.setText("File path:");
        fileLength.setText("File length:");
        interval.setText("Interval: ");
        labelFreq.setText("Frequency: ");
        labelChanel.setText("Selected chanel: ");
        chanelButton.setDisable(true);
        buttonFilter.setDisable(true);
        saveMenuItem.setDisable(true);
        slider.setValue(100);
    }

    public void exit(ActionEvent event) {
        System.exit(0);
    }

    public void zoom(ActionEvent event) {

        xAxis.setAutoRanging(false);
        yAxis.setAutoRanging(false);
        xAxis.setUpperBound(upperBound * (slider.getValue() / 100));
        xAxis.setLowerBound(0);
    }

    public void scroll() {

        lineChart.setOnScroll(new EventHandler<ScrollEvent>() {
            @Override
            public void handle(ScrollEvent ev) {
                double scrollFactor = 2.5;
                int deltaY = (int) ev.getDeltaY();
                //System.out.println(deltaY);
                if (deltaY < 0) {
                    scrollFactor = -2.5;
                }

                xAxis.setUpperBound(xAxis.getUpperBound() + scrollFactor);
                xAxis.setLowerBound(xAxis.getLowerBound() + scrollFactor);

                ev.consume();
            }
        });

    }

    public void leftScroll(ActionEvent e) {
        xAxis.setUpperBound(xAxis.getUpperBound() - 5);
        xAxis.setLowerBound(xAxis.getLowerBound() - 5);
    }

    public void rightScroll(ActionEvent e) {
        xAxis.setUpperBound(xAxis.getUpperBound() + 5);
        xAxis.setLowerBound(xAxis.getLowerBound() + 5);
    }

    public void viewChanel(ActionEvent e) {

        if (checkBox.isSelected() == false) {
            lineChart.getData().clear();
        }

        xAxis.setAutoRanging(true);
        yAxis.setAutoRanging(true);

        String choice = choiceBox.getValue().toString();
        int choiceNumber = Integer.parseInt(choice.substring(8, choice.length()));

        series = new XYChart.Series();

        short[][] array = new short[63][fileLoader.getNumOfLines()];

        array = fileLoader.getArray();

        int counter = 0;
        double frequency = fileLoader.getFreq();

        for (int i = 0; i < fileLoader.getNumOfLines(); i = i + 4) {

            series.getData().add(new XYChart.Data(counter / (frequency / 4), array[choiceNumber - 1][i]));
            counter++;
        }
        series.setName("Chanel " + choiceNumber);
        lineChart.getData().add(series);
        series.getNode().setStyle("-fx-stroke-width: 1px;");

        if (checkBox.isSelected() == true) {
            labelChanel.setText(labelChanel.getText() + " ," + choiceNumber);
        } else {
            labelChanel.setText("Selected chanel: " + choiceNumber);

        }

    }

    public void filterChanel(ActionEvent e) {
        lineChart.getData().clear();

        Filter filter = new Filter();
        filterArray = new double[fileLoader.getNumOfLines()];

        String choice = choiceBox.getValue().toString();
        int choiceNumber = Integer.parseInt(choice.substring(8, choice.length()));

        filterArray = filter.load(fileLoader.getArray(), choiceNumber - 1, fileLoader.getNumOfLines());
        series = new XYChart.Series();
        series.setName("Chanel " + choiceNumber);

        int counter = 0;
        double frequency = fileLoader.getFreq();

        for (int i = 0; i < fileLoader.getNumOfLines(); i = i + 4) {

            series.getData().add(new XYChart.Data(counter / (frequency / 4), filterArray[i]));
            counter++;
        }

        lineChart.getData().add(series);
        series.getNode().setStyle("-fx-stroke-width: 1px;");

        labelChanel.setText("Selected chanel: " + choiceNumber);
        status.setText("Status: Chanel filtered");

        saveMenuItem.setDisable(false);
        saveMenuItem.setStyle("-fx-opacity: 1.0; -fx-text-fill: black;");

    }

    public void write(ActionEvent e) {

        Filter.write(filterArray);
        status.setText("Status: Filter saved");
    }

    public void mouseCords() {
        lineChart.setOnMouseMoved((MouseEvent event) -> {

            Point2D mouseSceneCoords = new Point2D(event.getSceneX(), event.getSceneY());
            double x = (int) xAxis.sceneToLocal(mouseSceneCoords).getX();
            double y = yAxis.sceneToLocal(mouseSceneCoords).getY();

            labelCursorCords.setText(String.format("(X = %.2f  Y =  %.2f)", xAxis.getValueForDisplay(x), yAxis.getValueForDisplay(y)));

            String graphYvalue = series.getData().get((int)xAxis.getValueForDisplay(x).doubleValue()).toString();
            System.out.println(graphYvalue.substring(graphYvalue.indexOf(",") + 1, graphYvalue.lastIndexOf(",")));

        });

        lineChart.setOnMouseExited((MouseEvent) -> {

            labelCursorCords.setText("");

        });
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        scroll();
        mouseCords();
    }

}
