package com.study.spring.repository;

import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.Transaction;
import com.study.spring.components.fireBase.FireBase;
import com.study.spring.dto.MessageResponseDto;
import com.study.spring.dto.UserInfoResponseDto;
import com.study.spring.dto.WaitRequestDto;
import com.study.spring.dto.common.resultType.DataBaseTable;
import com.study.spring.exceptionHandler.CustumException.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.study.spring.dto.common.resultType.DataBaseTable.WAIT_ROOM_CARD;
import static com.study.spring.exceptionHandler.CustumException.ErrorCode.FAIL_DATABASE_FIND;
import static com.study.spring.exceptionHandler.CustumException.ErrorCode.FAIL_DATABASE_SAVE;

@Repository
@RequiredArgsConstructor
public class WaitRoomRepository {

    private final String CURRENT_CUSTOM_NUM = "currentCustomNum";
    private final FireBase fireBase;


    public MessageResponseDto cardArrangementUpdate(Map<String, List<String>> waitRoomMap, String key, String uid) {
        try {
            Firestore db = fireBase.getDB();

            DocumentReference docRef = db.collection(WAIT_ROOM_CARD.getTable()).document(uid);
            docRef.update(key, waitRoomMap.get(key));
        } catch (Exception e) {
            throw new CustomException(FAIL_DATABASE_SAVE);
        }
        return new MessageResponseDto(200, "저장되었습니다.");
    }

    public MessageResponseDto costumeArrangementUpdate(WaitRequestDto waitRequestDto) {
        Firestore db = fireBase.getDB();
        DocumentReference docRef = db.collection(WAIT_ROOM_CARD.getTable()).document(waitRequestDto.getUid());

        db.runTransaction((Transaction.Function<Void>) transaction -> {
            transaction.update(docRef, CURRENT_CUSTOM_NUM, waitRequestDto.getCurrentCustomNum());
            return null;
        });
        throw new RuntimeException();
//        return new MessageResponseDto(200, "저장되었습니다.");
    }

    public MessageResponseDto costumeArrangementUpdate2(Firestore db, Transaction transaction, WaitRequestDto waitRequestDto) {
        DocumentReference docRef = db.collection(WAIT_ROOM_CARD.getTable()).document(waitRequestDto.getUid());
        transaction.update(docRef, CURRENT_CUSTOM_NUM, waitRequestDto.getCurrentCustomNum());
        throw new RuntimeException("test");
//        return new MessageResponseDto(200, "저장되었습니다.");
    }

    public Boolean existTable(String uid) {
        try {
            Firestore db = fireBase.getDB();
            return db.collection(WAIT_ROOM_CARD.getTable())
                    .document(uid).get().get()
                    .exists();
        } catch (Exception e) {
            throw new CustomException(FAIL_DATABASE_FIND);
        }
    }

    public MessageResponseDto cardArrangementSet(Map<String, List<String>> waitRoomMap, String uid) {
        try {
            Firestore db = fireBase.getDB();
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

            Firestore db = fireBase.getDB();
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
            Firestore db = fireBase.getDB();

            UserInfoResponseDto userInfoResponseDto = db.collection(DataBaseTable.WAIT_ROOM_CARD.getTable())
                    .document(uid)
                    .get()
                    .get()
                    .toObject(UserInfoResponseDto.class);
            return Optional.ofNullable(userInfoResponseDto);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new CustomException(FAIL_DATABASE_FIND);
        }
    }
}
