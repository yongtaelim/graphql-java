package com.yong.graphql.springbootgraphqlexample.repository;

import com.yong.graphql.springbootgraphqlexample.model.Book;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * BookRepository
 */
public interface BookRepository extends JpaRepository<Book, String> {
}