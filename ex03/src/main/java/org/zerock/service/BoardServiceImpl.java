package org.zerock.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;
import org.zerock.mapper.BoardMapper;

import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
@Service // 비즈니스 영역을 담당하는 객체임을 표시
@AllArgsConstructor // 모든 파라미터를 이용하는 생성자를 만듦, BoardMapper를 주입받는 생성자가 만들어짐
public class BoardServiceImpl implements BoardService{
	
	@Setter(onMethod_ = @Autowired)
	private BoardMapper mapper; // Impl 클래스가 정상적으로 작동되기위해 BoarMapper 객체가 필요
	
	// 게시물 등록
	// BoardServiceImpl 에서 파라미터로 전달되는 BoardVO 타입의객체를 BoardMapper를 통해서 처리
	@Override
	public void register(BoardVO board) {
		log.info("regist ------" + board);
		mapper.insertSelectKey(board);
	}
	// 게시물 조회
	@Override
	public BoardVO get(Long bno) {
		log.info("get ---------" + bno);
		return mapper.read(bno);
	}
	// 게시물 수정
	// return 값을 void로 설계 할수도 있지만 조금더 엄격하게 하기 위해
	// boolean 타입으로 정상적으로 수정과 삭제가 이루어지면 1이라는 값을 반환하여 == 연산자를 이용해 참/거짓 으로 처리
	@Override
	public boolean modify(BoardVO board) {
		log.info("modify --------" + board);
		return mapper.update(board) == 1;  
	}
	// 게시물 삭제
	@Override
	public boolean remove(Long bno) {
		log.info("remove---------" + bno);
		return mapper.delete(bno) == 1;
	}
	// 게시물 리스트
//	@Override
//	public List<BoardVO> getList() {
//		log.info("get List ------");
//		return mapper.getList();
//	}
	
	// 게시물 리스트 (페이징)
	@Override
	public List<BoardVO> getList(Criteria cri) {
		log.info("get List with criteria : "+ cri);
		return mapper.getListWithPaging(cri);
	}
	// 전체 게시물 수
	@Override
	public int getTotal(Criteria cri) {
		log.info("get total count");
		return mapper.getTotalCount(cri);
	}

}
