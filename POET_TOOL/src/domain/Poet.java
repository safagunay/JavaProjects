/*
 *  @author SafaGunay-SerayArslan 220201044-220201039
 */

package domain;

public class Poet {
	
	private String title;
	private int numberOfWords;
	private int orderInPoets;
	
	public Poet(String title, int numberOfWords, int orderInPoets) {
		super();
		setTitle(title);
		setNumberOfWords(numberOfWords);
		setOrderInPoets(orderInPoets);
		
	}
	
	@Override
	public boolean equals(Object otherObject) {
		
		if(otherObject==null)    
			return false;
		else if(otherObject.getClass()!=getClass())
			return false;
		
		Poet otherPoet = (Poet) otherObject;
		return(title.equals(otherPoet.getTitle())&& 
				numberOfWords==otherPoet.getNumberOfWords());
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getNumberOfWords() {
		return numberOfWords;
	}

	public void setNumberOfWords(int numberOfWords) {
		this.numberOfWords = numberOfWords;
	}

	public int getOrderInPoets() {
		return orderInPoets;
	}

	public void setOrderInPoets(int orderInPoets) {
		this.orderInPoets = orderInPoets;
	}
	
	@Override
	public String toString() {
		return "***" + title + " [numberOfWords=" + numberOfWords 
				+ ", orderInPoets=" + orderInPoets + "]***";
	}
}
