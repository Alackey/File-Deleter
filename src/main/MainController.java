package main;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FilenameFilter;
import java.net.URL;
import java.nio.file.Files;
import java.util.ResourceBundle;

public class MainController implements Initializable{

    @FXML
    private Button chooseDirectory;

    @FXML
    private ComboBox comboBox;

    @FXML
    private DatePicker datePicker;

    @FXML
    private MenuItem oneDayID, oneWeekID, twoWeeksID, oneMonthID;

    File selectedDirectory;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //comboBox.getItems().addAll("test", "test 2");

        System.out.println(comboBox.getItems());
    }

    public void openFolder() {
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Choose a Folder");
        //File defaultDirectory = new File("user.home");
        chooser.setInitialDirectory(new File(System.getProperty("user.home")));
        selectedDirectory = chooser.showDialog(Main.stage);
        String[] temp = selectedDirectory.getPath().split("/");
        chooseDirectory.setText("Directory Choosen: " + temp[temp.length-1]);
        System.out.println(selectedDirectory);


    }

    public void oneDay() {
        datePicker.getEditor().setText(oneDayID.getText());
    }

    public void oneWeek() {
        datePicker.getEditor().setText(oneWeekID.getText());
    }

    public void twoWeeks() {
        datePicker.getEditor().setText(twoWeeksID.getText());
    }

    public void oneMonth() {
        datePicker.getEditor().setText(oneMonthID.getText());
    }

    public void deleteButton() {
        System.out.println("hey");
        System.out.println(comboBox.getEditor().getText());
        deleteFile();
    }

    public void deleteFile () {
        final File folder = selectedDirectory;
        final File[] files = folder.listFiles( new FilenameFilter() {
            @Override
            public boolean accept( final File dir, final String name ) {
                return name.matches( ".*\\" + comboBox.getEditor().getText());
            }
        } );
        for ( final File file : files ) {
            if (datePicker.getEditor().getText().equals("One Day")) {
                //if (file.lastModified())
               //     file.
            }
            if (!file.delete()) {
                System.err.println("Can't remove " + file.getAbsolutePath());
            }
        }
    }
}