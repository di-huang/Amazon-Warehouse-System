package production_unitTesting;

import java.util.ArrayList;

import production_unitTesting.MockFloor.Directions;
/**
 * 
 * @author josephtleiferman
 *
 */
public interface Floor {
	
	public static final int width = 60, height = 30;
	static public int gridSize = 10;
	public static final int UPPERB = 5;
    public static final int LOWERB = 0;
	public Point getLocation(String l);
	public boolean objectAt(Point l);
	public void updateObjectLocation(String object, Point location);
	/** 
     * 
     * @param start start location of a given object
     * @param end end destination
     * @return ArrayList returns a route from start to end of type Directions ex [LEFT,RIGHT,UP,DOWN,DOWN]
     */ 
	public static ArrayList<MockFloor.Directions> getRoute(Point start, Point end) {
		ArrayList<Directions> route = new ArrayList<>();
        Point currentLocation = new Point(start.getX(),start.getY(),"currentLocation");
        // alternator will alternate between odd and even so that the robot will move
        // in either the x or y direction until it is in line with either the x or y
        int alternator = 0;
        // will create a route until object is at final location
        while((currentLocation.getX() != end.getX()) || (currentLocation.getY() != end.getY())) {
            
            if(alternator%2==0 && currentLocation.getX() != end.getX()) {
                // find whether moving left or right will get object closer to destination
                int diff1 = Math.abs(currentLocation.getX()+1 - end.getX());
                int diff2 = Math.abs(currentLocation.getX()-1 - end.getX());
                Point tempLocation = new Point(currentLocation.getX(),currentLocation.getY(),"tempLocation");
                if(diff1<diff2) {
                    tempLocation.setPoint(tempLocation.getX()+1, tempLocation.getY()); 
                    if(tempLocation.getX()<UPPERB) {
                        currentLocation.setPoint(currentLocation.getX()+1,currentLocation.getY());
                        route.add(Directions.RIGHT);
                    }
                }
                else {
                	tempLocation.setPoint(tempLocation.getX()-1, tempLocation.getY());
                    
                    if( tempLocation.getX()>LOWERB ) {
                    	currentLocation.setPoint(currentLocation.getX()-1,currentLocation.getY());
                        route.add(Directions.LEFT);
                    }
                }
            }else if(alternator%2==1 && currentLocation.getY() != end.getY()) {
                
            	// find whether moving up or down will get object closer to destination
                int diff1 = Math.abs(currentLocation.getY()+1 - end.getY());
                int diff2 = Math.abs(currentLocation.getY()-1 - end.getY());
                Point tempLocation = new Point(currentLocation.getX(),currentLocation.getY(),"tempLocation");
                if(diff1<diff2) {
                	tempLocation.setPoint(tempLocation.getX(), tempLocation.getY()+1);
                    
                    if( tempLocation.getY()>LOWERB) {
                    	currentLocation.setPoint(currentLocation.getX(),currentLocation.getY()+1);
                        route.add(Directions.DOWN);
                    }
                }
                else {
                	tempLocation.setPoint(tempLocation.getX(), tempLocation.getY()-1);
                    if( tempLocation.getY()<UPPERB) {
                    	currentLocation.setPoint(currentLocation.getX(),currentLocation.getY()-1);
                        route.add(Directions.UP);
                    }
                }
            }
            
            alternator +=1;
        }
        return route;
        
	}
	public void placeRobot(Point l);
}

