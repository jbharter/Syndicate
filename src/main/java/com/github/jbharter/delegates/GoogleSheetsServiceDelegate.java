package com.github.jbharter.delegates;

import com.github.jbharter.delegates.services.BaseService;
import com.github.jbharter.delegates.services.GoogleSheetsService;

public class GoogleSheetsServiceDelegate extends BaseGoogleServiceDelegate implements BaseDelegate {
    @Override
    public BaseService getService() {
        return new GoogleSheetsService();
    }
}
