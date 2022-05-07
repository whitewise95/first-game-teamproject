package com.study.spring.components.fireBase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.*;
import com.google.firebase.cloud.FirestoreClient;
import com.study.spring.components.*;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.*;

@Component
public class FireBase {

    private FireBaseProperties fireBaseProperties;

    public FireBase(Components components) {
        this.fireBaseProperties = components.getFireBaseProperties();
    }

    private static Firestore DB;

    public void dbInit() throws Exception {
        FirebaseApp firebaseApp = null;
        List<FirebaseApp> fireApp = FirebaseApp.getApps();

        if (Optional.ofNullable(fireApp).isPresent() && !fireApp.isEmpty()) {
            firebaseApp = fireApp.stream()
                    .filter(fire -> fire.getName().equals(FirebaseApp.DEFAULT_APP_NAME))
                    .findFirst()
                    .orElseGet(() -> null);
        }
        if (!Optional.ofNullable(firebaseApp).isPresent()) {
            FileInputStream serviceAccount = new FileInputStream(
                    new File("").getAbsolutePath() + "/" + fireBaseProperties.getFireBaseKey()
            );

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl(fireBaseProperties.getDatabaseUrl())
                    .build();
            firebaseApp.initializeApp(options);
        }
    }

    public static Firestore makeDatabaseConn() {
        return DB = FirestoreClient.getFirestore();
    }

}
