package com.study.spring.service;

import com.study.spring.domain.CardCoordinate;
import com.study.spring.dto.*;
import com.study.spring.repository.WaitRoomRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class WaitRoomService {

    private final String LIST = "List";
    private final WaitRoomRepository waitRoomRepository;

    public WaitRoomService(WaitRoomRepository waitRoomRepository) {
        this.waitRoomRepository = waitRoomRepository;
    }

    public MessageResponseDto cardArrangement(WaitRequestDto waitRequestDto, String cardType) {
        Map<String, List<CardCoordinate>> waitRoomMap = new HashMap<>();
        String key = cardType + LIST;

        waitRoomMap.put(key, DtoListToEntityList(waitRequestDto));
        return waitRoomRepository.cardArrangement(
                waitRoomMap,
                key,
                waitRequestDto.getUid()
        );
    }

    public MessageResponseDto costumeArrangement(WaitRequestDto waitRequestDto) {
        return waitRoomRepository.costumeArrangement(waitRequestDto);
    }

    public List<CardCoordinate> DtoListToEntityList(WaitRequestDto waitRequestDtos) {
        List<CardCoordinate> cardCoordinates = new ArrayList<>();

        for (WaitRequestDto.CardDto cardDto : waitRequestDtos.getCardList()) {
            cardCoordinates.add(new CardCoordinate(cardDto));
        }
        return cardCoordinates;
    }

}
