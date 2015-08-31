package controllers;



import java.io.*;


/**
 * @author Sougata
 * Aug 25, 2015 
 */
// run this way

// javac JavaRunPython.java

// java -classpath . JavaRunPython



public class JavaRunPython {

	public static void run(String[] callAndArgs) {
		try {

			Process p = Runtime.getRuntime().exec(callAndArgs);
		}

		catch (IOException e) {

			System.out.println("exception occured");

			e.printStackTrace();

			System.exit(-1);

		}

	}

}