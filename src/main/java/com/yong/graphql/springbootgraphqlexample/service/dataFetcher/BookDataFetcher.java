package com.yong.graphql.springbootgraphqlexample.service.dataFetcher;

import com.yong.graphql.springbootgraphqlexample.model.Book;
import com.yong.graphql.springbootgraphqlexample.repository.BookRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

/**
 * BookDataFetcher
 */
@Component
public class BookDataFetcher implements DataFetcher<Book>{

    @Autowired
    BookRepository bookRepository;

    @Override
    public Book get(DataFetchingEnvironment environment) {
        String isn = environment.getArgument("id");
        return bookRepository.findById(isn).get();
	}

}