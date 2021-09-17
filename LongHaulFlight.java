//Sina Pahlavan
//501 034 271
/*
 * A long haul flight is a flight that travels thousands of kilometers and typically has separate seating areas 
 */

public class LongHaulFlight extends Flight
{
	int numFirstClassPassengers;
	String seatType;
	
	// Possible seat types
	public static final String firstClass = "First Class Seat";
	public static final String economy 		= "Economy Seat";  
	

	public LongHaulFlight(String flightNum, String airline, String dest, String departure, int flightDuration, Aircraft aircraft)
	{
		// use the super() call to initialize all inherited variables
		// also initialize the new instance variables
		super(flightNum,airline,dest,departure,flightDuration,aircraft);
		seatType = economy;//set seat type to economy
		numFirstClassPassengers = 0;//set number of passengers to 0.
	}

	public LongHaulFlight()
	{
     // default constructor
		this.flightNum = "AC100";
		this.airline = "Air Canada";
		this.dest = "New York";
		this.origin = "Toronto";
		this.departureTime = "22:30";
		this.flightDuration = 150;
		this.aircraft = new Aircraft(300,0,"Boeing 737");
		this.seatType = economy;
		this.numFirstClassPassengers = 0;
	}
	


	// return the total passenger count of economy passengers *and* first class passengers
	// use instance variable at top and inherited method that returns economy passenger count
	public int getPassengerCount()
	{
		/**
		 * the total number of first class students is added to the inherited method get passengers which returns the
		 * 		number of economy passengers
		 */
		return numFirstClassPassengers+super.getPassengers();
	}

	public String toString(){
		/**
		 * overrides toString() from flight
		 * adds the phrase ' long haul' to the end
		 * returns a string version of the flight
		 */
		return airline + "\t Flight:  " + flightNum + "\t Dest: " + dest + "\t Departing: " + departureTime + "\t Duration: " + flightDuration + "\t Status: " + status + "		Long haul";
	}
	public FlightType getFlightType(){return FlightType.LONGHAUL;}//overriding the getFlightType from flight so that it returns longhaul

}
