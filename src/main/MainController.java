package main;


import api.DriveAPI;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.stage.DirectoryChooser;
import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.*;
import org.controlsfx.dialog.Dialog;

import java.awt.*;
import java.io.*;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Scanner;

public class MainController implements Initializable {

    private DriveAPI drive = new DriveAPI();

    @FXML
    private Button chooseDirectory;

    @FXML
    private ComboBox comboBox;

    @FXML
    private DatePicker datePicker;

    @FXML
    private MenuItem oneDayID, oneWeekID, twoWeeksID, oneMonthID, twoMonthsID;

    File selectedDirectory;
    Byte directoryChoosenCount = 0;

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

        directoryChoosenCount = 1;

        //Adds folder name to "Directory" button
        chooseDirectory.setText("Directory Choosen: " + temp[temp.length - 1]);

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
    public void deleteButton() throws FileNotFoundException {
        deleteFile();
    }

    public void deleteFile() throws FileNotFoundException {
        final File folder = selectedDirectory;
        String fileNames = "";

        if (directoryChoosenCount == 0) {
            Dialogs.create()
                    .owner(Main.stage)
                    .title("Error Dialog")
                    .masthead("Directory Not Choosen")
                    .message("Choose directory by clicking on the 'Choose Directory' button.")
                    .showError();
        }

        if (comboBox.getEditor().getText().equals("")) {
            Dialogs.create()
                    .owner(Main.stage)
                    .title("Error Dialog")
                    .masthead("File Type Not Chosen")
                    .message("Choose file type by clicking on arrow attached to 'File Type' box.")
                    .showError();
        }

        //Gets files based on the file type selected and puts them into an array
        final File[] files = folder.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(final File dir, final String name) {
                return name.matches(".*\\" + comboBox.getEditor().getText());
            }
        });
        //Goes through files[] and deletes the files one by one
        Action response = Dialogs.create()
                .owner(Main.stage)
                .title("Confirm Dialog")
                .masthead("You cannot recover the files!!!")
                .message("Do you want to continue?")
                .showConfirm();

        if (response == Dialog.ACTION_YES) {
            System.out.println("yes");
            for (final File file : files) {
                fileNames = fileNames + file.getName() + ", ";
                if (datePicker.getEditor().getText().equals("")) {
                    if (!file.delete()) {
                        System.err.println("Can't remove " + file.getAbsolutePath());
                    }
                } else {
                    lastModified(file);
                }
            }
        }
        log(fileNames);
    }

    public void lastModified(File timedFile) {
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

    public void openAbout() throws IOException {
        //Opens text file with notepad
        File about = new File("src/about.txt");
        Desktop.getDesktop().edit(about);
    }

    public void log(String fileNames) throws FileNotFoundException {
        String oldLogData = "";
        Date date = new Date();

        File log = new File("log.txt");
        Scanner sc = new Scanner(log);
        while (sc.hasNextLine()) {
            oldLogData = oldLogData + sc.nextLine() + "\n";
        }

        PrintWriter pw = new PrintWriter(log);
        pw.println(oldLogData + date.toString() + "\nDeleted: "+ fileNames + "\n");

        pw.close();
        sc.close();
    }

    public void saveToDrive() throws IOException {
        drive.save();
    }

    public void close() {
        Main.stage.close();
    }
}