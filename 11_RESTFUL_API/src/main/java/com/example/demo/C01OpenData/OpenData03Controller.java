package com.example.demo.C01OpenData;



import com.example.demo.C01OpenData.bus.BUSResult;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/openData")
public class OpenData03Controller {

    @GetMapping("/bus/realtime")
    public void bus_realtime() throws UnsupportedEncodingException {
        String url = "https://apis.data.go.kr/6270000/dbmsapi01/getRealtime";
        String serviceKey =  "";
        String bsId = "7001001600";
        String routeNo = "649";

        URI uri = UriComponentsBuilder
                .fromHttpUrl(url)
                .queryParam("serviceKey", URLEncoder.encode(serviceKey, "UTF-8")) // 인코딩 꼭 필요
                .queryParam("bsId", bsId)
                .queryParam("routeNo", routeNo)
                .build(true) // 자동 인코딩 방지 → true 설정 중요
                .toUri();

        RestTemplate rt = new RestTemplate();
        ResponseEntity<BUSResult> response = rt.exchange(uri, HttpMethod.GET,null,BUSResult.class);
        System.out.println(response.getBody());
        // 가공처리

        //

        //
    }

}
