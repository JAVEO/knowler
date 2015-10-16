package eu.javeo.knowler.client.mobile.knowler.event.youtube;

import eu.javeo.knowler.client.mobile.knowler.event.ApplicationEvent;

public class SeekToEvent implements ApplicationEvent {

	private int millis;

	public SeekToEvent(int millis) {
		this.millis = millis;
	}

	public int getMillis() {
		return millis;
	}
}
