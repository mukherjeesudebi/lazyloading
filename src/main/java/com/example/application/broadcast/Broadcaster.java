package com.example.application.broadcast;

import java.util.LinkedList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import com.example.application.dto.PersonDTO;
import com.vaadin.flow.shared.Registration;

public class Broadcaster {
	 static Executor executor = Executors.newSingleThreadExecutor();

	    static LinkedList<Consumer<PersonDTO>> listeners = new LinkedList<>();

	    public static synchronized Registration register(
	            Consumer<PersonDTO> listener) {
	        listeners.add(listener);

	        return () -> {
	            synchronized (Broadcaster.class) {
	                listeners.remove(listener);
	            }
	        };
	    }

	    public static synchronized void broadcast(PersonDTO person) {
	        for (Consumer<PersonDTO> listener : listeners) {
	            executor.execute(() -> listener.accept(person));
	        }
	    }
}
