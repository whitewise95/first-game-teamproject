package com.study.spring.repository;

import com.google.cloud.firestore.*;
import com.study.spring.components.fireBase.FireBase;
import com.study.spring.dto.common.resultType.DataBaseTable;
import org.springframework.stereotype.Repository;

import java.util.Map;

import static com.study.spring.domain.resultType.DataBaseType.DEFAULT_DATABASE;

@Repository
public class StatisticRepository {

    private final FireBase fireBase;

    public StatisticRepository(FireBase fireBase) {
        this.fireBase = fireBase;
    }

    public Firestore newCreateFireBase() throws Exception {
        fireBase.dbInit(DEFAULT_DATABASE);
        return fireBase.makeDatabaseConn();
    }

    public void defaultChange(Map<String, Object> updateUserInfo) {
        try {
            Firestore db = newCreateFireBase();
            db.collection(DataBaseTable.USER.getTable())
                    .document(updateUserInfo.get("uid").toString())
                    .set(updateUserInfo, SetOptions.merge());
        } catch (Exception e) {
            throw new RuntimeException(String.format("%s 를 찾을 수 없습니다.", updateUserInfo.get("uid").toString()));
        }
    }
}
