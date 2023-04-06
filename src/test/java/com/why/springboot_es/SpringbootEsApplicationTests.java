package com.why.springboot_es;

import com.why.springboot_es.pojo.es.EsBlog;
import com.why.springboot_es.repository.es.EsBlogRepository;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringbootEsApplicationTests {
    @Autowired
    EsBlogRepository esBlogRepository;
    @Test
    void contextLoads() {
    }


//    @Test
//    public void testEsRepository(){
//        Iterable<EsBlog> all = esBlogRepository.findAll();
//        for (EsBlog esBlog : all) {
//            System.out.println("----"+esBlog.getTitle());
//        }
//
//
//    }
}
