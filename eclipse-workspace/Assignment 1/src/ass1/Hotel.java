package ass1;

import java.time.LocalDate;
import java.util.ArrayList;

public class Hotel {
	private String Name;
	private ArrayList<Room> allRooms;
	
	//constructor
	public Hotel(String Name, String RoomNo, String capacity) {
		this.Name = Name;
		this.allRooms = new ArrayList<Room>();
		String room_type = roomTypes(Integer.parseInt(capacity));
		Room newRoom = new Room(Integer.parseInt(RoomNo), Integer.parseInt(capacity), false, room_type);
		allRooms.add(newRoom);
	}
	
	public String getName() {
		return Name;
	}
	
	public String roomTypes(int capacity) {
        switch(capacity) {
        	case 1:
        		return "Single";
        	case 2:
        		return "Double";
        	case 3:
        		return "Triple";
        	default:
        		return null;
        }	
	}
	
	//build booking for customer to stay in this hotel
	public boolean buildRequest(Customer c, String list, LocalDate date, int noNights) {
		String[] str = list.split(" ");
		//loop through the line array to find all rooms to create booking for
		for(int i =1; i < str.length; i++) {
			Room a = findRooms(Integer.parseInt(str[i])); //find the valid and build the booking to store in the room object
			if(a != null) {
			    if (a.bookRoom(c, date, noNights) == false) { //if some error happened   	
			    	return false; //failed
			    }
			}
		}
	
		return true;
	}
	
	public Room findRooms(int roomNo) {
		for(int i =0; i < allRooms.size(); i++) {
			Room a = allRooms.get(i);
			if(a.getRoomNo() == roomNo) {
				return a;
			}
		}
		return null;
	}
	
	
	public ArrayList<Room> getAllRooms() {
		return allRooms;
	}

	//add new rooms into the hotel
	public void appendHotelRoom(int roomNo, int capacity) {
		if(findRooms(roomNo) == null) {
			Room newRoom = new Room(roomNo, capacity, false, roomTypes(capacity));
			if(allRooms.contains(newRoom)) {
				System.out.println("Room exists");
				return;
			}else {
				allRooms.add(newRoom);
			}
	    }	
	}
	
	//check if there exists valid rooms in this hotel that satisfies all requests
	public int[] availableRooms(LocalDate d, int noNights, int singles, int doubles, int triples) {
		Room r = null;
		int size = 0;
		int myInt = singles + doubles + triples;
		int[] myRooms = new int[myInt]; 
		
		//initialise the rooms to all -1
		for(int i =0; i < myRooms.length; i++) myRooms[i] = -1;
		
		for(int i =0; i < allRooms.size(); i++) { //get all rooms under the hotel
			r = allRooms.get(i);
			
			if((singles > 0) && (r.getRoom_type() == "Single")) { //if singles request > 0, and the room type is single
				if(r.isOccupied()) { //if someone has booked the room
					int roomNo = r.stayRange(d, noNights); //pass in request's date and check if the customer can stay without clashing with another customer
					//return 0 if clash
					if(roomNo > 0) {
						myRooms[size++] = roomNo; //record valid room
						singles--;   //mark room request satisifed by decrement the room request by 1
					}
				}else {
					myRooms[size++] = r.getRoomNo(); //record valid room
					singles--;   //mark room request satisifed
				}
				
			}
			
			if((doubles > 0) && (r.getRoom_type() == "Double")) {
				if(r.isOccupied()) { //if someone has booked the room
					int roomNo = r.stayRange(d, noNights);
					if(roomNo > 0) {
						myRooms[size++] = roomNo;
						doubles--;
					}
				}else {
					
					int a = r.getRoomNo();
					myRooms[size++] = a;
					doubles--;
				}
				
			}
			
			if((triples > 0) && (r.getRoom_type() == "Triple")) {
				if(r.isOccupied()) { //if someone has booked the room
					int roomNo = r.stayRange(d, noNights);
					if(roomNo > 0) {
						myRooms[size++] = roomNo;
						triples--;
					}
				}else {
					myRooms[size++] = r.getRoomNo();
					triples--;
				}
			}
			
			myInt = singles + doubles + triples;
			
			if(myInt == 0) { //if all rooms requests are 0, means all rooms satisfied
				
				break; //quit for loop
			}
		}
		myInt = singles + doubles + triples;
	
		if(myInt > 0) { //if room requests are still > 0 when loop quits
		    return null; //means not enough rooms in this hotel can satisfy the request
		}
		
		return myRooms; //else return all valid room numbers recorded
	}
	
}
