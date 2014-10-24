package main;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.swing.*;
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
    private MenuItem oneDayID, oneWeekID, twoWeeksID, oneMonthID, twoMonthsID;

    File selectedDirectory;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //comboBox.getItems().addAll("test", "test 2");

        System.out.println(comboBox.getItems());
    }

    public void openFolder() {
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Choose a Folder");

        //Sets initial directory
        chooser.setInitialDirectory(new File(System.getProperty("user.home")));

        //Opens new window to choose directory
        selectedDirectory = chooser.showDialog(Main.stage);

        //Gets folder name
        String[] temp = selectedDirectory.getPath().split("/");

        //Adds folder name to "Directory" button
        chooseDirectory.setText("Directory Choosen: " + temp[temp.length-1]);
        System.out.println(selectedDirectory);
    }

    //Sets the text of the DatePicker box to oneDay
    public void oneDay() {
        datePicker.getEditor().setText(oneDayID.getText());
    }

    //Sets the text of the DatePicker box to oneWeek
    public void oneWeek() {
        datePicker.getEditor().setText(oneWeekID.getText());
    }

    //Sets the text of the DatePicker box to twoWeeks
    public void twoWeeks() {
        datePicker.getEditor().setText(twoWeeksID.getText());
    }

    //Sets the text of the DatePicker box to oneMonth
    public void oneMonth() {
        datePicker.getEditor().setText(oneMonthID.getText());
    }
    //Sets the text of the DatePicker box to twoMonths
    public void twoMonths() {
        datePicker.getEditor().setText(twoMonthsID.getText());
    }

    //Calls deleteFile() to delete selected file types
    public void deleteButton() {
        System.out.println("hey");
        System.out.println(comboBox.getEditor().getText());
        deleteFile();
    }

    public void deleteFile () {
        final File folder = selectedDirectory;
        //Gets files based on the file type selected and puts them into an array
        final File[] files = folder.listFiles( new FilenameFilter() {
            @Override
            public boolean accept( final File dir, final String name ) {
                return name.matches( ".*\\" + comboBox.getEditor().getText());
            }
        } );
        //Goes through files[] and deletes the files one by one
        for ( final File file : files ) {
            if (datePicker.getEditor().getText().equals("")) {
                if (!file.delete()) {
                    System.err.println("Can't remove " + file.getAbsolutePath());
                }
            } else {
               lastModified(file);
            }
        }
    }

    public void lastModified (File timedFile) {
        //Deletes files if last modified seconds is lest than a day
        if (datePicker.getEditor().getText().equals("One Day")) {
            if ((System.currentTimeMillis() - timedFile.lastModified()) <= 86400000) {
                if (!timedFile.delete()) {
                    System.err.println("Can't remove " + timedFile.getAbsolutePath());
                }
            }
        }
        //Deletes files if last modified seconds is lest than a week
        else if (datePicker.getEditor().getText().equals("One Week")) {
            if ((System.currentTimeMillis() - timedFile.lastModified()) <= 604800000) {
                if (!timedFile.delete()) {
                    System.err.println("Can't remove " + timedFile.getAbsolutePath());
                }
            }
        }
        //Deletes files if last modified seconds is lest than two weeks
        else if (datePicker.getEditor().getText().equals("Two Weeks")) {
            if ((System.currentTimeMillis() - timedFile.lastModified()) <= 1209600000) {
                if (!timedFile.delete()) {
                    System.err.println("Can't remove " + timedFile.getAbsolutePath());
                }
            }
        }
        //Deletes files if last modified seconds is lest than a month
        else if (datePicker.getEditor().getText().equals("One Month")) {
            if ((System.currentTimeMillis() - timedFile.lastModified()) <= 2629740000L) {
                if (!timedFile.delete()) {
                    System.err.println("Can't remove " + timedFile.getAbsolutePath());
                }
            }
        }
        //Deletes files if last modified seconds is lest than two months
        else if (datePicker.getEditor().getText().equals("Two Months")) {
            if ((System.currentTimeMillis() - timedFile.lastModified()) <= 5259490000L) {
                if (!timedFile.delete()) {
                    System.err.println("Can't remove " + timedFile.getAbsolutePath());
                }
            }
        }
    }
}