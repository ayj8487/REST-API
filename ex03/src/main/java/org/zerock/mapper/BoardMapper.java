package org.zerock.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;

public interface BoardMapper {
	// 간단한 SQL쿼리라면 @어노테이션으로 처리할 수 있지만
	// 복잡한 쿼리나 상황에 따라다른 SQL 쿼리를 사용하려면 MyBatis를 활용해 XML로 대체 한다 

	//	@Select("select * from tbl_board where bno > 0")
	
	// 게시글 목록
	public List<BoardVO> getList();
	
	// 게시글 목록 (페이징)
	// domain에 작성한 Character타입을 파라미터로 사용하는 메서드 생성
	public List<BoardVO> getListWithPaging(Criteria cri);

	// insert만 처리되고 생성된PK (bno) 값을 알 필요가 없는경우
	public void insert(BoardVO board);

	// insert문이 실행되고 생성된PK 값을 알야야 하는 경우
	public void insertSelectKey(BoardVO board);
	
	// 게시글 조회
	public BoardVO read(Long bno);

	// 게시글 삭제
	public int delete(Long bno);
	
	// 게시글 수정
	public int update(BoardVO board);
	
	// 전체 게시물의 수 (검색 기능 구현을 위해 Criteria 파라미터 전달)
	public int getTotalCount(Criteria cri);
}
