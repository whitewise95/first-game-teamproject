package com.study.spring.components.fireBase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.*;
import com.google.firebase.cloud.FirestoreClient;
import com.study.spring.components.*;
import com.study.spring.domain.resultType.DataBaseType;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.*;

@Component
public class FireBase {

    private FireBaseProperties fireBaseProperties;
    private FileInputStream serviceAccount;
    private FirebaseOptions options;

    public FireBase(Components components) {
        this.fireBaseProperties = components.getFireBaseProperties();
    }

    private static Firestore DB;

    public void dbInit(DataBaseType dataBaseUrl) throws Exception {
        FirebaseApp firebaseApp = null;
        List<FirebaseApp> fireApp = FirebaseApp.getApps();

        if (Optional.ofNullable(fireApp).isPresent() && !fireApp.isEmpty()) {
            firebaseApp = fireApp.stream()
                    .filter(fire -> fire.getName().equals(dataBaseUrl.getKey()))
                    .findFirst()
                    .orElseGet(() -> null);

        }
        if (!Optional.ofNullable(firebaseApp).isPresent()) {
            serviceAccount = new FileInputStream(
                    new File("").getAbsolutePath() + "/" + fireBaseProperties.getFireBaseKey()
            );
            options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl(dataBaseUrl.getType())
                    .build();

            firebaseApp.initializeApp(options, dataBaseUrl.getKey());
        }


    }

    public static Firestore makeDatabaseConn() {
        return DB = FirestoreClient.getFirestore();
    }

}
