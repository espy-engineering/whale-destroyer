package com.Espy;

import java.util.ArrayList;
import java.util.List;

public class WordGuess
{
    private final ArrayList<Letter> letters;
    private final String eliminatedLetters;
    public String mandatoryLetters;
    public WordGuess(String guess)
    {
        var letterPairs = SplitToTwoChar(guess);
        letters = new ArrayList<>();
        for (String letterPair : letterPairs)
        {
            letters.add(new Letter(letterPair));
        }
        eliminatedLetters = GetEliminatedLetters();
        mandatoryLetters = GetMandatoryLetters();
    }

    public void UpdateLocations(List<String> locations)
    {
        for (int index = 0; index < locations.size(); index++)
        {
            var updatedString = RemoveEliminatedLettersFromString(locations.get(index));
            locations.set(index, updatedString);
            var currentLetter = letters.get(index);
            if (currentLetter.state == LetterInput.InWordWrongLocation)
            {
                updatedString = RemoveSingleLetterFromString(locations.get(index), currentLetter.letter);
                locations.set(index, updatedString);
            }
            if (currentLetter.state == LetterInput.InWordRightLocation)
            {
                locations.set(index, Character.toString(currentLetter.letter));
            }
        }
    }

    private String GetEliminatedLetters()
    {
        var returnString = new StringBuilder();
        for (Letter letter : letters)
        {
            if (letter.state == LetterInput.NotInWord)
            {
                returnString.append(letter.letter);
            }
        }
        return returnString.toString();
    }

    private String GetMandatoryLetters()
    {
        var returnString = new StringBuilder();
        for (Letter letter : letters)
        {
            if (letter.state != LetterInput.NotInWord)
            {
                returnString.append(letter.letter);
            }
        }
        return returnString.toString();
    }

    private String RemoveSingleLetterFromString(String validLetters, Character letter)
    {
        return validLetters.replace(Character.toString(letter), "");
    }

    private String RemoveEliminatedLettersFromString(String validLetters)
    {
        for(char eliminatedLetter : eliminatedLetters.toCharArray())
        {
            validLetters = RemoveSingleLetterFromString(validLetters, eliminatedLetter);
        }
        return validLetters;
    }

    private static String[] SplitToTwoChar(String text)
    {
        List<String> parts = new ArrayList<>();
        int length = text.length();
        for (int i = 0; i < length; i += 2) {
            parts.add(text.substring(i, Math.min(length, i + 2)));
        }
        return parts.toArray(new String[0]);
    }
}
