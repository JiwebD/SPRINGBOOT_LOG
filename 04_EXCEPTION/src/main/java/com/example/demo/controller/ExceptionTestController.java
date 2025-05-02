package com.example.demo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.FileNotFoundException;

@Controller
@Slf4j
@RequestMapping("/except")
public class ExceptionTestController {

	// FileNotFoundException 발생 시 실행될 예외 처리 메서드
	// 이 컨트롤러 내부에서 FileNotFoundException이 발생하면 이 메서드가 호출됨
	// 리플렉션을 통해 예외 타입이 자동 매핑됨
	@ExceptionHandler(FileNotFoundException.class)
	public String fileNotFoundExceptionHandler(Exception e, Model model) {
		log.error("error : " + e); // 로그 출력
		return "except/error";    // 예외 페이지로 이동
	}

	// ArithmeticException 처리용 핸들러 
	// 모든 예외를 하나로 처리하려면 아래의 Exception.class 핸들러 사용 가능
	@ExceptionHandler(ArithmeticException.class)
	public String arithmeticExceptionHandler(Exception e, Model model) {
		log.error("error : " + e);
		return "except/error";
	}

	// 모든 예외를 처리할 수 있는 최상위 핸들러
//	@ExceptionHandler(Exception.class)
//	public String arithmeticExceptionHandler(Exception e, Model model) {
//		log.error("error : " + e);
//		return "except/error";
//	}
	
	@GetMapping("/ex")
	public void ex1_1() throws FileNotFoundException {
		log.info("GET -");
		throw new FileNotFoundException("파일을 찾을 수 없습니다.");
	}

	// /except/page01 요청 시 FileNotFoundException을 강제로 발생시켜 테스트
	@GetMapping("/page01")
	public void ex1() throws FileNotFoundException {
		log.info("GET /except/page01");
		throw new FileNotFoundException("파일을 찾을 수 없습니다.");
	}

	// /except/page02/{num}/{div} 요청 시 정수 나눗셈 수행
	// div가 0일 경우 ArithmeticException 발생 → 예외 처리 메서드가 있다면 실행됨
	@GetMapping("/page02/{num}/{div}")
	public String ex2(
			@PathVariable("num") int num,
			@PathVariable("div") int div,
			Model model
	) throws ArithmeticException {
		log.info("GET /except/page02..." + (num / div));
		model.addAttribute("result", (num / div));
		return "except/page02";
	}
	
////	요청 URL이 /except/page02/10/2라면,
////	뷰 이름은 /WEB-INF/views/except/page02/10/2.jsp로 해석하려고 함 (패턴 매칭 실패 → 404 에러)
//	@GetMapping("/page02/{num}/{div}")
//	public void ex2(
//			@PathVariable("num") int num,
//			@PathVariable("div") int div,
//			Model model
//			) throws ArithmeticException {
//		log.info("GET /except/page02..." + (num / div));
//		model.addAttribute("result", (num / div));
//
//	}

}