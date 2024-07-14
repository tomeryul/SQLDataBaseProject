package electionProg;

public class NotValidIdException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NotValidIdException() {
		super("not valid id -must be 9 digits");
	}
}
