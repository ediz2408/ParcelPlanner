package parcelPlanner;
public class DriverEntry {
	private String fName;
    private String lName;

    //overloaded constructor
    public DriverEntry(String fName, String lName) {
        this.fName = fName;
        this.lName = lName;
    }

    //getters
    public String getFname() {
        return fName;
    }

    public String getLname() {
        return lName;
    }
    
    //setters
    public void setFname(String fName) {
        this.fName = fName;
    }

    public void setLname(String lName) {
        this.lName = lName;
    }
}