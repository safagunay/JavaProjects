package domain;

import java.util.ArrayList;
import java.util.Stack;

import fileAccess.EstateAgencyDAL;
import presentation.EstateAgencyGUI;

public class EstateAgency {
	
	ArrayList<House> houseList;
	ArrayList<House> searchResultList;
	Search lastMadeSearch;
	boolean tableOrder;
	
	public static final boolean ASCENDING_PRICE_ORDER = true;
	public static final boolean DESCENDING_PRICE_ORDER = false;
	
	EstateAgencyGUI estateAgencyGUI;
	EstateAgencyDAL estateAgencyDAL;
	
	public EstateAgency() {
		
		houseList = new ArrayList<House>();
		
		String path = "housing.txt";
		estateAgencyDAL = new EstateAgencyDAL(this,path);
		estateAgencyDAL.readAll();
		
		initializeSearchResultList();
		lastMadeSearch = null;
		tableOrder = ASCENDING_PRICE_ORDER;
		
		estateAgencyGUI = new EstateAgencyGUI(this);
	
	}
	
	private void initializeSearchResultList() {
		
		sortByPrice(houseList);
		searchResultList = new ArrayList<House>();
		for(House house:houseList) 
			searchResultList.add(house);
	}

	public boolean removeHouse(House house) {
		
		return houseList.remove(house);
	}
	
	
	public boolean removeHouse(String id) {
		
		int parsedID = 0;
		try{
			parsedID = Integer.parseInt(id);
		}catch(NumberFormatException e) {
			return false;
		}
		House tempHouse = new House(parsedID,0,0,0,0,true);
		if(removeHouse(tempHouse)) {
			if(removeHouseFromSearchList(tempHouse))
				estateAgencyGUI.updateTable();
			return true;
		}
		return false;
	}
	
	public boolean removeHouseFromSearchList(House house) {
		
		return searchResultList.remove(house);
	}
	
	public void start() {
		
		estateAgencyGUI.createAndShow();
	}
	
	public void addHouse(House house) {
		 
		houseList.add(house);
	}
	
	private static void sortByPrice(ArrayList<House> houseList) {
		
		houseList.sort( new PriceComparator() );
	}

	public boolean addHouseFromGUI(House house) {
		
		if(contains(house.getID()))
			return false;
		houseList.add(house);
		if(filterHouse(house)) {
			searchResultList.add(house);
			sortByPrice(searchResultList);
		}
		if(tableOrder==DESCENDING_PRICE_ORDER) {
			reverseList(searchResultList);
		}
		estateAgencyGUI.updateTable();
		return true;
	}
	
	public boolean contains(int id) {
		return houseList.contains(new House(id,0,0,0,0,true));
	}

	private boolean filterHouse(House house) {
		
		if(lastMadeSearch==null)
			return true;
		
		int lowerPrice = lastMadeSearch.getPriceFilter()[0];
		int upperPrice = lastMadeSearch.getPriceFilter()[1];
		int lowerSize = lastMadeSearch.getSizeFilter()[0];
		int upperSize = lastMadeSearch.getSizeFilter()[1];
		int roomFilter = lastMadeSearch.getRoomFilter();
		
		
		if(lowerPrice<=house.getPrice() && house.getPrice()<=upperPrice) 
			if(lowerSize<=house.getSize() && house.getSize()<=upperSize) 
				if(roomFilter!=-1) {
					if(house.getRooms()==roomFilter)
						return true;
				}
				else
					return true;
					
		return false;
	}

	public ArrayList<House> getHouseList() {
		return houseList;
	}

	public void setHouseList(ArrayList<House> houseList) {
		this.houseList = houseList;
	}
	
	public ArrayList<House> getSearchResultList() {
		return searchResultList;
	}

	public void setSearchResultList(ArrayList<House> searchResults) {
		this.searchResultList = searchResults;
	}

	public Search getLastMadeSearch() {
		return lastMadeSearch;
	}

	public void setLastMadeSearch(Search lastMadeSearch) {
		this.lastMadeSearch = lastMadeSearch;
	}

	public boolean getTableOrder() {
		return tableOrder;
	}
	
	public void setTableOrder(boolean orderType) {
		tableOrder=orderType;
	}

	public void filterSearchResults(Search search) {
		
		if(search==null) {
			if(lastMadeSearch!=null) {
				searchResultList = new ArrayList<House>();
				for(House house:houseList) 
					searchResultList.add(house);
				sortByPrice(searchResultList);
				if(tableOrder==DESCENDING_PRICE_ORDER)
					reverseList(searchResultList);
				estateAgencyGUI.updateTable();
			}
			return;
		}
		if(search.equals(lastMadeSearch))
			return;
		
		setLastMadeSearch(search);
		setSearchResultList(new ArrayList<House>());
		for(House house : houseList) 
			if(filterHouse(house))
				searchResultList.add(house);
		sortByPrice(houseList);
		if(tableOrder==DESCENDING_PRICE_ORDER)
			reverseList(searchResultList);
		estateAgencyGUI.updateTable();
	}
	
	public void saveToFile() {
		
		estateAgencyDAL.saveAll();
	}
	
	private static void reverseList(ArrayList<House> houseList) {
		
		Stack<House> houseStack = new Stack<House>();
		for(House h : houseList) 
			houseStack.push(h);
		int size = houseList.size();
		houseList.clear();
		for(int i=0;i<size;i++) 
			houseList.add(houseStack.pop());
	}
	public void switchOrder(boolean orderType) {
		if(tableOrder!=orderType) {
			reverseList(searchResultList);
			estateAgencyGUI.updateTable();
			tableOrder = !tableOrder;
		}
	}
}
