/**
 * when you try to reserve a first class seat on a medium or short haul flight
 */
public class FlightHasNoFirstClassSeatsException extends Exception {
    public FlightHasNoFirstClassSeatsException(String message){super(message);}
}
