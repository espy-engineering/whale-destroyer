package com.Espy;

import java.util.Hashtable;
import java.util.List;

public class Printer
{
    public static void PrintTopMostPopularWords(List<String> words)
    {
        System.out.printf("Most popular remaining words out of %s:%n", words.size());
        int limit = Math.min(words.size(), 10);
        for(int i=0; i<limit; i++)
        {
            System.out.println(words.get(i));
        }
    }

    public static void PrintAllWords(List<String> words)
    {
        System.out.println("List of remaining words:");
        for (String word : words) {
            System.out.println(word);
        }
    }

    public static void PrintTopTenLetters(Hashtable<Character, Integer> characterFrequency, String topTenCharacters)
    {
        System.out.println("Most frequently appearing letters:");
        for (char character : topTenCharacters.toCharArray())
        {
            System.out.print("Letter ");
            System.out.print(character);
            System.out.print(" appears ");
            System.out.print(characterFrequency.get(character));
            System.out.print(" times");
            System.out.println();
        }
    }

    public static void PrintLeastPopularWordsInList(List<String> words)
    {
        System.out.printf("Least popular remaining words out %s:%n", words.size());
        int limit = Math.min(words.size(), 10);
        for(int i=1; i<limit; i++)
        {
            System.out.println(words.get(words.size()-i));
        }
    }

    public static void PrintNextGuess(String guess)
    {
        System.out.println("The next best guess would be:");
        System.out.println(guess);
    }
}
