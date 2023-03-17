package com.example.application.handlers;

import javax.servlet.http.HttpServletResponse;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.ErrorParameter;
import com.vaadin.flow.router.HasErrorParameter;

@Tag(Tag.DIV)
public class RuntimeExceptionHandler extends Component implements HasErrorParameter<RuntimeException> {

	@Override
	public int setErrorParameter(BeforeEnterEvent event, ErrorParameter<RuntimeException> parameter) {
		VerticalLayout layout = new VerticalLayout();
		layout.add(new H2("Some error occurred"), new H2("Add simmilar error handlers for different type of errors"));
        getElement().appendChild(layout.getElement());
		return HttpServletResponse.SC_NO_CONTENT;
	}

}
