package com.elasticsearch.demo.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "customer_index",type = "customer")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    @Id
    String id;

    String firstName;

    String lastName;

    String address;

    int age;
}
