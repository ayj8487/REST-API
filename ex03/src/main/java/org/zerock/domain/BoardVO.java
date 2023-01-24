package org.zerock.domain;

import java.util.Date;

import lombok.Data;

@Data // Lombok 을 이용한 getter/setter 자동생성
public class BoardVO {

	private Long bno;
	private String title;
	private String content;
	private String writer;
	private Date regdate;
	private Date updateDate;
}
