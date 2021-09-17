/**
 * trying to print PASMAN or cancel passenger for a flight that is already empty
 */
public class FlightEmptyException extends Exception{
    public FlightEmptyException(String message){super(message);}
}