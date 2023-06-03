package com.study.spring.repository;

import com.google.cloud.Timestamp;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.FieldValue;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.study.spring.components.fireBase.FireBase;
import com.study.spring.domain.User;
import com.study.spring.dto.OAuth;
import com.study.spring.dto.StoreDto;
import com.study.spring.dto.common.resultType.DataBaseTable;
import com.study.spring.exceptionHandler.CustumException.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.study.spring.domain.resultType.DataBaseType.REAL_TIME_DATABASE;
import static com.study.spring.dto.common.resultType.DataBaseTable.USER;
import static com.study.spring.exceptionHandler.CustumException.ErrorCode.FAIL_DATABASE_FIND;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final FireBase fireBase;

    public UserRecord guestSelect(String uid) {
        try {
            return FirebaseAuth.getInstance().getUser(uid);
        } catch (Exception e) {
            return null;
        }
    }

    public Boolean existUid(String uid) {
        try {
            Firestore db = fireBase.getDB();
            return db.collection(USER.getTable()).document(uid).get().get().exists();
        } catch (Exception e) {
            throw new CustomException(FAIL_DATABASE_FIND);
        }
    }

    public OAuth socialSelect(OAuth oAuth) throws Exception {
        Firestore db = fireBase.getDB();
        OAuth responseOAuth = db.collection(DataBaseTable.SOCIAL.getTable()).document(oAuth.getUniqueNumber()).get().get().toObject(OAuth.class);
        return responseOAuth;
    }

    public void socialInsert(OAuth oAuth) {
        try {
            DatabaseReference ref = FirebaseDatabase.getInstance(REAL_TIME_DATABASE.getType()).getReference();
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
            user.getCostumeList().add(new User.Costume(1));
            user.setLevel(1);
            Firestore db = fireBase.getDB();
            db.collection(USER.getTable()).document(user.getUid()).set(user);
        } catch (Exception e) {
            throw new RuntimeException("MemberMapper.userInsert 시스템오류 : " + e.getMessage());
        }
    }

    public UserRecord findUserToUid(String uid) throws Exception {
        return FirebaseAuth.getInstance().getUser(uid);
    }

    public User userSelect(String uid) {
        try {
            Firestore db = fireBase.getDB();
            User user = db.collection(USER.getTable()).document(uid).get().get().toObject(User.class);
            return user;
        } catch (Exception e) {
            throw new RuntimeException("시스템 오류 : " + e.getMessage());
        }
    }

    public String nickNameChange(User user) {
        try {
            Firestore db = fireBase.getDB();
            DocumentReference docRef = db.collection(USER.getTable()).document(user.getUid());
            docRef.update("nickName", user.getNickName());
            return user.getNickName();
        } catch (Exception e) {
            throw new RuntimeException("시스템 오류 : " + e.getMessage());
        }
    }

    public int nickNameSelect(String nickName) {
        try {
            Firestore db = fireBase.getDB();
            List<QueryDocumentSnapshot> documents = db.collection(USER.getTable())
                    .whereEqualTo("nickName", nickName).get().get().getDocuments();
            return documents.size();
        } catch (Exception e) {
            throw new RuntimeException("시스템 오류 : " + e.getMessage());
        }
    }

    public String login(OAuth oAuth) {
        try {
            DatabaseReference ref = FirebaseDatabase.getInstance(REAL_TIME_DATABASE.getType()).getReference();
            ref.child(oAuth.getUniqueNumber()).setValueAsync(USER.getTable());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return oAuth.getUniqueNumber();
    }

    public Timestamp updateMemberItem(User user, StoreDto.Update update, Integer gold) {
        try {
            Firestore db = fireBase.getDB();
            return db.collection(USER.getTable())
                    .document(user.getUid())
                    .update("costumeList", FieldValue.arrayUnion(new User.Costume(update.getItemNum())), "gold", gold)
                    .get()
                    .getUpdateTime();

        } catch (Exception e) {
            throw new RuntimeException("시스템 오류 : " + e.getMessage());
        }
    }
}
