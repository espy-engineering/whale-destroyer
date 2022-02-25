package com.Espy;

import java.io.IOException;
import java.util.*;

public class Statistics
{
    public static final String alphabet = "abcdefghijklmnopqrstuvwxyz";
    public static void main(String[] args) throws IOException
    {
        var words = WordGetter.GetWordsFromFile(Solver.listOfAllPossibleWordsFileName);
        var listOfWordsEndingInS = new ArrayList<String>();
        for (var word : words)
        {
            if (word.endsWith("s") && !word.endsWith("ss"))
            {
                listOfWordsEndingInS.add(word);
            }
        }
        Printer.PrintAllWords(listOfWordsEndingInS);
    }
    public static void PrintWordStatistics(List<String> words)
    {
        Printer.PrintTopMostPopularWords(words);
        Printer.PrintLeastPopularWordsInList(words);
        var characterFrequency = GetCharacterFrequency(words);
        var topTenCharacters = GetTopTenCharacters(characterFrequency);
        Printer.PrintTopTenLetters(characterFrequency, topTenCharacters);
    }

    private static Hashtable<Character, Integer> GetCharacterFrequency(List<String> words)
    {
        var returnTable = new Hashtable<Character, Integer>();
        for(char character : alphabet.toCharArray())
        {
            var count = GetFrequencyOfCharacterInWordList(character, words);
            returnTable.put(character, count);
        }
        return returnTable;
    }

    private static int GetFrequencyOfCharacterInWordList(char character, List<String> words)
    {
        var count = 0;
        for(String word : words)
        {
            if(word.contains(String.valueOf(character)))
            {
                count++;
            }
        }
        return count;
    }

    private static String GetTopTenCharacters(Hashtable<Character, Integer> characterDictionary)
    {
        ArrayList<Map.Entry<Character, Integer>> sortedCharacterList = new ArrayList<>(characterDictionary.entrySet());
        var comparable = CreateHashMapComparator();
        sortedCharacterList.sort(comparable);
        return GetSortedListOfCharactersFromSortedKeyMap(sortedCharacterList);
    }

    private static String GetSortedListOfCharactersFromSortedKeyMap(ArrayList<Map.Entry<Character, Integer>> sortedCharacterList)
    {
        StringBuilder returnString = new StringBuilder();
        for (int index = 0; index < 10; index++)
        {
            returnString.append(sortedCharacterList.get(index).toString().charAt(0));
        }
        return returnString.toString();
    }

    public static Comparator<Map.Entry<?, Integer>> CreateHashMapComparator()
    {
        return (o1, o2) -> o2.getValue().compareTo(o1.getValue());
    }
}
