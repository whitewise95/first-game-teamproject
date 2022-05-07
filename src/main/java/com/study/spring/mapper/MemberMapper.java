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

    public UserRecord socialLogin(String uid) {
        try {
            Firestore db = newCreateFireBase();
            return FirebaseAuth.getInstance().getUser(uid);
        } catch (Exception e) {
            return null;
        }

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

    public void socialInsert(OAuth oAuth) {
        try {
            Firestore db = newCreateFireBase();
            db.collection("social").document(oAuth.getUniqueNumber()).set(oAuth);
        } catch (Exception e) {
            throw new RuntimeException("MemberMapper.userInsert 시스템오류 : " + e.getMessage());
        }

    }

    public void userInsert(User user) {
        try {
            Firestore db = newCreateFireBase();
            db.collection("user").document(user.getUid()).set(
                    user.builder()
                            .email("guest")
                            .level(1)
                            .build()
            );
        } catch (Exception e) {
            throw new RuntimeException("MemberMapper.userInsert 시스템오류 : " + e.getMessage());
        }
        //        User.Card card1 = User.Card.builder().cardExp(0).cardLevel(1).skillName("Human").number(1).build();
        //        User.Card card2 = User.Card.builder().cardExp(0).cardLevel(1).skillName("Human").number(2).build();
        //        User.Card card3 = User.Card.builder().cardExp(0).cardLevel(1).skillName("Hunter").number(1).build();
        //        User.Card card4 = User.Card.builder().cardExp(0).cardLevel(1).skillName("Hunter").number(2).build();
        //        User.Card card5 = User.Card.builder().cardExp(0).cardLevel(1).skillName("Common").number(1).build();
        //        User.Card card6 = User.Card.builder().cardExp(0).cardLevel(1).skillName("Common").number(2).build();
        //        user.getCardList().add(card1);
        //        user.getCardList().add(card2);
        //        user.getCardList().add(card3);
        //        user.getCardList().add(card4);
        //        user.getCardList().add(card5);
        //        user.getCardList().add(card6);

        //        User.Costume costume1 = new User.Costume(1);
        //        User.Costume costume2 = new User.Costume(2);
        //        user.getCostumes().add(costume1);
        //        user.getCostumes().add(costume2);

    }

    public UserRecord findUserToUid(String uid) throws Exception {
        Firestore db = newCreateFireBase();
        return FirebaseAuth.getInstance().getUser(uid);
    }

    public User userSelect(String uid) {
        try {
            Firestore db = newCreateFireBase();
            return db.collection("user").document(uid).get().get().toObject(User.class);
        } catch (Exception e) {
            throw new RuntimeException("시스템 오류 : " + e.getMessage());
        }
    }

    public String nickNameChange(User user) {
        try {
            Firestore db = newCreateFireBase();
            DocumentReference docRef = db.collection("user").document(user.getUid());
            docRef.update("nickName", user.getNickName());
            return "success";
        } catch (Exception e) {
            throw new RuntimeException("시스템 오류 : " + e.getMessage());
        }
    }

    public int nickNameSelect(String nickName) {
        try {
            Firestore db = newCreateFireBase();
            List<QueryDocumentSnapshot> documents = db.collection("user").whereEqualTo("nickName", nickName).get().get().getDocuments();
            return documents.size();
        } catch (Exception e) {
            throw new RuntimeException("시스템 오류 : " + e.getMessage());
        }
    }
}
