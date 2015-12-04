/**
 * 
 */
package pl.dominika.exception;

/**
 * @author DPURSZKE
 *
 */
public class GameOverException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public GameOverException(String string) {
		super(string);
	}

}
