package eu.javeo.knowler.client.mobile.knowler.event.youtube;

import eu.javeo.knowler.client.mobile.knowler.event.ApplicationEvent;

public class BufferingEvent implements ApplicationEvent {

	private boolean isBuffering;

	public BufferingEvent(boolean isBuffering) {
		this.isBuffering = isBuffering;
	}

	public boolean isBuffering() {
		return isBuffering;
	}
}
