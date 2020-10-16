package com.main;

import java.util.ArrayList;
import java.util.Random;

public class Bank {

	// variables
	private String name;
	private ArrayList<User> users; // list of users in the bank
	private ArrayList<Account> accounts; // list of accounts in the bank

	
	// constructor - create a new Bank object with empty lists of users and accounts
	public Bank(String name) {
		this.name = name;
		this.users = new ArrayList<User>();
		this.accounts = new ArrayList<Account>();
	}

	// generate a new universally unique id for the user
	public String getNewUserUUID() {

		// initialise variables
		String uuid;
		Random rng = new Random();
		int length = 6;
		boolean nonUnique;

		// continue looping until we get a unique ID
		do {

			// generate number
			uuid = "";
			for (int i = 0; i < length; i++) {
				uuid += ((Integer) rng.nextInt(10)).toString(); // adding a digit between 0-9 in string form
			}

			// check to make sure it's unique
			nonUnique = false;
			for (User u : this.users) {
				if (uuid.compareTo(u.getUUID()) == 0) { // 0 means they are equal
					nonUnique = true;
					break;
				}
			}
		} while (nonUnique);

		return uuid;
	}

	// generate a new universally unique id for an account
	public String getNewAccountUUID() {

		// initialise variables
		String uuid;
		Random rng = new Random();
		int length = 10;
		boolean nonUnique;

		// continue looping until we get a unique ID
		do {

			// generate number
			uuid = "";
			for (int i = 0; i < length; i++) {
				uuid += ((Integer) rng.nextInt(10)).toString(); // adding a digit between 0-9 in string form
			}

			// check to make sure it's unique
			nonUnique = false;
			for (Account a : this.accounts) {
				if (uuid.compareTo(a.getUUID()) == 0) { // 0 means they are equal
					nonUnique = true;
					break;
				}
			}
		} while (nonUnique);

		return uuid;
	}
	
	
	// add an account to the bank
	public void addAccount(Account anAcct) {
		this.accounts.add(anAcct);
	}

	
	// create a new user for the bank
	public User addUser(String firstName, String lastName, String pin) {
		
		// create a new user object
		User newUser = new User(firstName, lastName, pin, this);
		this.users.add(newUser); // adds user to list
		
		// create a savings account for the user and add to User and Bank account lists
		Account newAccount = new Account("Savings", newUser, this);
		newUser.addAccount(newAccount);
		this.addAccount(newAccount);
		
		return newUser;
	}
	
	// get the User object associated with a particular userID and pin, if they are valid
	// return the User object if login is successful, otherwise return null
	public User userLogin(String userID, String pin) {
		
		// search through the list of users
		for (User u : this.users) {
			
			// check if user ID is correct
			if(u.getUUID().compareTo(userID) == 0 && u.validatePin(pin)) {
				return u;
			}
		}
		// if we havent found the user or have an incorrect pin
		return null;
	}
	
	// gets the name of the bank

	public String getName() {
		return this.name;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
