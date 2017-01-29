package runner.cl;

import java.util.ArrayList;
import java.util.Scanner;

import mafia.game.*;
import mafia.game.PlayerFactory;
import mafia.game.RoleFactory;
import mafia.values.RoleOption;

public class App {
	private static RoleFactory rf;
	private static PlayerFactory pf = new PlayerFactory();
	private static Game game;
	
	public static void main(String args[]) {
		// Initialize.
		Scanner reader = new Scanner(System.in);
		game = new Game(pf);
		
		String[] names = promptForNames(reader);
		ArrayList<Role> roles = promptForRoles(reader, names.length);
		
		game.setPlayerNames(names);
		game.setRoles(roles);
		game.assignRoles();
		
		reader.close();
	}
	
	private static String[] promptForNames(Scanner reader) {
		ArrayList<String> playerNames = new ArrayList<String>();
		String newName;
		
		// Prompt for player name.
		do {
			System.out.println("Enter a name (Leave blank to stop): ");
			newName = reader.nextLine();
			if (!newName.isEmpty()) {
				playerNames.add(newName);
			}
		} while (!newName.isEmpty());
		// Stop when no name given.
		
		
		
		// Convert to String[] and return.
		String[] playerNamesArray = new String[playerNames.size()];
		playerNamesArray = playerNames.toArray(playerNamesArray);
		return playerNamesArray;
	}
	
	private static ArrayList<Role> promptForRoles(Scanner reader, int numPlayers) {
		// Initialize.
		int choice;
		ArrayList<Role> roles = new ArrayList<Role>();
		
		// Prompt for a role, once for each player.
		for (int i = 0; i < numPlayers; i++) {
			System.out.println("Choose a Role to add.");
			dispalyRoleList();
			System.out.println("Enter a Number from 1 to " + (RoleOption.values().length));
			choice = reader.nextInt() - 1; // Adjust to 0 index.
			if (choice >= 0) {
				roles.add(RoleFactory.createRole(RoleOption.values()[choice]));
			} else {
				i--;
				System.out.println("Invalid input, " + choice + ".  Try again ...");
			}
		}
		return roles;
	}
	
	private static void dispalyRoleList() {
		int itemNo = 1;
		for (RoleOption opt : RoleOption.values()) {
			System.out.println(itemNo + ". " + opt.name());
			itemNo++;
		}
	}
	
//	private static RoleOption getRoleEnum(String stringRole) {
//		RoleOption foundEnum = RoleOption.INVALID;
//		for (RoleOption opt : RoleOption.values()) {
//			if (opt.name().equals(stringRole)) {
//				foundEnum = opt;
//			}
//		}
//		return foundEnum;
//	}
}