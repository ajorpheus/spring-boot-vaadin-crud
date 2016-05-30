/*
 *
 *  * Copyright 2003-2015 Monitise Group Limited. All Rights Reserved.
 *  *
 *  * Save to the extent permitted by law, you may not use, copy, modify,
 *  * distribute or create derivative works of this material or any part
 *  * of it without the prior written consent of Monitise Group Limited.
 *  * Any reproduction of this material must contain this notice.
 *
 */

package amazon;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

/**
 * Created by az on 27/05/2016.
 */
@SpringComponent
@UIScope
public class ProductEditor extends VerticalLayout{

	private final ProductRepository repository;

	private Product product;

	TextField name = new TextField("Product Name");
	TextField url = new TextField("Product Url");
	TextField expectedPrice = new TextField("Expected Price");

	Button save = new Button("Save", FontAwesome.SAVE);
	Button cancel = new Button("Cancel");
	Button delete = new Button("Delete", FontAwesome.TRASH_O);
	CssLayout actions = new CssLayout(save, cancel, delete);

	@Autowired
	public ProductEditor(ProductRepository repository) {
		this.repository = repository;

		addComponents(actions, name, url, expectedPrice);

		save.addClickListener(clickEvent -> repository.save(product));
		cancel.addClickListener(clickEvent -> editProduct(product));
		delete.addClickListener(clickEvent -> repository.delete(product));

		setVisible(false);
	}

	public final void editProduct(Product p) {
		boolean isPersisted = p.getId() != null;

		if (isPersisted){
			this.product = repository.findOne(p.getId());
		} else {
			this.product = p;
		}

		cancel.setVisible(isPersisted);

		BeanFieldGroup.bindFieldsUnbuffered(product, this);
		setVisible(true);
		save.focus();
	}

	public void setChangeHandler(ChangeHandler h) {
		// ChangeHandler is notified when either save or delete
		// is clicked
		save.addClickListener(e -> h.onChange());
		delete.addClickListener(e -> h.onChange());
	}

	public interface ChangeHandler {
		void onChange();
	}
}
