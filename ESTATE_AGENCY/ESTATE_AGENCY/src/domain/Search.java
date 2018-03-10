package domain;

public class Search {
	
	int[] priceFilter;
	int[] sizeFilter;
	int roomFilter;
	
	
	public Search(int[] priceFilter, int[] sizeFilter, int roomFilter) {
		
		super();
		this.priceFilter = priceFilter;
		this.sizeFilter = sizeFilter;
		this.roomFilter = roomFilter;
		
	}
	
	public Search(String lowerPrice, String upperPrice, String lowerSize, String upperSize,
					String roomNumber) throws IllegalArgumentException {
		
		boolean error = false;
		String errorMessages = "";
		
		//Creating price filter
		int parsedLowerPrice=0;
		int parsedUpperPrice=0;
		try {
			parsedLowerPrice = convertInt(lowerPrice);
			parsedUpperPrice = convertInt(upperPrice);
		}catch(IllegalArgumentException e) {
			errorMessages+="Please enter valid price limits.\n";
			error = true;
		}
		int[] priceFilter = new int[2];
		if(!error) {
		
			if(parsedLowerPrice==-1 && parsedUpperPrice!=-1) {
				priceFilter[0] = 0;
				priceFilter[1] = parsedUpperPrice;
			}
			else if(parsedLowerPrice!=-1 && parsedUpperPrice==-1) {
				priceFilter[0] = parsedLowerPrice;
				priceFilter[1] = Integer.MAX_VALUE;
			}
			else if(parsedLowerPrice==-1 && parsedUpperPrice==-1) {
				priceFilter[0] = 0;
				priceFilter[1] = Integer.MAX_VALUE;
			}
			else {
				priceFilter[0] = parsedLowerPrice;
				priceFilter[1] = parsedUpperPrice;
			}
		}
		
		//Creating size filter
		int parsedLowerSize=0;
		int parsedUpperSize=0;
		try {
			parsedLowerSize = convertInt(lowerSize);
			parsedUpperSize = convertInt(upperSize);
		}catch(IllegalArgumentException e) {
			errorMessages+="Please enter valid size limits.\n";
			error = true;
		}
		int[] sizeFilter = new int[2];
		if(!error) {
			if(parsedLowerSize==-1 && parsedUpperSize!=-1) {
				sizeFilter[0] = 0;
				sizeFilter[1] = parsedUpperSize;
			}
			else if(parsedLowerSize!=-1 && parsedUpperSize==-1) {
				sizeFilter[0] = parsedLowerSize;
				sizeFilter[1] = Integer.MAX_VALUE;
			}
			else if(parsedLowerSize==-1 && parsedUpperSize==-1) {
				sizeFilter[0] = 0;
				sizeFilter[1] = Integer.MAX_VALUE;
			}
			else {
				sizeFilter[0] = parsedLowerSize;
				sizeFilter[1] = parsedUpperSize;
			}
		}
		
		//Creating room filter
		int parsedRoomNumber = 0;
		try {
			parsedRoomNumber = convertInt(roomNumber);
		}catch(IllegalArgumentException e) {
			errorMessages+="Please enter valid room number.\n";
			error = true;
		}
		if(error) {
			throw new IllegalArgumentException(errorMessages);
		}
		
		//Checking validity
		if(priceFilter[0] > priceFilter[1] || sizeFilter[0] > sizeFilter[1]) {
			System.out.println(priceFilter[0] + " " + priceFilter[1]);
			throw new IllegalArgumentException("Invalid boundary selected.\n"
												+ " minumum should be smaller than the maximum.");
		}
		if(parsedRoomNumber>500) 
			throw new IllegalArgumentException("isterseniz cumhurbaskanligi sarayimiz var 1000 odali");
		
		//Setting attributes
		setPriceFilter(priceFilter);
		setSizeFilter(sizeFilter);
		setRoomFilter(parsedRoomNumber);
	}
	
	private int convertInt(String number) throws IllegalArgumentException {
		
		if(number.equals("")) {
			return -1;
		}
		int parsedNum = 0;
		try{
			parsedNum=Integer.parseInt(number);
		}catch(NumberFormatException e) {
			throw new IllegalArgumentException("Error:Invalid value entered.\n ");
		}
		if(parsedNum<0) {
			throw new IllegalArgumentException("Error:Negative value entered.\n ");
		}
		return parsedNum;
	}

	@Override
	public boolean equals(Object otherSearch) {
		
		if(otherSearch==null) 
			return false;
		if(otherSearch.getClass()!=getClass())
			return false;
		
		Search search = (Search) otherSearch;
		return (search.getPriceFilter().equals(priceFilter) && search.getSizeFilter().equals(sizeFilter) && 
					search.getRoomFilter()==roomFilter);
		
	}

	public int[] getPriceFilter() {
		return priceFilter;
	}

	public void setPriceFilter(int[] priceFilter) {
		this.priceFilter = priceFilter;
	}

	public int[] getSizeFilter() {
		return sizeFilter;
	}

	public void setSizeFilter(int[] sizeFilter) {
		this.sizeFilter = sizeFilter;
	}

	public int getRoomFilter() {
		return roomFilter;
	}

	public void setRoomFilter(int roomFilter) {
		this.roomFilter = roomFilter;
	}
}
