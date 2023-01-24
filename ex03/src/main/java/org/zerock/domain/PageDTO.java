package org.zerock.domain;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class PageDTO {
			//  pageNum 현재 페이지 번호 	
			//  amount 현재 페이지 번호
	private int startPage; // 페이지 첫 번호
	private int endPage; // 페이지 끝 번호
	private boolean prev, next; // 이전, 다음 (이전과 다음으로 이동 가능한 링크의 여부)
	
	private int total; // 전체 데이터 수
	private Criteria cri; // 페이지번호, 한페이지당 몇개의 데이터 VO
	
	// PageDTO 는 생성자를 정의하고, Criteria와 전체 데이터 수(total)을 파라미터로 지정
	public PageDTO(Criteria cri, int total) {
		 this.cri = cri;
		 this.total = total;
		 
	 // ** 페이징 끝 번호 (페이지 번호가 10개씩 보인다고 가정)
		 // 끝 번호 먼저 계산시 첫 번호를 알기 수월함
		 this.endPage = (int)(Math.ceil(cri.getPageNum() / 10.0)) * 10;
		 
	 // ** 페이징 첫 번호
		 // 10개씩 보여준다면 시작번호(startPage) 는 무조건 끝 번호(endPage)에서 9라는 값을 뺀 값이 됨
		 this.startPage = this.endPage - 9;
		 
	 // ** total을 통한 재 계산
		 // 만일 끝 번호(andPage)와 한 페이지당 출력되는 데이터 수(amount)의 곱이 전체 데이터 수 (total) 보다 크다면 endPage는 다시 total을 통해서 계산
		 // 만약 진짜 끝 페이지(realEnd)가 구해둔 끝 번호(endPage)보다 작다면 끝 번호는 작은 값이 되어야함
		 int realEnd = (int) (Math.ceil((total * 1.0) / cri.getAmount()));

		 if(realEnd < this.endPage) {
			 this.endPage = realEnd;
		 }
	
	 // ** 이전 계산
		 // 시작 번호(startPage)가 1 보다 큰 경우라면 존재함
		 this.prev = this.startPage > 1;
	 // ** 다음 계산
		 //	realEnd가 끝 번호(endPage)보다 큰 경우에만 존재
		 this.next = this.endPage < realEnd;
	
	}
}
