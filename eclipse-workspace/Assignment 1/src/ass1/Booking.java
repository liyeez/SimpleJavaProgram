package ass1;
import java.time.LocalDate;

public class Booking {
	
	private String custName;
	private LocalDate arrivalDate;
    private int noNights; 
    
    public static String monthType(int month) {
        switch(month) {
        	case 1:
        		return "Jan";
        	case 2:
        		return "Feb";
        	case 3:
        		return "Mar";
        	case 4:
        		return "Apr";
        	case 5:
        		return "May";
        	case 6:
        		return "Jun";
        	case 7:
        		return "Jul";
        	case 8:
        		return "Aug";
        	case 9:
        		return "Sep";
        	case 10:
        		return "Oct";
        	case 11:
        		return "Nov";
        	case 12:
        		return "Dec";
        	default:
        		return null;
        }	
	}
    
	public Booking(String custName, LocalDate arrivalDate, int noNights) {
		this.custName = custName;
		this.arrivalDate = arrivalDate;
		this.noNights = noNights;
	}	
    
    public String getCustName() {
		return custName;
	}

	public LocalDate getArrivalDate() {
		return arrivalDate;
	}
	public int getNoNights() {
		return noNights;
	}
 
	@Override
	public String toString() {
		int a = arrivalDate.getDayOfMonth();
		String abc = "";
		if(arrivalDate.getDayOfMonth() < 10) {
			abc += ("0" + String.valueOf(a));
			return monthType(arrivalDate.getMonthValue()) + " " + abc + " " + noNights;
		}
		return monthType(arrivalDate.getMonthValue()) + " " + arrivalDate.getDayOfMonth()+ " " + noNights;
	}
	
	
	
}
