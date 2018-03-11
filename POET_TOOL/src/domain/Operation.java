/*
 *  @author SafaGunay-SerayArslan 220201044-220201039
 */

package domain;
/*
 */

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import dataAccess.DataAccessLayer;
import presentation.Menu;

public class Operation {
	
	private long currentPosIndex;
	private int currentPoetIndex;
	//the poet's order in poets.txt equals its index in poetList.
	private ArrayList<Poet> poetList;
	private HashMap<String,ArrayList<Long>> wordToPositionIndexMap;
	private HashMap<String,ArrayList<Integer>> wordToPoetIndexMap;
	private DataAccessLayer dal;
	Menu menu;
	//The file in mainFilePath is updated when new poet is added 
	//thus synchronized with poetList.
	String mainFilePath;

	public Operation(String mainFilePath) throws Exception  {
		currentPosIndex = 1;
		currentPoetIndex = 0;
		poetList = new ArrayList<Poet>();
		poetList.add(null);
		wordToPositionIndexMap = new HashMap<String,ArrayList<Long>>();
		wordToPoetIndexMap = new HashMap<String,ArrayList<Integer>>();
		
		this.mainFilePath = mainFilePath;
		dal = new DataAccessLayer(this);
		dal.readFile(mainFilePath);
		menu = new Menu(this);
		menu.start();
	}
	
	public String getMainFilePath() {
		return mainFilePath;
	}
	
	public Poet getPoetAt(int poetIndex) {
		if(poetIndex>currentPoetIndex)
			return null;
		else return poetList.get(poetIndex);
	}
	
	public int getCurrentPoetIndex() {
		return currentPoetIndex;
	}

	public void addPoet(Poet poet) {
		poetList.add(++currentPoetIndex, poet);
	}
	
	public ArrayList<Poet> findAcrostics(String letterSequence) {
		if(letterSequence==null || letterSequence.length()==0)
			return null;
		String normalSequence = letterSequence.toLowerCase();
		char[] letters = normalSequence.toCharArray();
		char firstLetter = letters[0];
		ArrayList<Poet> foundPoets = new ArrayList<Poet>();
		Set<String> setOfAllWords = wordToPoetIndexMap.keySet();
		//finding poets starts with the first letter.
		for(String word0:setOfAllWords)
			if(word0.charAt(0)==firstLetter) {
				Poet[] poets = findPoetsBeginningWithTheWord(word0);
				if(poets.length==0)
					continue;
				//poet found => collect all the words between its position boundaries.
				else {
					for(Poet poet:poets) {
						int poetIndex = poetList.indexOf(poet);
						long lowestPosIndex = calculateLowestPosition(poetIndex);
						long highestPosIndex = lowestPosIndex + poet.getNumberOfWords()-1;
						TreeMap<Long,String> foundPositions = new TreeMap<Long,String>();
						for(String word1:setOfAllWords) 
							foundPositions.putAll(
									getIncludedPositions(word1, lowestPosIndex, highestPosIndex));
						//separate positions at the beginning of the line.(which are negative indexed)
						SortedMap<Long,String>  negativePositions =
								foundPositions.subMap(Long.MIN_VALUE, (long) 0);
						int size = negativePositions.size();
						//check whether numberOfLines matches length of charSequence.
						if(size!=letters.length)
							continue;
						Collection<String> lineStarterWords = negativePositions.values();
						boolean acrosticFound = true;
						//check whether letters at the beginning of each line matches charSequence.
						for(String lineStarterWord:lineStarterWords) 
							if(lineStarterWord.charAt(0)!=letters[--size]) {
								acrosticFound=false;
								break;
							}
						if(acrosticFound)
							foundPoets.add(poet);
					}
				}
			}
		return foundPoets;
	}
	//Text is read from the file in MAINPATH by looking the 
	//poetIndex=poet's numberOfOrder.
	public String retrievePoetText(Integer poetIndex) throws Exception {
		return dal.getPoetText(poetIndex);
	}
	//helper function of findAcrostics(String letterSequence)
	private TreeMap<Long,String> getIncludedPositions(String word, long lowestPosIndex, 
			long highestPosIndex) {
		ArrayList<Long> posIndexList = wordToPositionIndexMap.get(word);
		TreeMap<Long,String> foundPositionIndexes = new TreeMap<Long,String>();
		for(Long posIndex:posIndexList) {
			if(-posIndex>=lowestPosIndex && -posIndex<=highestPosIndex) 
				foundPositionIndexes.put(posIndex,word);
			else if(posIndex>=lowestPosIndex && posIndex<=highestPosIndex)
				foundPositionIndexes.put(posIndex, word);
		}
		return foundPositionIndexes;
	}

	public Poet[] findPoetsBeginningWithTheWord(String word0) {
		
		Set<String> setOfAllWords = wordToPositionIndexMap.keySet();
		ArrayList<Integer> foundPoetIndexes = new ArrayList<Integer>();
		for(String word:setOfAllWords) {
			if(word0.equalsIgnoreCase(word)) {
				ArrayList<Long> posIndexList = wordToPositionIndexMap.get(word);
				ArrayList<Integer> poetIndexList = wordToPoetIndexMap.get(word);
				for(Integer poetIndex:poetIndexList) {
					long lowestPosIndex = calculateLowestPosition(poetIndex);
					if(posIndexList.contains(-1*lowestPosIndex))
						foundPoetIndexes.add(poetIndex);
				}
			}		
		}
		int size = foundPoetIndexes.size();
		Poet[] correspondingPoets = new Poet[size];
		for(int i=0;i<size;i++) {
			correspondingPoets[i] = poetList.get(
					foundPoetIndexes.get(i));
		}
		return correspondingPoets;
	}
	
	public void addWord(String word,boolean isBegginingOfLine) {
		
		String normalWord = word.toLowerCase();
		
		ArrayList<Long> posIndexList = wordToPositionIndexMap.get(normalWord);
		if(posIndexList==null) {
			posIndexList = new ArrayList<Long>();
			if(isBegginingOfLine) 
				posIndexList.add(-1*currentPosIndex);
			else
				posIndexList.add(currentPosIndex);
			wordToPositionIndexMap.put(normalWord,posIndexList);
		}
		else {
			if(isBegginingOfLine)
				posIndexList.add(-1*currentPosIndex);
			else 
				posIndexList.add(currentPosIndex);
		}
		currentPosIndex++;
		
		ArrayList<Integer> poetIndexList = wordToPoetIndexMap.get(normalWord);
		if(poetIndexList==null) {
			poetIndexList = new ArrayList<Integer>();
			poetIndexList.add(currentPoetIndex);
			wordToPoetIndexMap.put(normalWord,poetIndexList);
		}
		else 
			if(!poetIndexList.contains(currentPoetIndex))
				poetIndexList.add(currentPoetIndex);
	}
	public TreeMap<Integer,String> findMostFrequentNWords(int n) {
		TreeMap<Integer,String> topNWords = new TreeMap<Integer,String>();
		Set<String> keySet = wordToPositionIndexMap.keySet();
		int lowestFrequency= 0;
		int counter = 0;
		for(String word:keySet) {
			int frequency = wordToPositionIndexMap.get(word).size();
			if(counter>=n && frequency>lowestFrequency && 
					!topNWords.containsKey(frequency)) 	{
				topNWords.remove(lowestFrequency);
				topNWords.put(frequency,word);
				lowestFrequency = topNWords.firstKey();
			}
			else if(counter<n && !topNWords.containsKey(frequency)) {
				topNWords.put(frequency,word);
				lowestFrequency = topNWords.firstKey();
				counter++;
			}
		}
		return topNWords;
	}
	
	private Long calculateLowestPosition(int poetIndex) {
		long lowestPosIndex = 1;
		for(int i=1;i<poetIndex;i++)
			lowestPosIndex += poetList.get(i).getNumberOfWords();
		return lowestPosIndex;
	}
	//this function is used in menu class.
	//Since path is not equal to mainPath 
	//poets will be appended to the end poets.txt
	public void addFromPath(String path) throws Exception {
		dal.readFile(path);
	}
	public boolean hasPoet(Poet poet) {
		return poetList.contains(poet);
	}

	public TreeMap<Integer,ArrayList<Poet>> findPoetsOfMaxOccurance(String word) {
		if(word==null)
			return null;
		ArrayList<Integer> poetIndexList = wordToPoetIndexMap.get(word);
		ArrayList<Long> posIndexList = wordToPositionIndexMap.get(word);
		int maxOccurence = 0;
		ArrayList<Poet> poetsOfMaxOccurence = new ArrayList<Poet>();
		if(poetIndexList==null)
			return null;
		long lowestPosIndex;
		long highestPosIndex;
		for(Integer currentPoetIndex:poetIndexList) {
			Poet currentPoet = poetList.get(currentPoetIndex);
			lowestPosIndex = calculateLowestPosition(
					currentPoetIndex);
			highestPosIndex = lowestPosIndex + currentPoet.getNumberOfWords()-1;
			int occurenceCounter = 0;
			for(Long posIndex : posIndexList) 
				if(lowestPosIndex<=Math.abs(posIndex) && highestPosIndex>=Math.abs(posIndex)) 
					occurenceCounter++;
			if(occurenceCounter>maxOccurence) {
				maxOccurence = occurenceCounter;
				poetsOfMaxOccurence.clear();
				poetsOfMaxOccurence.add(currentPoet);
			}
			else if(occurenceCounter==maxOccurence)
				poetsOfMaxOccurence.add(currentPoet);
		}
		TreeMap<Integer,ArrayList<Poet>> maxOccurenceToPoetList =
				new TreeMap<Integer,ArrayList<Poet>>();
		maxOccurenceToPoetList.put(maxOccurence, poetsOfMaxOccurence);
		return maxOccurenceToPoetList;
	}
}
