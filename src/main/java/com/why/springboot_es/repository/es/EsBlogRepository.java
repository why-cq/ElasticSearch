package com.why.springboot_es.repository.es;

import com.why.springboot_es.pojo.es.EsBlog;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface EsBlogRepository extends ElasticsearchRepository<EsBlog,Integer> {


}
