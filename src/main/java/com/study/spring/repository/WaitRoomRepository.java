package com.study.spring.repository;

import com.google.cloud.firestore.*;
import com.study.spring.components.fireBase.FireBase;
import com.study.spring.domain.CardCoordinate;
import com.study.spring.dto.*;
import com.study.spring.exceptionHandler.CustumException.CustomException;
import org.springframework.stereotype.Repository;

import java.util.*;

import static com.study.spring.domain.resultType.DataBaseType.DEFAULT_DATABASE;
import static com.study.spring.dto.common.resultType.DataBaseTable.WAIT_ROOM_CARD;
import static com.study.spring.exceptionHandler.CustumException.ErrorCode.FAIL_DATABASE_SAVE;

@Repository
public class WaitRoomRepository {

    private final String CURRENT_CUSTOM_NUM = "currentCustomNum";
    private final FireBase fireBase;

    public WaitRoomRepository(FireBase fireBase) {
        this.fireBase = fireBase;
    }

    public Firestore newCreateFireBase(String dataBaseUrl) throws Exception {
        fireBase.dbInit(dataBaseUrl);
        return fireBase.makeDatabaseConn();
    }

    public MessageResponseDto cardArrangement(Map<String, List<CardCoordinate>> waitRoomMap, String key, String uid) {
        try {
            Firestore db = newCreateFireBase(DEFAULT_DATABASE.getType());
            DocumentReference docRef = db.collection(WAIT_ROOM_CARD.getTable()).document(uid);
            docRef.update(key, waitRoomMap.get(key));
        } catch (Exception e) {
            throw new CustomException(FAIL_DATABASE_SAVE);
        }
        return new MessageResponseDto(200, "저장되었습니다.");
    }

    public MessageResponseDto costumeArrangement(WaitRequestDto waitRequestDto) {
        try {
            Firestore db = newCreateFireBase(DEFAULT_DATABASE.getType());
            DocumentReference docRef = db.collection(WAIT_ROOM_CARD.getTable()).document(waitRequestDto.getUid());
            docRef.update(CURRENT_CUSTOM_NUM, waitRequestDto.getCurrentCustomNum());
        } catch (Exception e) {
            throw new CustomException(FAIL_DATABASE_SAVE);
        }
        return new MessageResponseDto(200, "저장되었습니다.");
    }
}
