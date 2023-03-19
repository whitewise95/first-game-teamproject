package com.study.spring.repository;

import com.google.cloud.firestore.*;
import com.google.firebase.FirebaseApp;
import com.study.spring.components.fireBase.FireBase;
import com.study.spring.domain.CardCoordinate;
import com.study.spring.domain.resultType.DataBaseType;
import com.study.spring.dto.*;
import com.study.spring.dto.common.resultType.DataBaseTable;
import com.study.spring.exceptionHandler.CustumException.*;
import org.springframework.stereotype.Repository;

import java.util.*;

import static com.study.spring.domain.resultType.DataBaseType.DEFAULT_DATABASE;
import static com.study.spring.dto.common.resultType.DataBaseTable.WAIT_ROOM_CARD;
import static com.study.spring.exceptionHandler.CustumException.ErrorCode.*;

@Repository
public class WaitRoomRepository {

    private final String CURRENT_CUSTOM_NUM = "currentCustomNum";
    private final FireBase fireBase;

    public WaitRoomRepository(FireBase fireBase) {
        this.fireBase = fireBase;
    }

    public Firestore newCreateFireBase(DataBaseType dataBaseUrl) throws Exception {
        fireBase.dbInit(dataBaseUrl);
        return fireBase.makeDatabaseConn();
    }

    public MessageResponseDto cardArrangementUpdate(Map<String, List<String>> waitRoomMap, String key, String uid) {
        try {
            Firestore db = newCreateFireBase(DEFAULT_DATABASE);

            DocumentReference docRef = db.collection(WAIT_ROOM_CARD.getTable()).document(uid);
            docRef.update(key, waitRoomMap.get(key));
        } catch (Exception e) {
            throw new CustomException(FAIL_DATABASE_SAVE);
        }
        return new MessageResponseDto(200, "저장되었습니다.");
    }

    public MessageResponseDto costumeArrangementUpdate(WaitRequestDto waitRequestDto) {
        try {
            Firestore db = newCreateFireBase(DEFAULT_DATABASE);

            DocumentReference docRef = db.collection(WAIT_ROOM_CARD.getTable())
                    .document(waitRequestDto.getUid());
            docRef.update(CURRENT_CUSTOM_NUM, waitRequestDto.getCurrentCustomNum());
        } catch (Exception e) {
            throw new CustomException(FAIL_DATABASE_SAVE);
        }
        return new MessageResponseDto(200, "저장되었습니다.");
    }

    public Boolean existTable(String uid) {
        try {
            Firestore db = newCreateFireBase(DEFAULT_DATABASE);
            return db.collection(WAIT_ROOM_CARD.getTable())
                    .document(uid).get().get()
                    .exists();
        } catch (Exception e) {
            throw new CustomException(FAIL_DATABASE_FIND);
        }
    }

    public MessageResponseDto cardArrangementSet(Map<String, List<String>> waitRoomMap, String uid) {
        try {
            Firestore db = newCreateFireBase(DEFAULT_DATABASE);
            db.collection(WAIT_ROOM_CARD.getTable())
                    .document(uid)
                    .set(waitRoomMap);
        } catch (Exception e) {
            throw new CustomException(FAIL_DATABASE_SAVE);
        }
        return new MessageResponseDto(200, "저장되었습니다.");
    }

    public MessageResponseDto costumeArrangementSet(WaitRequestDto waitRequestDto) {
        try {
            Map<String, Integer> waitRoomMap = new HashMap<>();
            waitRoomMap.put(CURRENT_CUSTOM_NUM, waitRequestDto.getCurrentCustomNum());

            Firestore db = newCreateFireBase(DEFAULT_DATABASE);
            db.collection(WAIT_ROOM_CARD.getTable())
                    .document(waitRequestDto.getUid())
                    .set(waitRoomMap);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException(FAIL_DATABASE_SAVE);
        }
        return new MessageResponseDto(200, "저장되었습니다.");
    }

    public Optional<UserInfoResponseDto> findByUid(String uid) {
        try {
            Firestore db = newCreateFireBase(DEFAULT_DATABASE);

            UserInfoResponseDto userInfoResponseDto= db.collection(DataBaseTable.WAIT_ROOM_CARD.getTable())
                    .document(uid)
                    .get()
                    .get()
                    .toObject(UserInfoResponseDto.class);
            return Optional.ofNullable(userInfoResponseDto);
        }catch (Exception e) {
            System.out.println(e.getMessage());
            throw new CustomException(FAIL_DATABASE_FIND);
        }
    }
}
