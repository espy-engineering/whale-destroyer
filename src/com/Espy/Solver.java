package com.Espy;

import java.io.IOException;
import java.util.*;

public class Solver
{
    public static List<String> staticListWords = new ArrayList<>();
    // private static final String addressForWords = "https://gist.githubusercontent.com/h3xx/1976236/raw/bbabb412261386673eff521dddbe1dc815373b1d/wiki-100k.txt";
    private static final String listOfAllPossibleWordsFileName = "ListOfAllPossibleWords.txt";
    public List<String> validLetters = new ArrayList<>();
    public List<String> words;
    public String mandatoryLetters = "";
    public static void main(String[] args) throws IOException
    {
        Initialize();
        var solver = new Solver();
        Statistics.PrintWordStatistics(solver.words);
        while (solver.words.size()>1)
        {
            var guess = InputManager.GetUserInput("Enter next formatted guess:");
            solver.TakeAGuess(guess);
            Printer.PrintTopMostPopularWords(solver.words);
            var emulator = new Emulator(solver);
            var nextBestGuess = emulator.DetermineNextBestGuess();
            Printer.PrintNextGuess(nextBestGuess);
        }
    }

    private List<String> RemoveWordsByMandatoryLetters(List<String> words)
    {
        var listOfWords = new ArrayList<String>();
        for(String word : words)
        {
            var containsAllMandatoryLetters = true;
            for(char letter : mandatoryLetters.toCharArray())
            {
                if (!word.contains(Character.toString(letter)))
                {
                    containsAllMandatoryLetters = false;
                    break;
                }
            }
            if (containsAllMandatoryLetters)
            {
                listOfWords.add(word);
            }
        }
        return listOfWords;
    }

    private List<String> RemoveWordsByInvalidLocation(List<String> words)
    {
        var listOfWords = new ArrayList<String>();
        for(String word : words)
        {
            var isValidWord = true;
            for(int letterLocation = 0; letterLocation < word.length() ; letterLocation++)
            {
                if (!validLetters.get(letterLocation).contains(Character.toString(word.charAt(letterLocation)))) {
                    isValidWord = false;
                    break;
                }
            }
            if(isValidWord)
            {
                listOfWords.add(word);
            }
        }
        return listOfWords;
    }

    public void TakeAGuess(String guess)
    {
        var firstGuess = new WordGuess(guess);
        firstGuess.UpdateLocations(validLetters);
        mandatoryLetters = mandatoryLetters + firstGuess.mandatoryLetters;
        words = RemoveWordsByMandatoryLetters(words);
        words = RemoveWordsByInvalidLocation(words);
    }

    public static void Initialize() throws IOException
    {
        staticListWords = WordGetter.GetWordsFromFile(listOfAllPossibleWordsFileName);
        // staticListWords = WordGetter.GetWordsFromWebsite(addressForWords);
        System.out.printf("Total number of words: %s%n", staticListWords.size());
        // staticListWords = WordCleaner.CleanWordList(staticListWords);
    }

    public void InitializeValidLetters()
    {
        validLetters.clear();
        for (int index = 0; index < 5; index++)
        {
            validLetters.add(Statistics.alphabet);
        }
    }

    public Solver()
    {
        words = new ArrayList<>(staticListWords);
        InitializeValidLetters();
    }

    public Solver(List<String> words, String mandatoryLetters, List<String> validLetters)
    {
        this.words = new ArrayList<>(words);
        this.mandatoryLetters = mandatoryLetters;
        this.validLetters = new ArrayList<>(validLetters);
    }
}