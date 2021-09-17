//Sina Pahlavan
//501 034 271
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.util.*;


public class FlightManager
{

    TreeMap<String,Flight> flightsMap = new TreeMap<String,Flight>();//arraylist to hold flights
    // Contains list of available airplane types and their seat capacity
    ArrayList<Aircraft> airplanes = new ArrayList<Aircraft>();
    Random random = new Random(); // random number generator - google "Java class Random". Use this in generateFlightNumber

    public FlightManager() throws FileNotFoundException {

        airplanes.add(new Aircraft(4,0,"Aircraft with 4 seats"));
        airplanes.add(new Aircraft(16,0,"Aircraft with 16 seats"));
        airplanes.add(new Aircraft(20, 0,"Boeing 737"));
        airplanes.add(new Aircraft(40,0,"Airbus 320"));
        airplanes.add(new Aircraft(60, 0,"Dash-8 100"));
        airplanes.add(new Aircraft(80, 0,"Bombardier 5000"));
        airplanes.add(new Aircraft(100, 12, "Boeing 747"));//this is the aircraft for the Tokyo flight
        //that's why it has 12 first class seats
        // Create some aircraft types with max seat capacities
        flightsMap = new TreeMap<String,Flight>();
        try{//getting the flights from the file
            File file = new File("flights.txt");
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()){
                String line = sc.nextLine();
                String[] words = line.split("\\W+");

                Flight flightToAdd;
                String airline = words[0];//the airline is always the first string
                String flightnum = generateFlightNumber(airline);//this method will generate a random flightnumber
                String dest = words[1];//destination is always the second string
                String departure = words[2];//departure is the third string
                int minSeats = Integer.parseInt(words[3]);//converting the minimum seats to an integer
                int duration = Integer.parseInt(words[4]);//converting the duration to an integer
                Aircraft aircraft = new Aircraft(0,0,"");
                for (Aircraft plane:airplanes){
                    if (plane.getTotalSeats()>=minSeats){//if a plane has more seats or equal seats than the minimum seats
                        //we will give that flight that airplane
                        aircraft = plane;
                        break;
                    }
                }
                //creating the flightsMap
                if (dest.equals("Tokyo")){//the flight going to tokyo requires to be a long haul
                    flightsMap.put(flightnum,new LongHaulFlight(flightnum,airline,dest,departure,duration,aircraft));
                }
                else{//if the flight is not going to Tokyo, it can be a regular flight
                    flightsMap.put(flightnum,new Flight(flightnum,airline,dest,departure,duration,aircraft));
                }
            }
        }
        catch(FileNotFoundException e){
            System.out.println(e.getMessage());
        }

    }

    /*
     * This private helper method generates and returns a flight number string from the airline name parameter
     * For example, if parameter string airline is "Air Canada" the flight number should be "ACxxx" where xxx is
     * a random 3 digit number between 101 and 300 (Hint: use class Random - see variable random at top of class)
     * you can assume every airline name is always 2 words.
     *
     */
    private String generateFlightNumber(String airline)
    {
        /**
         * @ param : String airline
         * Given an airline, we generate a random number beween 101 and 300
         * Then, we will add that number to the two letter short version of the airline and return a string as the flight number
         */
        // Your code here
        int randomNum = 101 + (int)(Math.random() * 201);//generating a random flight number between 101 and 300
        //we have to add 101 to a random number from 0 to 200
        String flightnumrand = Integer.toString(randomNum);//we turn the random number to a string so that we can
        //concacenate the string
        if (airline.equals("Air_Canada")){
            return "AC" + flightnumrand;
        }
        else if (airline.equals("United_Airlines")) {
            return "UA" + flightnumrand;
        }
        else if (airline.equals("Porter_Airlines")){
            return "PA" + flightnumrand;
        }
        return null;

    }
    // Prints all flights in flights array list (see class Flight toString() method)
    // This one is done for you!
    public void printAllFlights()
    {
        /**
         * print all flights by going through the flightsMap treemap and printing the string version of each flight
         */
        for (String key : flightsMap.keySet()){
            System.out.println(flightsMap.get(key).toString());
        }
    }

    // Given a flight number (e.g. "UA220"), check to see if there are economy seats available
    // if so return true, if not return false

    public void printSeat(String flightNum) throws FlightNotFoundException{
        boolean found = false;
        for (String key : flightsMap.keySet()){
           if (flightsMap.get(key).flightNum.equals(flightNum)){
               flightsMap.get(key).printSeats();
               found =true;//if we find a flight, we print its seats and set found to true so that
               //we will escape the exception thrown
           }
       }
       if (found == false){//throws a flightNum not found exception using a variable foud
           throw new FlightNotFoundException("Could not Find Flight: " + flightNum);
       }
    }


    // Given a flight number string flightNum and a seat type, reserve a seat on a flight
    // If successful, return a Reservation object
    // NOTE: seat types are not used for basic Flight objects (seats are all "Economy Seats")
    // class LongHaulFlight defines two seat types
    // I  suggest you first write this method *without* considering class LongHaulFlight
    // once that works (test it!!), add the long haul flight code
    public Reservation reserveSeatOnFlight(String flightNum,Passenger aPassenger) throws FlightHasNoFirstClassSeatsException, FlightNotFoundException,SeatOccupiedException,SeatDoesNotExistException,PassengerAlreadyOnFlightException {
        /**
         * @ param : String flightNum
         * @ param : Passenger aPassenger
         * if we can find the flight num and the flight does not contain that passenger, we will reserve a seat for them
         * @ return : reservation
         */


        if (flightsMap.containsKey(flightNum)){//getting the a passenger object using a flight number
            flightsMap.get(flightNum).reserveSeat(aPassenger);//using the reserve seat method of each flight
            return new Reservation(flightNum,aPassenger);//return a reservation that the flightReservation System can add
            //to its arraylist of reservations
        }
        else{//throwing a flightnotfoundexception if we can't find the flight number in the tree
            throw new FlightNotFoundException("Could not Find Flight " + flightNum);

        }
    }



    /*
     * Given a Reservation object, cancel the seat on the flight
     */
    public void cancelReservation(String flightnum,Passenger aPassenger)throws FlightEmptyException,PassengerNotOnFlightException,FlightNotFoundException
    {
        // Get the flight number string from res
        // Search flights to find the Flight object -
        // if found, cancel the seat on the flight
        Flight aflight;
        boolean done = false;
        for (String key:flightsMap.keySet()){
            if (key.equals(flightnum)){
                aflight = flightsMap.get(key);
                aflight.cancelSeat(aPassenger);//we will find the flight using its flight num in the tree map
                //then will call its cancel seat method and assign the passenger object as the paramter
                done = true;

                }

            }
        if (done==false){// if we get here, that means we couldn't find the flight. So we will throw the excpetion
            throw new FlightNotFoundException("Could not find flight "+ flightnum);
        }







    }




    public void printPassengersForAFlight(String flightnum) throws FlightNotFoundException,FlightEmptyException{
        boolean found =false;
        for (String str : flightsMap.keySet()){
            if (str.equals(flightnum)){
                flightsMap.get(str).printPassengers();
                found = true;
            }
        }
        if (found==false){
            throw new FlightNotFoundException("Could not find flight: " + flightnum);
        }
    }


}
