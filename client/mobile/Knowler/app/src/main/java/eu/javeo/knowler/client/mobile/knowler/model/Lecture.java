package eu.javeo.knowler.client.mobile.knowler.model;

import android.util.SparseArray;
import java.util.Date;

/**
 * Created by rjuzaszek on 23.07.15.
 */
public class Lecture {

	public SparseArray<SlideElement> mapping;

	private String videoUrl;

	private String title;

	private Date createdDate;

	private String filename;

	public Lecture(String videoUrl, String title, Date createdDate, String filename) {
		this.videoUrl = videoUrl;
		this.title = title;
		this.createdDate = createdDate;
		this.filename = filename;
	}

	public Lecture(Lecture lec) {
		this.videoUrl = lec.getVideoUrl();
		this.title = lec.getTitle();
		this.createdDate = lec.getCreatedDate();
		this.filename = lec.getFilename();

		//mapping = new SparseArray<SlideElement>();
		//for(int i=0; i < lec.mapping.size(); i++) {
		//   mapping.append(lec.mapping.keyAt(i),new );
		//musze sprawdzić czy metoda clone działa do głebokiego kopiowania całego SparseArray
		// }
	}

	String getVideoUrl() {
		return videoUrl;
	}

	void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

	String getTitle() {
		return title;
	}

	void setTitle(String title) {
		this.title = title;
	}

	Date getCreatedDate() {
		return createdDate;
	}

	void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	String getFilename() {
		return filename;
	}

	void setFilename(String filename) {
		this.filename = filename;
	}
}
