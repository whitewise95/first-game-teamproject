package com.study.spring.repository;

import com.google.cloud.firestore.*;
import com.google.firebase.auth.*;
import com.google.firebase.database.*;
import com.study.spring.components.fireBase.FireBase;
import com.study.spring.domain.User;
import com.study.spring.dto.OAuth;
import com.study.spring.dto.common.resultType.DataBaseTable;
import com.study.spring.exceptionHandler.CustumException.CustomException;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.study.spring.domain.resultType.DataBaseType.*;
import static com.study.spring.dto.common.resultType.DataBaseTable.USER;
import static com.study.spring.exceptionHandler.CustumException.ErrorCode.*;

@Repository
public class MemberRepository {

    private FireBase fireBase;

    public MemberRepository(FireBase fireBase) {
        this.fireBase = fireBase;
    }

    public Firestore newCreateFireBase(String dataBaseUrl) throws Exception {
        fireBase.dbInit(dataBaseUrl);
        return fireBase.makeDatabaseConn();
    }

    public UserRecord guestSelect(String uid) {
        try {
            Firestore db = newCreateFireBase(DEFAULT_DATABASE.getType());
            return FirebaseAuth.getInstance().getUser(uid);
        } catch (Exception e) {
            return null;
        }
    }

    public Boolean existUid(String uid) {
        try {
            Firestore db = newCreateFireBase(DEFAULT_DATABASE.getType());
             return db.collection(USER.getTable()).document(uid).get().get().exists();
        } catch (Exception e) {
            throw new CustomException(FAIL_DATABASE_FIND);
        }
    }

    public OAuth socialSelect(OAuth oAuth) throws Exception {
        Firestore db = newCreateFireBase(DEFAULT_DATABASE.getType());
        OAuth responseOAuth = db.collection(DataBaseTable.SOCIAL.getTable()).document(oAuth.getUniqueNumber()).get().get().toObject(OAuth.class);
        return responseOAuth;
    }

    public void socialInsert(OAuth oAuth) {
        try {
            newCreateFireBase(REAL_TIME_DATABASE.getType());
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
            Firestore db = newCreateFireBase(DEFAULT_DATABASE.getType());
            db.collection(USER.getTable()).document(user.getUid()).set(user);
        } catch (Exception e) {
            throw new RuntimeException("MemberMapper.userInsert 시스템오류 : " + e.getMessage());
        }
    }

    public UserRecord findUserToUid(String uid) throws Exception {
        Firestore db = newCreateFireBase(DEFAULT_DATABASE.getType());
        return FirebaseAuth.getInstance().getUser(uid);
    }

    public User userSelect(String uid) {
        try {
            Firestore db = newCreateFireBase(DEFAULT_DATABASE.getType());
            return db.collection(USER.getTable()).document(uid).get().get().toObject(User.class);
        } catch (Exception e) {
            throw new RuntimeException("시스템 오류 : " + e.getMessage());
        }
    }

    public String nickNameChange(User user) {
        try {
            Firestore db = newCreateFireBase(DEFAULT_DATABASE.getType());
            DocumentReference docRef = db.collection(USER.getTable()).document(user.getUid());
            docRef.update("nickName", user.getNickName());
            return user.getNickName();
        } catch (Exception e) {
            throw new RuntimeException("시스템 오류 : " + e.getMessage());
        }
    }

    public int nickNameSelect(String nickName) {
        try {
            Firestore db = newCreateFireBase(DEFAULT_DATABASE.getType());
            List<QueryDocumentSnapshot> documents = db.collection(USER.getTable())
                    .whereEqualTo("nickName", nickName).get().get().getDocuments();
            return documents.size();
        } catch (Exception e) {
            throw new RuntimeException("시스템 오류 : " + e.getMessage());
        }
    }

    public String login(OAuth oAuth) {
        try {
            Firestore db = newCreateFireBase(REAL_TIME_DATABASE.getType());
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

            ref.child(oAuth.getUniqueNumber()).setValueAsync(USER.getTable());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return oAuth.getUniqueNumber();
    }
}
