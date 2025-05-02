package com.example.demo.controller;


import com.example.demo.domain.MemoDto;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.beans.PropertyEditorSupport;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Controller
@Slf4j
@RequestMapping("/memo")
public class MemoController {
	
	
	//→ WebDataBinder를 통해 특정 필드에 대해 커스텀 바인딩 처리를 적용하는 코드asdasd
	//→ 폼에서 넘어온 문자열 데이터를 자바 객체로 바꾸는 과정을 직접 지정한 것
	@InitBinder
	public void dataBinder(WebDataBinder webDataBinder) {
		log.info("MemoController's dataBinder ..." + webDataBinder);// 커스텀 타입 변환 바인딩 확인용 로그

		webDataBinder.registerCustomEditor(LocalDate.class, "dateTest", new DateTestEditor());
		//LocalDate.class : 변환될 필드의 타입
		//"dateTest" : 바인딩 대상 필드 이름 (ex:UserDto에서 private LocalDate birthday)
		//new DateTestEditor() : 변환 로직을 수행할 사용자 정의 변환기
	}
		
	@GetMapping("/add")
	public void add_get() {
		log.info("GET /memo/add...");
	}
	@PostMapping("/add")
	public void add_post(@Valid MemoDto dto, BindingResult bindingResult, Model model) {
		log.info("POST /memo/add..." + dto);
		
		
	    // BindingResult: @Valid로 실행된 유효성 검사 결과를 담는 객체
	    // dto에서 지정한 검증 애노테이션(@Min 등)의 유효성 결과가 여기에 저장됨

	    // 유효성 검사 실패 시 true 반환 → 에러 처리
		if(bindingResult.hasErrors()) {
			// 예: id 값이 10 미만이면 에러 발생, 해당 에러 메시지를 출력
//			log.info("유효성 에러발생 : " + bindingResult.getFieldError("id").getDefaultMessage());
			
			for(FieldError error : bindingResult.getFieldErrors()) {
				log.info("Error Field : " + error.getField()+" Error Msg : " + error.getDefaultMessage());
				model.addAttribute(error.getField(),error.getDefaultMessage());
			}
			
		}
	}
	
	// static private
	private static class DateTestEditor extends PropertyEditorSupport {

		@Override
		public void setAsText(String dateTest) throws IllegalArgumentException {
			log.info("DateTestEditor's setAsText invoke.." + dateTest);
			LocalDate date = null;
			if(dateTest.isEmpty()) {
				//값 비었으면 지금날짜 저장
				date = LocalDate.now();
			}else {
				//yyyy#MM#dd -> yyyy-MM-dd(LocalDate format)
				dateTest = dateTest.replaceAll("#", "-");
				date = LocalDate.parse(dateTest,DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			}
			
			setValue(date);
		}
		

	}


}
