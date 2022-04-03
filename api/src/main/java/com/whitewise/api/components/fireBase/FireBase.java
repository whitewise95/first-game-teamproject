package com.whitewise.api.components.fireBase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.*;
import com.google.firebase.auth.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.util.*;

@Component
public class FireBase {

    private FirebaseOptions option;
    private static Firestore DB;
    private FirebaseAuth mAuth;
    private final static String PATH = "C:/Users/coffe/Desktop/fireBaseKey/";
    private final static String KEY = "firstproject-2bccc-firebase-adminsdk-3uyjr-b540a84607.json";
    private final static String COLLECTION_NAME = "user";

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
            FileInputStream serviceAccount = new FileInputStream(PATH + KEY);

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();
            firebaseApp.initializeApp(options);
        }
    }

    public static Firestore makeDatabaseConn() {
        //Firestore 인스턴스 생성
        return DB = FirestoreClient.getFirestore();
    }



    public String test(String idToken) throws FirebaseAuthException {
        FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
        String uid = decodedToken.getUid();
        return uid;
    }


}
