package com.study.spring.mapper;

import com.google.cloud.firestore.*;
import com.google.firebase.auth.*;
import com.google.firebase.database.*;
import com.study.spring.components.fireBase.FireBase;
import com.study.spring.domain.User;
import com.study.spring.dto.OAuth;
import com.study.spring.dto.common.resultType.DataBaseTable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MemberMapper {

    public final static String DEFAULT_DATABASE = "https://Egoism.firebaseio.com/";
    public final static String REAL_TIME_DATABASE = "https://egoism-3af0d-default-rtdb.firebaseio.com/";

    private FireBase fireBase;

    public MemberMapper(FireBase fireBase) {
        this.fireBase = fireBase;
    }

    public Firestore newCreateFireBase(String dataBaseUrl) throws Exception {
        fireBase.dbInit(dataBaseUrl);
        return fireBase.makeDatabaseConn();
    }

    public UserRecord guestSelect(String uid) {
        try {
            Firestore db = newCreateFireBase(DEFAULT_DATABASE);
            return FirebaseAuth.getInstance().getUser(uid);
        } catch (Exception e) {
            return null;
        }
    }

    public OAuth socialSelect(OAuth oAuth) throws Exception {
        Firestore db = newCreateFireBase(DEFAULT_DATABASE);
        OAuth responseOAuth = db.collection(DataBaseTable.SOCIAL.getTable()).document(oAuth.getUniqueNumber()).get().get().toObject(OAuth.class);
        return responseOAuth;
    }

    public void socialDelete(OAuth responseOAuth) throws Exception {
        Firestore db = newCreateFireBase(DEFAULT_DATABASE);
        db.collection(DataBaseTable.SOCIAL.getTable()).document(responseOAuth.getUniqueNumber()).delete();
    }

    public void socialInsert(OAuth oAuth) {
        try {
            newCreateFireBase(REAL_TIME_DATABASE);
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
            ref.child(oAuth.getUniqueNumber()).setValueAsync(oAuth.getUid());
        } catch (Exception e) {
            throw new RuntimeException("MemberMapper.userInsert 시스템오류 : " + e.getMessage());
        }

    }

    public void userInsert(User user) {
        try {
            User.Card card1 = User.Card.builder().cardExp(0).cardLevel(1).skillName("Human").number(1).build();
            User.Card card2 = User.Card.builder().cardExp(0).cardLevel(1).skillName("Human").number(2).build();
            User.Card card3 = User.Card.builder().cardExp(0).cardLevel(1).skillName("Hunter").number(1).build();
            User.Card card4 = User.Card.builder().cardExp(0).cardLevel(1).skillName("Hunter").number(2).build();
            User.Card card5 = User.Card.builder().cardExp(0).cardLevel(1).skillName("Common").number(1).build();
            User.Card card6 = User.Card.builder().cardExp(0).cardLevel(1).skillName("Common").number(2).build();
            user.getCardList().add(card1);
            user.getCardList().add(card2);
            user.getCardList().add(card3);
            user.getCardList().add(card4);
            user.getCardList().add(card5);
            user.getCardList().add(card6);
            for (int i = 1; i < 5; i++) {
                user.getCostumeList().add(new User.Costume(i));
            }
            user.setLevel(1);
            Firestore db = newCreateFireBase(DEFAULT_DATABASE);
            db.collection(DataBaseTable.USER.getTable()).document(user.getUid()).set(user);
        } catch (Exception e) {
            throw new RuntimeException("MemberMapper.userInsert 시스템오류 : " + e.getMessage());
        }
    }

    public UserRecord findUserToUid(String uid) throws Exception {
        Firestore db = newCreateFireBase(DEFAULT_DATABASE);
        return FirebaseAuth.getInstance().getUser(uid);
    }

    public User userSelect(String uid) {
        try {
            Firestore db = newCreateFireBase(DEFAULT_DATABASE);
            return db.collection(DataBaseTable.USER.getTable()).document(uid).get().get().toObject(User.class);
        } catch (Exception e) {
            throw new RuntimeException("시스템 오류 : " + e.getMessage());
        }
    }

    public String nickNameChange(User user) {
        try {
            Firestore db = newCreateFireBase(DEFAULT_DATABASE);
            DocumentReference docRef = db.collection(DataBaseTable.USER.getTable()).document(user.getUid());
            docRef.update("nickName", user.getNickName());
            return user.getNickName();
        } catch (Exception e) {
            throw new RuntimeException("시스템 오류 : " + e.getMessage());
        }
    }

    public int nickNameSelect(String nickName) {
        try {
            Firestore db = newCreateFireBase(DEFAULT_DATABASE);
            List<QueryDocumentSnapshot> documents = db.collection(DataBaseTable.USER.getTable())
                    .whereEqualTo("nickName", nickName).get().get().getDocuments();
            return documents.size();
        } catch (Exception e) {
            throw new RuntimeException("시스템 오류 : " + e.getMessage());
        }
    }

    public String login(OAuth oAuth) {
        try {
            Firestore db = newCreateFireBase(REAL_TIME_DATABASE);
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

            ref.child(oAuth.getUniqueNumber()).setValueAsync(DataBaseTable.USER.getTable());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return oAuth.getUniqueNumber();
    }
}
