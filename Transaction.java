package com.main;

import java.util.Date;

public class Transaction {

	// variables
	private double amount;
	private Date timestamp; // time & date of transaction
	private String memo; // memo for this transaction
	private Account inAccount; // the account in which the transaction occurred in

	/**
	 * Constructor - create a new transaction for an account
	 * 
	 * @param amount    the transaction amount
	 * @param inAccount which account the transaction will take place in
	 */
	public Transaction(double amount, Account inAccount) {

		this.amount = amount;
		this.inAccount = inAccount;
		this.timestamp = new Date();
		this.memo = "";
	}

	/**
	 * Constructor - create a new transaction for an account with a note describing
	 * the transaction
	 * 
	 * @param amount    the transaction amount
	 * @param memo      a note describing the transaction
	 * @param inAccount which account the transaction will take place in
	 */
	public Transaction(double amount, String memo, Account inAccount) {

		// call the two-arg constructor first
		this(amount, inAccount);

		// set the memo
		this.memo = memo;
	}

	/**
	 * Gets the amount of the transaction
	 * 
	 * @return the amount (double)
	 */
	public double getAmount() {
		return this.amount;
	}

	/**
	 * Get a string summarising the transaction
	 * 
	 * @return a summary of the transaction
	 */
	public String getSummaryLine() {

		if (this.amount >= 0) {
			return String.format("%s : £%.02f : %s", this.timestamp.toString(), this.amount, this.memo);
		} else {
			return String.format("%s : £(%.02f) : %s", this.timestamp.toString(), -this.amount, this.memo);
		}
	}

}
