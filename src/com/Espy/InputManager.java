package com.Espy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class InputManager
{
    public static String GetUserInput(String prompt) throws IOException
    {
        System.out.println(prompt);
        var input = System.in;
        var streamReader = new InputStreamReader(input);
        var bufferedReader = new BufferedReader(streamReader);
        return bufferedReader.readLine();
    }
}
