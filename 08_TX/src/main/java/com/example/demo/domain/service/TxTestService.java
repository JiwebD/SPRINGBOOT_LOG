package com.example.demo.domain.service;

import com.example.demo.domain.dto.MemoDto;
import com.example.demo.domain.entity.Memo;
import com.example.demo.domain.mapper.MemoMapper;
import com.example.demo.domain.repository.MemoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
@Slf4j
public class TxTestService {

    @Autowired
    private MemoMapper memoMapper;

    //@Transactional(rollbackFor = SQLException.class,transactionManager = "dataSourceTransactionManager")
    public void addMemoTx(MemoDto dto) throws Exception	 {
        log.info("MemoService's addMemoTx Call! ");
        memoMapper.insert(dto);//01 정상INSERT
        throw new SQLException();
    }

    //JPA REPOSITORY
    @Autowired
    private MemoRepository memoRepository;

//    @Transactional(rollbackFor = SQLException.class,transactionManager = "jpaTransactionManager")
    public void addMemoTx2(MemoDto dto) throws Exception{
        log.info("MemoService's addMemoTx2 Call!");
        Memo memo = new Memo();
        memo.setId(dto.getId());
        memo.setText(dto.getText());
        memoRepository.save(memo);
        throw new SQLException();
    }
}
