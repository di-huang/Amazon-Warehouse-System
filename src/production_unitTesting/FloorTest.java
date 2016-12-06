
package production_unitTesting;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

/**
 * @author josephtleiferman
 *
 */
public class FloorTest {
	MockFloor f = new MockFloor();
	Point p = new Point(2,5,"p");
	
	@Test
	public void test() {
		//testing Point class
		int [] t1 = p.getPoint();
		int[] tt1 = {2,5};
		assertArrayEquals(tt1,t1);
		p.setPoint(5,2);
		int[] tt2 = {5,2};
		assertArrayEquals(tt2,p.getPoint());
		
		// testing getLocation
		String tester = "SHELVE_1";
		Point expected = new Point(2,2,"expected");
		Point got  = f.getLocation(tester);
		assertArrayEquals(expected.getPoint(),got.getPoint());
		//testing getLocation
		Point expected1 = new Point(1,5,"expected1");
		tester = "PICKER";
		got = f.getLocation(tester);
		assertArrayEquals(expected1.getPoint() ,got.getPoint());
		// testing objectAt
		boolean got1 = f.objectAt(expected);
		assertEquals(true,got1);
		//testing objectAt
		got1 = f.objectAt(expected1);
		//testing objectAt
		assertEquals(true,got1);
		//testing objectAt
		Point notInFloor = new Point(0,0,"notInFloor");
		assertEquals(false,f.objectAt(notInFloor));
		//testing updateShelve
		f.updateObjectLocation("SHELVE_1", notInFloor);
		assertEquals(notInFloor,f.FLOOR_LOCATIONS.get("SHELVE_1"));
		//testing get route
		ArrayList<MockFloor.Directions> expectedRoute = new ArrayList<>();

		Point shelve1 = new Point(5,0,"shelve_1");
		Point charger2 = new Point(1,2,"charger2");
		expectedRoute.add(MockFloor.Directions.LEFT);
		expectedRoute.add(MockFloor.Directions.DOWN);
		expectedRoute.add(MockFloor.Directions.LEFT);	
		expectedRoute.add(MockFloor.Directions.DOWN);
		expectedRoute.add(MockFloor.Directions.LEFT);
		expectedRoute.add(MockFloor.Directions.LEFT);
	
	}
	

}
