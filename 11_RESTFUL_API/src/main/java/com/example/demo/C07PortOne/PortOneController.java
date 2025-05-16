package com.example.demo.C07PortOne;

import com.example.demo.C04Kakao.C02KakaoLoginController;
import lombok.Data;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Controller
@Slf4j
@RequestMapping("/portOne")
public class PortOneController {

    String RESTAPI_KEY = "";
    String RESTAPI_SECRET  = "";
    private PortOneTokenResponse portOneTokenResponse;

    @GetMapping("/index")
    public void index(){
        log.info("GET /portOne/index...");
    }
    @GetMapping("/getToken")
    @ResponseBody
    public void getToken(){
        log.info("GET /portOne/getToken....");
        //요청 정보 확인
        String url = "https://api.iamport.kr/users/getToken";
        //요청 헤더 설정
        HttpHeaders header = new HttpHeaders();
//        header.add("Content-Type","application/json"); //form x
        //요청 바디 설정
        MultiValueMap<String,String> params = new LinkedMultiValueMap<>();
        params.add("imp_key",RESTAPI_KEY);
        params.add("imp_secret",RESTAPI_SECRET);

        HttpEntity< MultiValueMap<String,String> > entity = new HttpEntity<>(params,header);

        //요청 후 응답확인
        RestTemplate rt = new RestTemplate();
        ResponseEntity<PortOneTokenResponse> response =
                rt.exchange(url, HttpMethod.POST,entity, PortOneTokenResponse.class);
        System.out.println(response.getBody());
        this.portOneTokenResponse =response.getBody();

    }
    //결제조회(다건조회)
    @GetMapping("/getPayments")
    @ResponseBody
    public void payments(){
        log.info("GET /portOne/getPayments...");
        String url = "https://api.iamport.kr/payments?imp_uid[]=imp_979464507864&merchant_uid[]=";
        //요청 헤더 설정
        HttpHeaders header = new HttpHeaders();
        header.add("Authorization","Bearer "+this.portOneTokenResponse.getResponse().getAccess_token());
        header.add("Content-Type","application/json");
        //요청 바디 설정
//        MultiValueMap<String,String> params = new LinkedMultiValueMap<>();
//        params.add("imp_uid[]","imp_051607289979");
//        params.add("merchant_uid[]","test_1234");

        HttpEntity entity = new HttpEntity<>(header);

        //요청 후 응답확인
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response =
                rt.exchange(url, HttpMethod.GET,entity, String.class);

        System.out.println(response);

    }

    //결제취소
    @GetMapping("/payment/cancel")
    @ResponseBody
    public void payment_calcel(){
        log.info("GET /portOne/payment/cancel...");

        //요청 정보 확인
        String url = "https://api.iamport.kr/payments/cancel";
        //요청 헤더 설정
        HttpHeaders header = new HttpHeaders();
        header.add("Content-Type","application/json"); //form x
        header.add("Authorization","Bearer "+portOneTokenResponse.getResponse().getAccess_token());
        //요청 바디 설정
//        Map<String,Object> params = new HashMap<>();
//        params.put("\"imp_uid\"", "\"imp_979464507864\"");
//        params.put("\"merchant_uid\"","\"merchant_a0fc8691-2e9a-4dc4-b47e-f42909e\"");
        JSONObject params = new JSONObject();
        params.put("imp_uid","imp_979464507864");
        params.put("merchant_uid","merchant_a0fc8691-2e9a-4dc4-b47e-f42909e");
        System.out.println("--------------");
        System.out.println(params);
        System.out.println("--------------");
//        JSONObject params = new JSONObject();
//        params.put("imp_uid", "imp_807755828471");
//        params.put("merchant_uid","merchant_6d22d835-fe9c-4045-b8e6-d8914fa");

        HttpEntity< String > entity = new HttpEntity<>(params.toString(),header);

        //요청 후 응답확인
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response =
                rt.exchange(url, HttpMethod.POST,entity, String.class);
        System.out.println(response.getBody());


    }



    @GetMapping("/certifications/{imp_uid}")
    @ResponseBody
    public void certifications(@PathVariable("imp_uid")String imp_uid){
        log.info("GET /portOne/getPayments...");
        String url = "https://api.iamport.kr/certifications/"+imp_uid;
        //요청 헤더 설정
        HttpHeaders header = new HttpHeaders();
        header.add("Authorization","Bearer "+this.portOneTokenResponse.getResponse().getAccess_token());
        header.add("Content-Type","application/json");

        HttpEntity entity = new HttpEntity<>(header);

        //요청 후 응답확인
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response =
                rt.exchange(url, HttpMethod.GET,entity, String.class);

        System.out.println(response);

    }



    //인증조회
    @Data
    private static class Response{
        public String access_token;
        public int now;
        public int expired_at;
    }
    @Data
    private static class PortOneTokenResponse{
        public int code;
        public Object message;
        public Response response;
    }
}
