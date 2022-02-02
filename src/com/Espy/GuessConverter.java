package com.Espy;

public record GuessConverter(String solution)
{
    public String ConvertGuessToFormattedGuess(String guess)
    {
        var convertedString = new StringBuilder();
        for (int index = 0; index < guess.length(); index++) {
            var currentCharacter = guess.charAt(index);
            convertedString.append(currentCharacter);
            var characterSuffix = DetermineCharacterSuffix(solution.charAt(index), currentCharacter);
            convertedString.append(characterSuffix);
        }
        return convertedString.toString();
    }

    private String DetermineCharacterSuffix(char solutionCharacter, char guessCharacter)
    {
        if (solutionCharacter == guessCharacter)
        {
            return "2";
        }
        if (solution.contains(Character.toString(guessCharacter)))
        {
            return "1";
        }
        return "0";
    }
}
