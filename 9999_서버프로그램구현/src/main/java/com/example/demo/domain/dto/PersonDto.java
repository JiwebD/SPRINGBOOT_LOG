package com.example.demo.domain.dto;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// 애노테이션
//@Getter
//@Setter
//@ToString
@Data	//getter, setter, tostring 모두 설정
@AllArgsConstructor	//모든인자 받는 생성자
@NoArgsConstructor	//디폴트 생성자
@Component	//bean
@Builder	//객체생성 패턴 
public class PersonDto {
	private String username;
	private int age;
	private String addr;

}
