/*
 *  @author SafaGunay-SerayArslan 220201044-220201039
 */

package presentation;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;

import domain.Operation;
import domain.Poet;

public class Menu {
	
	Operation operation;
	Scanner keyboard;

	public Menu(Operation operation) {
		
		this.operation = operation;
		keyboard = new Scanner(System.in);
	}

	public void start() throws NumberFormatException, Exception {
		

		displayMenu();
		
		int selectedOption = 0;

		boolean goOn = true;

		while (goOn) {

			try {
			  
				selectedOption = Integer.parseInt(getInputFromKeyboard());
			} 
			
			catch (NumberFormatException e) { selectedOption=0; }
			
			switch (selectedOption) {

				case 1:
					addPoetFromPath();
					break;
	
				case 2:
					findPoetOfMaxOccurence();
					break;
					
				case 3:
					findTop10Words();
					break;
				case 4:
					findPoetsBegginingWithTheWord();
					break;
				case 5:
					try {
						findAcrostics();
					} catch (Exception e) {
						System.out.println("Error!");
						e.printStackTrace();
					}
					break;
				case 6:
					System.out.println("enter the poet index:");
					System.out.println(operation.retrievePoetText(
							Integer.parseInt(getInputFromKeyboard())));
					break;
				default:
					System.out.println("Invalid choice; Enter 9 to quit.");
					continue;
				case 9:
					goOn = false;
			}

			if (goOn)
				displayMenu();
		}
		System.exit(0);
		keyboard.close();
	}
	
	private void findAcrostics() throws Exception {
		System.out.println("Please enter the word you want to search for: ");
		String word = getInputFromKeyboard();
		if(word.length()==0) {
			System.out.println("Invalid word entered,try again!");
			return;
		}
		ArrayList<Poet> foundPoets = operation.findAcrostics(word);
		if(foundPoets.size()==0) 
			System.out.println("Sorry, no acrostics found "
					+ "for the word \"" + word + "\"");
		else {
			for(Poet poet:foundPoets)
				System.out.println(operation.retrievePoetText(
						poet.getOrderInPoets()));
		}
		
		
	}
	private void findPoetsBegginingWithTheWord() {
		System.out.println("Please enter the word you want to search for: ");
		String word = getInputFromKeyboard();
		Poet[] poets = operation.findPoetsBeginningWithTheWord(word);
		int size = poets.length;
		if(size==0)
			System.out.println("Sorry, no poet begins "
					+ "with the word \"" + word + "\"");
		else {
			System.out.println("Following poet/poets begin "
					+ "with the word \"" + word + "\"");
			for(int i=0;i<size;i++)
				System.out.println(poets[i]);
		}
			
	}

	private void findTop10Words() {
		TreeMap<Integer,String> top10Words = 
				operation.findMostFrequentNWords(10);
		System.out.println("Top-ten most frequent words are : ");
		int size = top10Words.size();
		for(int i=0;i<size;i++) {
			int highestKey = top10Words.lastKey();
			System.out.println( top10Words.get(highestKey) + " => " + highestKey + " times.");
			top10Words.remove(highestKey);
		}
		
	}
	private void addPoetFromPath() {
		System.out.println("Please enter file path: ");
		String path = getInputFromKeyboard();
		try {
			operation.addFromPath(path);
		} catch(Exception e) {
			System.out.println(e.getMessage() + ", please try again");
			return;
		}
		System.out.println("Poets are added from file successfully. \n"
				+ "(pre-existing poets were not added)");
	}
	
	private void findPoetOfMaxOccurence() {
		System.out.println("Please Enter the word you want to search for: ");
		String word = getInputFromKeyboard().toLowerCase();
		TreeMap<Integer,ArrayList<Poet>>
			maxOccurenceToPoetList = operation.findPoetsOfMaxOccurance(
				word );
		if(maxOccurenceToPoetList==null)
			System.out.println("Sorry,the word " +"\""+ word +"\" never occurs.");
		else {
			System.out.println("The word " +"\""+ word +"\"" + 
				" occurs the most time in the "+ " following poet/poets:");
			int maxOccurence = maxOccurenceToPoetList.firstKey();
			ArrayList<Poet> poets = maxOccurenceToPoetList.get(maxOccurence);
			System.out.println("Number Of occurences => " + maxOccurence);
			for(Poet poet:poets)
				System.out.println(poet);
		}
	}
	private String getInputFromKeyboard() {
		return keyboard.nextLine();
	}
	
	private static void displayMenu() {
		
		System.out.println("\n------------Menu------------");
		System.out.println("\n1.Add new poet from a file");
		System.out.println("\n2.Find the poet that has max occurence of a spesific word");
		System.out.println("\n3.List top-10 most frequent words");
		System.out.println("\n4.Find poets that begin with a spesific word");
		System.out.println("\n5.Find Acrostics");
		System.out.println("\n6.Retrieve poet by poetOrder in the file");
		System.out.println("---------------------------");
	}

}
