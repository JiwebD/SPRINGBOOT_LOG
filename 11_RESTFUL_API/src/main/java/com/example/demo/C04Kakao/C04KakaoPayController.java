package com.example.demo.C04Kakao;

import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

@Controller
@Slf4j
@RequestMapping("/kakao/pay")
public class C04KakaoPayController {

    private String SECRET_KEY = "DEVFD564DCCB8B8DD8F771DC48F0701E6DB9D6DB";

    @GetMapping("/req")
    public void req() {
        log.info("GET /kakao/pay/req...");

        //요청 정보 확인
        String url = "https://open-api.kakaopay.com/online/v1/payment/ready";

        //요청 헤더 설정
        HttpHeaders header = new HttpHeaders();
        header.add("Authorization", "SECRET_KEY " + SECRET_KEY);
        header.add("Content-Type", "application/json");

        //요청 바디 설정
//        MultiValueMap<String, String> params = new LinkedMultiValueMap<>(); //요청받는 값이 많을때 쓰기좋은 Map
        JSONObject params = new JSONObject();
        params.put("cid", "TC0ONETIME");
        params.put("partner_order_id", "partner_order_id");
        params.put("partner_user_id", "partner_user_id");
        params.put("item_name", "초코파이");
        params.put("quantity", "1");
        params.put("total_amount", "2200");
        params.put("vat_amount", "200");
        params.put("tax_free_amount", "0");
        params.put("approval_url", "http://localhost:8090/kakao/pay/success");
        params.put("fail_url", "http://localhost:8090/kakao/pay/fail");
        params.put("cancel_url", "http://localhost:8090/kakao/pay/cancel");


        HttpEntity< JSONObject > entity = new HttpEntity<>(params, header);

        //요청 후 응답확인
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response =
                rt.exchange(url, HttpMethod.POST, entity, String.class); //토큰 받기 메서드 POST

        System.out.println(response);
        // console에 나오는 바디 복사해서 json to java로 클래스로 변환 private static class KakaoTokenResponse로 아래 붙여넣기


        //가공 처리

    }

    @GetMapping("/success")
    @ResponseBody
    public void success() {
        log.info("GET /kakao/pay/success....");
    }

    @GetMapping("/fail")
    @ResponseBody
    public void fail() {
        log.info("GET /kakao/pay/fail....");
    }

    @GetMapping("/cancel")
    @ResponseBody
    public void cancel() {
        log.info("GET /kakao/pay/cancel....");
    }

}
