package com.elasticsearch.demo.service.serviceimpl;

import com.elasticsearch.demo.model.Customer;
import com.elasticsearch.demo.repository.CustomerRepository;
import com.elasticsearch.demo.repository.service.CustomerRepoService;
import com.elasticsearch.demo.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.bucket.filter.InternalFilter;
import org.elasticsearch.search.aggregations.metrics.valuecount.InternalValueCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerRepoService customerRepoService;

    @Autowired
    ElasticsearchTemplate elasticsearchTemplate;

    @Override
    public void saveAllCustomers(List<Customer> customers) {
        customerRepository.saveAll(customers);
    }

    @Override
    public Iterable<Customer> findAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public List<Customer> findByFirstname(String firstName, int age) {
//        return customerRepository.findByFirstName(firstName);
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        boolQueryBuilder.must(QueryBuilders.matchQuery("firstName", firstName));
        boolQueryBuilder.should(QueryBuilders.termQuery("age", age));
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withIndices("customer_index")
                .withQuery(boolQueryBuilder)
                .withFields("firstName","lastName")
                .build();
        log.info(searchQuery.getQuery() + "");
        List<Customer> customers = elasticsearchTemplate.queryForList(searchQuery, Customer.class);
        return customers;
    }

    @Override
    public Long getCustomerByAge(Integer age) {
        AggregatedPage<Customer> customers = customerRepoService.findCustomerByAge(age);
        InternalFilter customerAge = customers.getAggregations().get("customerAge");
        InternalValueCount count = customerAge.getAggregations().get("customers");
        return count.getValue();
    }
}
