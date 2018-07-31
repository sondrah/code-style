package hoved;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Pattern;


public class Person {
	private static int numberOfPersons = 0;		//Class variable used to assign lopenummer
	private int lopenummer;
	private String name;
	private String email;
	
	/**
	 * Constructs a 'Person' object with input from user
	 */
	public Person() {
		boolean isValidEmail = false;

		lopenummer = ++numberOfPersons;				//Assigns lopenummer and increments class variable 
		
		name = askForString("Skriv inn navn: ");	//Sets name
		
		//loops until user puts in a valid email
		while(!isValidEmail) {		
			String temp = null;
			temp = askForString("Skriv inn epost: ");	
																		//checks with regex if text is an email. returns true/false
			isValidEmail = Pattern.matches("\\b[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}\\b", temp);	
			
			if(isValidEmail) {
				email = temp;
			}
			else {
				System.out.println("Ikke godkjent epost. Sjekk at du skrev riktig. Prov igjen");
			}
		}
		
	}
	
	/**
	 * Makes an object of Person with these predefined params
	 * @param lopenummer Integer
	 * @param name String
	 * @param email String
	 */
	public Person(int lopenummer, String name, String email) {
		this.lopenummer = lopenummer;
		this.name = name;
		this.email = email;
	}
	
	
	public int getLopenummer() {
		return lopenummer;
	}
	public String getName() {
		return name;
	}
	public String getEmail() {
		return email;
	}
	
	/**
	 * Sets the class variable 'numberOfPersons'
	 * @param nr Integer
	 */
	public static void setNumberOfPersons(int nr) {
		numberOfPersons = nr;
	}
	
	/**
	 * Gets a string entered by user
	 * @param txt String to show user in console with help to what to write
	 * @return String 
	 */
	public static String askForString(String txt) {
		String input = null;
															//BufferedReader & InputStreamReader used to be able to get user-input while running program in Eclipse IDE
															//https://stackoverflow.com/questions/4644415/java-how-to-get-input-from-system-console/4645193#4645193
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try {
			System.out.println(txt);			
			input = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return input;
	}
}
