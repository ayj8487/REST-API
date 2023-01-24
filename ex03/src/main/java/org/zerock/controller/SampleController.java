package org.zerock.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.domain.SampleVO;
import org.zerock.domain.Ticket;

import lombok.extern.log4j.Log4j;


@RestController // RestController 의 경우 순수한 데이터 형태이므로 다양한 포맷데이터 전송가능, 주로JSON, XML
@RequestMapping("/sample")
@Log4j
public class SampleController {

/**
	REST는 하나의 URI는 하나의 고유한 리소스를 대표하도록 설계된다는 개념에 전송방식을 결합해 원하는 작업을 지정
	REST 방식에서 가장 기억해야 하는 점은 서버에서 전송하는 것이 순수한 데이터 이다.
 */
	
/*
@RestController : Controller 가 REST 방식을 처리하기 위한 것임을 명시
@ResponseBody : 일반적인 JSP와 같은 뷰 로 전달되는게 아니라 데이터 자체를 전달하기위한 용도
@PathVariable : URL 경로에 있는 값을 파라미터로 추출 할 때 사용
@CrossOrigin : Ajax의 크로스 도메인 문제를 해결해주는 어노테이션
@RequestBody : JSON 데이터를 원하는 타입으로 바인딩 처리
 */	
	// 1 - 1 @RestContoller 의 반환타입
	// 1. 문자열 반환
	// 기존의 @Controller는 문자열 반환시 JSP파일의 이름으로 처리하지만 @Restcontroller는 순수한 데이터 
	@GetMapping(value = "/getText", produces = "text/plain; charset=UTF-8")
	// produces 속성은 해당 메서드가 MIME 타입을 의미, 문자열로 직접지정 할 수 있다
	// http://localhost:8080/sample/getText 브라우저 호출시 "안녕하세요"데이터 확인 가능
	public String getText() {
		
		log.info("MIME TYPE : "+ MediaType.TEXT_PLAIN_VALUE);
		
		return "안녕하세요";
	}
	
	// 2. 객체의 반환 (SampleVO : mno,firstName,lastName)
	// SampleVO를 반환(리턴)하는 메서드
	// http://localhost:8080/sample/getSample 호출시 XML 
	// http://localhost:8080/sample/getSample.json 호출시 json 
	@GetMapping(value = "/getSample", produces = { 
			MediaType.APPLICATION_JSON_UTF8_VALUE, 
			MediaType.APPLICATION_XML_VALUE })
	public SampleVO getSample() {
		
		return new SampleVO(123, "안", "영준");
	}
	// produces 속성은 생략도 가능 , 생략해도 위와같은 결과 값 호출 가능
	@GetMapping(value = "/getSample2")
	public SampleVO getSample2() {
		
		return new SampleVO(321, "영준", "안");
	}
	
	// 3. 컬렉션 타입(리스트,배열) 의 객체 반환
	// http://localhost:8080/sample/getList
	// 1 부터 10 미만까지 루프를 처리하면서 SampleVo 객체를 만들어 List<SampleVO>로 만듦
	@GetMapping(value = "/getList")
	public List<SampleVO> getList(){
		return IntStream.range(1, 10)
				.mapToObj(i -> new SampleVO(i, i + "First", i + " Last"))
				.collect(Collectors.toList());
	}
	// 맵의 경우 '키' 와 '값' 을 가지는하나의 객체로 간주
	// 맵의 경우 '키' 에 속하는 데이터는 XML 로 변환되는 경우에 태그의 이름이 되기 때문에 문자열 지정
	// http://localhost:8080/sample/getMap
	@GetMapping(value = "/getMap")
	public Map<String, SampleVO> getMap(){
		Map<String, SampleVO> map = new HashMap<>();
		map.put("First", new SampleVO(123, "안영준", "안녕안녕"));
		
		return map;
	}
	// 4. ResponseEntity 타입 
	// REST 방식으로 호출하는경우 데이터 자체를 전송하기 때문에 데이터와 함께 상태메시지 등을 함께전달
	// HTTP 의 상태 코드와 에러 등 전달 받을 수 있음
	/** 
	 check 는 height/weight 파라미터를 전달받음, 이때 만약 height 값이 150 보다 적다면 502(BAD_GATEWAY)
		그렇지않다면 200(ok) 코드와 데이터를 전송함 
	http://localhost:8080/sample/check.json?height=140&weight=60	
	(개발자 모드로 새로고침시 502	나 200이 전달되는것을 볼 수 있음)
	*/
	@GetMapping(value = "/check", params = {"height", "weight"})
	public ResponseEntity<SampleVO> check(Double height, Double weight){
		SampleVO vo = new SampleVO(0, ""+ height, "" + weight);

		ResponseEntity<SampleVO> result = null;
		
		if (height < 150) {
			result = ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(vo);
		}else {
			result = ResponseEntity.status(HttpStatus.OK).body(vo);
		}
		return result;
	}

	// 2 - 1 @RestController에서의 파라미터
	// 1. @PathVariable 
	// URL 상에 경로의 일부를 파라미터로 사용 {} 로 처리된 부븐은 컨트롤러에서 메서드의 변수로 처리 가능
	// @PathVariable 은 {} 의 이름을 처리할 때 사용
	// @PathVariable을 적용하고 싶은 경우 {} 을 이용해 변수명을 지정하고 지정된 이름의 변수 값을 얻을 수 있음, 
	// 		값을 얻을때는 int나 double같은 기본자료형 사용 불가
	// http://localhost:8080/sample/product/String(문자열)/int(숫자) 호출시 변수의 값으로 처리되는것을 확인
	@GetMapping(" /product/{cat}/{pid}")
	public String[] getPath(
			@PathVariable("cat") String cat,
			@PathVariable("pid") Integer pid) {
		return new String[] {"category: " + cat, "productid "+ pid};
	}
	
	// 2. @RequestBody 
	// @RequestBody 는 전달된 요청의 내용 으로 해당파라미터의 타입으로 반환,
	// 또는 다양한 포맷의 입력 데이터를 반환 할 수 있다.
	// 대부분 JSON 데이터를 서버에 보내서 원하는 탕비의 객체로 변환하는 용도로 사용
	// RequestBody 말 그대로 요청한 내용 을 처리하기 때문에 @PostMapping 만을 사용
	@PostMapping("/ticket")
	public Ticket convert(@RequestBody Ticket ticket) {
		log.info("convert..... ticket "+ticket);
		
		return ticket;
	}
}
