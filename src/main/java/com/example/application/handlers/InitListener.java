package com.example.application.handlers;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.VaadinServiceInitListener;
import com.vaadin.flow.server.VaadinSession;

@Component
public class InitListener implements VaadinServiceInitListener {

	@Override
	public void serviceInit(ServiceInitEvent event) {

		/*
		 * event.getSource().addSessionInitListener(initEvent -> {
		 * LoggerFactory.getLogger(getClass()).
		 * info("A new Session has been initialized!");
		 * initEvent.getSession().setErrorHandler(new CustomErrorHandler()); });
		 */

		event.getSource().addUIInitListener(initEvent -> {
			LoggerFactory.getLogger(getClass()).info("A new UI has been initialized!");
			UI ui = initEvent.getUI();
			VaadinSession session = ui.getSession();
			session.setErrorHandler(new CustomErrorHandler());
		});

	}
}
