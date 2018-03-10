package domain;

public class House {
	
	private int id;
	private int price;
	private int size;
	private int rooms;
	private int bathrooms;
	private boolean airConditioner;
	
	private static final String yesAirConditioner = "yes";
	private static final String noAirConditioner= "no";
	
	public House(int id,int price, int size, int rooms, int bathrooms, boolean airConditioner) {
		
		setID(id);
		setPrice(price);
		setSize(size);
		setRooms(rooms);
		setBathrooms(bathrooms);
		setAirConditioner(airConditioner);
	}
	
	public House(String id,String price,String size,String rooms,String bathrooms,String airConditioner) {

		setID(id);
		setPrice(price);
		setSize(size);
		setRooms(rooms);
		setBathrooms(bathrooms);
		setAirConditioner(airConditioner);
		
	}
	@Override
	public boolean equals(Object otherHouse) {
		
		if(otherHouse==null)
			return false;
		if(otherHouse.getClass()!=getClass())
			return false;
		
		House house = (House) otherHouse;
		return house.getID()==getID();
	}
	
	public void setID(int id) {
		
		this.id = id;
		
	}
	
	public boolean setID(String id) {
		
		int parsedID = 0;
		try{
			parsedID = Integer.parseInt(id);
		}catch(NumberFormatException e) {
			return false;
		}
		setID(parsedID);
		return true;
	}
	
	public int getID() {
		return id;
	}
	
	public int getPrice() {
		return price;
	}
	public void setPrice(int parsedPrice) {
		
		this.price = parsedPrice;
		
	}
	
	public boolean setPrice(String price) {
		int parsedPrice = 0;
		try{
			parsedPrice = Integer.parseInt(price);
		}catch(NumberFormatException e) {
			return false;
		}
		setPrice(parsedPrice);
		return true;
	}
	
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	
	public boolean setSize(String size) {
		int parsedSize = 0;
		try{
			parsedSize = Integer.parseInt(size);	
		}catch(NumberFormatException e) {
			return false;
		}
		setSize(parsedSize);
		return true;
	}
	
	public int getRooms() {
		return rooms;
	}
	public void setRooms(int rooms) {
		this.rooms = rooms;
	}
	
	public boolean setRooms(String rooms) {
		int parsedRooms = 0;
		try{
			parsedRooms = Integer.parseInt(rooms);	
		}catch(NumberFormatException e) {
			return false;
		}
		setRooms(parsedRooms);
		return true;
	}
	
	public int getBathrooms() {
		return bathrooms;
	}
	public void setBathrooms(int bathrooms) {
		this.bathrooms = bathrooms;
	}
	
	public boolean setBathrooms(String bathrooms) {
		int parsedBathRooms = 0;
		try{
			parsedBathRooms = Integer.parseInt(bathrooms);	
		}catch(NumberFormatException e) {
			return false;
		}
		setBathrooms(parsedBathRooms);
		return true;
	}
	
	public boolean isAirConditioner() {
		return airConditioner;
	}
	public void setAirConditioner(boolean airConditioner) {
		this.airConditioner = airConditioner;
	}
	
	public boolean setAirConditioner(String airConditioner) {
		if(airConditioner.equals(yesAirConditioner)) {
			setAirConditioner(true);
			return true;
		}
		if(airConditioner.equals(noAirConditioner)) {
			setAirConditioner(false);
			return true;
		}
		
		return false;
	}
	
	@Override
	public House clone()  {
		
		return new House(id,price,size,rooms,bathrooms
										, airConditioner);
	}
	
	public String toFile() {
		return id + "," + price + ", " +  size + ", " + rooms + ", " +  bathrooms + ", " +  airConditioner;
	}
	
	@Override
	public String toString() {
		return "House [ID=" + id + ", price=" + price + ", size=" + size + ", rooms=" + rooms + ", bathrooms=" + bathrooms
				+ ", airConditioner=" + airConditioner + "]";
	}

}
