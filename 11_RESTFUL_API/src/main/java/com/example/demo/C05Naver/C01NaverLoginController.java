package com.example.demo.C05Naver;

import com.example.demo.C04Kakao.C02KakaoLoginController;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

@Controller
@Slf4j
@RequestMapping("/naver")
public class C01NaverLoginController {

    private String NAVER_CLIENT_ID = "5_tY3PE8ZkcIZ5toaitk";
    private String NAVER_CLIENT_SECRET ="xeM3tJft66";
    private String REDIRECT_URI = "http://localhost:8090/naver/callback";

    NaverTokenResponse naverTokenResponse;
    NaverProfileResponse naverProfileResponse;

    @GetMapping("/login")
    public String login(){
        log.info("GET /naver/login...");
        return  "redirect:https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id="+NAVER_CLIENT_ID+"&state=STATE_STRING&redirect_uri="+REDIRECT_URI;
    }

    @GetMapping("/callback")
    public String callback(
            @RequestParam("code") String code,
            @RequestParam("state") String state
            ){
        log.info("GET /naver/callback...." + code + " " + state);

            //요청 정보 확인

            String url="https://nid.naver.com/oauth2.0/token";

            //요청 헤더 설정
            HttpHeaders header = new HttpHeaders();

            //요청 바디 설정
            MultiValueMap<String,String> params = new LinkedMultiValueMap<>(); //요청받는 값이 많을때 쓰기좋은 Map
            params.add("grant_type","authorization_code");
            params.add("client_id",NAVER_CLIENT_ID);
            params.add("client_secret",NAVER_CLIENT_SECRET);
            params.add("code",code);
            params.add("state",state);

            HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params,header);

            //요청 후 응답확인
            RestTemplate rt = new RestTemplate();
            ResponseEntity<NaverTokenResponse> response =
                    rt.exchange(url, HttpMethod.POST, entity, NaverTokenResponse.class); //토큰 받기 메서드 POST

            System.out.println(response.getBody());
            this.naverTokenResponse = response.getBody();

            return "redirect:/naver/main";


            //가공 처리
    }

    @GetMapping("/main")
    public void main(Model model){
        log.info("GET /naver/main...");

        //정보확인
        String url="https://openapi.naver.com/v1/nid/me";

        //요청헤더
        HttpHeaders header = new HttpHeaders();
        header.add("Authorization","Bearer " + this.naverTokenResponse.getAccess_token());

        //요청바디
        //ENTITY
        HttpEntity entity = new HttpEntity<>(header);

        //요청 -> 응답
        RestTemplate rt = new RestTemplate();
        ResponseEntity<NaverProfileResponse> response = rt.exchange(url, HttpMethod.POST, entity, NaverProfileResponse.class);
        System.out.println(response.getBody());
        // console에 나오는 바디 복사해서 json to java로 클래스로 변환 아래 붙여넣기 root -> NaverProfileResponse 로 이름 바꾸기

        this.naverProfileResponse = response.getBody();

        model.addAttribute("profile", this.naverProfileResponse);


    }

    // unlink 시 로그인 정보는
    @GetMapping("/unlink")
    public void unlink(){
        log.info("GET /naver/unlink...");

        //요청 정보 확인

        String url="https://nid.naver.com/oauth2.0/token";

        //요청 헤더 설정
        HttpHeaders header = new HttpHeaders();

        //요청 바디 설정
        MultiValueMap<String,String> params = new LinkedMultiValueMap<>(); //요청받는 값이 많을때 쓰기좋은 Map
        params.add("grant_type","delete");
        params.add("client_id",NAVER_CLIENT_ID);
        params.add("client_secret",NAVER_CLIENT_SECRET);
        params.add("access_token",this.naverTokenResponse.getAccess_token());


        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params,header);

        //요청 후 응답확인
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response =
                rt.exchange(url, HttpMethod.POST, entity, String.class); //토큰 받기 메서드 POST

        System.out.println(response.getBody());
    }


    //편법으로 로그아웃 으로 연결해제하기
    //naver홈에서 로그아웃 해보고 네트워크에서 https://nid.naver.com/nidlogin.logout?returl=https://www.naver.com/ 찾아서 리다이렉트로 사용하기
    @GetMapping("/logout")
    public String logout(){
        log.info("GET /naver/logout...");

        return "redirect:https://nid.naver.com/nidlogin.logout?returl=http://localhost:8090/naver/main";

    }

    // /callback 응답
    @Data
    private static class NaverTokenResponse{
        public String access_token;
        public String refresh_token;
        public String token_type;
        public String expires_in;
    }


    // /main 응답
    @Data
    private static class Response{
        public String id;
        public String profile_image;
        public String email;
        public String name;
    }

    @Data
    private static class NaverProfileResponse{
        public String resultcode;
        public String message;
        public Response response;
    }



}
