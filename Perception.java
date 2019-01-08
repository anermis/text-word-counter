package searching;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;
import java.lang.Character;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Perception {

	public static void main(String[] args) {

		String txtFile = "read the .txt file";
		String run = "read the command to run";
		String totalText = "the whole txt file in a String";
		String line = "one by one the lines of the text";
		boolean wcOrFind = true; // true if the user run wc otherwise run find

		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date date = new Date();

		if (args.length != 2) {
			System.out.println("Please provide vaild arguments");
			System.out.println("argument 1: path to text file");
			System.out.println("argument 2: command to apply <wc or find>");
			System.exit(0);
		} else {
			txtFile = args[0];
			File txt = new File(txtFile);
			if (!txt.isFile()) {
				System.out.println(txtFile + " is not a valid file path");
				System.exit(0);
			}
			run = args[1];
		}
		if (run.equals("wc")) {
			wcOrFind = true;
		} else if (run.equals("find")) {
			wcOrFind = false;
		} else {
			System.out.println(sdf.format(cal.getTime()) + ": File " + txtFile + " found!");
			System.out.println("Argument " + run + " is invalid. Only wc and find are supported");
			System.exit(0);
		}

		try {
			FileReader file = new FileReader(txtFile);
			BufferedReader observer = new BufferedReader(file);
			totalText = " ";
			line = observer.readLine();

			while (line != null) {
				totalText = totalText + line + " ";
				line = observer.readLine();
			}
			observer.close();
		} catch (FileNotFoundException fnfe) {// may not needed
			System.out.println(txtFile + " is not a valid file path");
			System.out.println();
			System.exit(0);
		} catch (IOException ioEx) {
			System.out.println("IO Exeption !");
			System.out.println();
			System.exit(0);
		}

		if (wcOrFind) { // What to run true -> wc false -> find
			System.out.println(sdf.format(cal.getTime()) + ": File " + txtFile + " found!");
			System.out.println(sdf.format(cal.getTime()) + ": Total word count started");
			System.out.print(sdf.format(cal.getTime()) + ": Word count finished. Counted ");
			System.out.print(wCounting(totalText) + " words");
			System.out.println();

			try {
				PrintWriter writer = new PrintWriter(new FileOutputStream(new File("wordcount.txt"), true));
				writer.println(
						dateFormat.format(date) + "    " + run + "        " + txtFile + "    " + wCounting(totalText));
				writer.close();
			} catch (FileNotFoundException fnfe) {
				System.out.println(txtFile + " is not a valid file path");
				System.out.println();
				System.exit(0);
			}
		} else {
			System.out.println(sdf.format(cal.getTime()) + ": File " + txtFile + " found!");
			System.out.print("Enter the word you wish to search in the file : ");

			Scanner sc = new Scanner(System.in);
			String wordToFind = sc.nextLine();
			sc.close();

			System.out.println(sdf.format(cal.getTime()) + ": Counting occurrences of word " + wordToFind);
			System.out.println(sdf.format(cal.getTime()) + ": Count of word " + wordToFind + " finished. "
					+ " occurrences found: " + find(totalText, wordToFind));

			try {
				PrintWriter writer = new PrintWriter(new FileOutputStream(new File("wordcount.txt"), true));
				writer.println(dateFormat.format(date) + "    " + run + "      " + txtFile + "    " + wordToFind + ": "
						+ find(totalText, wordToFind));
				writer.close();
			} catch (FileNotFoundException e) {

			}
		}
	}

	public static int wCounting(String inpout) {
		char[] testChar = inpout.toCharArray();
		int counter = 0;
		int endOfTxt = testChar.length;
		boolean possWord = false; // true when we count possWord aka possible word

		for (int i = 0; i < endOfTxt; i++) {

			if (Character.isLetter(testChar[i])) {
				possWord = true;
			} else if (!(Character.isLetter(testChar[i]) || testChar[i] == '\'') && (possWord)) {
				counter = counter + 1;
				possWord = false;
			}
		}
		return counter;
	}

	public static int find(String inpout, String find) {
		char[] testChar = inpout.toLowerCase().toCharArray();
		char[] wordToFind = find.toLowerCase().toCharArray();
		int counter = 0;

		for (int i = 0; i < testChar.length; i++) {
			if (testChar[i] == wordToFind[0]) {
				int j = 1;
				for (; j < wordToFind.length; j++) {
					if (testChar[i + j] != wordToFind[j]) {
						break;
					}
				}

				if (j == wordToFind.length)
					counter++;
			}
		}
		return counter;
	}
}