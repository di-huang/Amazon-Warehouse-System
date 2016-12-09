package production;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * 
 * @author josephtleiferman, Di Huang, Wei Gui
 * 
 *
 */
public class Floor {
	public static final Point SHELVE_1 = new Point(2,2,"SHELVE_1");		// homepoint of shelves
    public static final Point SHELVE_2 = new Point(3,2,"SHELVE_2");
    public static final Point SHELVE_3 = new Point(5,1,"SHELVE_3");
    public static final Point SHELVE_4 = new Point(5,2,"SHELVE_4");
    public static final Point SHELVE_5 = new Point(5,3,"SHELVE_5");
    public static final Point SHELVE_X = new Point(5,4,"SHELVE_X");		// reserved for magical replenishment
    public static Shelf[] SHELVES = {new Shelf(SHELVE_1), new Shelf(SHELVE_2), new Shelf(SHELVE_3),
    		new Shelf(SHELVE_4), new Shelf(SHELVE_5),new Shelf(SHELVE_X)}; // initialize shelves
    public static final Point CHARGER = new Point(2,0,"CHARGER");
    public static final Point CHARGER_2 = new Point(5,0,"CHARGER_2");
    public static final Point PICKER = new Point(1,5,"PICKER");
    public static final Point PICKER_WAITTING_AREA = new Point(2,5,"PICKER_WAITTING_AREA");
    public static final Point PACKER = new Point(1,2,"PACKER");
    public static final Point[] PICKERBELT = { new Point(0,5,"PICKERBELT5"),new Point(0,4,"PICKERBELT4"),new Point(0,3,"PICKERBELT3") };
    public static final Point[] PACKERBELT = { new Point(0,2,"PICKERBELT2"),new Point(0,1,"PICKERBELT1"),new Point(0,0,"PICKERBELT0") };
	public static final int width = 300, height = 300;
	public static final int gridSize = 50;
	public static final int UPPERB = 5;
    public static final int LOWERB = 0;
	public static void clearstaticFields() {
		SHELVES = new Shelf[]{new Shelf(SHELVE_1), new Shelf(SHELVE_2), new Shelf(SHELVE_3),
	    		new Shelf(SHELVE_4), new Shelf(SHELVE_5),new Shelf(SHELVE_X)};
		FLOOR_LOCATIONS = new HashMap<>();
	}
	/**
     * @author dihuang
     * Generating dynamic next direction(improve applicability and expandability), so we have to call this method each tick. 
     * Robot calls this method currently.
     * @param robot, start point, end point
     * @return dynamic direction
     */
    public static Directions getDirection(Robot robot, Point curP, Point end) {
    	if(curP.equals(end)) return null;
    	Directions[] directions = {Directions.UP, Directions.DOWN, Directions.LEFT, Directions.RIGHT};
        Point nxtP = null;		// next point
        Directions opD = null;	//optimal direction
        outter:for(Directions d : directions){
    		nxtP = curP.nextpoint(d);	// curP: current point
    		if(nxtP.out()) continue;	// out of map
    		if(robot.carrying){
    			for(Shelf s : SHELVES){
    				if(s == robot.shelf) continue;	// exclude itself
        			if(nxtP.equals(s.getPos())){
        				continue outter;
        			}
        		}
    		}
    		if(curP.closeTo(d, end)){
    			opD = d;
    			break;
    		}
    		opD = d;
    	}
        return opD;
	}
    
    /** 
     * Should only be used to get a route to a shelf
     * Use getRouteWithShelf() once it has picked up the shelf to prevent shelves from colliding
     * 
     * @param start start location of a given object
     * @param end end destination
     * @return ArrayList returns a route from start to end of type Directions ex [LEFT,RIGHT,UP,DOWN,DOWN]
     */ 
    public static LinkedList<Directions> getRoute(Point start, Point end) {
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
                    if(tempLocation.getX()<UPPERB-1) {
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
	
	/** 
     * 
     * Used in the case when a robot has already picked up a shelf
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
                    // used to find if shelf is next location
                    boolean shelfFlag = false;
                    for(Shelf p: SHELVES) {
                    	if(p.getPos().getX() == tempLocation.getX() && p.getPos().getY() == tempLocation.getY()) {
                    		shelfFlag = true;
                    	}
                    }
                    if(tempLocation.getX()<UPPERB && shelfFlag == false) {
                        currentLocation.setPoint(currentLocation.getX()+1,currentLocation.getY());
                        route.add(Directions.RIGHT);
                    } else {
                    	// shelf in the way just go opposite direction to resolve
                    	if( tempLocation.getX()>LOWERB ) {
                        	currentLocation.setPoint(currentLocation.getX()-1,currentLocation.getY());
                            route.add(Directions.LEFT);
                        }     	
                    }
                }
                else {
                	tempLocation.setPoint(tempLocation.getX()-1, tempLocation.getY());
                	boolean shelfFlag = false;
                    for(Shelf p: SHELVES) {
                    	if(p.getPos().getX() == tempLocation.getX() && p.getPos().getY() == tempLocation.getY()) {
                    		shelfFlag = true;
                    	}
                    }
                    if( tempLocation.getX()>LOWERB && shelfFlag == false) {
                    	currentLocation.setPoint(currentLocation.getX()-1,currentLocation.getY());
                        route.add(Directions.LEFT);
                    } else {
                    	// shelf in the way just go opposite direction make sure your not leaving area
                    	if(tempLocation.getX()<UPPERB) {
                            currentLocation.setPoint(currentLocation.getX()+1,currentLocation.getY());
                            route.add(Directions.RIGHT);
                        }
                    	
                    }
                }
            }else if(alternator%2==1 && currentLocation.getY() != end.getY()) {
                
            	// find whether moving up or down will get object closer to destination
                int diff1 = Math.abs(currentLocation.getY()+1 - end.getY());
                int diff2 = Math.abs(currentLocation.getY()-1 - end.getY());
                Point tempLocation = new Point(currentLocation.getX(),currentLocation.getY(),"tempLocation");
                boolean shelfFlag = false;
                if(diff1<diff2) {
                	tempLocation.setPoint(tempLocation.getX(), tempLocation.getY()+1);
                	System.out.println("tempLocation: " + tempLocation.getX()+  " ," + tempLocation.getY());
                	// used to find if shelf is next location
                    shelfFlag = false;
                    for(Shelf p: SHELVES) {
                    	if(p.getPos().getX() == tempLocation.getX() && p.getPos().getY() == tempLocation.getY()) {
                    		System.out.println("shelfLocation: " + p.getPos().getX()+  " ," + p.getPos().getY());
                    		shelfFlag = true;
                    	}
                    }
                    if( tempLocation.getY()>LOWERB && shelfFlag == false) {
                    	currentLocation.setPoint(currentLocation.getX(),currentLocation.getY()+1);
                        route.add(Directions.DOWN);
                    } else {
                    	//shelf in the way go opposite direction to resolve
                    	if( tempLocation.getY()<UPPERB) {
                        	currentLocation.setPoint(currentLocation.getX(),currentLocation.getY()-1);
                            route.add(Directions.UP);
                        }
                    }
                }
                else {
                	tempLocation.setPoint(tempLocation.getX(), tempLocation.getY()-1);
                	System.out.println("tempLocation: " + tempLocation.getX()+  " ," + tempLocation.getY());
                	// used to find if shelf is next location
                    shelfFlag = false;
                    for(Shelf p: SHELVES) {
                    	if((p.getPos().getX() == tempLocation.getX()) && (p.getPos().getY() == tempLocation.getY())) {
                    		System.out.println("shelfLocation: " + p.getPos().getX()+  " ," + p.getPos().getY());
                    		shelfFlag = true;
                    	}
                    }
                    if( tempLocation.getY()<UPPERB && shelfFlag == false) {
                    	currentLocation.setPoint(currentLocation.getX(),currentLocation.getY()-1);
                        route.add(Directions.UP);
                    }else {
                    	//shelf in the way go opposite direction to resolve
                    	if( tempLocation.getY()>LOWERB) {
                        	currentLocation.setPoint(currentLocation.getX(),currentLocation.getY()+1);
                            route.add(Directions.DOWN);
                        }
                    }
                }
            }
            
            alternator +=1;
        }
        return route;
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
	/**********************************************************************
	 * following methods are not used for our simulation pattern right now
	 */
   
    public static HashMap<String,Point> FLOOR_LOCATIONS = new HashMap<>();
   
    // initially just statically create these objects
   public Floor() {
	   FLOOR_LOCATIONS.put("SHELVE_1",SHELVES[0].getPos());
       FLOOR_LOCATIONS.put("SHELVE_2",SHELVES[1].getPos());
       FLOOR_LOCATIONS.put("CHARGER",CHARGER);
       FLOOR_LOCATIONS.put("CHARGER_2",CHARGER_2);
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
}

/** Directions will be used for the route creating
*
*you can parse the route as follows 
*<p>
* UP = y + 1 
* DOWN = y - 1
* LEFT = x - 1
* RIGHT = x + 1
*/
enum Directions {
    UP,DOWN,LEFT,RIGHT
}

