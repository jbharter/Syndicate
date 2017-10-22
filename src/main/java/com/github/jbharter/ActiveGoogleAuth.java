package com.github.jbharter;

import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;

import java.util.List;

public class ActiveGoogleAuth {

    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    private List<String> SCOPES;
    private String APPLICATION_NAME;

    public ActiveGoogleAuth() {}
}
