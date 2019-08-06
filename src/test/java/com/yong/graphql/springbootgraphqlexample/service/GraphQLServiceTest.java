package com.yong.graphql.springbootgraphqlexample.service;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import graphql.ExecutionResult;

/**
 * GraphQLServiceTest
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class GraphQLServiceTest {

    @Autowired
    GraphQLService grapaQLService;

    @Before
    public void setUp() {
        System.out.println("asdf");
    }

    @Test
    public void getGraphQLTest() {
        String query = "{"+
            "allBooks {"+
                "isn"+
            "}"+
        "}";
        ExecutionResult execute = grapaQLService.getGraphQL().execute(query);
        Map<String, Object> executeMap = execute.getData();
        List<Map<String,Object>> books = (List<Map<String, Object>>) executeMap.get("allBooks");
        Map<String, Object> book1 = books.get(0);
        assertEquals(book1.get("isn"), "123");
        Map<String, Object> book2 = books.get(1);
        assertEquals(book2.get("isn"), "124");
        Map<String, Object> book3 = books.get(2);
        assertEquals(book3.get("isn"), "125");
    }

}