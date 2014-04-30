package data;

public class Racer extends People {
	String town;
	boolean sex;
	boolean trailer;
	boolean slalom;
	boolean drag;

	public Racer() {

	}

	public boolean getSex() {
		return sex;
	}

	public String getTown() {
		return town;
	}

	public boolean getTrailer() {
		return trailer;
	}

	public boolean getSlalom() {
		return slalom;
	}

	public boolean getDrag() {
		return drag;
	}

	public void setTown(String town) {
		this.town = town;
	}

	public void setSex(boolean sex) {
		this.sex = sex;
	}

	public void setTrailer(boolean trailer) {
		this.trailer = trailer;
	}

	public void setSlalom(boolean slalom) {
		this.slalom = slalom;
	}

	public void setDrag(boolean drag) {
		this.drag = drag;
	}
}
