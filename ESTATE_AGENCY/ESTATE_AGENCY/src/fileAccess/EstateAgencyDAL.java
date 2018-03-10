package fileAccess;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

import domain.EstateAgency;
import domain.House;

public class EstateAgencyDAL {
	
	EstateAgency estateAgency;
	String path;
	
	public EstateAgencyDAL(EstateAgency estateAgency, String path) {
		
		this.estateAgency = estateAgency;
		this.path = path;
	}

	public void readAll() {
		
		House house;
		Scanner scanner;
		StringTokenizer tokenizer;
		
		String id = null;
		String price = null;
		String size = null;
		String rooms = null;
		String bathRooms = null;
		String airConditioner = null;

		try {
			scanner = new Scanner(new File(path));
		} catch (FileNotFoundException e) {
			System.out.println("File not found!");
			return;
		}

		while (scanner.hasNextLine()) {
			
			String line = scanner.nextLine();
			tokenizer = new StringTokenizer(line, ",");
			
			if (tokenizer.hasMoreTokens()) {
				id = tokenizer.nextToken();
			}
			
			if (tokenizer.hasMoreTokens()) {
				price = tokenizer.nextToken();
			}
			
			if (tokenizer.hasMoreTokens()) {
				size = tokenizer.nextToken();
			}

			if (tokenizer.hasMoreTokens()) {
				rooms = tokenizer.nextToken();
			}

			if (tokenizer.hasMoreTokens()) {
				bathRooms = tokenizer.nextToken();
			}
			
			if(tokenizer.hasMoreTokens()) {
				airConditioner = tokenizer.nextToken();
			}
			
			house = new House(id,price,size,rooms,bathRooms,airConditioner);
			estateAgency.addHouse(house);
		}
		scanner.close();
	}
	
	public void saveAll() {
		PrintWriter outputStream;
		
		try {
			outputStream = new PrintWriter(new File("housing_updated.txt"));
		} catch (Exception e) {
			System.out.println("Cannot write to file");
			return;
		}

		ArrayList<House> houseList = estateAgency.getHouseList();
		for (House house : houseList) {
			outputStream.println(house.toFile());
		}
		
		outputStream.close();	
		
	}
	
}
