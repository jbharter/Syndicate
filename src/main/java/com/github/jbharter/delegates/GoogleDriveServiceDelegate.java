package com.github.jbharter.delegates;

import com.github.jbharter.delegates.services.BaseService;
import com.github.jbharter.delegates.services.GoogleDriveService;

public class GoogleDriveServiceDelegate extends BaseGoogleServiceDelegate implements BaseDelegate {
    @Override
    public BaseService getService() {
        return new GoogleDriveService();
    }
}
