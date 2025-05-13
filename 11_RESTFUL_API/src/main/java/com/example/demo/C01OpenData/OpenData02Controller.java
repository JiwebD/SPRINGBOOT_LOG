package com.example.demo.C01OpenData;

import com.fasterxml.jackson.annotation.JsonProperty;
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
import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
@RequestMapping("/openData")
public class OpenData02Controller {
    //대구광역시_돌발 교통정보 조회 서비스(신)
    String url = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtNcst";
    String serviceKey = "HauU5iw/VTPuszdg+Y3+wC8FzxKs16gfBMhMYJtewiNUT85RF7xqD11yKfXPY6NePG3YzZd3eqYEaE7uypOriQ==";
    String pageNo = "1";
    String numOfRows = "10";
    String dataType = "JSON";
    String base_date = "20250512";
    String base_time = "0600";
    String nx = "89";
    String ny = "90";

    @GetMapping("/forcast")
    public void forcast(Model model) throws UnsupportedEncodingException {
        log.info("GET /openData/forcast...");
        //01 서버 정보

        URI uri = UriComponentsBuilder
                .fromHttpUrl(url)
                .queryParam("serviceKey", URLEncoder.encode(serviceKey, "UTF-8"))
                .queryParam("pageNo", pageNo)
                .queryParam("numOfRows",numOfRows)
                .queryParam("dataType",dataType)
                .queryParam("base_date",base_date)
                .queryParam("base_time",base_time)
                .queryParam("nx",nx)
                .queryParam("ny",ny)
                .build(true)
                .toUri();

        System.out.println(uri);

        //02 요청 헤더 설정(x)

        //03 요청 바디 설정(x)

        //04 요청 -> 응답 확인
        RestTemplate rt = new RestTemplate();
        ResponseEntity<Root> response =
                rt.exchange(uri, HttpMethod.GET,null, Root.class);
        System.out.println(response);

        //05 기타 가공처리
//        if(response.get)

        //뷰 전달 가공처리
        Root root = response.getBody();
        Response rs = root.getResponse();
        Body body = rs.getBody();
        Items items = body.getItems();
        List<Item> list = items.getItem();
        list.stream().forEach(System.out::println);

        model.addAttribute("list" , list);

    }

    //----------------------------------------


    @Data
    private static class Body{
        public String dataType;
        public Items items;
        public int pageNo;
        public int numOfRows;
        public int totalCount;
    }

    @Data
    private static class Header{
        public String resultCode;
        public String resultMsg;
    }

    @Data
    private static class Item{
        public String baseDate;
        public String baseTime;
        public String category;
        public int nx;
        public int ny;
        public String obsrValue;
    }

    @Data
    private static class Items{
        public ArrayList<Item> item;
    }

    @Data
    private static class Response{
        public Header header;
        public Body body;
    }

    @Data
    private static class Root{
        public Response response;
    }





}
