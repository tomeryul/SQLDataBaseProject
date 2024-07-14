package electionProg;

public class TooYoungExcepction extends Exception {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TooYoungExcepction() {
		super(" is too young - must be over 18 ");
		
	}
}