package com.study.spring.service;

import static com.study.spring.exceptionHandler.CustumException.ErrorCode.NOT_FIND_USER;

import com.study.spring.dto.MessageResponseDto;
import com.study.spring.dto.WaitRequestDto;
import com.study.spring.exceptionHandler.CustumException.CustomException;
import com.study.spring.repository.MemberRepository;
import com.study.spring.repository.WaitRoomRepository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class WaitRoomService {

	private final String LIST = "List";
	private final WaitRoomRepository waitRoomRepository;
	private final MemberRepository memberRepository;

	public WaitRoomService(WaitRoomRepository waitRoomRepository, MemberRepository memberRepository) {
		this.waitRoomRepository = waitRoomRepository;
		this.memberRepository = memberRepository;
	}

	public MessageResponseDto cardArrangement(WaitRequestDto waitRequestDto, String cardType) {
		Map<String, List<String>> waitRoomMap = new HashMap<>();
		String key = cardType.toLowerCase() + LIST;
		waitRoomMap.put(key, waitRequestDto.getCardList());

		if (!isExistUserWithRestRoomTableExist(waitRequestDto.getUid())) {
			return waitRoomRepository.cardArrangementSet(
				waitRoomMap,
				waitRequestDto.getUid()
			);
		}
		return waitRoomRepository.cardArrangementUpdate(
			waitRoomMap,
			key,
			waitRequestDto.getUid()
		);
	}

	public MessageResponseDto costumeArrangement(WaitRequestDto waitRequestDto) {
		if (!isExistUserWithRestRoomTableExist(waitRequestDto.getUid())) {
			return waitRoomRepository.costumeArrangementSet(waitRequestDto);
		}
		return waitRoomRepository.costumeArrangementUpdate(waitRequestDto);
	}

	private boolean isExistTable(String uid) {
		return waitRoomRepository.existTable(uid);
	}

	private boolean isExistUserWithRestRoomTableExist(String uid) {
		if (memberRepository.existUid(uid)) {
			return isExistTable(uid);
		} else {
			throw new CustomException(NOT_FIND_USER);
		}
	}

}
