package GUI;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Login {
	
	public boolean validateUser(String ID, String password) {
		try {
			File file = new File("Data/Login.txt");
			Scanner scanner = new Scanner(file);
			while(scanner.hasNext()) {
				String sID = scanner.next();
				String sPassword = scanner.next();
				if (ID.equals(sID) && password.equals(sPassword)) {
					return true;
				}
			}
			scanner.close();
		} catch (FileNotFoundException e) {
		}		
		return false;
	}
	
}
