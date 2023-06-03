package com.study.spring.repository;

import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.SetOptions;
import com.study.spring.components.fireBase.FireBase;
import com.study.spring.dto.common.resultType.DataBaseTable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
@RequiredArgsConstructor
public class StatisticRepository {

    private final FireBase fireBase;


    public void defaultChange(Map<String, Object> updateUserInfo) {
        try {
            Firestore db = fireBase.getDB();
            db.collection(DataBaseTable.USER.getTable())
                    .document(updateUserInfo.get("uid").toString())
                    .set(updateUserInfo, SetOptions.merge());
        } catch (Exception e) {
            throw new RuntimeException(String.format("%s 를 찾을 수 없습니다.", updateUserInfo.get("uid").toString()));
        }
    }
}
