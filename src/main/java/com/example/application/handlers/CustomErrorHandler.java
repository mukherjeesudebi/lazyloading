package com.example.application.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.server.ErrorEvent;
import com.vaadin.flow.server.ErrorHandler;

public class CustomErrorHandler implements ErrorHandler {

	private static final Logger logger = LoggerFactory.getLogger(CustomErrorHandler.class);

	@Override
	public void error(ErrorEvent event) {
		logger.error("Something wrong happened", event.getThrowable());
		if (UI.getCurrent() != null) {
			UI.getCurrent().access(() -> {
				ConfirmDialog confirmDialog = new ConfirmDialog();
				confirmDialog.setHeader("Runtime Exception");
				confirmDialog.setText("An internal error has occurred.");
				confirmDialog.setWidth("400px");
				confirmDialog.setHeight("200px");
				confirmDialog.open();
				confirmDialog.setConfirmText("ok");
			});
		}

	}

}
