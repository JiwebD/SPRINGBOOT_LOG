package com.example.demo.domain.repository;

import com.example.demo.domain.entity.Book;
import com.example.demo.domain.entity.Lend;
import com.example.demo.domain.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@SpringBootTest
class LendRepositoryTest {

    @Autowired
    private LendRepository lendRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void t1() throws Exception {

        //저장되어있는 도서코드를 가지는 Book
        Book book = bookRepository.findById(333l).get();
        //저장되어있는 유저정보를 가지는 User
        User user = userRepository.findById("user1").get();

        Lend lend = new Lend();
        lend.setBook(book);
        lend.setUser(user);
        lendRepository.save(lend);


    }

    @Test
    public void t2() throws Exception {
        Lend lend = lendRepository.findById(1L).get();
        Book book = bookRepository.findById(444L).get();
        lend.setBook(book);
        lendRepository.save(lend);
    }

    @Test
    public void t3() throws Exception {
        lendRepository.deleteById(1L);
    }

    @Test
    public void t4() throws Exception {
//        List<Lend> list = lendRepository.findLendsByUser("user1");
//        list.stream().forEach(System.out::println);

        List<Lend> list = lendRepository.findLendsByUserAndBook();
        list.stream().forEach(System.out::println);
    }

    @Test
    @Transactional(rollbackFor = Exception.class) //LAZY Option 사용시 Transactional사용해야함.
    public void t5() throws Exception {
        System.out.println("------------------ FETCH 방식 테스트 시작 ------------------");
        Optional<Lend> lendOptional = lendRepository.findById(2L);
        System.out.println("------------------ findById(2L) ------------------");
        Lend lend = lendOptional.get();
        System.out.println("------------------ getUser() ------------------");
        User user = lend.getUser(); // LAZY Option 사용시 해당 시점에서 쿼리 실행
        System.out.println(user);
        System.out.println("------------------ FETCH 방식 테스트 종료 ------------------");


    }


}