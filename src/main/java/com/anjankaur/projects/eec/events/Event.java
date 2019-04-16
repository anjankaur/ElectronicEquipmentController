package com.anjankaur.projects.eec.events;


import java.util.EventObject;

/*
 * Base class for other events 
 */
public class Event extends EventObject{

	public Event(Object source) {
		super(source);
		
	}


}
