package gallery;

import android.graphics.Bitmap;

public class ImageItem {
	private Bitmap image;
	private String title;
	private int rowid;

	public ImageItem() {
		image = null;
		title = null;
	}
	
	public ImageItem(int rowid) {
		this.rowid = rowid;
	}

	public ImageItem(Bitmap image, String title) {
		this.image = image;
		this.title = title;
	}

	public void setId(int id) {
		rowid = id;
	}

	public int getId() {
		return rowid;
	}

	public Bitmap getImage() {
		return image;
	}

	public void setImage(Bitmap image) {
		this.image = image;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
