package com.healthmonitor.configs;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import io.github.cdimascio.dotenv.Dotenv;
import jakarta.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FirebaseConfigs {

    private final Dotenv dotenv = Dotenv.load();

    @PostConstruct
    public void initFirebase() throws IOException {
        FileInputStream serviceAccount = new FileInputStream(
                dotenv.get("FIREBASE_ACCOUNT_KEY_PATH"));

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl(dotenv.get("FIREBASE_DB_URL"))
                .build();

        if (FirebaseApp.getApps().isEmpty()) {
            FirebaseApp.initializeApp(options);
        }
    }
}
