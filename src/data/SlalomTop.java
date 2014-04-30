package data;

public class SlalomTop extends People{
	
	int won;
	int pid;
	
	public SlalomTop() {
		
	}
	
	public SlalomTop(int num, String nam, int win, int pid) {
		won = win;
		number = num;
		name = nam;
		this.pid = pid;
	}
	
	public void setPid(int pid) {
		this.pid = pid;
	}
	
	public int getPid() {
		return pid;
	}

	public int getWon() {
		return won;
	}

	public void setWon(int won) {
		this.won = won;
	}
}
