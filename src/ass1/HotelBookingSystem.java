package ass1;
import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.time.*;
import java.util.Scanner;


public class HotelBookingSystem {
	private ArrayList<Hotel> Hotels;
    private ArrayList<Customer> Customers;
    
    //switch statement that return int from room type
    public static int roomType(String s) {
        switch(s) {
        	case "single":
        		return 1;
        	case "double":
        		return 2;
        	case "triple":
        		return 3;
        	default:
        		return 0;
        }	
	}
	//switch statement that return int from month type
	public static int monthType(String s) {
        switch(s) {
        	case "Jan":
        		return 1;
        	case "Feb":
        		return 2;
        	case "Mar":
        		return 3;
        	case "Apr":
        		return 4;
        	case "May":
        		return 5;
        	case "Jun":
        		return 6;
        	case "Jul":
        		return 7;
        	case "Aug":
        		return 8;
        	case "Sep":
        		return 9;
        	case "Oct":
        		return 10;
        	case "Nov":
        		return 11;
        	case "Dec":
        		return 12;
        	default:
        		return 0;
        }	
	}

	//check if string is of ascii characters
  	private static boolean isASCII(String s) {
		for (int i = 0; i < s.length(); i++) 
			if (s.charAt(i) > 127) 
				return false;
		return true;
	}

	public static void main(String[] args) {
		HotelBookingSystem skibidipap = new HotelBookingSystem(); //create a new hotel booking system
		
		Scanner sc = null;
	    
	    try{
	        sc = new Scanner(new File(args[0]));    // args[0] is the first command line argument
	        
	        while(sc.hasNextLine()) { //while input line is found
	        	String line = sc.nextLine();
	        	if(line.isEmpty()) break;
	        	String[] str = line.split(" ");
	        	
	        	switch(str[0]) {
	        		case "Hotel": //building hotel list in the same order as input
	        			if(!skibidipap.containsHotel(str[1])) { //if such a hotel has not been recorded in the system before
	        				Hotel newHotel = new Hotel(str[1], str[2], str[3]); //create hotel
	        				skibidipap.appendHotel(newHotel); //append new hotel
	        			}else {
	        				Hotel shaq = skibidipap.findHotel(str[1]); //find hotel in list
	        				shaq.appendHotelRoom(Integer.parseInt(str[2]), Integer.parseInt(str[3])); //append a new room (also checks if room is alrdy in hotel)
	        			}
	        			break;
	        			
	        		case "Booking":
	        			if(skibidipap.containCust(str[1])) { //check if cust alrdy exists in bookings
	        				System.out.println("Customer can only have 1 booking");
	        				break;
	        				
	        			}else {
	        				
	        				//try to find a room for the customer before creating a customer/booking obj
	        				LocalDate date = null;
	        				
	        				Month month = Month.of(monthType(str[2]));
	        				
	        				date = Year.now().atMonth(month).atDay(Integer.parseInt(str[3])); //generate LocalDate for "arrivalDate"
	      
	        				int noNights = Integer.parseInt(str[4]); 
	        				int singles = 0;
	        				int doubles = 0;
	        				int triples = 0;
	        				
	        				for(int i =5; i < str.length; i+=2) { //marks the type of requests 
	        				    if(isASCII(str[i]) == true) {
	        						if(roomType(str[i]) == 1) {
	        				    		singles = Integer.parseInt(str[i+1]); 
	        				    	
	        				    	}else if(roomType(str[i]) == 2) {
	        					        doubles = Integer.parseInt(str[i+1]); 
	        				    	
	        				    	}else if(roomType(str[i]) == 3) {
	        				    		triples = Integer.parseInt(str[i+1]); 
	        				    	}
	        				    }	
	        				}
	        				
	        				//pass in args to check if a booking can be made given time range
	        				String validRooms = skibidipap.bookingValid(date, noNights, singles, doubles, triples);
	        				
	        				//passes back a String of room Numbers if a valid booking can be made
	        				if (validRooms != null) {
	        					//create a booking from given room numbers and return confirmation output string
	        					String a = skibidipap.buildBooking(str[1], validRooms, date, noNights, true, false);
	        					//attempt to book failed unexpectedly
	        					if(a == null) {
	        						System.out.println("Booking Error");
	        					}else {
	        						System.out.println(a);
	        						
	        					}
	        					
	        				}else { //time range/ room requests cannot be fullfilled
	        					System.out.println("Booking rejected");
	        				}
	        			}
	        			
	        			break;
	        		
	        		case "Change":
	        			
	        			//check a customer exists so that a valid booking exists within a system 
	        			if(skibidipap.containCust(str[1])){
	        				LocalDate date = null;
	        				Month month = Month.of(monthType(str[2]));
	        				date = Year.now().atMonth(month).atDay(Integer.parseInt(str[3])); //generate LocalDate for "arrivalDate"
	        				int noNights = Integer.parseInt(str[4]);
	        				
	        				noNights = Integer.parseInt(str[4]);
	        				int singles = 0;
	        				int doubles = 0;
	        				int triples = 0;
	        				
	        				for(int i =5; i < str.length; i+=2) { //marks the type of requests 
	        				    if(isASCII(str[i]) == true) {
	        						if(roomType(str[i]) == 1) {
	        				    		singles = Integer.parseInt(str[i+1]); 
	        				    	
	        				    	}else if(roomType(str[i]) == 2) {
	        					        doubles = Integer.parseInt(str[i+1]); 
	        				    	
	        				    	}else if(roomType(str[i]) == 3) {
	        				    		triples = Integer.parseInt(str[i+1]); 
	        				    	}
	        				    }	
	        				}
	        				//check new time range and room requests can be fullfilled or not
	        				String validRooms = skibidipap.changeBooking(str[1] , date, noNights, singles, doubles, triples);
	        				//if new requests cannot be satisfied
	        				if(validRooms == null) {
	        					//restore previous booking
	        					if(skibidipap.restoreBooking(str[1])) {
	        						//inform customer it is rejected
	        						System.out.println("Change rejected");
	        					}else {
	        						//unexpected failure while restoring booking
	        						System.out.println("Change Error");
	        					}	
	        				}else { //if change options available
	        					
	        					skibidipap.loseOnHold(str[1]); //remove rooms in customer obj and also remove bookings from rooms obj
	        					//build the new booking officially
	        					String newBook = skibidipap.buildBooking(str[1], validRooms, date, noNights, false, true);
	        					
	        					if(newBook == null) {
	        						System.out.println("Booking Error");
	        					}else {
	        						//prints out new booking status
	        						System.out.println(newBook);
	        					}
	        				}
	        				
	        			}else {
	        				System.out.println("Change rejected");
	        			
	        			}
	        			break;
	        		
	        		case "Cancel":
	        			if(skibidipap.containCust(str[1])){ //check if customer exists at all
	        				skibidipap.cancelBooking(str[1]); //cancel 
	        				System.out.println("Cancel " + str[1]);
	        				
	        			}else {
	        				System.out.println("Cancel rejected");
	        			}
	        			break;
	        			
	        		case "Print":
	        			System.out.println(skibidipap.printHotels(str[1]));
	        			break;
	        		default:
	        			
	        			System.out.println("EXITED" + str[1]);
	    
	        			
	        	}
	        	
	        }
	    }
	    
	    catch (FileNotFoundException e){
	        System.out.println(e.getMessage());
	    }  
	    finally{
	        if (sc != null) sc.close();
	    }
	    
	    
	    	
	}
    //constructor
    public HotelBookingSystem() {
		Hotels = new ArrayList<Hotel> ();
		Customers = new ArrayList<Customer> ();
	}
    
	public ArrayList<Hotel> getHotels() {
		return Hotels;
	}
	
	public ArrayList<Customer> getCustomers() {
		return Customers;
	}
	
	public void appendHotel(Hotel newHotel) {
		Hotels.add(newHotel);
	}
	
	public Hotel findHotel(String Name) {
	    Hotel hotel = null;
	    for(int i =0; i < Hotels.size() ; i++) {
	    	hotel = Hotels.get(i);
	    	if(Name.contentEquals(hotel.getName())) {
	    		return hotel;
	    	}
	    }
		return null;
	}
	
	public Customer findCust(String Name) {
	    Customer c = null;
	    for(int i =0; i < Customers.size() ; i++) {
	    	c = Customers.get(i);
	    	if(Name.contentEquals(c.getName())) {
	    		return c;
	    	}
	    }
		return null;
	}
	
	public boolean containsHotel(String HotelName) {
		for(int i =0; i < Hotels.size(); i++) {
			Hotel a = Hotels.get(i);
			if(HotelName.equals(a.getName())) {
				return true;
			}
		}
		return false;
	}
	

	public boolean containCust(String Name) {
		for(int i =0; i < Customers.size(); i++) {
			Customer a = Customers.get(i);
			
			if(Name.contentEquals(a.getName())) {
				return true;
			}
		}
		return false;
	}
	
	//remove all booking kept on hold that is under customer's Name
	public void loseOnHold(String custName) {
		Room r;
		Customer c = findCust(custName);
		ArrayList<Room> oldRooms = c.getRooms();
		while(oldRooms.isEmpty() != true) {
			r = oldRooms.get(oldRooms.size()-1);
			r.loseOnHold(custName);
			oldRooms.remove(oldRooms.size()-1);
		}
		assert(c.getRooms().isEmpty());
		Customers.remove(c);
	}
	
	public String buildBooking(String custName, String rooms, LocalDate date, int noNights, boolean book, boolean change) {	
		
		String[] str = rooms.split(" "); //split the string of rooms passed in that involved all valid rooms that can satisfied 
		Hotel h = findHotel(str[0]);     //the hotel that can satisfy the request passed in as first part of string
		Customer c = new Customer(custName); 
		Customers.add(c); 
		boolean a = h.buildRequest(c, rooms, date, noNights); 
		
		if(a == false) {
			return null; 
		}
		
		//for output statement, create string that correspond to the command
		if(book) return "Booking " + custName + " " + h.getName() + c.rooms();  
		if(change)  return "Change " + custName + " " + h.getName() + c.rooms();
		
		return null;
	}
	
	//restore the customer's prev booking when a change request failed
	public boolean restoreBooking(String custName) {
		Customer c = findCust(custName); //find the customer from list
		ArrayList<Room> oldRooms = c.getRooms(); //get the rooms stored in customer's obj
		if(oldRooms.isEmpty()) return false; //if all rooms they've book is empty, return error
		for(int i =0; i < oldRooms.size(); i++) {
			Room r = oldRooms.get(i); //register the booking back into rooms's bookings list
			r.restoreBooking(custName); //pass in customer's name to find the booking from on hold list
		}
		return true;
	}
	
	//when a change request is received 
	public String changeBooking(String custName, LocalDate date, int noNights, int singles, int doubles, int triples) {
		
		//put everything on hold
		Customer c = findCust(custName);
		ArrayList<Room> oldRooms = c.getRooms(); 
		
		for(int i =0; i < oldRooms.size(); i++) {
		    Room r = oldRooms.get(i);
		    r.bookingOnHold(custName); //put all customer's booking on hold
		}
		//try to find new rooms, first check new request dates
		String hotel = bookingValid(date, noNights, singles, doubles, triples);
	    if(hotel != null) { 
	        return hotel; //if the booking is valid return number of rooms
	    }	
		return null;
	}
	
	//check if "date" is valid for a new booking
	public String bookingValid(LocalDate date, int noNights, int singles, int doubles, int triples) {
		
		int myInt = singles + doubles + triples; //myInt = number of the rooms requested
		int flag = 0;
		
		//loop through hotels in the order of input
		for(int i =0; i < Hotels.size(); i++) {
			Hotel a = Hotels.get(i); 
			int[] array = a.availableRooms(date, noNights, singles, doubles, triples); //get each hotel and check rooms availability
			if(array != null) {
				for(int j=0; j < array.length; j++) {
					if(array[j] != -1) { //every index that is != -1, represent a valid room number
						flag++;  //increment number of valid rooms, and check if the number of valid rooms adds up to number of rooms requested	
					}
				}
				
				if(flag == myInt) { //if this hotel has enough rooms to satisfy the request
					String validRooms = a.getName(); //first append return string with the hotel name
					for(int j =0; j < array.length; j++) {
						validRooms += " ";
						validRooms += array[j]; //then append all detected valid room numbers to the array
					}
				    
					return validRooms; //return the available rooms that satisfies
				}
			}	
		}
		
		return null;
	}
	
	//cancel request received
    public void cancelBooking(String custName) {
    	Customer c = findCust(custName); //get the customer obj
    	
    	while(c.getRooms().isEmpty() == false) { //if all rooms customer has booked is not equals to none
    		int i =0;
    		Room r = c.getRooms().get(i); //for a room in the list
    		for(int j =0; j < r.getRoomBookings().size(); j++) { //get all bookings in the room
    			Booking b = r.getRoomBookings().get(j); 
    			if(b.getCustName().contentEquals(custName)) { //if a booking under the customer name exists
    				r.getRoomBookings().remove(j); //remove the booking
    				if(r.getRoomBookings().isEmpty() == true) { //if after the removal of the booking, the room has no other bookings left
    					r.setOccupied(false); //set the room from occupied to available
    				}
    			}
    		}
    		c.getRooms().remove(r); //finally remove the room from the customer obj
    		i++;
    	}
    	if(c.getRooms().isEmpty() == true) { //double check to make sure the customer now has no room booked under his name
    		Customers.remove(c); //remove the customer from system
    	}
    }
    
    public String printHotels(String hotelName) {
    	Hotel h = findHotel(hotelName);
    	String a = "";
    	
    	for(int i =0; i < h.getAllRooms().size(); i++) {
    		a += hotelName + " "; //append hotel name
    		Room r = h.getAllRooms().get(i);
    		a += r.printBookings(""); //append booking in string
    		if(i+1 < h.getAllRooms().size()) a += "\n"; //adds new line only when it is not the last lien
    	}
    	return a;
    }
}
