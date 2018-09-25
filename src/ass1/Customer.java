package ass1;
import java.util.ArrayList;

public class Customer {
    String Name;
    ArrayList<Room> Rooms;

    public Customer(String name) {
		Name = name;
		this.Rooms = new ArrayList<Room>();
	}
    
	public String getName() {
		return Name;
	}
	
	public ArrayList<Room> getRooms() {
		return Rooms;
	}
    
	public String rooms() {
		String str1 = " ";
		for(int i =0; i < Rooms.size(); i++) {
			Room a = Rooms.get(i);
			str1 += (a.getRoomNo() + " ");
		}
		return str1;
	}
    
	
}

	