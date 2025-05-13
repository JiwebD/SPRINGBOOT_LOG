package com.example.demo.C01OpenData;

import com.example.demo.C01OpenData.bus.BUSResult;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/openData")
public class OpenData03Controller {

    @GetMapping("/bus/realtime")
    public void bus_realtime(Model model) throws UnsupportedEncodingException {
        String url = "https://apis.data.go.kr/6270000/dbmsapi01/getRealtime";
        String serviceKey = "HauU5iw/VTPuszdg+Y3+wC8FzxKs16gfBMhMYJtewiNUT85RF7xqD11yKfXPY6NePG3YzZd3eqYEaE7uypOriQ==";
        String bsId = "7001001400";
        String routeNo = "609";


        URI uri = UriComponentsBuilder
                .fromHttpUrl(url)
                .queryParam("serviceKey", URLEncoder.encode(serviceKey, "UTF-8"))
                .queryParam("bsId", bsId)
                .queryParam("routeNo",routeNo)
                .build(true)
                .toUri();

        System.out.println(url);


        //요청 헤더
        //요청 바디
        //요청 후 응답값 받기
        RestTemplate rt = new RestTemplate();
        ResponseEntity<BUSResult> response = rt.exchange(uri, HttpMethod.GET ,null, BUSResult.class);
        System.out.println(response.getBody());
        //가공처리

    }



}
