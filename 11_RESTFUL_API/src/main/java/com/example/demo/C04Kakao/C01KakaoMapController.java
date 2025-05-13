package com.example.demo.C04Kakao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping("/kakao/map")
public class C01KakaoMapController {
    @GetMapping("/01")
    public void map_01(){
        log.info("GET /kakao/map/01...");
    }
}
