package com.github.jbharter;

import com.google.gson.Gson;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class StaticGoogleAuth {
    public enum jwtMapKeys {
        type,
        project_id,
        private_key_id,
        private_key,
        client_email,
        auth_uri,
        token_uri,
        auth_provider_x509_cert_url,
        client_x509_cert_url
    }
    private final Map<jwtMapKeys,String> configMap = new HashMap<>();
    private final InputStream inputStream;

    public StaticGoogleAuth(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public StaticGoogleAuth(ClassLoader loader, String resourceName) {
        this.inputStream = loader.getResourceAsStream(resourceName);
    }

    public StaticGoogleAuth(String... args) {
        this(jwtMapKeys.type,args[0],
             jwtMapKeys.project_id,args[1],
             jwtMapKeys.private_key_id,args[2],
             jwtMapKeys.private_key,args[3],
             jwtMapKeys.client_email,args[4],
             jwtMapKeys.auth_uri,args[5],
             jwtMapKeys.token_uri,args[6],
             jwtMapKeys.auth_provider_x509_cert_url,args[7],
             jwtMapKeys.client_x509_cert_url,args[8]);
    }

    public StaticGoogleAuth(jwtMapKeys key0, String val0,
                            jwtMapKeys key1, String val1,
                            jwtMapKeys key2, String val2,
                            jwtMapKeys key3, String val3,
                            jwtMapKeys key4, String val4,
                            jwtMapKeys key5, String val5,
                            jwtMapKeys key6, String val6,
                            jwtMapKeys key7, String val7,
                            jwtMapKeys key8, String val8) {
        configMap.put(key0,val0);
        configMap.put(key1,val1);
        configMap.put(key2,val2);
        configMap.put(key3,val3);
        configMap.put(key4,val4);
        configMap.put(key5,val5);
        configMap.put(key6,val6);
        configMap.put(key7,val7);
        configMap.put(key8,val8);

        Gson gson = new Gson();
        String stringified = gson.toJson(configMap);
        inputStream = new ByteArrayInputStream(stringified.getBytes(StandardCharsets.UTF_8));
    }
}
