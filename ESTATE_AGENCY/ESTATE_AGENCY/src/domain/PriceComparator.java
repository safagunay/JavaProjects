/*Comparator class to comrate
*two house objects by their price
*/
package domain;

import java.util.Comparator;

public class PriceComparator implements Comparator<House>  {
	@Override
	public int compare(House house, House otherHouse) {
		
		if(house.getPrice()>otherHouse.getPrice())
			return 1;
		if(house.getPrice()<otherHouse.getPrice())
			return -1;
		else return 0;
	}
}
