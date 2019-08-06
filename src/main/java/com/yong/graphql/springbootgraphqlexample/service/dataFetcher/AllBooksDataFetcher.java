package com.yong.graphql.springbootgraphqlexample.service.dataFetcher;

import java.util.List;

import com.yong.graphql.springbootgraphqlexample.model.Book;
import com.yong.graphql.springbootgraphqlexample.repository.BookRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

/**
 * AllBooksDataFetcher
 * 
 * @param <Book>
 */
@Component
public class AllBooksDataFetcher implements DataFetcher<List<Book>> {

    @Autowired
    BookRepository BookRepository;

    @Override
    public List<Book> get(DataFetchingEnvironment environment) {
        return BookRepository.findAll();
	}

    
}