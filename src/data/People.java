package data;

public class People {
	
	public int number;
	public String name;
	
	public People() {
		
	}
	
	public People(int num, String nam) {
		number = num;
		name = nam;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
