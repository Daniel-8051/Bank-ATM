package com.main;

import java.util.ArrayList;

public class Account {

	// variables
	private String name;
	private String uuid; // the account id number
	private User holder; // the holder of the account
	private ArrayList<Transaction> transactions; // list of transactions for this account

	//
	/**
	 * Constructor - creates a new account
	 * 
	 * @param name    of the account
	 * @param holder  of the account (user)
	 * @param theBank the bank the account is in
	 */
	public Account(String name, User holder, Bank theBank) {

		// set the account name and holder
		this.name = name;
		this.holder = holder;

		// get new account UUID
		this.uuid = theBank.getNewAccountUUID();

		// initialise transactions
		this.transactions = new ArrayList<Transaction>();

	}

	/**
	 * Get the account ID
	 * 
	 * @return the universal unique identifier
	 */
	public String getUUID() {
		return this.uuid;
	}

	/**
	 * Get summary line for the account showing the UUID, account balance and the
	 * name associated with the account
	 * 
	 * @return the summary line
	 */
	public String getSummaryLine() {

		// get the account's balance
		double balance = this.getBalance();

		// summary line depending on whether the balance is negative
		if (balance >= 0) {
			return String.format("%s : £%.02f : %s", this.uuid, balance, this.name); // 2 digits of precision after the
																						// decimal point
		} else {
			return String.format("%s : £(%.02f) : %s", this.uuid, balance, this.name);
		}
	}

	/**
	 * Gets the balance of this account by adding the amounts of the transactions
	 * 
	 * @return the balance (double)
	 */
	public double getBalance() {

		double balance = 0.0;
		for (Transaction t : this.transactions) {
			balance += t.getAmount();
		}
		return balance;
	}

	/**
	 * Prints the transaction history of the account
	 */
	public void printTransHistory() {
		System.out.printf("\nTransaction history for account %s\n", this.uuid);
		for (int i = this.transactions.size() - 1; i >= 0; i--) {
			System.out.println(this.transactions.get(i).getSummaryLine());
		}
		System.out.println();
	}

	/**
	 * Add a new transaction in this account
	 * 
	 * @param amount the transaction amount
	 * @param memo   a note the user may want to add about the transaction
	 */
	public void addTransaction(double amount, String memo) {

		// create new transaction object and it add it to our list
		Transaction newTrans = new Transaction(amount, memo, this);
		this.transactions.add(newTrans);
	}

}
