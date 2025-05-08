package com.example.demo.domain.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemoDto {

	public MemoDto(int id, String text) {
		this.id = id;
		this.text = text;
	}
	
	//@Min(value, message): 숫자가 주어진 최솟값 이상인지 검증.
	@Min(value = 10,message = "ID는 10이상이 어야 합니다.")
	@Max(value = 65535,message = "ID의 최대 숫자는 65535이하 이어야 합니다.")
	 @NotNull(message="ID는 필수항목입니다")
	private Integer id;
	
	//@NotBlank(message): 문자열이 비어 있지 않은지 확인합니다.
	@NotBlank(message="메모를 입력하세요")
	private String text;
	@NotBlank(message="메모를 입력하세요")
	@Email(message="example@example.com에 맞게 입력해주세요")
	private String writer;
	
 
	
	// @DateTimeFormat: 문자열을 LocalDateTime으로 변환할 때 사용할 포맷 지정
	// 스프링은 @RequestParam, @ModelAttribute 등을 통해 문자열을 자바 객체로 바인딩할 때
	// LocalDateTime처럼 형식이 필요한 필드는 명확한 포맷이 없으면 400 오류 발생 가능
	@DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm")	// 폼에서 넘어오는 날짜 문자열 포맷 지정
	@NotNull(message="날짜정보를 선택해주세요")
	private LocalDateTime createAt;
	
	//
//	private LocalDate dateTest;


}

/*
 * 숫자 유효성 검사:
 * 
 * @Min(value, message): 숫자가 주어진 최솟값보다 큰지 확인합니다.
 * 
 * @Max(value, message): 숫자가 주어진 최댓값보다 작은지 확인합니다.
 * 
 * @Positive: 숫자가 양수인지 확인합니다.
 * 
 * @PositiveOrZero: 숫자가 양수 또는 0인지 확인합니다.
 * 
 * @Negative: 숫자가 음수인지 확인합니다.
 * 
 * @NegativeOrZero: 숫자가 음수 또는 0인지 확인합니다. 문자열 유효성 검사:
 * 
 * @NotBlank(message): 문자열이 비어 있지 않은지 확인합니다.
 * 
 * @Size(min, max, message): 문자열의 길이가 주어진 범위 내에 있는지 확인합니다.
 * 
 * @Email(message): 문자열이 유효한 이메일 주소인지 확인합니다.
 * 
 * @Pattern(regexp, message): 정규 표현식에 맞는지 확인합니다.
 * 
 * @URL(message): 문자열이 유효한 URL인지 확인합니다. 날짜와 시간 유효성 검사:
 * 
 * @DateTimeFormat(pattern): 날짜와 시간의 형식을 지정합니다.
 * 
 * @Future: 날짜가 미래인지 확인합니다.
 * 
 * @FutureOrPresent: 날짜가 미래이거나 현재인지 확인합니다.
 * 
 * @Past: 날짜가 과거인지 확인합니다.
 * 
 * @PastOrPresent: 날짜가 과거이거나 현재인지 확인합니다. 기타:
 * 
 * @AssertTrue: boolean 타입의 값이 true인지 확인합니다.
 * 
 * @AssertFalse: boolean 타입의 값이 false인지 확인합니다.
 * 
 * @CreditCardNumber: 신용카드 번호의 유효성을 검증합니다.
 */
