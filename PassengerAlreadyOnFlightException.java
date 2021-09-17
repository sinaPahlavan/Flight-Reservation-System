/**
 * Trying to reserve a seat for a passenger that is already on the flight
 */
public class PassengerAlreadyOnFlightException extends Exception{
    public PassengerAlreadyOnFlightException(String message){super(message);};
}
