package com.example.demo.controller;

import com.example.demo.domain.dto.MemoDto;
import com.example.demo.domain.entity.Memo;
import com.example.demo.domain.repository.MemoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@Slf4j
@RequestMapping("/th")  //templates 기본경로에서 /th
public class ThymeleafTestController {

    @Autowired
    private MemoRepository memoRepository;

    @GetMapping("/test1")
    public void test1(Model model){
        log.info("GET /th/test1....");
        model.addAttribute("name","hong");
        MemoDto memo = new MemoDto();
        memo.setId(111);
        memo.setText("aaa");
        memo.setWriter("aaa@naver.com");
        model.addAttribute("memo",memo);
        
        //분기처리실습
        model.addAttribute("isAuth","false");

        //반복문 실습
        List<Memo> memoList = memoRepository.findAll();
        model.addAttribute("memoList",memoList);

    }

    @GetMapping("/param1")
    public void param1(@ModelAttribute MemoDto memoDto){
        log.info("GET /th/param1..." + memoDto);
    }

    @GetMapping("/param2/{id}/{text}/{writer}")
    public void param2(@ModelAttribute MemoDto memoDto){
        log.info("GET /th/param2..." + memoDto);
    }

    @GetMapping("/test2")
    public void test2(){
        log.info("GET /th/test2...");
    }

}
