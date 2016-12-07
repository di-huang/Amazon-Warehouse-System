package production;

import java.util.LinkedList;

import org.junit.Test;

import production_unitTesting.MockFloor;
import production_unitTesting.Point;

/**
 * @author josephtleiferman
 *
 */
public class getRouteTest {

	MockFloor f = new MockFloor();
	Point p = new Point(2,5,"p");
	
	Floor floor = new Floor();
	
	
	@Test
	public void testingGetRoute() {
		
		//testing get route
		LinkedList<Directions> expectedRoute = new LinkedList<>();

		production.Point  robot = new production.Point(3,1);
		production.Point picker = new production.Point(1,5);
		expectedRoute.add(Directions.LEFT);
		expectedRoute.add(Directions.DOWN);
		expectedRoute.add(Directions.LEFT);	
		expectedRoute.add(Directions.DOWN);
		expectedRoute.add(Directions.LEFT);
		expectedRoute.add(Directions.LEFT);
		expectedRoute.add(Directions.LEFT);	
		expectedRoute.add(Directions.DOWN);
		expectedRoute.add(Directions.LEFT);
		expectedRoute.add(Directions.LEFT);
		LinkedList<Directions> actualRoute = floor.getRouteWithShelf(robot, picker);
		for(int i=0;i< actualRoute.size();i++) {
			System.out.println("Expected: " + expectedRoute.get(i));
			System.out.println("Actual: " + actualRoute.get(i));
			
		}
	}
}
