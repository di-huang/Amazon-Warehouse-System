package production_unitTesting;


import java.util.HashMap;
import java.util.LinkedList;

/**
 * 
 * @author josephtleiferman
 *
 */
public class MockFloor implements Floor {
	public static Point SHELVE_1 = new Point(2,2,"SHELVE_1"); // (x,y) coordinates location
    public static Point SHELVE_2 = new Point(3,2,"SHELVE_2"); // (x,y) coordinates location
    public static Point ROBOT = new Point(5,1,"ROBOT"); // start robot on charger 
    public static final Point CHARGER_1 = new Point(2,0,"CHARGER_1");
    public static final Point CHARGER_2 = new Point(3,0,"CHARGER_2"); 
    public static final Point RECEIVING_DOCK = new Point(5,0,"RECEIVING_DOCK");
    public static final Point PICKER = new Point(1,5,"PICKER");
    public static final Point PACKER = new Point(1,2,"PACKER");
    public static Point[] PICKERBELT = { new Point(0,5,"PICKERBELT5"),new Point(0,4,"PICKERBELT4"),new Point(0,3,"PICKERBELT3") };
    public static Point[] PACKERBELT = { new Point(0,2,"PICKERBELT2"),new Point(0,1,"PICKERBELT1"),new Point(0,0,"PICKERBELT0") };
   
    public static final int UPPERB = 5;
    public static final int LOWERB = 0;
    
    /** Directions will be used for the route creating
     *
    *you can parse the route as follows 
    *<p>
    * UP = y + 1 
    * DOWN = y - 1
    * LEFT = x - 1
    * RIGHT = x + 1
    */
    public enum Directions {
        UP,DOWN,LEFT,RIGHT
    }
    public static HashMap<String,Point> FLOOR_LOCATIONS = new HashMap<>();
    
    // initially just statically create these objects
    public MockFloor() {
        FLOOR_LOCATIONS.put("SHELVE_1",SHELVE_1);
        FLOOR_LOCATIONS.put("SHELVE_2",SHELVE_2);
        FLOOR_LOCATIONS.put("CHARGER_1",CHARGER_1);
        FLOOR_LOCATIONS.put("CHARGER_2",CHARGER_2);
        FLOOR_LOCATIONS.put("RECEIVING_DOCK",RECEIVING_DOCK);
        FLOOR_LOCATIONS.put("PICKER",PICKER);
        FLOOR_LOCATIONS.put("PACKER",PACKER);
        
        
        
    }
    /**
     *Given a string l getLocation(String l) will return a Point 
     *storing the location of that object or null if not a valid object
     *<p>
     *
     *@param l string containing locations name 
     *@return Point location of the object (x,y)
     */
    
    public Point getLocation(String l) {
        return FLOOR_LOCATIONS.get(l);
    }
     /** 
      * return true or false depending on if an object is at location [x,y] in Point 
      * <p>
      * @param l a Point [x,y] of objects location  
      * @return boolean whether an object is at a given [x,y]
      */
    public boolean objectAt(Point l) {
        
        for(Point x: FLOOR_LOCATIONS.values() ) {
            if(x.getX() == l.getX() && x.getY()== l.getY()) {
                return true;
            }
        }
        return false;
    }
    /** updates location of a given shelf
     * 
     * @param object the shelves name
     * @param location the location of shelf
     * 
     */
    public void updateObjectLocation(String object, Point location) { 
        Point newLocation = FLOOR_LOCATIONS.get(object);
        newLocation = location;
        FLOOR_LOCATIONS.put(object, newLocation);
        System.out.println(object + "'s location updated too" + " (" + location.getX() + "," + location.getY()+  ")");

    }
    /**
     * will be called when the floor is first initialized so that we can choose where the robot begins 
     * for example on a charge or at the dock
     * <p>
     * @param l location to place the robot to start with
     */
    public void placeRobot(Point l) {
    	ROBOT = l;
    	updateObjectLocation("ROBOT",l);
    }
    /** 
     * Used in the case when a robot has already picked up a shelf
     * 
     * @param start start location of a given object
     * @param end end destination
     * @return ArrayList returns a route from start to end of type Directions ex [LEFT,RIGHT,UP,DOWN,DOWN]
     */ 
    public static LinkedList<Directions> getRouteWithShelf(Point start, Point end) {
		LinkedList<Directions> route = new LinkedList<>();
        Point currentLocation = new Point(start.getX(),start.getY(),"currentLocation");
        // alternator will alternate between odd and even so that the robot will move
        // in either the x or y direction until   is in line with either the x or y
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
}
