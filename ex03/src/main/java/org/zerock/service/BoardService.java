package org.zerock.service;

import java.util.List;

import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;

public interface BoardService {
	// 명백하게 select 를 해야되는 메서드는 리턴 타입을 지정할 수 있다.
	// 그 외 get, List는 처음부터 메서드의 리턴 타입을 결정해서 진행 할 수 있다.
	
	// 게시물등록
	public void register(BoardVO board);
	// 게시물 번호
	public BoardVO get(Long bno);
	// 게시물 수정
	public boolean modify(BoardVO board);
	// 게시물 삭제
	public boolean remove(Long bno);
	// 게시물 리스트
//	public List<BoardVO> getList();

	// 게시물 리스트(페이징)
	public List<BoardVO>getList(Criteria cri);
	
	// 전체 게시물 수
	// 굳이 Criteria 파라미터를 전달하지 않아도 되지만 목록과 전체데이터 개수를 일관성있게 하기 위해
	public int getTotal(Criteria cri);
	
}
