package com.chakrar.sample.books;

import java.util.List;

import org.springframework.data.couchbase.core.query.N1qlPrimaryIndexed;
import org.springframework.data.couchbase.core.query.N1qlSecondaryIndexed;
import org.springframework.data.couchbase.core.query.ViewIndexed;
import org.springframework.data.couchbase.repository.CouchbasePagingAndSortingRepository;


@ViewIndexed(designDoc = "book")
@N1qlPrimaryIndexed
@N1qlSecondaryIndexed(indexName = "bookSecondaryIndex")
public interface BookStoreRepository extends CouchbasePagingAndSortingRepository<Book, Long> {

	List<Book> findAll();	
	
	List<Book> findByAuthor(String author);
	
	List<Book> findByTitleStartsWithIgnoreCase(String title);
	
	List<Book> findByCategory(String category);
}
