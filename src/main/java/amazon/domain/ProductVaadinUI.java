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

package amazon.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.vaadin.annotations.Theme;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.Sizeable;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 * Created by az on 27/05/2016.
 */
@SpringUI
@Theme("valo")
public class ProductVaadinUI extends UI{

	private final ProductRepository repository;

	private final ProductEditor productEditor;

	private final Grid grid;

	private final TextField filter;

	private final Button addNewBtn;

	@Autowired
	public ProductVaadinUI(ProductRepository repository, ProductEditor productEditor) {
		this.repository = repository;
		this.productEditor = productEditor;
		this.grid = new Grid();
		this.filter = new TextField();
		this.addNewBtn = new Button("New prod");
	}

	@Override
	protected void init(VaadinRequest request) {
		// build layout
		HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn);
		VerticalLayout mainLayout = new VerticalLayout(actions, grid, productEditor);
		setContent(mainLayout);

		// Configure layouts and components
		actions.setSpacing(true);
		mainLayout.setMargin(true);
		mainLayout.setSpacing(true);

		grid.setHeight(300, Sizeable.Unit.PIXELS);
		grid.setColumns("id", "name", "url", "expectedPrice");
		grid.setColumnReorderingAllowed(true);
		grid.setWidth(100, Unit.PERCENTAGE);

		filter.setInputPrompt("Filter by name");

		// Hook logic to components

		// Replace listing with filtered content when user changes filter
		filter.addTextChangeListener(e -> listProducts(e.getText()));

		// Connect selected Product to editor or hide if none is selected
		grid.addSelectionListener(e -> {
			if (e.getSelected().isEmpty()) {
				productEditor.setVisible(false);
			}
			else {
				productEditor.editProduct((Product) grid.getSelectedRow());
			}
		});

		// Instantiate and edit new Product the new button is clicked
		addNewBtn.addClickListener(e -> productEditor.editProduct(new Product("", "", "")));

		// Listen changes made by the editor, refresh data from backend
		productEditor.setChangeHandler(() -> {
			productEditor.setVisible(false);
			listProducts(filter.getValue());
		});

		// Initialize listing
		listProducts(null);
	}

	// tag::listProducts[]
	private void listProducts(String text) {
		if (StringUtils.isEmpty(text)) {
			grid.setContainerDataSource(
					new BeanItemContainer(Product.class, repository.findAll()));
		}
		else {
			grid.setContainerDataSource(new BeanItemContainer(Product.class,
					repository.findByNameStartsWithIgnoreCase(text)));
		}
	}
}
