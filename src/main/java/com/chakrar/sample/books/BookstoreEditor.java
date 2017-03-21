/**
 * 
 */
package com.chakrar.sample.books;

import org.vaadin.viritin.form.AbstractForm;
import org.vaadin.viritin.layouts.MFormLayout;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Component;
import com.vaadin.ui.TextField;

/**
 * @author eratnch
 *
 */
@SpringComponent
@UIScope
public class BookstoreEditor extends AbstractForm<Book> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;



	public BookstoreEditor() {
        setVisible(false);
    }

	
    //Fields to edit properties in CustomerExpenseDetail entity 
    TextField author = new TextField("Author");
    
    TextField title = new TextField("Title");
	
    TextField category = new TextField("Category");
    
    TextField isbn = new TextField("ISBN");
    
    

	@Override
	protected Component createContent() {
		return new MFormLayout(author, title, category, isbn, getToolbar());
	}

}
