/**
 * This exception takes care of a situation where there a seat is already occupied but you are trying to reserve that seat
 */
public class SeatOccupiedException extends Exception{
    public SeatOccupiedException(String message){
        super(message);
    }

}
