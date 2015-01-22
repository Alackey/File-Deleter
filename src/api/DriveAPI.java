package api;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;



import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.util.Arrays;

public class DriveAPI {

    private static String CLIENT_ID = "681085551322-moahfubh2ojv3qtclkqcbkoe7qg1p7p8.apps.googleusercontent.com";
    private static String CLIENT_SECRET = "F4Can-jLt_6hEPBG7AByRy68";

    private static String REDIRECT_URI = "urn:ietf:wg:oauth:2.0:oob:auto";

    private static GetAllWindowTitles windows = new GetAllWindowTitles();

    public void save() throws IOException {
        HttpTransport httpTransport = new NetHttpTransport();
        JsonFactory jsonFactory = new JacksonFactory();

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                httpTransport, jsonFactory, CLIENT_ID, CLIENT_SECRET, Arrays.asList(DriveScopes.DRIVE))
                .setAccessType("online")
                .setApprovalPrompt("auto").build();

        String url = flow.newAuthorizationUrl().setRedirectUri(REDIRECT_URI).build();
        URI uri = URI.create(url);
        Desktop.getDesktop().browse(uri);

        //Waits X seconds before checking for authorization acceptance
        try {
            Thread.sleep(10000);                 //1000 milliseconds is one second.
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }

        //Gets code from browser title
        String code = windows.authCode();

        GoogleTokenResponse response = flow.newTokenRequest(code).setRedirectUri(REDIRECT_URI).execute();
        GoogleCredential credential = new GoogleCredential().setFromTokenResponse(response);

        //Create a new authorized API client
        Drive service = new Drive.Builder(httpTransport, jsonFactory, credential).build();

        //Insert a file
        File body = new File();
        body.setTitle("File Deleter Log");
        body.setDescription("Delete Log for File Deleter");
        body.setMimeType("text/plain");

        java.io.File fileContent = new java.io.File("log.txt");
        FileContent mediaContent = new FileContent("text/plain", fileContent);

        File file = service.files().insert(body, mediaContent).execute();
        System.out.println("File ID: " + file.getId());
    }
}