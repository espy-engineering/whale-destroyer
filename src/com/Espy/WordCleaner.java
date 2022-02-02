package com.Espy;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class WordCleaner
{
    public static List<String> CleanWordList(List<String> staticListWords)
    {
        staticListWords = RemoveWordsWithStupidCharacters(staticListWords);
        System.out.printf("Total number of words that only use characters A-Z: %s%n", staticListWords.size());
        staticListWords = GetFiveLetterWords(staticListWords);
        System.out.printf("Total number of 5 letter words: %s%n", staticListWords.size());
        staticListWords = ToLowerCase(staticListWords);
        staticListWords = RemoveDuplicates(staticListWords);
        System.out.printf("Total number words without case sensitive duplicates: %s%n", staticListWords.size());
        return staticListWords;
    }

    private static List<String> RemoveWordsWithStupidCharacters(List<String> words)
    {
        var updatedList = new ArrayList<String>();
        for(String word : words)
        {
            if (word.matches(".*[a-zA-Z].*"))
            {
                updatedList.add(word);
            }
        }
        return updatedList;
    }

    private static List<String> RemoveDuplicates(List<String> words)
    {
        var listOfWords = new ArrayList<String>();
        for(String word : words)
        {
            if(!listOfWords.contains(word))
            {
                listOfWords.add(word);
            }
        }
        return listOfWords;
    }

    private static List<String> ToLowerCase(List<String> words)
    {
        var listOfWords = new ArrayList<String>();
        for (String word : words)
        {
            listOfWords.add(word.toLowerCase(Locale.ROOT));
        }
        return listOfWords;
    }

    private static List<String> GetFiveLetterWords(List<String> words)
    {
        var listOfFiveLetterWords = new ArrayList<String>();
        for(String word : words)
        {
            if (word.length() == 5)
            {
                listOfFiveLetterWords.add(word);
            }
        }
        return listOfFiveLetterWords;
    }
}
