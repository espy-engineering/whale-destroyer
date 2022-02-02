package com.Espy;

import java.io.IOException;
import java.util.*;

public class Emulator
{
    public static void main(String[] args) throws IOException
    {
        Solver.Initialize();
        var solver = new Solver();
        var emulator = new Emulator(solver);
        var bestFirstGuess = emulator.DetermineNextBestGuess();
        Printer.PrintNextGuess(bestFirstGuess);
    }

    private final Hashtable<String, Integer> dictionaryOfResults;
    private final List<String> wordsToTest;
    private final Solver previousSolver;
    private final List<String> remainingValidWords;

    public Emulator(Solver previousSolver)
    {
        this.previousSolver = previousSolver;
        remainingValidWords = previousSolver.words;
        wordsToTest = DetermineWordsToTest();
        dictionaryOfResults = GetInitialDictionaryOfResults();
    }

    private List<String> DetermineWordsToTest()
    {
        var wordsToTest = previousSolver.words;
        if (previousSolver.words.size() > 2 )
        {
            wordsToTest = Solver.staticListWords;
        }
        return wordsToTest;
    }

    private Hashtable<String, Integer> GetInitialDictionaryOfResults()
    {
        var dictionaryOfResults = new Hashtable<String, Integer>();
        for (String word : wordsToTest)
        {
            dictionaryOfResults.put(word, 0);
        }
        return dictionaryOfResults;
    }

    public String DetermineNextBestGuess()
    {
        System.out.print("Determining next best guess.");
        CollectHeuristicsOnRemainingValidWords();
        System.out.println();
        var sortedResults = GetSortedListOfWordsFromDictionaryOfResults();
        Printer.PrintLeastPopularWordsInList(sortedResults);
        return sortedResults.get(sortedResults.size()-1);
    }

    private void CollectHeuristicsOnRemainingValidWords()
    {
        var progressBarCounter = 0;
        for (String wordToGuess : remainingValidWords)
        {
            var guessConverter = new GuessConverter(wordToGuess);
            CollectDataOnGuesses(guessConverter);
            progressBarCounter = UpdateCounter(progressBarCounter);
        }
    }

    private int UpdateCounter(int counter)
    {
        if (counter % 10 == 0)
        {
            System.out.print(".");
        }
        return ++counter;
    }

    private void CollectDataOnGuesses(GuessConverter guessConverter)
    {
        for (String guess : wordsToTest)
        {
            var solver = new Solver(previousSolver.words, previousSolver.mandatoryLetters, previousSolver.validLetters);
            var formattedGuess = guessConverter.ConvertGuessToFormattedGuess(guess);
            solver.TakeAGuess(formattedGuess);
            var newTotal = dictionaryOfResults.get(guess) + solver.words.size();
            dictionaryOfResults.put(guess, newTotal);
        }
    }

    private List<String> GetSortedListOfWordsFromDictionaryOfResults()
    {
        ArrayList<Map.Entry<String, Integer>> sortedKeyMap = new ArrayList<>(dictionaryOfResults.entrySet());
        var comparator = Statistics.CreateHashMapComparator();
        sortedKeyMap.sort(comparator);
        return GetSortedListOfWordsFromSortedKeyMap(sortedKeyMap);
    }

    private static List<String> GetSortedListOfWordsFromSortedKeyMap(ArrayList<Map.Entry<String, Integer>> sortedListOfKeyValuePairs)
    {
        var sortedListOfStrings = new ArrayList<String>();
        for (var result : sortedListOfKeyValuePairs)
        {
            sortedListOfStrings.add(result.toString());
        }
        return sortedListOfStrings;
    }
}
