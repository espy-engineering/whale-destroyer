package com.Espy;

public class Letter
{
    public char letter;
    public LetterInput state;
    public Letter(String letterString)
    {
        letter = letterString.charAt(0);
        var stateChar = letterString.charAt(1);
        var stateInt = Character.getNumericValue(stateChar);
        state = LetterInput.fromInteger(stateInt);
    }
}
