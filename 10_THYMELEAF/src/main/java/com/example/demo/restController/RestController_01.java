package com.example.demo.restController;

import com.example.demo.domain.dto.MemoDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/rest")
public class RestController_01 {

	@GetMapping(value="/getText" , produces=MediaType.TEXT_PLAIN_VALUE)
	public String f1() {
		log.info("GET /rest/getText...");
		return "HELLO WORLD";
	}
	
	@GetMapping(value="/getJson" , produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public MemoDto f2() {
		log.info("GET /rest/getJson...");
		return new MemoDto(11,"ABCD","FFFF",LocalDateTime.now());
	}
	
	@GetMapping(value="/getXml" , produces=MediaType.APPLICATION_XML_VALUE)
	public MemoDto f3() {
		log.info("GET /rest/getXml...");
		return new MemoDto(11,"ABCD","FFFF",LocalDateTime.now());
	}
	
	@GetMapping(value="/getXmlList" , produces=MediaType.APPLICATION_XML_VALUE)
	public List<MemoDto> f4(){
		log.info("GET /rest/getXmlList...");
		List<MemoDto> list = new ArrayList();
		for(int i=0; i<50; i++) {
			list.add(new MemoDto(i,"A"+i, "aa", LocalDateTime.now()));
		}
		return list;
	}
	
	@GetMapping(value="/getXmlList2/{show}" , produces=MediaType.APPLICATION_XML_VALUE)
	public  ResponseEntity< List<MemoDto> > f5(@PathVariable("show") boolean show) { 
		log.info("GET /rest/getXmlList2...");
		
		if(show) {
			List<MemoDto> list = new ArrayList();
			for(int i=0;i<50;i++) {
				list.add(new MemoDto(i,"A"+i, "aa", LocalDateTime.now()));
			}
			return ResponseEntity.status(HttpStatus.OK).body(list);
		}	
		return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(null);	
	}
}
