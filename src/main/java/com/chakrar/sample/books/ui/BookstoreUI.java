package com.chakrar.sample.books.ui;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.vaadin.viritin.button.ConfirmButton;
import org.vaadin.viritin.fields.MTable;
import org.vaadin.viritin.fields.MTextField;
import org.vaadin.viritin.fields.MValueChangeEvent;
import org.vaadin.viritin.fields.MValueChangeListener;
import org.vaadin.viritin.form.AbstractForm.DeleteHandler;
import org.vaadin.viritin.form.AbstractForm.ResetHandler;
import org.vaadin.viritin.form.AbstractForm.SavedHandler;
import org.vaadin.viritin.layouts.MVerticalLayout;

import com.chakrar.sample.books.Book;
import com.chakrar.sample.books.BookStoreRepository;
import com.chakrar.sample.books.BookstoreEditor;
import com.vaadin.annotations.Theme;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@SpringUI
@Theme("valo")
public class BookstoreUI extends UI {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final BookStoreRepository repo;

	private final BookstoreEditor editor;

	private final MTable<Book> grid;
	
	private final TextField filter;

	private final Button addNewBtn;
	

	   private TextField filterByName = new MTextField()
	            .withInputPrompt("Filter by title");
	   //private Button addNew = new MButton(FontAwesome.PLUS, this::add);
	    //private Button edit = new MButton(FontAwesome.PENCIL_SQUARE_O, this::edit);
	    private Button delete = new ConfirmButton(FontAwesome.TRASH_O,
	            "Are you sure you want to delete the entry?", this::remove);
	
	static final Logger log = LoggerFactory.getLogger(BookstoreUI.class);

	
	@Autowired
	public BookstoreUI(BookStoreRepository repo, BookstoreEditor editor) {
		log.info(" *******       Inside  BookstoreUI      ********* ");
		this.repo = repo;
		this.editor = editor;
		this.grid = new MTable<>(Book.class).withProperties("title", "author",  "isbn", "category").withHeight("300px");
		
		
		this.grid.setBeans(repo.findAll()); 
		this.addNewBtn = new Button(FontAwesome.PLUS_CIRCLE);
		this.filter = new TextField();
		
	}


	@Override
	protected void init(VaadinRequest request) {
		
		grid.addMValueChangeListener(new MValueChangeListener<Book>() {
			@Override
			public void valueChange(MValueChangeEvent<Book> e) {
				if (e.getValue() == null) {
					editor.setVisible(false);
				} else {
					editor.setEntity(e.getValue());
				}
			}
		});

		// Instantiate and edit new Expense the new button is clicked
		addNewBtn.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent e) {
				editor.setEntity(new Book("", "", "", ""));
			}
		});

		// Listen changes made by the editor, refresh data from backend
		editor.setSavedHandler(new SavedHandler<Book>() {
			@Override
			public void onSave(Book ed) {
				repo.save(ed);
				listBooks(null);
				editor.setVisible(false);
			}
		});

		editor.setResetHandler(new ResetHandler<Book>() {
			@Override
			public void onReset(Book ed) {
				editor.setVisible(false);
				listBooks(null);
			}
		});

		editor.setDeleteHandler(new DeleteHandler<Book>() {
			@Override
			public void onDelete(Book ed) {
				repo.delete(ed);
				listBooks(null);
			}
		});




		
		// build layout
		HorizontalLayout actions = new HorizontalLayout(filterByName, addNewBtn, delete); 
		
		VerticalLayout mainLayout = new MVerticalLayout(actions, grid, editor);
		setContent(mainLayout);
		
		actions.setSpacing(true);

		mainLayout.setMargin(true);
		mainLayout.setSpacing(true);
		
		//filter.setInputPrompt("Filter by merchant");
		
		// Replace listing with filtered content when user changes filter
		filterByName.addTextChangeListener(new TextChangeListener() {
			@Override
			public void textChange(TextChangeEvent e) {
				listBooks(e.getText());
			}
		});	
		
		
		// Initialize listing
		listBooks(null);
	}

	
    /*public void add(ClickEvent clickEvent) {
        edit(new Book(null, null, null, null));
    }
    
    public void edit(ClickEvent e) {
        edit(grid.getValue());
    }*/
    
    public void remove(ClickEvent e) {
        repo.delete(grid.getValue());
        grid.setValue(null);
        listBooks(null);
    }

	/**
	 * 
	 * @param title
	 */
	private void listBooks(String title) {
		
		if (StringUtils.isEmpty(title)) {
			grid.setBeans(repo.findAll());
		} else {
			//grid.setBeans(repo.findByTitleStartsWithIgnoreCase(title));
			String likeFilter = "%" + title + "%";
			grid.setRows(repo.findByTitleLikeIgnoreCase(likeFilter));
		}		
		
        
	}
	
	
}
