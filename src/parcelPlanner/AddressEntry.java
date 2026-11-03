package parcelPlanner;

public class AddressEntry {
	private String postcode;
    private double xCoord;
    private double yCoord;

    //overloaded constructor
    public AddressEntry(String postcode, double xCoord, double yCoord) {
        this.postcode = postcode;
        this.xCoord = xCoord;
        this.yCoord = yCoord;
    }

    //getters
    public String getPostcode() {
        return postcode;
    }

    public double getXCoord() {
        return xCoord;
    }

    public double getYCoord() {
        return yCoord;
    }
    
    //setters
    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public void setXCoord(double xCoord) {
        this.xCoord = xCoord;
    }

    public void setYCoord(double yCoord) {
        this.yCoord = yCoord;
    }
}