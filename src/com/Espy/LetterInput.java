package com.Espy;

public enum LetterInput
{
    NotInWord,
    InWordWrongLocation,
    InWordRightLocation;

    public static LetterInput fromInteger(int x)
    {
        return switch (x) {
            case 0 -> NotInWord;
            case 1 -> InWordWrongLocation;
            case 2 -> InWordRightLocation;
            default -> null;
        };
    }
}
