package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Config {

	private static final Properties properties = new Properties();

	static {
		initPropertiesFromFile();

	}

	private static void initPropertiesFromFile() {
		
		File file = new File("config.properties");


		System.out.println("User properties file path: {}"+ file.toPath());

		FileInputStream fileInput = null;
		try {
			fileInput = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		//load properties file
		try {
			properties.load(fileInput);
			fileInput.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getProperty(String s) {
		return properties.getProperty(s);
	}
}
