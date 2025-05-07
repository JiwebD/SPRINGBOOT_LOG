package com.example.demo.controller;

import com.example.demo.domain.dto.PersonDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Slf4j
@RequestMapping("/param")
public class ParameterTestcontroller {
	
	// param/p01?name=홍길동
	//required=true로 하면 쿼리 파라미터 name을 필수로 받아옴 쿼리파라미터 = url뒤에 붙는 ?  이후의 키=값 쌍.
	//false로 하면 name이 없어도 호출됨 name 값 =null
	@RequestMapping(value = "/p01", method = RequestMethod.GET)
	public void p01(@RequestParam(value="name", required=true) String name) {	
		log.info("GET /param/p01..." + name);									
																				
	}
	
	//
	@RequestMapping("/p02")
	public void p02(@RequestParam(value="name", required=true) String name) {
		log.info("GET /param/p02..." + name);
	}
	
	//postman 으로 테스트
	//http://localhost:8090/ex02_parameter_my/param/p03 입력하고 post선택    *GET 선택하면400잘못된 요청오류
	// Body탭에서 x-www-form-urlencoded 에서 Key에 name , Value에 박효신입력하고 send
	//console 확인
	@RequestMapping("/p03")
	public void p03(@RequestParam(value="name", required=true) String name) {
		log.info("GET /param/p03..." + name);
	}
	
	//@RequestParam		: 동기요청 파라미터 처리 	/ form 기반 전달되는 파라미터 받기
	//@Requestbody		: 비동기요청 파라미터 처리 / form / json 등 파라미터 받기
	
	//postman 으로 테스트
	// @RequestBody
	//http://localhost:8090/ex02_parameter_my/param/p04 입력하고 GET선택  
	// Body탭에서 x-www-form-urlencoded 에서 Key에 name , Value에 박효신입력하고 send
	// Body탭에서 raw 에서 { "name" : "홍길동"} 입력후 send
	//console 확인
	@RequestMapping("/p04")  //post 영역에서는 리퀘스트 바디의 영역보다 넓다
	public void p04(@RequestBody String name) {
		log.info("GET /param/p04..." + name);
	}
	
	//http://localhost:8090/ex02_parameter_my/param/p05
	@RequestMapping("/p05")	
	public void p05(@RequestParam(value="name", defaultValue="홍길동") String name) {
		log.info("GET /param/p05..." + name);
	}
	
	// postman으로 테스트
	// ex02_parameter_my/param/p06  입력, GET선택
	// Params에서 key name, age, addr    values aa , 11  aa입력 send
	// 확인
	@RequestMapping(value = "/p06",  method = RequestMethod.GET)	
	public void p06(
					@RequestParam(value="name")	String name,
					@RequestParam(value="age")	String age,
					@RequestParam(value="addr")	String addr
					)
	{
		log.info("GET /param/p06..." + name + " " + age+ " " + addr);
	}
	
	//
	@RequestMapping(value = "/p07", method = RequestMethod.GET)
	public void p07(@ModelAttribute PersonDto dto)
	{
		log.info("GET /param/p07..." + dto);
	}
	
	// /param/p08/홍길동/55/대구
	@RequestMapping(value = "/p08/{username}/{age}/{addr}", method = RequestMethod.GET)
	public void p08(
			@PathVariable("username") String name,
			@PathVariable int age,
			@PathVariable String addr
			)
	{
		log.info("GET /param/p08..." + name + " " + age + " " + addr);
	}
	
	// /p09/홍길동/55/대구
	@RequestMapping(value = "/p09/{username}/{age}/{addr}", method = RequestMethod.GET)
	public void p09(@ModelAttribute PersonDto dto)
	{
		log.info("GET /param/p09..." + dto);
	}
	
	// http://localhost:8090/ex02_parameter_my/param/page01?username=aaa&age=11&addr=대구
	@GetMapping("/page01")
	public void page01(PersonDto dto, Model model) {
		log.info("GET /param/page01..." + dto);
		//반환자료형이 void 일때 /WEB-INF/views/param/page01.jsp와 매핑
		
		//파라미터
		//유효성
		//서비스
		//뷰이동 + 데이터 전달(Model객체 - DispatherServlet(FC))
		model.addAttribute("dto", dto);  //setAttribute과 같다고 생각하면됨
		model.addAttribute("test1","test1Value");
	}
	
	@GetMapping("/page02")
	public String page02(PersonDto dto, Model model) {
		log.info("GET /param/page02..." + dto);
		//반환자료형이 void 일때 /WEB-INF/views/param/page01.jsp와 매핑
		
		//파라미터
		//유효성
		//서비스
		//뷰이동 + 데이터 전달(Model객체 - DispatherServlet(FC))
		model.addAttribute("dto", dto);  //setAttribute과 같다고 생각하면됨
		model.addAttribute("test2","test2Value");
		
		//반환 자료형 void : /WEB-INF/views/param/page02.jsp와 매핑
		//위치변경시 String + return "path"
		
		return "param/page01";
	}
	
	
	@GetMapping("/page03/{username}/{age}/{addr}")
	public String page03(PersonDto dto, Model model) {
		log.info("GET /param/page03..." + dto);
		model.addAttribute("dto", dto);
		model.addAttribute("test3", "test3Value");

		return "param/page01";
	}
	
	@GetMapping("/page04/{username}/{age}/{addr}")
	public ModelAndView page04(PersonDto dto) {
		log.info("GET /param/page04..." + dto);
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("dto",dto);
		modelAndView.setViewName("param/page01");	//String으로 했을때 return값 "param/page01"과 같다. 저쪽으로 보낸다는 기능
		
		return modelAndView;

	}
	
	//SERVLET 방식
	@GetMapping("/page05")
	public String page05(HttpServletRequest req, HttpServletResponse resp) {
		log.info("GET /param/page05...");
		String name = req.getParameter("username");
		int age = Integer.parseInt(req.getParameter("age"));
		String addr = req.getParameter("addr");
		log.info(name+" " + age);
		PersonDto dto = new PersonDto(name,age,addr);
		req.setAttribute("dto", dto);
		
		HttpSession session = req.getSession();
		log.info("session : " + session);
		
		return "param/page01";
	
	}
	
	//Forward
	@GetMapping("/forward1")
	public String f1(Model model) {
		log.info("param/forward1..");
		model.addAttribute("f1","f1Value");
		return "forward:/param/forward2";
	}
	//Forward
	@GetMapping("/forward2")
	public String f2(Model model) {
		log.info("param/forward2..");
		model.addAttribute("f2","f2Value");
		return "forward:/param/forward3";
	}

	@GetMapping("/forward3")
	public String f3(Model model) {
		log.info("param/forward3..");
		model.addAttribute("f3","f3Value");
		return "param/forward_result";
	}
	
	
	//리다이렉트는 새로운요청이라 r3값만 받음
	//Redirect
//	@GetMapping("/redirect1")
//	public String r1(Model model) {
//		log.info("param/redirect1..");
//		model.addAttribute("r1","r1Value");
//		return "redirect:/param/redirect2";
//	}
	
	//Redirect
	//RedirectAttributes redirectAttributes 사용
	//http://localhost:8090/project/param/redirect1 GET 요청
	//http://localhost:8090/project/param/redirect2?r1=r1Value 이동할것을 명령(REDIRECT)
	@GetMapping("/redirect1")
	public String r1(Model model , RedirectAttributes redirectAttributes) {
		log.info("param/redirect1..");
//		model.addAttribute("r1","r1Value");
		redirectAttributes.addAttribute("r1","r1Value");	//쿼리스트링으로 전달 => redirect2?r1=r1Value
		return "redirect:/param/redirect2";
	}
	//Redirect
	@GetMapping("/redirect2")
	public String r2(Model model, 
			@RequestParam("r1") String r1,
			RedirectAttributes redirectAttributes
			) {
		log.info("param/redirect2.. r1 : " + r1);
//		model.addAttribute("r2","r2Value");
		redirectAttributes.addAttribute("r1","r1Value"); //쿼리스트링으로 전달
		redirectAttributes.addAttribute("r2","r2Value"); //쿼리스트링으로 전달
		return "redirect:/param/redirect_result"; 
	}

	@GetMapping("/redirect_result")
	public void r_result(
			Model model,
			@RequestParam("r1") String r1,
			@RequestParam("r2") String r2
			) {
		model.addAttribute("r1",r1);
		model.addAttribute("r2",r2);
		model.addAttribute("r3","rs3Value");
		log.info("param/redirect_result.. : ");

	}
}
