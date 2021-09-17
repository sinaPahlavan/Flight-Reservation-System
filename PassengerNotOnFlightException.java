/**
 * This is thrown when you are trying to cancel a seat for a passenger that is not on that flight
 */
public class PassengerNotOnFlightException extends Exception{
    public PassengerNotOnFlightException(String message){super(message);}
}