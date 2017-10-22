package com.github.jbharter.delegates;

import com.github.jbharter.delegates.services.BaseService;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;

public abstract class BaseGoogleServiceDelegate implements BaseDelegate {
    // Google services require either a "secret.json" or perhaps a manual entry of these "static authentication Parameters

    static HttpTransport httpTransport;

    static {
        try {
            httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void envoy() throws DelegateException {

    }

    public boolean envoyReturned() {
        return false;
    }

    public abstract BaseService getService();


}
