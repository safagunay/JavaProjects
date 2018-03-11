/*
 *  @author SafaGunay-SerayArslan 220201044-220201039
 */

package dataAccess;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

import domain.Operation;
import domain.Poet;

public class DataAccessLayer {
	
	Operation operation;
	String title;
	int orderInPoets;
	int numberOfWords;
	String mainFilePath;
	
	public DataAccessLayer(Operation operation) {
		this.operation = operation;
		this.mainFilePath = operation.getMainFilePath();
	}
	public void readFile(String path) throws Exception {
		
		Poet poet;
		String poetText = "";
		Scanner scanner;
		StringTokenizer tokenizer;
		String line;
		int lineCounter = 0;
		scanner = new Scanner(new File(path));
		//Poet objects and their corresponding wordTokenizers have the same index in both lists.
		ArrayList<Poet> poetList = new ArrayList<Poet>();
		ArrayList<String> poetTextList = new ArrayList<String>();
		ArrayList<StringTokenizer> wordTokenizerList = new ArrayList<StringTokenizer>();
		
		//File format is being checked and data is created.
		boolean dataExists = false;
		while(scanner.hasNextLine()) {
			line = scanner.nextLine();
			lineCounter++;
			if(line.trim().length()==0)
				continue;
			dataExists = true;
			tokenizer = new StringTokenizer(line, ":");
			if(!tokenizer.nextToken().equalsIgnoreCase("Title")) {
				scanner.close();
				throw new Exception("Illegal file format, line: " + lineCounter);
			}
			title = tokenizer.nextToken().substring(1);
			
			while(scanner.hasNextLine()) {
				line = scanner.nextLine();
				lineCounter++;
				if(line.trim().length()!=0) 
					poetText+= " ^" + line + "\n";
				else 
					break;
			}
			if(poetText.length()<5) {
				scanner.close();
				throw new Exception("Poet with title: " + title + " is unappropriate, " +
										"line: " + lineCounter);
			}
			tokenizer = new StringTokenizer(poetText, " \n,.!-?;&");
			numberOfWords = tokenizer.countTokens();
			orderInPoets = operation.getCurrentPoetIndex() + poetList.size() + 1;
			poet = new Poet(title,numberOfWords,orderInPoets);
			if(!poetList.contains(poet) && !operation.hasPoet(poet)) {
				poetList.add(poet);
				wordTokenizerList.add(tokenizer);
				poetTextList.add(poetText);
				poetText = new String("");
			}
		}
		scanner.close();
		if(!dataExists)
			throw new Exception("No data exists in file, " + path);
		if(!path.equals(mainFilePath)) {
			int size = poetList.size();
			for(int i=0;i<size;i++)
			appendToMainFile(poetList.get(i),poetTextList.get(i));
		}
		addPoets(poetList, wordTokenizerList);
	}
	
	private void appendToMainFile(Poet poet,String poetText) throws IOException {
		FileWriter fw = new FileWriter(mainFilePath, true);
		BufferedWriter bw = new BufferedWriter(fw);
	    PrintWriter out = new PrintWriter(bw);
		
	    String firstLine = "Title: " + poet.getTitle() + "\n";
		StringTokenizer tokenizer = new StringTokenizer(poetText, "\n");
		out.append("\n");
		out.append(firstLine);
		while(tokenizer.hasMoreTokens())
			out.append(tokenizer.nextToken().substring(2) + "\n");
		out.close();
	}
	
	public String getPoetText(Integer poetIndex) throws Exception {
		Poet searchingPoet = operation.getPoetAt(poetIndex);
		if(searchingPoet==null) 
			throw new Exception("No poet exists in the spesified index.");
		Scanner fileReader = new Scanner(new File(mainFilePath));
		int orderInFile = searchingPoet.getOrderInPoets();
		int currentOrder = 0;
		String line = null;
		String poetText = searchingPoet.toString() + "\n";
		while(fileReader.hasNextLine()) {
			line = fileReader.nextLine();
			if(line.trim().length()==0)
				continue;
			currentOrder++;
			if(orderInFile==currentOrder) 
				while(fileReader.hasNextLine()) {
					line = fileReader.nextLine();
					if(line.trim().length()!=0)
						poetText+=line + "\n";
					else
						break;
				}
			else {
				while(fileReader.hasNextLine()) {
					line = fileReader.nextLine();
					if(line.trim().length()==0)
						break;
				}
			}
		}
		fileReader.close();
		return poetText;
	}
	private void addPoets(ArrayList<Poet> poetList, ArrayList<StringTokenizer> wordTokenizerList) {
		int size = poetList.size();
		StringTokenizer currentPoetTokens;
		for(int i=0;i<size;i++) {
			operation.addPoet(poetList.get(i));
			currentPoetTokens = wordTokenizerList.get(i);
			while(currentPoetTokens.hasMoreTokens()) {
				String word = currentPoetTokens.nextToken();
				if(word.charAt(0)=='^') {
					operation.addWord(word.substring(1),true);
				}
				else
					operation.addWord(word,false);
			}
		}
	}
}