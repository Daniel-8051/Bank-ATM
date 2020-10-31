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

	/**
	 * Constructor - create a new user
	 * 
	 * @param firstName of the user
	 * @param lastName  of the user
	 * @param pin       code of the user
	 * @param theBank   the users bank
	 */
	public User(String firstName, String lastName, String pin, Bank theBank) {

		// set the user's name
		this.firstName = firstName;
		this.lastName = lastName;

		// store the pins MD5 hash, rather than the actual value, for security purposes
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			this.pinHash = md.digest(pin.getBytes()); // getting the bytes from the pin, digesting them through md and
														// storing them in pinHash
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

	/**
	 * Allows outside class to add an account to the private list in this user class
	 * 
	 * @param anAcct the account to be added
	 */
	public void addAccount(Account anAcct) {
		this.accounts.add(anAcct);
	}

	/**
	 * Gets the universal unique id for the user
	 * 
	 * @return the UUID for the user
	 */
	public String getUUID() {
		return UUID;
	}

	/**
	 * Check whether a given pin matches the true User pin
	 * 
	 * @param aPin - the pin to be checked
	 * @return true if the pin matches the users pin
	 */
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

	/**
	 * Returns the first name of the user
	 * 
	 * @return
	 */
	public String getFirstName() {
		return this.firstName;
	}

	/**
	 * Prints summary for the accounts of this user
	 */
	public void printAccountsSummary() {

		System.out.printf("\n\n%s's accounts summary\n", this.firstName);
		// loop through the users account and prints the summary line
		for (int i = 0; i < this.accounts.size(); i++) {
			System.out.printf("%d) %s\n", i + 1, this.accounts.get(i).getSummaryLine());
		}
		System.out.println();

	}

	/**
	 * Gets the number of accounts of the user
	 * 
	 * @return the number of accounts
	 */
	public int numAccounts() {
		return this.accounts.size();
	}

	/**
	 * Print transaction history for a particular account
	 * 
	 * @param acctIdx the index of the account in the accounts list
	 */
	public void printAcctTransactionHistory(int acctIdx) {
		this.accounts.get(acctIdx).printTransHistory();
	}

	/**
	 * Get the balance of a particular account
	 * 
	 * @param acctIdx the index of the account in the accounts list
	 * @return the balance of the specified account
	 */
	public double getAcctBalance(int acctIdx) {
		return this.accounts.get(acctIdx).getBalance();
	}

	/**
	 * Get the UUID of a particular account
	 * 
	 * @param acctIdx the index of the account in the accounts list
	 * @return the universal unique identifier for the specified account
	 */
	public String getAccountUUID(int acctIdx) {
		return this.accounts.get(acctIdx).getUUID();
	}

	/**
	 * Add a transaction to a particular account
	 * 
	 * @param acctIdx the index of the account in the accounts list
	 * @param amount  the transaction amount
	 * @param memo    a note the user may want to add about the transaction
	 */
	public void addAccountTransaction(int acctIdx, double amount, String memo) {
		this.accounts.get(acctIdx).addTransaction(amount, memo);
	}

}
