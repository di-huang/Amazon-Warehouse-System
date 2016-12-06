package production_unitTesting;
/**
 * 
 * @author Wei Gui
 *
 */
public interface Robot{
	
	public String getID();
	public boolean getBatteryState();
	public void gocharge(); 
	public void Activate(Object targetitem,Object targetposition);
	
}
