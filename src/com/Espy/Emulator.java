package com.Espy;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Emulator
{
    public static void main(String[] args) throws IOException, InterruptedException
    {
        Solver.Initialize();
        var solver = new Solver();
        var emulator = new Emulator(solver);
        var bestFirstGuess = emulator.DetermineNextBestGuess();
        Printer.PrintNextGuess(bestFirstGuess);
    }

    private final ConcurrentHashMap<String, Integer> dictionaryOfResults;
    private final List<String> wordsToTest;
    private final Solver previousSolver;
    private final List<String> remainingValidWords;

    public Emulator(Solver previousSolver)
    {
        this.previousSolver = previousSolver;
        remainingValidWords = DetermineMostLikelyWords(previousSolver.words);
        wordsToTest = DetermineWordsToTest();
        dictionaryOfResults = GetInitialDictionaryOfResults();
    }

    private List<String> DetermineMostLikelyWords(List<String> words)
    {
        words = RemovePlurals(words);
        return words;
    }

    private List<String> RemovePlurals(List<String> words)
    {
        var listOfWordsEndingInS = new ArrayList<String>();
        for (var word : words)
        {
            if (!word.endsWith("s") || word.endsWith("ss"))
            {
                listOfWordsEndingInS.add(word);
            }
        }
        return listOfWordsEndingInS;
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

    private ConcurrentHashMap<String, Integer> GetInitialDictionaryOfResults()
    {
        var dictionaryOfResults = new ConcurrentHashMap<String, Integer>();
        for (String word : wordsToTest)
        {
            dictionaryOfResults.put(word, 0);
        }
        return dictionaryOfResults;
    }

    public String DetermineNextBestGuess() throws InterruptedException
    {
        System.out.print("Determining next best guess.");
        CollectHeuristicsOnRemainingValidWordsThreaded();
        System.out.println();
        var sortedResults = GetSortedListOfWordsFromDictionaryOfResults();
        Printer.PrintLeastPopularWordsInList(sortedResults);
        return sortedResults.get(sortedResults.size()-1);
    }

    private void CollectHeuristicsOnRemainingValidWordsThreaded() throws InterruptedException
    {
        var numberOfWorker = 10;
        var subsets = PartitionList(remainingValidWords, numberOfWorker);
        var workers = new ArrayList<Thread>();
        for (var sublist : subsets)
        {
            var guessThread = new GuessThread(previousSolver, wordsToTest, sublist, dictionaryOfResults);
            var thread = new Thread(guessThread);
            workers.add(thread);
        }
        for (var worker : workers)
        {
            worker.start();
        }

        for (var worker : workers)
        {
            worker.join();
        }
    }

    private static List<List<String>> PartitionList(List<String> items, int count)
    {
        var listOfLists = new ArrayList<List<String>>();
        var divisions = (double)items.size()/(double)count;
        var partitionSize = (int)Math.ceil(divisions);
        for (var index = 0; index < items.size(); index=index+partitionSize)
        {
            var endIndex = Math.min((index+partitionSize), items.size());
            var partitionedList = items.subList(index, endIndex);
            listOfLists.add(partitionedList);
        }
        return listOfLists;
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
