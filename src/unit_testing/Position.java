package production_unitTesting;

//For Testing only
//Simulating the action of Floor
/**
 * 
 * @author Wei Gui
 *
 */
public class Position {
	private int x;
	private int y;
	public Position(int a, int b){
		this.x=a;
		this.y=b;
	}
	public int getx() {
		return x;
	}
	public int gety() {
		return y;
	}
	public void set(int a,int b) {
		this.x=a;
		this.y=b;
	}
}
