package com.example.demo.config.auth;

import com.example.demo.config.auth.provider.KakaoUserInfo;
import com.example.demo.config.auth.provider.OAuth2UserInfo;
import com.example.demo.domain.dto.UserDto;
import com.example.demo.domain.entity.User;
import com.example.demo.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Map;
import java.util.Optional;

@Service
public class PrincipalDetailsOAuth2Service extends DefaultOAuth2UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        System.out.println("PrincipalDetailsOAuth2Service's loadUser invoke..");
//        System.out.println("userRequest : " + userRequest);
//        System.out.println("userRequest.getClientRegistration() :"+ userRequest.getClientRegistration());
//        System.out.println("userRequest.getAccessToken() : "+ userRequest.getAccessToken());
//        System.out.println("userRequest.getAdditionalParameters() : "+ userRequest.getAdditionalParameters());
//        System.out.println("userRequest.getAccessToken().getTokenValue() : "+ userRequest.getAccessToken().getTokenValue());
//        System.out.println("userRequest.getAccessToken().getTokenType().getValue() : "+ userRequest.getAccessToken().getTokenType().getValue());
//        System.out.println("userRequest.getAccessToken().getScopes() : "+ userRequest.getAccessToken().getScopes());

        //OAuth2UserInfo
        OAuth2User oAuth2User = super.loadUser(userRequest);
        System.out.println("oAuth2User : " + oAuth2User);
        System.out.println("getAttributes : " + oAuth2User.getAttributes());

        OAuth2UserInfo oAuth2UserInfo = null;

        //카카오 로그인시
        Map<String, Object> attributes = oAuth2User.getAttributes();
        Long id = (Long) attributes.get("id");
        LocalDateTime connected_at = OffsetDateTime.parse(attributes.get("connected_at").toString()).toLocalDateTime();
        Map<String, Object> properties = (Map<String, Object>) attributes.get("properties");
        Map<String, Object> kakao_account = (Map<String, Object>)attributes.get("kakao_account");
        System.out.println("id :" + id);
        System.out.println("connected_at :" + connected_at);
        System.out.println("properties :" + properties);
        oAuth2UserInfo = new KakaoUserInfo(id, connected_at, properties, kakao_account);
        System.out.println("oAuth2UserInfo : " + oAuth2UserInfo);

        //네이버 로그인시
        //구글 로그인시

        
        // 최초 로그인시 로컬계정 DB 저장 처리
        String username = oAuth2UserInfo.getProvider()+"_"+oAuth2UserInfo.getProviderId();
        String password = passwordEncoder.encode("1234");
        Optional<User> userOptional = userRepository.findById(username);
        //UserDto 생성 (이유 : PrincipalDetails에 포함)
        //UserEntity 생성 (이유 : 최초 로그인시 DB 저장용도)
        UserDto userDto = null;
        if(userOptional.isEmpty()){
            //최초 로그인(Dto , Entity)
            userDto = UserDto   .builder()
                                .username(username)
                                .password(password)
                                .role("ROLE_USER")
                                .build();
            User user = userDto.toEntity();
            userRepository.save(user); //계정 등록
        }else{
            //기존 유지 존재(Dto)
            User user = userOptional.get();
            userDto = UserDto.toDto(user);
        }



        return oAuth2User;
    }
}
