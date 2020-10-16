package com.main;

import java.util.ArrayList;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class User {
	
	// variables
	private String firstName;
	private String lastName;
	private String UUID; // universal unique identifier
	private byte pinHash[]; // pin used for user to log in (store the hash not the actual value)
	private ArrayList<Account> accounts; // list of accounts for this user
	
	
	// constructor - create a new user
	public User(String firstName, String lastName, String pin, Bank theBank) {
		
		// set the user's name
		this.firstName = firstName;
		this.lastName = lastName;
		
		// store the pins MD5 hash, rather than the actual value, for security purposes
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			this.pinHash = md.digest(pin.getBytes()); // getting the bytes from the pin, digesting them through md and storing them in pinHash
		} catch (NoSuchAlgorithmException e) {
			System.err.println("error, caught NoSuchAlgorithmException");
			e.printStackTrace();
			System.exit(1);
		}

		// get a new unique universal id for the user
		this.UUID = theBank.getNewUserUUID();
		
		// create empty list of accounts
		this.accounts = new ArrayList<Account>();
		
		// print out log message
		System.out.printf("New user %s, %s with ID %s created.\n", lastName, firstName, this.UUID);
		
	}

	// allows outside class to add an account to the private list in this user class
	public void addAccount(Account anAcct) {
		this.accounts.add(anAcct);
	}

	// gets the universal unique id
	public String getUUID() {
		return UUID;
	}

	// check whether a given pin matches the true User pin
	public boolean validatePin(String aPin) {

		
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			return MessageDigest.isEqual(md.digest(aPin.getBytes()), this.pinHash);
		} catch (NoSuchAlgorithmException e) {
			System.err.println("error, caught NoSuchAlgorithmException");
			e.printStackTrace();
			System.exit(1);
		}
		return false;
		
	}
	
	// returns the first name of the user

	public String getFirstName() {
		return this.firstName;
	}

	// prints summaries for the accounts of this user
	public void printAccountsSummary() {
		
		System.out.printf("\n\n%s's accounts summary\n", this.firstName);
		for(int i = 0; i < this.accounts.size(); i++) {
			System.out.printf("%d) %s\n", i+1, this.accounts.get(i).getSummaryLine());
		}
		System.out.println();
		
	}

	// gets the number of accounts of the user
	public int numAccounts() {
		return this.accounts.size();
	}

	// print transaction history for a particular account
	public void printAcctTransactionHistory(int acctIdx) {
		this.accounts.get(acctIdx).printTransHistory();
	}

	// get the balance of a particular account
	public double getAcctBalance(int acctIdx) {
		return this.accounts.get(acctIdx).getBalance();
	}

	// get the UUID of a particular account 
	public String getAccountUUID(int acctIdx) {
		return this.accounts.get(acctIdx).getUUID();
	}

	// add a transaction to a particular account
	public void addAccountTransaction(int acctIdx, double amount, String memo) {
		this.accounts.get(acctIdx).addTransaction(amount, memo);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
