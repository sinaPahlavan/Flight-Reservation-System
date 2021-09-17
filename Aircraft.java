//Sina Pahlavan
//501 034 271
/*
 * 
 * This class models an aircraft type with a model name, a maximum number of economy seats, and a max number of first class seats
 * 
 * Add code such that class Aircraft implements the Comparable interface
 * Compare two Aircraft objects by first comparing the number of economy seats. If the number is equal, then compare the
 * number of first class seats 
 */
public class Aircraft //implements the comparable interface
{
  int numEconomySeats;
  int numFirstClassSeats;
  String model;
  String[][] seatLayout;


  public Aircraft(int economy, int firstClass,String aModel)
  {
  	String[] letters = {"A","B","C","D"};//these letters will be used to write the seat number and letters
  	this.numEconomySeats = economy;
  	this.numFirstClassSeats = firstClass;
  	this.model = model;
  	int totSeat = economy + firstClass;
  	if (totSeat<=16){//if we have less than or equal to 16 seats, we will only have two rows
  		seatLayout = new String[2][totSeat/2];
	}
	else{//if we have more than 16 seats, we will have four rows.
		seatLayout = new String[4][totSeat/4];
	}
	for (int i=0;i<seatLayout.length;i++){
		for (int j=0;j<seatLayout[i].length;j++){
			seatLayout[i][j] = Integer.toString(j+1) + letters[i] ;
		}
	}
	if (firstClass>0){//theonly flight that has first class seats is the one going to Tokyo and it has 12 of them.
		/**
		 * this method will be used to create 12 first class seats on the flight going to Tokyo
		 */
		for (int i=0;i<4;i++){
			for (int j=0;j<3;j++){
				seatLayout[i][j]+="+";
			}
		}
	}
  }
  
	public int getNumSeats()
	{
		return numEconomySeats;
	}// returns num of economy seats
	
	public int getTotalSeats()
	{
		return numEconomySeats + numFirstClassSeats;
	}// returns num of total seats
	
	public int getNumFirstClassSeats()
	{
		return numFirstClassSeats;
	}//returns num of first class seats

	public String getModel()
	{
		return model;
	}//returns aircraft model

	public void setModel(String model)
	{
		this.model = model;
	}// you can set the model of the aircraft using this method
	
	public void print()//prints some information about the aircraft
	{
		System.out.println("Model: " + model + "\t Economy Seats: " + numEconomySeats + "\t First Class Seats: " + numFirstClassSeats);
	}

	/*
	 * Write a compareTo method that is part of the Comparable interface
	 */


	
}
  
