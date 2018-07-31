package hoved;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Vector;

/**
 * Holds main and a vector to keep track of all persons/oppforinger
 * @author solex
 *
 */
public class App {

	static Vector<Person> persons = new Vector<Person>();		//Vector with all persons
	

	public static void main(String[] args) {	
		int input = 0;		//Holds the input from the user in the menu
	
		
		while(input != 6) {		
			
			showMenu();
			
			input = askForInt("Velg en av valgene(1-6)");
			
			switch(input) {
			case 1: 
				addPerson();
				break;	
			case 2: 
				showPersons();
				break;
			case 3:
				deletePerson();
				break;
			case 4:
				writeToFile();
				break;
			case 5: 
				readFromFile();
				break;
			case 6:
				break;	//does nothing. Program goes out of switch, then while-loop and then end of main and program ends
			default: 
				System.out.println("Ikke gyldig input. Se meny");
				break;
			}
		}
	}
	
	
	/**
	 * Asks user for filename(.csv) and reads from that file and puts the data in 'person' objects and put them in the 'persons' vector
	 */
	public static void readFromFile() {
		String filename;
		String temp = "";		//One line in the file
		String[] strArray; 		//Array of the values of one line
		int nr;
		BufferedReader br;
		
		
		filename = askForString("Skriv fullt navn paa fil som skal leses");
		
		try {						//Wipes the vector clean, because there can be conflict with lopenummer if the user 
			persons.clear();		//  has put in some 'persons' and then reads from file, in the same runtime of the program
			
			//reads from file until EOF
			br = new BufferedReader(new FileReader(filename));
			temp = br.readLine();
			while(temp != null) {
				strArray = temp.split(",");				//Array with all data for one person
				nr = Integer.parseInt(strArray[0]);		//Fetches lopenummer
				
				persons.add(new Person(nr, strArray[1], strArray[2]));		//Makes 'person' object and puts it in the vector
				
				temp = br.readLine();
			}
			
			//Lopenummer is not updated after import, so sets it to the last 'person' in the vector, 
			//  because that one will allways have the highest
			Person.setNumberOfPersons(persons.lastElement().getLopenummer());
		
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
	}
	
	
	/**
	 * Asks for filename and saves all objects of 'person' from the vector 'persons'
	 * to a .csv file named by the user
	 */
	public static void writeToFile() {
		PrintWriter writer = null;
		String filename = null;
		String temp = null;
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
	
		filename = askForString("Skriv inn navn paa filen(utelat filending): ");
		
		filename += ".csv";			//Makes file on format: comma-separated values
		try {
			writer = new PrintWriter(filename, "UTF-8");	//makes file or overwrites if it already exists
			
			//Iterates through all persons and write one person at the time to file
			for(int i = 0; i < persons.size(); i++) {
				Person p = persons.get(i);
				temp = p.getLopenummer() + "," + p.getName() + "," + p.getEmail(); 	//File has same format as listed out to user
				
				writer.println(temp);		//Writes a line to file
			}
			writer.close();	
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} finally {
			writer.close();		//Makes sure to close file if any exceptions are raised
		}
	}
	
	
	/**
	 * Shows all persons and ask user which one to delete and deletes it
	 */
	public static void deletePerson() {
		int nrToRemove = 0;	
		boolean validLopenummer = false;
		
		showPersons();
		
		//loops until user gives lopenummer that a person has
		while(!validLopenummer) {
			
			nrToRemove = askForInt("Hvilken person vil du fjerne(Nr): ");
		
			//Checks if any persons have this lopenummer. If so, it is removed
			for(int i = 0; i < persons.size(); i++) {
				Person p = persons.get(i);
				if(p.getLopenummer() == nrToRemove) {	
					persons.removeElementAt(i);
					validLopenummer = true;
				}
			}
			
		}
	}
	
	
	/**
	 * Creates an object of peron and adds it to the vector 'persons'
	 */
	public static void addPerson() {
		Person person = new Person();
		persons.add(person);
	}
	
	
	/**
	 * Shows all person with Name, Lopenummer and emails
	 */
	public static void showPersons() {
		System.out.println("Nr\tNavn\tEpostadresse");
		
		for(int i = 0; i < persons.size(); i++) {
			Person p = persons.get(i);
			System.out.println(p.getLopenummer() + "\t" + p.getName() + "\t\t" + p.getEmail());
		}
	}
	
	
	/**
	 * Shows all the options the user has
	 */
	public static void showMenu() {
		System.out.println("\n1 - Lag ny oppforing");
		System.out.println("2 - List opp alle oppforinger");
		System.out.println("3 - Slett en oppforing");
		System.out.println("4 - Lagre til disk");
		System.out.println("5 - Les fra disk");
		System.out.println("6 - Avslutt\n");
	}
	
	
	/**
	 * Gets an integer typed in by user
	 * @param txt String to show in console. For example tell user what range the nr can be in
	 * @return Integer
	 */
	public static int askForInt(String txt) {
		int input = 0;										//BufferedReader & InputStreamReader used to be able to get user-input while running program in Eclipse IDE
															//https://stackoverflow.com/questions/4644415/java-how-to-get-input-from-system-console/4645193#4645193
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try {
			System.out.println(txt);			
			input = Integer.parseInt(br.readLine());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return input;
	}
	
	
	/**
	 * Gets a string entered by user
	 * @param txt String to show user in console with help to what to write
	 * @return String 
	 */
	public static String askForString(String txt) {
		String input = null;
		
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
