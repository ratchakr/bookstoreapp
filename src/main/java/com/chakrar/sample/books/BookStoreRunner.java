package com.chakrar.sample.books;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BookStoreRunner implements CommandLineRunner {
	

	private static final Logger log = LoggerFactory.getLogger(BookStoreRunner.class);
	
	@Autowired
	BookStoreService bs;
	
	

	
	@Override
	public void run(String... arg0) throws Exception {
		log.info("...Inside BookStoreRunner...");
		log.info(" bucket created = "+ bs.getBucket().name());
		
		log.info("Delete any existing data first");
		bs.deleteAll();
		
		Book book = new Book ("History of Mankind", "Gabriel Garcia", "ISBN123", "History");
		Book book2 = new Book ("War and Turpentine", "Stefan Hertmans", "ISBN222", "Fiction");
		Book book3 = new Book ("The Night Gardener", "Eric Fan", "ISBN333", "Kids Books");
		Book book4 = new Book ("The Immortal Irishman", "Timothy Egan", "ISBN444", "History");
		Book book5 = new Book ("Grit: The Power of Passion and Perseverance", "Angela Duckworth", "ISBN555", "Business");
		Book book6 = new Book ("The Kite Runner", "Khaled Hosseini", "ISBN663", "Fiction");
		Book book7 = new Book ("Breaking Blue", "Timothy Egan", "ISBN777", "Thriller");
		try {
			bs.save(book);
			bs.save(book2);
			bs.save(book3);
			bs.save(book4);
			bs.save(book5);
			bs.save(book6);
			bs.save(book7);
		} catch (Exception e) {
			log.error(" !!!!!!!!!!!!      ERROR         !!!!!!!!!!!!!");
			e.printStackTrace();
		}
		
		// let's find some books
		
		List<Book> books = bs.findAll();
		
		for (Book b : books) {
			log.info("Book Details = "+ b);
		}
		
		
        List<Book> booksByAUthor = bs.findByAuthor("Timothy Egan");
        
        for (Book b : booksByAUthor) {
			log.info("Books by Timothy Egan = "+ b);
		}
        
        
        List<Book> booksByTitle = bs.findByTitle("The");
        
        for (Book b : booksByTitle) {
			log.info("Book Starting with title 'The' = "+ b);
		}
		
        
        List<Book> booksByCategory = bs.findByCategory("Fiction");
        
        for (Book b : booksByCategory) {
			log.info("Book in Fiction = "+ b);
		}        
        

		
		
	}
	

}
