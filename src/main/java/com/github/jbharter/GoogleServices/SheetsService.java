package com.github.jbharter.GoogleServices;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.Spreadsheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SheetsService {

    private static Sheets sheetsService;

    private static final Logger log = LoggerFactory.getLogger(SheetsService.class);

    public SheetsService() {
        try {
            getGoogleSheetsService();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static Sheets newGoogleSheetsService() throws IOException {
        Credential credential = Utils.getCredential();
        return new Sheets.Builder(Utils.httpTransport, Utils.JSON_FACTORY, Utils.setHttpTimeout(credential,1)).setApplicationName(Utils.APPLICATION_NAME).build();
    }

    private static Sheets getGoogleSheetsService() throws IOException {

        if (sheetsService == null) {
            sheetsService = newGoogleSheetsService();
        }
        return sheetsService;
    }

    public Spreadsheet getSpreadsheetById(String spreadSheetId) {
        try {
            List<String> ranges = new ArrayList<>();
            Sheets.Spreadsheets.Get request = sheetsService.spreadsheets().get(spreadSheetId);
            request.setRanges(ranges);
            request.setIncludeGridData(true);
            Spreadsheet s = request.execute();
            return s;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
