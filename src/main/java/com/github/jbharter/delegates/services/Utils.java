package com.github.jbharter.delegates.services;

import com.github.jbharter.delegates.services.GoogleDriveService;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.sheets.v4.SheetsScopes;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

class Utils {

    static final String APPLICATION_NAME = "Application Name";

    static final String loc = "secret.json";

    static HttpTransport httpTransport;

    static {
        try {
            httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    static List<String> SCOPES = Arrays.asList(DriveScopes.DRIVE_READONLY, SheetsScopes.SPREADSHEETS_READONLY);

    static GoogleCredential getCredential() throws IOException {

        return GoogleCredential.fromStream(GoogleDriveService.class.getClassLoader().getResourceAsStream(loc)).createScoped(SCOPES);
    }

    static HttpRequestInitializer setHttpTimeout(final HttpRequestInitializer requestInitializer, int timeoutMinutes) {
        return httpRequest -> {
            requestInitializer.initialize(httpRequest);
            httpRequest.setConnectTimeout(timeoutMinutes * 60000);  // 3 minutes connect timeout
            httpRequest.setReadTimeout(timeoutMinutes * 60000);  // 3 minutes read timeout
        };
    }
}
