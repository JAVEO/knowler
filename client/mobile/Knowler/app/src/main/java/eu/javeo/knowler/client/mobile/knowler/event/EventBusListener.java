package eu.javeo.knowler.client.mobile.knowler.event;

public interface EventBusListener<T> {

	void onEvent(T event);
}
