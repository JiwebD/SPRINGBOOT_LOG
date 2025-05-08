package com.example.demo.domain.mapper;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
@MapperScan
class MemoMapperTest {

    @Autowired
    private MemoMapper memoMapper;

    @Test
    public void t1() throws Exception{
        MemoDto memoDto = new MemoDto(444, "a", "a", LocalDateTime.now(), null);
        memoMapper.insert(memoDto);
        System.out.println(memoDto);
    }

    @Test
    public void t2() throws Exception{
        MemoDto memoDto = new MemoDto(555, "a", "a", LocalDateTime.now(), null);
        memoMapper.insertXml(memoDto);
        System.out.println(memoDto);
    }

}