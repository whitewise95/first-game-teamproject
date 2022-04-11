package com.study.spring.mapper;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.auth.*;
import com.study.spring.components.fireBase.FireBase;
import com.study.spring.domain.User;
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

    public void insertMember(User user) throws Exception {
        Firestore db = newCreateFireBase();
        db.collection("user").document(user.getEmail()).set(user);
    }

    public User findOverLaPUserId(User user) throws Exception {
        Firestore db = newCreateFireBase();
        DocumentReference docRef = db.collection("user").document(user.getEmail());
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();

        if (document.exists()) {
            return document.toObject(User.class);
        }
        return new User();
    }

    public UserRecord socialLogin(String uid) throws Exception {
        Firestore db = newCreateFireBase();
        return FirebaseAuth.getInstance().getUser(uid);
    }

}
