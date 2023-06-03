package com.study.spring.components.fireBase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import com.study.spring.components.FireBaseProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FireBase {

    private final FireBaseProperties fireBaseProperties;
    private FileInputStream serviceAccount;
    private FirebaseOptions options;

    private Firestore DB;

    public void init() throws Exception {
        FirebaseApp firebaseApp = null;
        serviceAccount = new FileInputStream(
                new File("").getAbsolutePath() + "/" + fireBaseProperties.getFireBaseKey()
        );

        options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

        firebaseApp.initializeApp(options);

        DB = FirestoreClient.getFirestore();
    }

    public Firestore getDB() {
        return DB;
    }
}
