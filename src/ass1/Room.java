package ass1;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;

public class Room {

	private int RoomNo;
	private int capacity;
	private boolean Occupied;
	private String room_type;
	private ArrayList<Booking> roomBookings;
	private ArrayList<Booking> onHold;
	
	public ArrayList<Booking> getRoomBookings() {
		return roomBookings;
	}
	
	public void setOccupied(boolean occupied) {
		Occupied = occupied;
	}
	
	//this only allocates one room
	public int stayRange(LocalDate cust2arrive, int noNights) {
		int room = -1;
		int flag = 0;
		for(int i =0; i < roomBookings.size(); i++) {
			Booking b = roomBookings.get(i);
			//2 possible situations:
			//1. the new cust "2" arrives before existing cust "1": needs to check if cust2 will checkout before cust1 arrives = VALID
			//2. the new cust "2" arrives later than existing cust "1": needs to check if cust1 will checkout before cust2 arrives = VALID
			LocalDate cust1arrive = b.getArrivalDate();
			LocalDate cust1CheckOut = b.getArrivalDate().plusDays(b.getNoNights()-1);
			LocalDate cust2CheckOut = cust2arrive.plusDays(noNights-1);
			//System.out.println();
			if(cust1CheckOut.isBefore(cust2arrive) || cust2CheckOut.isBefore(cust1arrive)) {
				room = RoomNo ; //booking success append array with roomNo
			}else {
				flag = 1;
				break;
			}
		}
	
		if(flag == 0) return room;
		return -1;
	}
	
	public boolean bookRoom(Customer c, LocalDate date, int noNights) {

	    Booking b = new Booking(c.getName() ,date, noNights);
	    if(c != null && b != null) {
	    	roomBookings.add(b); //add new booking that this room has
		    sortBookings(); //for printing purpose
		    Occupied = true;
	    	c.getRooms().add(this); //update customer who requested the booking 
	    	
	    	return true;
	    }
	   
		return false;
	}
	
	//restore booking under customer name that was put on on hold list
	public boolean restoreBooking(String custName) {
		for(int i =0; i < onHold.size(); i++) {//get all bookings that are on hold
			Booking b = onHold.get(i);
			if(b.getCustName().contentEquals(custName)) { //check if a booking under the name is on hold
				if(roomBookings.isEmpty()) {
					Occupied = true; //mark the room occupied again
				}
				roomBookings.add(b); //restore the booking back 
				onHold.remove(i); //remove booking from on hold list
			}
		}
		return false;
	}
    //remove every booking that is listed as on hold
	public void loseOnHold(String custName) {
		for(int i =0; i < onHold.size(); i++) {
			Booking b = onHold.get(i);
			if(b.getCustName().contentEquals(custName)) {
				onHold.remove(i);
			}
		}
		if(roomBookings.isEmpty()) Occupied = false;
	}
	
	public boolean bookingOnHold(String custName) {
		int[] index = new int[] {roomBookings.size()};
		for(int i =0; i < index.length; i++) index[i] = -1;
		int size = 0;
		for(int i =0; i < roomBookings.size(); i++) {
			Booking b = roomBookings.get(i);
			if(b.getCustName().contentEquals(custName)) {
				index[size++] = i;
				//roomBookings.remove(i);
				onHold.add(b);
				
			}
		}
		for(int i =0; i < index.length; i++) {
			if(index[i] != -1) roomBookings.remove(index[i]);
		}
		if(roomBookings.isEmpty()) {
			Occupied = false; //make the room available again
		}
		return false;
	}
	
	public String getRoom_type() {
		return room_type;
	}
    
	//simple bubblesort
	public void sortBookings() {
		for(int i =0; i < roomBookings.size(); i++) {
			for(int j =0 ; j < roomBookings.size()-i-1; j++) {
				LocalDate a = roomBookings.get(j).getArrivalDate();
				LocalDate b = roomBookings.get(j+1).getArrivalDate();
				if(a.isAfter(b)) { 
				    Collections.swap(roomBookings, j, j+1);
				}
			}	
		}
	}
	//constructor
	public Room(int roomNo, int capacity, boolean occupied, String room_type) {
		RoomNo = roomNo;
		this.capacity = capacity;
		Occupied = occupied;
		this.room_type = room_type;
		roomBookings = new ArrayList<Booking>();
		onHold = new ArrayList<Booking>();
	}

	public int getRoomNo() {
		return RoomNo;
	}

	public boolean isOccupied() {
		return Occupied;
	}
	
    
	public String printBookings(String a) {
		sortBookings();
	    a += (RoomNo + " ");
		for(int i =0; i < roomBookings.size(); i++) {
			Booking b = roomBookings.get(i);
			a += b.toString() + " ";
		}
		return a;
	}
}
