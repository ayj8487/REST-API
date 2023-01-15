package org.zerock.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.domain.SampleVO;

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
	
	
}
