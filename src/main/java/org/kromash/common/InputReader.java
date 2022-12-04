package org.kromash.common;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class InputReader {
    String session;

    public InputReader() {
        Properties appProperties = new Properties();
        ClassLoader loader = Thread.currentThread().getContextClassLoader();

        try(InputStream resourceStream = loader.getResourceAsStream("config.properties")) {
            appProperties.load(resourceStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        session = appProperties.getProperty("SESSION");
    }

    public List<String> readInputLines(int day) {
        String urlString = String.format("https://adventofcode.com/2022/day/%d/input", day);
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Cookie", String.format("session=%s", session));
            conn.setInstanceFollowRedirects(true);


            int status = conn.getResponseCode();

            Reader streamReader = null;

            if (status > 299) {
                streamReader = new InputStreamReader(conn.getErrorStream());
            } else {
                streamReader = new InputStreamReader(conn.getInputStream());
            }

            BufferedReader in = new BufferedReader(streamReader);
            String inputLine;
            List<String> content = new ArrayList<>();
            while ((inputLine = in.readLine()) != null) {
                content.add(inputLine);
            }
            in.close();

            return content;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
