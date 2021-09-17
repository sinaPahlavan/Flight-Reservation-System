/**
 * This class is used for the situation where you are trying to reserve a seat that does not exist for example
 * there are 9 rows but you want to reserve a seat on row 10
 */
public class SeatDoesNotExistException extends Exception{

    public SeatDoesNotExistException(String message) {
        super(message);
    }
}