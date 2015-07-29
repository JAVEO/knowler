package eu.javeo.knowler.client.mobile.knowler.model;

/**
 * Created by rjuzaszek on 24.07.15.
 */
public class SlideElement {

	private int id;

	private String url;

	private int time;

	public SlideElement(int id, String url, int time) {
		this.id = id;
		this.url = url;
		this.time = time;
	}

	public SlideElement(SlideElement s) {
		this.id = s.getId();
		this.url = s.getUrl();
		this.time = s.getTime();
	}

	int getId() {
		return id;
	}

	void setId(int id) {
		this.id = id;
	}

	String getUrl() {
		return url;
	}

	void setUrl(String url) {
		this.url = url;
	}

	int getTime() {
		return time;
	}

	void setTime(int time) {
		this.time = time;
	}
}