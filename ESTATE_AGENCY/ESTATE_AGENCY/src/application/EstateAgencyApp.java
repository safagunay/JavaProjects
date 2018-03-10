package application;

import domain.EstateAgency;

public class EstateAgencyApp {
	
	public static void main(String... args) {
		
		EstateAgency estateAgency = new EstateAgency();
		estateAgency.start();
		System.out.println("terminated.");

	}
	
	
}
