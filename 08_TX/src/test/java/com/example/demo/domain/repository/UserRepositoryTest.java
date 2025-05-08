package com.example.demo.domain.repository;

import com.example.demo.domain.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    //기본 CRUD TEST
    @Test

    public void t1() throws Exception{
        //INSERT
        User user =
                User.builder()
                        .username("김지성")
                        .password("1234")
                        .role("RUEL_USER")
                        .build();
//        userRepository.save(user);

//        //UPDATE
//        user.setPassword("12341234");
//        user.setRole("ROLE_MANAGER");
//        userRepository.save(user);

        //DELETE
        userRepository.deleteById("김지성");
    }

    @Test
    public void t2() throws Exception{
//        List<User> list = userRepository.selectByRole("ROLE_USER");
//        list.stream().forEach(System.out::println);

        List<User> list = userRepository.selectAllLikeUsername("1");
        list.stream().forEach(System.out::println);
    }



}