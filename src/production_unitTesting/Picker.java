package production_unitTesting;

/**
 * 
 * @author Ted Herman
 * 
 * The Picker interface is implemented by the Orders
 * component; when a Robot arrives to the Picker, it 
 * invokes Picker.notify() to tell the Orders that the
 * requested Robot has arrived with a shelf
 * 
 */
public interface Picker {
  void notify(Robot r, String itemShelf);
 
}