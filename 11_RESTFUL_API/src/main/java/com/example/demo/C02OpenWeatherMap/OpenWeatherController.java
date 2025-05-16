package com.example.demo.C02OpenWeatherMap;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.ArrayList;

@RestController
@Slf4j
@RequestMapping("/open/weather")
public class OpenWeatherController {
    //https://api.openweathermap.org/data/2.5/weather?lat={lat}&lon={lon}&appid={API key}
    @GetMapping("/get/{lat}/{lon}")
    public ResponseEntity<Root> get(@PathVariable String lat , @PathVariable String lon) throws UnsupportedEncodingException {
        log.info("GET /open/weather/get....");
        String url="https://api.openweathermap.org/data/2.5/weather";
//        String lat="";
//        String lon="";
        String serviceKey="";
        URI uri = UriComponentsBuilder
                .fromHttpUrl(url)
                .queryParam("appid", URLEncoder.encode(serviceKey, "UTF-8")) // 인코딩 꼭 필요
                .queryParam("lat", lat)
                .queryParam("lon", lon)
                .build(true) // 자동 인코딩 방지 → true 설정 중요
                .toUri();

        //요청 헤더 x
        //요청 바디 x

        //요청 -> 응답
        RestTemplate rt = new RestTemplate();
        ResponseEntity<Root> response = rt.exchange(uri, HttpMethod.GET,null,Root.class);
        System.out.println(response.getBody());

        return new ResponseEntity<>(response.getBody(), HttpStatus.OK);






    }

    // import com.fasterxml.jackson.databind.ObjectMapper; // version 2.11.1
// import com.fasterxml.jackson.annotation.JsonProperty; // version 2.11.1
/* ObjectMapper om = new ObjectMapper();
Root root = om.readValue(myJsonString, Root.class); */
    @Data
    private static class Clouds{
        public int all;
    }
    @Data
    private static class Coord{
        public double lon;
        public double lat;
    }
    @Data
    private static class Main{
        public double temp;
        public double feels_like;
        public double temp_min;
        public double temp_max;
        public int pressure;
        public int humidity;
        public int sea_level;
        public int grnd_level;
    }
    @Data
    private static class Root{
        public Coord coord;
        public ArrayList<Weather> weather;
        public String base;
        public Main main;
        public int visibility;
        public Wind wind;
        public Clouds clouds;
        public int dt;
        public Sys sys;
        public int timezone;
        public int id;
        public String name;
        public int cod;
    }
    @Data
    private static class Sys{
        public int type;
        public int id;
        public String country;
        public int sunrise;
        public int sunset;
    }
    @Data
    private static class Weather{
        public int id;
        public String main;
        public String description;
        public String icon;
    }
    @Data
    private static class Wind{
        public double speed;
        public int deg;
    }


}
