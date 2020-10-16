package com.main;

import java.util.ArrayList;

public class Account {
	
	// variables 
	private String name;
	private String uuid; // the account id number
	private User holder; // the user object that 
	private ArrayList<Transaction> transactions; // list of transactions for this account
	
	
	// constructor - create a new account
	public Account(String name, User holder, Bank theBank) {
		
		// set the account name and holder
		this.name = name;
		this.holder = holder;
		
		// get new account UUID
		this.uuid = theBank.getNewAccountUUID();
		
		// initialise transactions
		this.transactions = new ArrayList<Transaction>();
		
	}


	// get the account ID
	public String getUUID() {
		return this.uuid;
	}
	
	


	// get summary line for the account
	public String getSummaryLine() {
		
		// get the account's balance
		double balance = this.getBalance();
		
		// for the summary line depending on whether the balance is negative
		if(balance >= 0) {
			return String.format("%s : £%.02f : %s", this.uuid, balance, this.name); // 2 digits of precision after the decimal point
		} else {
			return String.format("%s : £(%.02f) : %s", this.uuid, balance, this.name); 
		}
	}
	
	
	// get the balance of this account by adding the amounts of the transactions
	public double getBalance() {
		
		double balance = 0.0;
		for(Transaction t : this.transactions) {
			balance += t.getAmount();
		}
		return balance;
	}


	// print the transaction history
	public void printTransHistory() {
		System.out.printf("\nTransaction history for account %s\n", this.uuid);
		for(int i = this.transactions.size()-1; i >= 0; i--) {
			System.out.println(this.transactions.get(i).getSummaryLine());
		}
		System.out.println();
	}

	// add a new transaction in this account
	public void addTransaction(double amount, String memo) {
		
		// create new transaction object and it add it to our list
		Transaction newTrans = new Transaction(amount, memo, this);
		this.transactions.add(newTrans);
	}
	
	
	
	
	
	
	
	
	
	
}
