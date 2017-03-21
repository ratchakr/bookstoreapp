package com.chakrar.sample.books;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.json.JsonObject;


@Service
public class BookStoreService {

	private static final Logger log = LoggerFactory.getLogger(BookStoreService.class);

	private Bucket bucket;
	
	@Autowired
	BookStoreRepository repo;

	/**
	 * @return the bucket
	 */
	public Bucket getBucket() {
		return bucket;
	}

	public BookStoreService(Bucket bucket) {
		this.bucket = bucket;
		log.info("******** Bucket :: = " + bucket.name());
	}

	/**
	 * Create a user account.
	 */
	public void deleteAll() {
		log.info("  Calling deleteAll ");
		try {
			repo.deleteAll();;
			log.info(" Data gone from DB ");
		} catch (Exception e) {
			log.error(" !!!!!!!!  ERROR   !!!!!!!!!");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Create a user account.
	 */
	public void save(final Book book) {
		log.info("   Saving bool with title===" + book.getTitle());
		try {
			repo.save(book);
			log.info(" Book Saved into DB");
		} catch (Exception e) {
			log.error(" !!!!!!!!  ERROR   !!!!!!!!!");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public List<Book> findByAuthor (String author) {
		return repo.findByAuthor(author);
	}
	
	
	public List<Book> findByTitle (String title) {
		return repo.findByTitleStartsWithIgnoreCase(title);
	}
	
	public List<Book> findByCategory (String category) {
		return repo.findByCategory(category);
	}
	
	public List<Book> findAll () {
		
		log.info(" Finding all books ");
		List<Book> books = repo.findAll();
		
		log.info(" Size =  " + books.size());
		
		return books;
	}
	
	
	/**
	 * 
	 * @param author
	 * @param from
	 * @param to
	 * @param title
	 * @param category
	 * @param optimize
	 * @return
	 */
/*	public List<Book> findByCriteria(String author, String isbn, String title, String category,
			boolean optimize) {

		
		Statement statement = select("author", "category", "isbn", "title").from(i(bucket.name())).
				where(x("title").eq(x("$title"))
					.and(x("author").eq(x("$author")))
					.and(x("category").eq(x("$category")))
					.and(x("isbn").eq(x("$isbn"))));
		
		JsonObject placeholderValues = JsonObject.create().put("title", title).put("author", author).put("category", category).put("isbn", isbn);
		
		List<Book> bookList = new ArrayList<Book>();
		Book book = null;

		N1qlParams params = N1qlParams.build().adhoc(false);
		N1qlQuery query = N1qlQuery.simple("select count(*) from `books`", params);
		
		bucket.query(query);
		
		for (N1qlQueryRow row : bucket.query(query)) {
			System.out.println("******* count query result =  "+row.value());
		}
		
		ParameterizedN1qlQuery pnq = N1qlQuery.parameterized(statement, placeholderValues);

		List<N1qlQueryRow> ret = bucket.query(pnq).allRows();
		
		if (null != ret) {
			System.out.println("*****  return  ====       "+ ret.size());
		}
		JsonObject rowJson = null;
		for (N1qlQueryRow row : bucket.query(pnq)) {
			rowJson = row.value();
			log.info(" RATNO rowJson === " + rowJson);
			try {
				
				book = JacksonTransformers.MAPPER.readValue(rowJson.toString(), Book.class);
				log.info(" got the book = " + book);
				log.info(" isbn = " + book.getIsbn());
			} catch (JsonParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			bookList.add(book);
		}
		return bookList;
	}*/

	/**
	 * 
	 * @param book
	 */
	public void store(Book book) {
		log.info("   Store called with title===" + book.getTitle());
		JsonObject data = JsonObject.create().put("type", "book").put("title", book.getTitle())
				.put("author", book.getAuthor()).put("isbn", book.getIsbn()).put("category", book.getCategory());
		String id = UUID.randomUUID().toString();
		JsonDocument doc = JsonDocument.create(""+id, data);

		try {
			log.info(" data before insert = " + data);
			bucket.insert(doc);
			JsonObject responseData = JsonObject.create().put("success", true).put("data", data);

			log.info(" data after insert = " + responseData);

		} catch (Exception e) {
			log.error("Error creating Book ");
			e.printStackTrace();
		}
		
	}

	

}
