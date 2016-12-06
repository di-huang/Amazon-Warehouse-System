package production;

/**
 * @author dihuang
 * entity, such as shelf, can't be passed through by other entity
 */
public interface Entity {
	public void block();	// entity can't be passed through
}
