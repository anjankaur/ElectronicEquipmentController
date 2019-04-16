package com.anjankaur.projects.eec.events;



public class MotionEvent extends Event{
	
	
	private boolean motionDetected = false;

	public MotionEvent(Object source) {
		super(source);
		
	}

	public boolean getMotionDetected() {
		return motionDetected;
	}

	public void setMotionDetected(boolean motionDetected) {
		this.motionDetected = motionDetected;
	}


}
