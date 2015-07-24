package eu.javeo.knowler.client.mobile.knowler.model;

/**
 * Created by rjuzaszek on 24.07.15.
 */
public class SlideElement {
    //dane
    private int id;
    private String url;
    private int time;

    //konstruktory
    public SlideElement() {
    }

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

    //metody dostępowe
    int getId() {
        return id;
    }
    String getUrl() {
        return url;
    }
    int getTime() {
        return time;
    }

    void setId(int id) {
        this.id = id;
    }
    void setUrl(String url) {
        this.url = url;
    }
    void setTime(int time) {
        this.time = time;
    }
}