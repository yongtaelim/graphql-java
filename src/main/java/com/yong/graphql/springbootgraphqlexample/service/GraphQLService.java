package com.yong.graphql.springbootgraphqlexample.service;

import java.io.File;
import java.io.IOException;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import com.yong.graphql.springbootgraphqlexample.model.Book;
import com.yong.graphql.springbootgraphqlexample.repository.BookRepository;
import com.yong.graphql.springbootgraphqlexample.service.dataFetcher.AllBooksDataFetcher;
import com.yong.graphql.springbootgraphqlexample.service.dataFetcher.BookDataFetcher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;

/**
 * GraphQLService
 */
@Service
public class GraphQLService {

    @Autowired
    BookRepository bookRepository;

    @Value("classpath:books.graphql")
    Resource resource;

    private GraphQL graphQL;
    @Autowired
    private AllBooksDataFetcher allBooksDataFetcher;
    @Autowired
    private BookDataFetcher bookDataFetcher;

    // load schema at application start up
    @PostConstruct
    private void loadSchema() throws IOException {

        //Load Books into the Book Repository
        loadDataIntoHSQL();

        // get the schema
        File schemaFile = resource.getFile();
        // parse schema
        TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(schemaFile);
        RuntimeWiring wiring = buildRuntimeWiring();
        GraphQLSchema schema = new SchemaGenerator().makeExecutableSchema(typeRegistry, wiring);
        graphQL = GraphQL.newGraphQL(schema).build();
    }

    private void loadDataIntoHSQL() {

        Book book1 = new Book();
        book1.setIsn("123");
        book1.setTitle("Book of Clouds");
        book1.setPublisher("Kindle Edition");
        book1.setAuthors(new String[] {"Chloe Aridjis"});
        book1.setPublishedDate("Nov 2017");

        Book book2 = new Book();
        book2.setIsn("124");
        book2.setTitle("Cloud Arch & Engineering");
        book2.setPublisher("Orielly");
        book2.setAuthors(new String[] {"Peter", "Sam"});
        book2.setPublishedDate("Jan 2015");
                    
        Book book3 = new Book();
        book3.setIsn("125");
        book3.setTitle("Java 9 Programming");
        book3.setPublisher("Orielly");
        book3.setAuthors(new String[] {"Venkat", "Ram"});
        book3.setPublishedDate("Dec 2016");

        Stream.of(book1, book2, book3
        ).forEach(book -> {
            bookRepository.save(book);
        });
    }

    private RuntimeWiring buildRuntimeWiring() {
        return RuntimeWiring.newRuntimeWiring()
                // select
                .type("Query", typeWiring -> typeWiring
                        .dataFetcher("allBooks", allBooksDataFetcher)
                        .dataFetcher("book", bookDataFetcher))
                // insert, update, delete
                // .type("Mutation")       
                .build();
    }


    public GraphQL getGraphQL() {
        return graphQL;
    }
}