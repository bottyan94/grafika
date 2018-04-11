package com.iit.uni.engine;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class Utils {

    public static String loadResource(String fileName) throws Exception {
        String result;
        try (InputStream in = Utils.class.getClass().getResourceAsStream(fileName);
                Scanner scanner = new Scanner(in, "UTF-8")) {
            result = scanner.useDelimiter("\\A").next();
        }
        return result;
    }

    public static String loadFile(String fileName) {

		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(fileName));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
			return null;
		}
		// allocate a string builder to add line per line
		StringBuilder strBuilder = new StringBuilder();

		try {
			String line = reader.readLine();
			// get text from file, line per line
			while (line != null) {
				strBuilder.append(line + "\n");
				line = reader.readLine();
			}
			// close resources
			reader.close();
			// stream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return strBuilder.toString();
	}
}