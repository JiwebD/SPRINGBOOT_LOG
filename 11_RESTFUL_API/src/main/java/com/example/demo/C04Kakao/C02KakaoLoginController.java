package com.example.demo.C04Kakao;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@Slf4j
@RequestMapping("/kakao")
public class C02KakaoLoginController {
//client_id	String	        앱 REST API 키
//                          [내 애플리케이션] > [앱 키]에서 확인 가능
//redirect_uri	String	    인가 코드를 전달받을 서비스 서버의 URI
//                          [내 애플리케이션] > [카카오 로그인] > [Redirect URI]에서 등록
//response_type	String	    code로 고정

    String REDIRECT_URI="http://192.168.16.85:8090/kakao/callback";
    String CLIENT_ID="8bcd6ddac90f3f9e1734e75b42ffdb6b";
    String RESPONSE_TYEP="code";
    String LOGOUT_REDIRECT_URI="http://localhost:8090/kakao/login";

    KakaoTokenResponse kakaoTokenResponse ;
    KakaoProfileResponse kakaoProfileResponse;
    KakaoFriendsResponse kakaoFriendsResponse;

    //인가 코드 받기
    //기본 정보
    //메서드	    URL	                                        인증 방식
    //GET	    https://kauth.kakao.com/oauth/authorize	       -
//    @GetMapping("/getCode")
    @GetMapping("/login")
    public String getCode(){
        log.info("GET /kakao/getCode...");
        return "redirect:https://kauth.kakao.com/oauth/authorize?"
                                                +"client_id="+CLIENT_ID
                                                +"&redirect_uri="+REDIRECT_URI
                                                +"&response_type="+RESPONSE_TYEP;
    }



    //카카오 로그인 STEP 2
    // /getCode 에서 카카오 로그인 화면으로 리디렉션하면,
    // 사용자가 로그인 후 카카오는 인가코드(code)를 콜백 주소로 전달한다.
    // 그 인가코드는 /callback 에서 받아 access token 요청에 사용한다.
    @GetMapping("/callback")
    public String callback(@RequestParam("code") String code){
        log.info("GET /kakao/callback..." + code);

        //요청 정보 확인
        //토큰 받기
        //기본 정보
        //메서드	    URL	                                    인증 방식
        //POST	    https://kauth.kakao.com/oauth/token	    -
        String url="https://kauth.kakao.com/oauth/token";

        //요청 헤더 설정
        //이름	        설명	                                                              필수
        //Content-Type	Content-Type: application/x-www-form-urlencoded;charset=utf-8      O
        //              요청 데이터 타입
        HttpHeaders header = new HttpHeaders();
        header.add("Content-Type","application/x-www-form-urlencoded;charset=utf-8");

        //요청 바디 설정
        //이름	            타입	        설명	                                    필수
        //grant_type	    String	    authorization_code로 고정	            O
        //client_id	        String	    앱 REST API 키                           O
        //                              [내 애플리케이션] > [앱 키]에서 확인 가능
        //redirect_uri	    String	    인가 코드가 리다이렉트된 URI	            O
        //code	            String	    인가 코드 받기 요청으로 얻은 인가 코드	    O
        MultiValueMap<String,String> params = new LinkedMultiValueMap<>(); //요청받는 값이 많을때 쓰기좋은 Map
        params.add("grant_type","authorization_code");
        params.add("client_id",CLIENT_ID);
        params.add("redirect_uri",REDIRECT_URI);
        params.add("code",code);

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params,header);

        //요청 후 응답확인
        RestTemplate rt = new RestTemplate();
        ResponseEntity<KakaoTokenResponse> response =
                rt.exchange(url, HttpMethod.POST, entity, KakaoTokenResponse.class); //토큰 받기 메서드 POST

        System.out.println(response.getBody());
        // console에 나오는 바디 복사해서 json to java로 클래스로 변환 private static class KakaoTokenResponse로 아래 붙여넣기

        this.kakaoTokenResponse = response.getBody();

        return "redirect:/kakao/main";

        //가공 처리


    }

    @GetMapping("/main")
    public void main(Model model){
        log.info("GET /kakao/main...");

        //정보확인
        //사용자 정보 가져오기
        //기본 정보
        //메서드	        URL	                                인증 방식
        //GET/POST	    https://kapi.kakao.com/v2/user/me	액세스 토큰
        //                                                  서비스 앱 어드민 키
        String url="https://kapi.kakao.com/v2/user/me";

        //요청헤더
        //요청: 액세스 토큰 방식
        //헤더
        //이름	            설명  	                                                        필수
        //Authorization	    Authorization: Bearer ${ACCESS_TOKEN}                           O
        //                  인증 방식, 액세스 토큰으로 인증 요청
        //Content-Type	    Content-Type: application/x-www-form-urlencoded;charset=utf-8   O
        //                  요청 데이터 타입
        HttpHeaders header = new HttpHeaders();
        header.add("Authorization","Bearer " + this.kakaoTokenResponse.getAccess_token());
        header.add("Content-Type","application/x-www-form-urlencoded;charset=utf-8");

        //요청바디
        //ENTITY
        HttpEntity entity = new HttpEntity<>(header);

        //요청 -> 응답
        RestTemplate rt = new RestTemplate();
        ResponseEntity<KakaoProfileResponse> response = rt.exchange(url, HttpMethod.POST, entity, KakaoProfileResponse.class);
        System.out.println(response.getBody());
        // console에 나오는 바디 복사해서 json to java로 클래스로 변환 아래 붙여넣기 root -> KakaoProfileResponse로 이름 바꾸기

        this.kakaoProfileResponse = response.getBody();

        model.addAttribute("profile", this.kakaoProfileResponse);

    }

    //로그아웃 , 요청: 액세스 토큰 방식
    @GetMapping("/logout")
    @ResponseBody
    public void logout(){
        log.info("GET /kakao/logout");

        //정보
        //로그아웃
        //기본 정보
        //메서드	    URL	                                    인증 방식
        //POST	    https://kapi.kakao.com/v1/user/logout	액세스 토큰
        //                                                  서비스 앱 어드민 키
        String url = "https://kapi.kakao.com/v1/user/logout";

        //요청헤더
        HttpHeaders header = new HttpHeaders();
        header.add("Authorization","Bearer " + this.kakaoTokenResponse.getAccess_token());

        //요청바디
        //ENTITY
        HttpEntity entity = new HttpEntity<>(header);

        //요청 -> 응답
        RestTemplate rt = new RestTemplate();
        ResponseEntity<KakaoProfileResponse> response = rt.exchange(url, HttpMethod.POST, entity, KakaoProfileResponse.class);

    }


    //연결 끊기 , 요청: 액세스 토큰 방식
    @GetMapping("/unlink")
    @ResponseBody
    public void unlink(){
        log.info("GET /kakao/unlink....");
        //정보
        String url = "https://kapi.kakao.com/v1/user/unlink";

        //요청헤더
        HttpHeaders header = new HttpHeaders();
        header.add("Authorization","Bearer " + this.kakaoTokenResponse.getAccess_token());

        //요청바디
        //ENTITY
        HttpEntity entity = new HttpEntity<>(header);

        //요청 -> 응답
        RestTemplate rt = new RestTemplate();
        ResponseEntity<KakaoProfileResponse> response = rt.exchange(url, HttpMethod.POST, entity, KakaoProfileResponse.class);
    }

    //카카오계정과 함께 로그아웃 , 요청 : 액세스 토큰 방식
    @GetMapping("/logoutAll")
    public String logoutAll(){
        log.info("GET /kakao/logoutAll...");
        return "redirect:https://kauth.kakao.com/oauth/logout?"
                +"client_id="+CLIENT_ID
                +"&logout_redirect_uri="+LOGOUT_REDIRECT_URI;
    }


    @GetMapping("/getCodeMsg")
    public String getCode_message(){
        log.info("GET /kakao/getCode_message...");
        return "redirect:https://kauth.kakao.com/oauth/authorize?"
                +"client_id="+CLIENT_ID
                +"&redirect_uri="+REDIRECT_URI
                +"&response_type="+RESPONSE_TYEP
                +"&scope=talk_message";
    }
    
    //기본 템플릿으로 메시지 보내기 / 나에게 보내기
    @GetMapping("/message/me/{message}")
    public void message_me(@PathVariable("message") String message){
        //정보
        //나에게 보내기
        //기본 정보
        //메서드	      URL	                                                인증 방식
        //POST	      https://kapi.kakao.com/v2/api/talk/memo/default/send	액세스 토큰
        String url = "https://kapi.kakao.com/v2/api/talk/memo/default/send";

        //요청 헤더
        HttpHeaders header = new HttpHeaders();
        header.add("Authorization","Bearer " + this.kakaoTokenResponse.getAccess_token());
        header.add("Content-Type","application/x-www-form-urlencoded;charset=utf-8");

        //요청 바디
        MultiValueMap<String,String> params = new LinkedMultiValueMap<>(); //요청받는 값이 많을때 쓰기좋은 Map
        JSONObject template_object = new JSONObject();  //{} 비어있는 json 객체 생성
        template_object.put("object_type","text");
        template_object.put("text",message);
        template_object.put("link",new JSONObject());
        template_object.put("button_title","");
        params.add("template_object", template_object.toString());

        //요청 엔티티(헤더+바디)
        HttpEntity< MultiValueMap<String,String> > entity = new HttpEntity<>(params,header);

        //요청-> 응답
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(url, HttpMethod.POST, entity, String.class);
    }


    //친구 목록 가져오기 동의
    @GetMapping("/getCodeFriends")
    public String getCode_Friends(){
        log.info("GET /kakao/getCode_Friends...");
        return "redirect:https://kauth.kakao.com/oauth/authorize?"
                +"client_id="+CLIENT_ID
                +"&redirect_uri="+REDIRECT_URI
                +"&response_type=code"+RESPONSE_TYEP
                +"&scope=friends,talk_message";
    }

    //카카오톡 소셜 / 친구 목록 가져오기
    @GetMapping("/friends")
    public void getFriends(){
        log.info("GET /kakao/friends");

        //정보
        //친구 목록 가져오기
        //기본 정보
        //메서드	    URL	                                        인증 방식
        //GET	    https://kapi.kakao.com/v1/api/talk/friends	액세스 토큰
        String url = "https://kapi.kakao.com/v1/api/talk/friends";

        //헤더
        HttpHeaders header = new HttpHeaders();
        header.add("Authorization","Bearer " + this.kakaoTokenResponse.getAccess_token());

        //본문
        //엔티티
        HttpEntity entity = new HttpEntity<>(header);
        //요청 -> 응답
        RestTemplate rt = new RestTemplate();
        ResponseEntity<KakaoFriendsResponse> response = rt.exchange(url, HttpMethod.GET, entity, KakaoFriendsResponse.class);
        System.out.println(response.getBody());

        this.kakaoFriendsResponse = response.getBody();
    }

    
    //친구에게 메시지 보내기
    @GetMapping("/message/friends/{message}")
    @ResponseBody //화면 전환이 이뤄지지 않도록 설정
    public void friends_message(@PathVariable("message") String message){
        log.info("GET /kakao/message/friends..." + message);

        //정보
        //친구에게 보내기
        //기본 정보
        //메서드	    URL	                                                                인증 방식
        //POST	    https://kapi.kakao.com/v1/api/talk/friends/message/default/send	    액세스 토큰
        String url = "https://kapi.kakao.com/v1/api/talk/friends/message/default/send";

        //요청 헤더
        HttpHeaders header = new HttpHeaders();
        header.add("Authorization","Bearer " + this.kakaoTokenResponse.getAccess_token());
        header.add("Content-Type","application/x-www-form-urlencoded;charset=utf-8");

        //요청 바디
        MultiValueMap<String,String> params = new LinkedMultiValueMap<>(); //요청받는 값이 많을때 쓰기좋은 Map

        //Receiver
        JSONArray uuids = new JSONArray();
        List<Element> list =  kakaoFriendsResponse.getElements();
        for(int i=0; i<list.size();i++){
            uuids.add( list.get(i).getUuid() );
        }

        //Template
        JSONObject template_object = new JSONObject();  //{} 비어있는 json 객체 생성
        template_object.put("object_type","text");
        template_object.put("text",message);
        template_object.put("link",new JSONObject());
        template_object.put("button_title","");

        params.add("template_object", template_object.toString());
        params.add("receiver_uuids", uuids.toString());

        //요청 엔티티(헤더+바디)
        HttpEntity< MultiValueMap<String,String> > entity = new HttpEntity<>(params,header);

        //요청-> 응답
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(url, HttpMethod.POST, entity, String.class);
    }
    //--------------------------
    // KakaoTokenResponse
    //--------------------------
    @Data
    private static class KakaoTokenResponse{
        public String access_token;
        public String token_type;
        public String refresh_token;
        public int expires_in;
        public String scope;
        public int refresh_token_expires_in;
    }

    //--------------------------
    // KakaoProfileResponse
    //--------------------------
    @Data
    private static class KakaoAccount{
        public boolean profile_nickname_needs_agreement;
        public boolean profile_image_needs_agreement;
        public Profile profile;
        public boolean has_email;
        public boolean email_needs_agreement;
        public boolean is_email_valid;
        public boolean is_email_verified;
        public String email;
    }
    @Data
    private static class Profile{
        public String nickname;
        public String thumbnail_image_url;
        public String profile_image_url;
        public boolean is_default_image;
        public boolean is_default_nickname;
    }
    @Data
    private static class Properties{
        public String nickname;
        public String profile_image;
        public String thumbnail_image;
    }
    @Data
    private static class KakaoProfileResponse{
        public long id;
        public Date connected_at;
        public Properties properties;
        public KakaoAccount kakao_account;
    }

    //------------------------
    //카카오 친구 메시지 보내기
    //------------------------
    @Data
    private static class Element{
        public String profile_nickname;
        public String profile_thumbnail_image;
        public boolean allowed_msg;
        public Object id;
        public String uuid;
        public boolean favorite;
    }
    @Data
    private static class KakaoFriendsResponse {
        public ArrayList<Element> elements;
        public int total_count;
        public Object after_url;
        public int favorite_count;
    }
}
