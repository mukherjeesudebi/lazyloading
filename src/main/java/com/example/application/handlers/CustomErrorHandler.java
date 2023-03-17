package  com.example.application.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.server.ErrorEvent;
import com.vaadin.flow.server.ErrorHandler;

public class CustomErrorHandler implements ErrorHandler {

	private static final Logger logger = LoggerFactory.getLogger(CustomErrorHandler.class);

	@Override
	public void error(ErrorEvent event) {
		logger.error("Something wrong happened", event.getThrowable());
		if (UI.getCurrent() != null) {
			UI.getCurrent().access(() -> {
				Notification notification = Notification.show("An internal error has occurred.", 500, Position.MIDDLE);
		        notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
			});
		}

	}

}
