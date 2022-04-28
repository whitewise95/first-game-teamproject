package com.study.spring.mapper.common;

import com.google.cloud.firestore.Firestore;
import com.google.firebase.auth.*;
import com.study.spring.components.fireBase.FireBase;
import com.study.spring.domain.card.SkillCard;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CommonMapper {

    private FireBase fireBase;

    public CommonMapper(FireBase fireBase) {
        this.fireBase = fireBase;
    }

    public Firestore newCreateFireBase() throws Exception {
        fireBase.dbInit();
        return fireBase.makeDatabaseConn();
    }

    public void insertDbData(List<SkillCard> skillCardList, String dbName, String pk) throws Exception {
        Firestore db = newCreateFireBase();
        skillCardList.forEach(skillCard ->
                db.collection(dbName).document(skillCard.getCardName()).set(skillCard)
        );
    }

    public void insertDbData2(List<SkillCard> skillCardList, String dbName, String pk) throws Exception {
        Firestore db = newCreateFireBase();
        skillCardList.forEach(skillCard ->
                db.collection("skillLIst").document(pk).collection("cardList").document(skillCard.getCardName()).set(skillCard)
        );
    }
    public void insertDbData3(List<SkillCard> skillCardList, String dbName, String pk) throws Exception {
        Firestore db = newCreateFireBase();
        skillCardList.forEach(skillCard ->
                db.collection(pk).document(skillCard.getCardName()).set(skillCard)
        );
    }

    public String createJwt(String uid) throws Exception {
        Firestore db = newCreateFireBase();
        return FirebaseAuth.getInstance().createCustomToken(uid);
    }

    public String getJwt(String idToken) throws Exception {
        Firestore db = newCreateFireBase();
        FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
        return decodedToken.getUid();
    }
}
