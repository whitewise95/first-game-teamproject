package com.study.spring.mapper;

import com.google.cloud.firestore.*;
import com.google.firebase.auth.*;
import com.study.spring.components.fireBase.FireBase;
import com.study.spring.domain.User;
import com.study.spring.dto.OAuth;
import org.springframework.stereotype.Repository;

import java.util.List;

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
        OAuth responseOAuth = db.collection("social").document(oAuth.getUniqueNumber()).get().get().toObject(OAuth.class);
        return responseOAuth;
    }

    public void socialDelete(OAuth responseOAuth) throws Exception {
        Firestore db = newCreateFireBase();
        db.collection("social").document(responseOAuth.getUniqueNumber()).delete();
    }

    public void socialInsert(OAuth oAuth) throws Exception {
        Firestore db = newCreateFireBase();
        db.collection("social").document(oAuth.getUniqueNumber()).set(oAuth);
    }

    public void userInsert(User user) throws Exception {
        Firestore db = newCreateFireBase();
        db.collection("user").document(user.getUid()).set(user);
    }

    public UserRecord findUserToUid(String uid) throws Exception {
        Firestore db = newCreateFireBase();
        return FirebaseAuth.getInstance().getUser(uid);
    }

    public User userSelect(String uid) throws Exception {
        Firestore db = newCreateFireBase();
        return db.collection("user").document(uid).get().get().toObject(User.class);
    }

    public String nickNameChange(User user) {
        try {
            Firestore db = newCreateFireBase();
            DocumentReference docRef = db.collection("user").document(user.getUid());
            docRef.update("nickName", user.getNickName());
            return "success";
        } catch (Exception e){
            return e.getMessage();
        }
    }

    public List<QueryDocumentSnapshot> nickNameSelect(String nickName) throws Exception {
        Firestore db = newCreateFireBase();
        return db.collection("cities").whereEqualTo("nickName", nickName).get().get().getDocuments();
    }
}
