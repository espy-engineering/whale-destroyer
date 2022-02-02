package com.Espy;

import java.io.IOException;

public class Simulator
{
    public static void main(String[] args) throws IOException
    {
        var wordToGuess = InputManager.GetUserInput("Enter word to guess");
        var guessConverter = new GuessConverter(wordToGuess);
        Solver.Initialize();
        var solver = new Solver();
        while (solver.words.size() > 2)
        {
            var guess = InputManager.GetUserInput("Enter guess");
            var formattedGuess = guessConverter.ConvertGuessToFormattedGuess(guess);
            solver.TakeAGuess(formattedGuess);
            Printer.PrintAllWords(solver.words);
        }
    }
}
