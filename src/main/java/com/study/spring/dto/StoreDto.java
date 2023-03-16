package com.study.spring.dto;

import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

public class StoreDto {

	/**
	 * 상점에서 구매이후 멤버에게 추가
	 * */
	@Getter
	@Setter
	public static class Update {

		/**
		 * UID
		 * */
		private String uid;

		/**
		 * 아이템 번호
		 * */
		@NotNull
		private Integer itemNum;

		/**
		 * 가격
		 * */
		@NotNull
		private Integer gold;
	}
}
