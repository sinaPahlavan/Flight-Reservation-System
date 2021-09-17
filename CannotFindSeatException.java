/**
 * When you enter a seat that is invalid
 */
public class CannotFindSeatException extends Exception{
    public CannotFindSeatException(String message){
        super(message);
    }

}