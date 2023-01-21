package org.zerock.domain;

import lombok.Data;

@Data
public class Ticket {
//	@RequestBody 예제를 위한 클래스 
	// @RequestBody 는 전달된 요청의 내용 으로 해당파라미터의 타입으로 반환,
	// 또는 다양한 포맷의 입력 데이터를 반환 할 수 있다.
	// 대부분 JSON 데이터를 서버에 보내서 원하는 탕비의 객체로 변환하는 용도로 사용
	
	private int bno; // 번호
	private String owner;  //소유주 
	private String grade; // 등급
	
}
