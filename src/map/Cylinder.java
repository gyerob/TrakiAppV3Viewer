package map;


public class Cylinder {
	
	private float[] vertices = {
			/*  	   1_______2
			   _6       |\   /|	X,3,4,2,1,3
			7/   \5		| \ / |
		   8/     \4	|  X  |
		   9\     /3	| / \ |
		   10\ _ /2	   3|/___\|4
			   1
			 		
			 
			 
			 
			*/
			
			
			
			//órajárással ellentétesen.
			
			//1. lap
			-0.309f, 1.0f, 0.951f,
			-0.309f, -1.0f, 0.951f,
			0.309f, 1.0f, 0.951f,
			
			-0.309f, -1.0f, 0.951f,
			0.309f, -1.0f, 0.951f,
			0.309f, 1.0f, 0.951f,
			
			//2.
			
			
			//3.
			
			
			//4.
			
			
			//5.
			
			
			//6.
			//7.
			//8.
			//9.
			//10.
			
	};
	
	private float[] color = {
			
	};
	
	private float[] normal = {
			
	};
	
	public Cylinder() {
		//10 szög esetén
		//x1=0.809 z1=0.588
		//x2=0.309 z2=0.951
		
	}

	public float[] getNormal() {
		return normal;
	}

	public void setNormal(float[] normal) {
		this.normal = normal;
	}

	public float[] getColor() {
		return color;
	}

	public void setColor(float[] color) {
		this.color = color;
	}

	public float[] getPos() {
		return vertices;
	}

	public void setPos(float[] pos) {
		this.vertices = pos;
	}
}
