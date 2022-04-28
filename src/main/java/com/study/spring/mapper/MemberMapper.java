package com.study.spring.mapper;

import com.google.cloud.firestore.Firestore;
import com.google.firebase.auth.*;
import com.study.spring.components.fireBase.FireBase;
import com.study.spring.config.auth.dto.OAuth;
import org.springframework.stereotype.Repository;

@Repository
public class MemberMapper {

    private FireBase fireBase;

    public MemberMapper(FireBase fireBase) {
        this.fireBase = fireBase;
    }

    public Firestore newCreateFireBase() throws Exception {
        fireBase.dbInit();
        return fireBase.makeDatabaseConn();
    }

    public UserRecord socialLogin(String uid) throws Exception {
        Firestore db = newCreateFireBase();
        return FirebaseAuth.getInstance().getUser(uid);
    }

    public OAuth socialSelect(OAuth oAuth) throws Exception {
        Firestore db = newCreateFireBase();
        return db.collection("social").document(oAuth.getUniqueNumber()).get().get().toObject(OAuth.class);
    }

    public void socialInsert(OAuth oAuth) throws Exception {
        Firestore db = newCreateFireBase();
        db.collection("social").document(oAuth.getUniqueNumber()).set(oAuth);
    }
}
