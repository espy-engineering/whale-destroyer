package com.Espy;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class WordGetter
{
    public static List<String> GetWordsFromWebsite(String urlString) throws IOException
    {
        var url = new URL(urlString);
        var connection = CreateConnection(url);
        var responseStream = connection.getInputStream();
        return GetContentFromInputStream(responseStream);
    }

    public static List<String> GetWordsFromFile(String filePath) throws IOException
    {
        var file = new File(filePath);
        var fileInputStream = new FileInputStream(file);
        return GetContentFromInputStream(fileInputStream);
    }

    private static HttpURLConnection CreateConnection(URL url) throws IOException
    {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(5000);
        return connection;
    }

    private static List<String> GetContentFromInputStream(InputStream responseStream) throws IOException
    {
        var streamReader = new InputStreamReader(responseStream);
        var bufferedReader = new BufferedReader(streamReader);
        return GetLinesFromBufferedReader(bufferedReader);
    }

    private static List<String> GetLinesFromBufferedReader(BufferedReader bufferedReader) throws IOException
    {
        String inputLine;
        var content = new ArrayList<String>();
        while ((inputLine = bufferedReader.readLine()) != null)
        {
            content.add(inputLine);
        }
        bufferedReader.close();
        return content;
    }
}
