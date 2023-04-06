package com.why.springboot_es.controller;


import com.why.springboot_es.pojo.es.EsBlog;
import com.why.springboot_es.pojo.mysql.MysqlBlog;
import com.why.springboot_es.repository.es.EsBlogRepository;
import com.why.springboot_es.repository.mysql.MysqlBlogRepository;
import org.elasticsearch.index.query.FuzzyQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class IndexContoller {

    @Autowired
    private MysqlBlogRepository mysqlBlogRepository;

    @Autowired
    private EsBlogRepository esBlogRepository;

    @RequestMapping("/")
    public String index() {
        List<MysqlBlog> all = mysqlBlogRepository.findAll();
        System.out.println(all.size());
        return "index.html";
    }

    @RequestMapping("/es")
    public String esindex() {
        System.out.println("进入方法");
        FuzzyQueryBuilder fuzzyQueryBuilder = QueryBuilders.fuzzyQuery("title", "java");
        System.out.println(fuzzyQueryBuilder);
        Iterable<EsBlog> search = esBlogRepository.search(fuzzyQueryBuilder);
        for (EsBlog esBlog : search) {
            System.out.println("我的title是:"+esBlog.getTitle());
        }
        return "index.html";
    }


}
