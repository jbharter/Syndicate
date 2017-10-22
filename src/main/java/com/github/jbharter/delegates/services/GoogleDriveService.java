package com.github.jbharter.delegates.services;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class GoogleDriveService extends BaseService {

    private static Drive driveService;

    private static final Logger log = LoggerFactory.getLogger(GoogleDriveService.class);

    public GoogleDriveService() {
        try {
            getGoogleDriveService();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Drive newGoogleDriveService() throws IOException {
        Credential credential = Utils.getCredential();
        return new Drive.Builder(Utils.httpTransport, Utils.JSON_FACTORY, credential).setApplicationName(Utils.APPLICATION_NAME).build();
    }

    static Drive getGoogleDriveService() throws IOException {

        if (driveService == null) {
            driveService = newGoogleDriveService();
        }
        return driveService;
    }

    public List<File> getListOfFiles() {

        try {
            List<File> result = new ArrayList<File>();
            Drive.Files.List request = driveService.files().list();

            FileList files = request.execute();
            result.addAll(files.getFiles());
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<File> getListOfFilesWhere(String key, String value) {
        List<File> results = new ArrayList<>();

        this.getListOfFiles().forEach(file -> {
            if (file.get(key).equals(value)) results.add(file);
        });
        return results;
    }

    public List<File> getListOfFilesWhereIncludes(String key, String value) {
        List<File> results = new ArrayList<>();

        this.getListOfFiles().forEach(file -> {
            if (file.get(key).toString().contains(value)) results.add(file);
        });
        return results;
    }

    public List<String> getPropertyFromFiles(String property) {
        return getListOfFiles().stream().map(eachFile -> {
            if (eachFile.get(property) != null) {
                return eachFile.get(property).toString();
            } else {
                return null;
            }
        }).filter(Objects::nonNull).collect(Collectors.toList());
    }

}
