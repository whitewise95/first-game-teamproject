package com.study.spring.service;

import com.study.spring.components.Components;
import com.study.spring.controller.IndexController;
import com.study.spring.domain.User;
import com.study.spring.dto.MessageResponseDto;
import com.study.spring.dto.OAuth;
import com.study.spring.dto.StoreDto;
import com.study.spring.dto.UserInfoResponseDto;
import com.study.spring.dto.WaitRequestDto;
import com.study.spring.repository.MemberRepository;
import com.study.spring.repository.WaitRoomRepository;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Optional;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

	private static final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
	private static final String GUEST = "guest";

	private final Components components;
	private final MemberRepository memberRepository;
	private final WaitRoomRepository waitRoomRepository;
	private final CommonService commonService;

	public MemberService(Components components,
						 MemberRepository memberRepository,
						 WaitRoomRepository waitRoomRepository, CommonService commonService) {
		this.components = components;
		this.memberRepository = memberRepository;
		this.waitRoomRepository = waitRoomRepository;
		this.commonService = commonService;
	}

	public void socialInsert(IndexController.RequestUserOauth requestUserOauth) {
		OAuth oAuth = OAuth.builder()
						   .uniqueNumber(requestUserOauth.getUniqueNumber())
						   .uid(requestUserOauth.getUid())
						   .build();

		User user = User.builder()
						.uid(requestUserOauth.getUid())
						.nickName(null)
						.email(requestUserOauth.getEmail())
						.build();

		memberRepository.socialInsert(oAuth);

		if (!Optional.ofNullable(memberRepository.userSelect(user.getUid())).isPresent()) {
			memberRepository.userInsert(user);
			WaitRequestDto waitRequestDto = new WaitRequestDto();
			waitRequestDto.setCurrentCustomNum(1);
			waitRequestDto.setUid(user.getUid());
			waitRoomRepository.costumeArrangementSet(waitRequestDto);
		}
	}

	public String nickNameChange(User user) {
		User selectUser = Optional.ofNullable(memberRepository.userSelect(user.getUid()))
								  .orElseThrow(() -> new RuntimeException(String.format("%s 에 대한 정보를 찾지못했습니다.", user.getUid())));
		if (StringUtils.equals(selectUser.getNickName(), user.getNickName())) {
			throw new RuntimeException("변경된 사항이 없습니다.");
		}
		if (memberRepository.nickNameSelect(user.getNickName()) > 0) {
			throw new RuntimeException(String.format("%s 는 이미 사용중입니다.", user.getNickName()));
		}
		return memberRepository.nickNameChange(user);
	}

	public UserInfoResponseDto userinfo(User user) {
		User currentUser = Optional.ofNullable(memberRepository.userSelect(user.getUid()))
								   .orElseThrow(() -> new RuntimeException(String.format("%s 에 대한 정보를 찾지못했습니다.", user.getUid())));

		UserInfoResponseDto userInfoResponseDto = waitRoomRepository.findByUid(currentUser.getUid())
																	.orElse(new UserInfoResponseDto());

		userInfoResponseDto.setUser(currentUser);
		return userInfoResponseDto;
	}

	public User guestSelect(User user) {
		if (!Optional.ofNullable(memberRepository.guestSelect(user.getUid())).isPresent()) {
			throw new RuntimeException(String.format("%s에 대한 정보를 찾지못했습니다.", user.getUid()));
		}

		User selectUser = memberRepository.userSelect(user.getUid());

		if (Optional.ofNullable(selectUser).isPresent()) {
			return selectUser;
		} else {
			memberRepository.userInsert(user);
			return memberRepository.userSelect(user.getUid());
		}
	}

	public String login(OAuth oAuth) {
		return memberRepository.login(oAuth);
	}

	/**
	 * 상점에서 구매
	 */
	public MessageResponseDto updateMemberItem(StoreDto.Update update) {
		User user = memberRepository.userSelect(update.getUid());
		if (user == null) {
			throw new RuntimeException(String.format("%s에 대한 정보를 찾지못했습니다.", user.getUid()));
		}

		Integer gold = user.getGold() - update.getGold();
		if (gold < 0) {
			throw new RuntimeException("골드가 부족합니다.");
		}

		return new MessageResponseDto(200, memberRepository.updateMemberItem(user, update, gold).toString());
	}
}
