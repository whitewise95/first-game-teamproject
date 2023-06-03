package com.study.spring.repository;

import com.google.cloud.firestore.Firestore;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import com.study.spring.components.fireBase.FireBase;
import com.study.spring.domain.SkillCard;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommonRepository {

    private final FireBase fireBase;

    public void insertDbData(List<SkillCard> skillCardList, String dbName, String pk) throws Exception {
        Firestore db = fireBase.getDB();
        skillCardList.forEach(skillCard ->
                db.collection(dbName).document(pk).set(skillCard)
        );
    }

    public String createJwt(String uid) throws Exception {
        Firestore db = fireBase.getDB();
        return FirebaseAuth.getInstance().createCustomToken(uid);
    }

    public String getJwt(String idToken) throws Exception {
        Firestore db = fireBase.getDB();
        FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
        return decodedToken.getUid();
    }
}
