package com.elasticsearch.demo.repository.service;

import com.elasticsearch.demo.model.Customer;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

@Service
public class CustomerRepoService {

    @Autowired
    ElasticsearchTemplate elasticsearchTemplate;

    public AggregatedPage<Customer> findCustomerByAge(Integer age) {
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder()
                .withIndices("customer_index")
                .withFields("age","id")
                .addAggregation(AggregationBuilders.filter("customerAge",
                        QueryBuilders.matchQuery("age", age))
                        .subAggregation(AggregationBuilders.count("customers").field("id.keyword")));
        SearchQuery searchQuery = nativeSearchQueryBuilder.build();
        return elasticsearchTemplate.queryForPage(searchQuery, Customer.class);


    }

}
