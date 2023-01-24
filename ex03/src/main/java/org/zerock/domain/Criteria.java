package org.zerock.domain;

import org.springframework.web.util.UriComponentsBuilder;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter // lombok 이용 getter/setter 처리
@Setter
@ToString
public class Criteria { // 검색의 "기준"
	// Criteria 클래스를 만들어두면 하나의 타입만으로 파라미터나 리턴 타입을 사용 할 수 있기에 편리
	
	// MyBatis 페이징 처리	
	// 페이징 처리를 위해서는 SQL 실행 전 1페이지번호, 2한페이지당 몇개의 데이터 의 파라미터가 필요

/*	
 	페이징 처리의 BoardMapper는 인터페이스와 어노테이션을 이용하기 때문에 SQL 구문 처리가 복잡하다.
	때문에 XML 로 처리하여 알아보기 쉽고 관리하기 쉽게 처리
*/	
	// 페이징
	private int pageNum; // 페이지 번호
	private int amount; // 한페이지당 몇 개의 데이터
	
	// 검색
	private String type;
	private String keyword;
	
	
	public Criteria() { // 생성자를 통해서 기본값을 1페이지, 10개로 지정처리 
		this(1,10);
	}
	
	public Criteria(int pagNum, int amount) {
		this.pageNum = pagNum;
		this.amount = amount;
	}

	// 검색시 MyBatis 동적태그(if, choose, trim, forEach 등) 를 배열로 처리하여 활용하기 위한 메서드 추가
	public String[] getTypeArr() {
		return type == null? new String[] {}: type.split("");
	}
	
	// 검색조건 유지 파라미터 묶음 (modify,remove)에 대체 사용 가능
	// UriComponentsBuilder 를 이용한 링크 생성 (매번 파라미터를 유지하는 일이 번거로울 때 용이함)
	// 여러개의 파라미터들을 연결해서 URL의 형태로 만들어주는 기능
	public String getListLink() {
		UriComponentsBuilder builder = UriComponentsBuilder.fromPath("")
			.queryParam("pageNum", this.pageNum)
			.queryParam("amount",this.getAmount())
			.queryParam("type", this.getType())
			.queryParam("keyword", this.getKeyword());

		return builder.toUriString();
	}
	

}
