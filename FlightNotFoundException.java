/**
 * In case you enter a flight number that does not exist
 */
public class FlightNotFoundException extends Exception{

    public FlightNotFoundException(String message) {
        super(message);
    }
}