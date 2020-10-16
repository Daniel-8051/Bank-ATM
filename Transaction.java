package com.main;

import java.util.Date;

public class Transaction {
	
	// variables
	private double amount;
	private Date timestamp; // time & date of transaction
	private String memo; // memo for this transaction
	private Account inAccount; // the account in which the transaction occurred in
	
	// constructor - create a new transaction
	public Transaction(double amount, Account inAccount) {
		
		this.amount = amount;
		this.inAccount = inAccount;
		this.timestamp = new Date();
		this.memo = "";
	}
	
	// constructor - with memo (overloading constructor)
	public Transaction(double amount, String memo, Account inAccount) {
		
		// call the two-arg constructor first
		this(amount, inAccount);
		
		// set the memo
		this.memo = memo;
	}
	
	

	// get the amount of the transaction
	public double getAmount() {
		return this.amount;
	}

	// get a string summarising the transaction
	public String getSummaryLine() {
		
		if(this.amount >= 0) {
			return String.format("%s : £%.02f : %s", this.timestamp.toString(), this.amount, this.memo);
		} else {
			return String.format("%s : £(%.02f) : %s", this.timestamp.toString(), -this.amount, this.memo);
		}
	}
	
	
	
	
	
	
	
	
	
}
