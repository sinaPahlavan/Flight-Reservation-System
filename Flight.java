//Sina Pahlavan
//501 034 271
import java.util.ArrayList;
import java.util.TreeMap;

/*
 *  Class to model an airline flight. In this simple system, all flights originate from Toronto
 *  
 *  This class models a simple flight that has only economy seats
 */
public class Flight
{
	public enum Status {DELAYED, ONTIME, ARRIVED, INFLIGHT};

	String flightNum;
	String airline;
	String origin, dest;
	String departureTime;
	Status status; // see enum Status above. google this to see how to use it
	int flightDuration;
	Aircraft aircraft;
	protected int passengers; // count of (economy) passengers on this flight - initially 0
	String[][] seats;
    protected ArrayList<Passenger> manifest;
    protected TreeMap<String,Passenger> seatMap;
	public static enum FlightType {SHORTHAUL, MEDIUMHAUL, LONGHAUL};
	public Flight()
	{

		// write code to initialize instance variables to default values
		this.flightNum = "AC100";
		this.airline = "Air Canada";
		this.dest = "New York";
		this.origin = "Toronto";
		this.departureTime = "22:30";
		this.flightDuration = 150;
		this.aircraft = new Aircraft(300,0,"Boeing 737");
		passengers = 0;
		status = Status.ONTIME;
		manifest = new ArrayList<Passenger>();
        seatMap = new TreeMap<String,Passenger>();
		String[][] seats = this.aircraft.seatLayout;

	}
	
	public Flight(String flightNum, String airline, String dest, String departure, int flightDuration, Aircraft aircraft)
	{
		this.flightNum = flightNum;
		this.airline = airline;
		this.dest = dest;
		this.origin = "Toronto";
		this.departureTime = departure;
		this.flightDuration = flightDuration;
		this.aircraft = aircraft;
		passengers = 0;
		status = Status.ONTIME;
		manifest = new ArrayList<Passenger>();
		seatMap = new TreeMap<String,Passenger>();
		this.seats = this.aircraft.seatLayout;
		
	}
	public String getFlightNum()
	{
		return flightNum;
	}
	public void setFlightNum(String flightNum)
	{
		this.flightNum = flightNum;
	}
	public String getAirline()
	{
		return airline;
	}
	public void setAirline(String airline)
	{
		this.airline = airline;
	}
	public String getOrigin()
	{
		return origin;
	}
	public void setOrigin(String origin)
	{
		this.origin = origin;
	}
	public String getDest()
	{
		return dest;
	}
	public void setDest(String dest)
	{
		this.dest = dest;
	}
	public String getDepartureTime()
	{
		return departureTime;
	}
	public void setDepartureTime(String departureTime)
	{
		this.departureTime = departureTime;
	}
	
	public Status getStatus()
	{
		return status;
	}
	public void setStatus(Status status)
	{
		this.status = status;
	}
	public int getFlightDuration()
	{
		return flightDuration;
	}
	public void setFlightDuration(int dur)
	{
		this.flightDuration = dur;
	}
	
	public int getPassengers()
	{
		return passengers;
	}
	public void setPassengers(int passengers)
	{
		this.passengers = passengers;
	}
	
	// Check to see if there is room on this flight - compare current passenger count
	// with aircraft max capacity of economy seats

	
	/*
	 * reserve a seat on this flight - essentially increases the passenger count by 1 only if there is room for more
	 * economy passengers on the aircraft used for this flight (see instance variables above)
	 */
	public void reserveSeat(Passenger aPassenger) throws FlightHasNoFirstClassSeatsException,SeatOccupiedException,SeatDoesNotExistException,PassengerAlreadyOnFlightException{
		/**
		 * param : passenger
		 * if reserve seat returns true, we increment passenger count
		 * based on the maximum number of seats on the aircraft, we generate a random number and assign to the passenger
		 * that becomes their seat number
		 * we will use the passenger setSeatNum method to change the seatNum attribute
		 * then will add the passenger to out arraylist of passengers
		 */
		String seat = aPassenger.getSeatNum();
		int row=0;//row on the seats 2D Array
		int column =0;


		for(Passenger pass:manifest) {//if we can find the passenger on manifest, that means they are already on the flight
			// we will throw and exception
			if (pass.getPassportNum().equals(aPassenger.getPassportNum())&&pass.getName().equals(aPassenger.getName())) {
				throw new PassengerAlreadyOnFlightException(aPassenger.getName() + " with passportnum " + aPassenger.getPassportNum() + " is already on the flight");
			}
		}
		if (seat.length()==3 && seat.charAt(2)=='+' && this.getFlightType()!=FlightType.LONGHAUL ){
			throw new FlightHasNoFirstClassSeatsException("This flight has no first class seats");
			//these exception will be throw if the flight has no first class seats but the user is attempting to reserve
			// a first class seat
		}

		if(seat.length()>3){//no seat will have more than three characters in it
			throw new SeatDoesNotExistException("Seat " + seat + " is invalid" );
		}

		if (seat.length()==2 || (seat.length()==3&&seat.charAt(2)=='+'&&this.getFlightType()==FlightType.LONGHAUL)) {
			//if the seat has two characters or it has three but the last one is a
			column = seat.charAt(0) - '1';

			char letter = seat.charAt(1);
			row = letter - 'A';
		}
		else if(seat.length()==3 && seat.charAt(2)!='+' ){//this happens when the seat number is 27A or 20D for example
			//the last character is not + but we have three characters. It is stil valid
			//but we need to count the first two characters as the column
			char ch1 = seat.charAt(0);
			char ch2 = seat.charAt(1);
			char letter = seat.charAt(2);
			String columnStr = Character.toString(ch1)+Character.toString(ch2);
			column = Integer.parseInt(columnStr)-1;
			row = letter - 'A';

		}

		if (row>=seats.length||column>=seats[0].length){//if no array element has the seat but the seat seems valid
			//this happens when there are only 4 columns, but you enter 6D for example
			throw new SeatDoesNotExistException("Seat: " + seat + " does not exist!");

		}
		else if (seats[row][column].equals(seat)) {
			seats[row][column] = "XX";
			manifest.add(aPassenger);
			System.out.println("Reserved seat for " + aPassenger.toString());
		}
		else if(seats[row][column].equals("XX")){//if the seat shows XX that means it is already occupied
			throw new SeatOccupiedException("Seat " + seat + " is already occupied");
		}

}

	public void cancelSeat(Passenger aPassenger)throws PassengerNotOnFlightException,FlightEmptyException {
		/**
		 * param : passenger
		 * given a passenger, we cancel their seat on the flight
		 * if this is successful we return true
		 * if we cannot find the passenger, we will return false
		 */
		boolean found = false;
		if (manifest.size()==0){//if the manifest is empty, that means there is nothing to cancel, we just throw an exception
			throw new FlightEmptyException("Flight " + flightNum + " is empty.");
		}

		for (Passenger aPass:manifest) {
			if (aPass.equals(aPassenger)) {
				found = true;

				String seat = aPass.getSeatNum();
				char letter = seat.charAt(1);
				aPassenger.setSeatNum(seat);
				int row = letter - 'A';
				int column = seat.charAt(0) - '1';
				seats[row][column] = seat;
				seatMap.remove(seat);
				//we remove the passenger from both the seatmap and the manifest
				for (int i = 0; i < manifest.size(); i++) {
					if (manifest.get(i).equals(aPassenger)) {
						manifest.remove(i);
						System.out.println("Passenger " + aPassenger.toString() + " removed from " + this.flightNum);
						break;
					}

				}
				break;

			}

		}
		if (found == false) {// if we can't find the passenger, we will throw an exception

			throw new PassengerNotOnFlightException("Could not find passenger with name: " + aPassenger.getName() + " and passport number: " + aPassenger.getPassportNum());
		}
	}


	public void printPassengers() throws FlightEmptyException{
		/**
		 * param : none
		 * we print all passengers that are on the flight
		 * Each passenger's string version is defined in the Passenger class in the toString method
		 */
		if (manifest.size()==0){//if there are no passengers print that the flight is empty
			throw new FlightEmptyException("Flight " + flightNum + " is empty");
		}
		else{
			for (Passenger pass : manifest){
				System.out.println(pass.toString());//printing the string version of each passenger
			}
		}

	}

	public boolean contains(Passenger aPassenger){
		/**
		 * @ param : Passenger
		 * given a passenger object, we will check to see if a passenger is on the passengers list
		 * if the list already contains that passenger, we will return true
		 * else will return fals
		 * the method will be used when we want to reserve a seat for a passenger and we want to make sure they
		 * are not already on the flight
		 */
		/*for (int i=0;i<listOfPassengers.size();i++){
			if (listOfPassengers.get(i).equals(aPassenger)){
				return true;
			}
		}*/
		return false;
	}
	public String toString()
	{
		//Returns a string version of the flight
		 return airline + "\t Flight:  " + flightNum + "\t Dest: " + dest + "\t Departing: " + departureTime + "\t Duration: " + flightDuration + "\t Status: " + status;
		
	}

	public void printSeats(){

		for (String[]x : seats){
			for (String y : x){
				System.out.print(y +"\t");
			}
			System.out.println("");
		}
		System.out.println();
		System.out.println("XX=Occupied Seat \t +=First Class Seat" );


	}
	public FlightType getFlightType(){return FlightType.MEDIUMHAUL;}


  
}