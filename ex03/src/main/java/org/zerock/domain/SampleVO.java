package org.zerock.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor // 모든 속성을 사용하는 생성자 만들기
@NoArgsConstructor // 비어있는 생성자 만들기
public class SampleVO {
	// 객체의 반환 : 전달된 객체를 생산하기 위해서 작성

	private Integer mno;
	private String firstName;
	private String lastNmae;
}
