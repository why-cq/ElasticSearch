package com.why.springboot_es.controller;


import com.why.springboot_es.pojo.es.EsBlog;
import com.why.springboot_es.pojo.mysql.MysqlBlog;
import com.why.springboot_es.repository.es.EsBlogRepository;
import com.why.springboot_es.repository.mysql.MysqlBlogRepository;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class DataController {
    @Autowired
    MysqlBlogRepository mysqlBlogRepository;
    @Autowired
    EsBlogRepository esBlogRepository;

    @GetMapping("/blogs")
    public Object blog(){
        List<MysqlBlog> mysqlBlogs = mysqlBlogRepository.queryAll();
        return mysqlBlogs;
    }

    @PostMapping("/search")
    public Object search(@RequestBody Param param){
        Map<String, Object> map = new HashMap<>();
        String type = param.getType();
        StopWatch watch = new StopWatch();
        watch.start();
        if(type.equalsIgnoreCase("mysql")){
            List<MysqlBlog> mysqlBlogs = mysqlBlogRepository.queryBlogs(param.getKeyword());
            map.put("list",mysqlBlogs);
        }else if(type.equalsIgnoreCase("es")){
            BoolQueryBuilder builder = QueryBuilders.boolQuery();
            builder.should(QueryBuilders.matchPhraseQuery("title",param.getKeyword()));
            builder.should(QueryBuilders.matchPhraseQuery("content",param.getKeyword()));
            String s = builder.toString();
            log.info(">>> {} <<<",s);
            Page<EsBlog> esBlogs = (Page<EsBlog>) esBlogRepository.search(builder);
            List<EsBlog> content = esBlogs.getContent();
            map.put("list",content);
        }else {
            return ">>> 我不知道该做什么 <<<";
        }
        watch.stop();
        long totalTimeMillis = watch.getTotalTimeMillis();
        map.put("duration",totalTimeMillis);
        return map;
    }

    @Data
    public static class Param{
        //mysql es
        private String type;
        private String keyword;
    }

}
