package data;

public class Slalom extends People {
	int hiba;
	String ido;
	String vido;

	public Slalom() {

	}

	public void setHiba(int h) {
		hiba = h;
	}

	public void setIdo(String time) {
		ido = time;
	}

	public void setVido(String time) {
		vido = time;
	}

	public int getHiba() {
		return hiba;
	}

	public String getIdo() {
		return ido;
	}

	public String getVido() {
		return vido;
	}
}