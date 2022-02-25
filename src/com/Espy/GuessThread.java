package com.Espy;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class GuessThread implements Runnable
{
    public static void main(String[] args)
    {
        var runTime = Runtime.getRuntime();
        var numberOfProcesses = runTime.availableProcessors();
        System.out.println("The number of available processes is");
        System.out.println(numberOfProcesses);
    }

    private final List<String> guessWordsToCollectDataOn;
    private final Solver previousSolver;
    private final List<String> possibleSolutionWordsBeingTested;
    private final ConcurrentHashMap<String, Integer> dictionaryOfResults;
    private static final AtomicInteger progressBarCounter = new AtomicInteger();

    public GuessThread(
            Solver previousSolver,
            List<String> guessWordsToCollectDataOn,
            List<String> possibleSolutionWordsBeingTested,
            ConcurrentHashMap<String, Integer> dictionaryOfResults)
    {
        this.previousSolver = previousSolver;
        this.guessWordsToCollectDataOn = guessWordsToCollectDataOn;
        this.possibleSolutionWordsBeingTested = possibleSolutionWordsBeingTested;
        this.dictionaryOfResults = dictionaryOfResults;
    }

    @Override
    public void run()
    {
        for (String wordToGuess : possibleSolutionWordsBeingTested)
        {
            var guessConverter = new GuessConverter(wordToGuess);
            CollectDataOnGuesses(guessConverter);
            UpdateCounter();
        }
    }

    private void CollectDataOnGuesses(GuessConverter guessConverter)
    {
        for (String guess : guessWordsToCollectDataOn)
        {
            var solver = new Solver(previousSolver.words, previousSolver.mandatoryLetters, previousSolver.validLetters);
            var formattedGuess = guessConverter.ConvertGuessToFormattedGuess(guess);
            solver.TakeAGuess(formattedGuess);
            var newTotal = dictionaryOfResults.get(guess) + solver.words.size();
            dictionaryOfResults.put(guess, newTotal);
        }
    }

    public static void UpdateCounter()
    {
        if (progressBarCounter.get() % 10 == 0)
        {
            System.out.print(".");
        }
        progressBarCounter.incrementAndGet();
    }
}
