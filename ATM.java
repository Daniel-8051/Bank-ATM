package com.main;

import java.util.Scanner;

public class ATM {

	public static void main(String[] args) {

		// initialise scanner
		Scanner sc = new Scanner(System.in);

		// create bank
		Bank theBank = new Bank("Bank of Ireland");

		// add user to bank which also creates savings accounts
		User aUser = theBank.addUser("Daniel", "McAuley", "1234");

		// add a checking account for the user
		Account newAccount = new Account("Checking", aUser, theBank);
		aUser.addAccount(newAccount);
		theBank.addAccount(newAccount);

		User curUser;
		while (true) { // infinite loop

			// stay in login prompt until successful login
			curUser = ATM.mainMenuPrompt(theBank, sc);

			// stay in the main menu until user quits
			ATM.printUserMenu(curUser, sc);

		}

	}

	/**
	 * Print the ATM's login menu
	 * 
	 * @param theBank is the bank the user is logging in to
	 * @param sc      is the scanner object being passed in to the method
	 * @return the authenticated user if login is successful
	 */
	private static User mainMenuPrompt(Bank theBank, Scanner sc) {

		// initialise variables
		String userID;
		String pin;
		User authUser;

		// prompt the user for user ID/ pin combo until the correct one is reached
		do {

			System.out.printf("\n\nWelcome to %s", theBank.getName());
			System.out.print("\nEnter the ID: ");
			userID = sc.nextLine();
			System.out.printf("Enter pin: ");
			pin = sc.nextLine();

			// try to get the user object corresponding with the ID/pin combo
			authUser = theBank.userLogin(userID, pin);
			if (authUser == null) {
				System.out.println("Incorrect user ID/pin combination" + "Please try again");
			}

		} while (authUser == null); // continue looping until successful login

		return authUser;
	}

	/**
	 * Shows the users account summary and the options they have available to them
	 * which include: Show transaction history, withdraw funds, deposit funds,
	 * transfer funds and quit
	 * 
	 * @param theUser the user
	 * @param sc      is the scanner object being passed into the method
	 */
	private static void printUserMenu(User theUser, Scanner sc) {

		// print a summary of user's accounts
		theUser.printAccountsSummary();

		// variables
		int choice;

		// user menu
		do {
			System.out.printf("Welcome %s, what would you like to do\n", theUser.getFirstName());
			System.out.println("	1) Show account transaction history");
			System.out.println("	2) Withdraw");
			System.out.println("	3) Deposit");
			System.out.println("	4) Transfer");
			System.out.println("	5) Quit");
			System.out.println();
			System.out.print("Enter choice: ");
			choice = sc.nextInt();

			if (choice < 1 || choice > 5) {
				System.out.println("Invalid choice. Please choose 1-5");
			}
		} while (choice < 1 || choice > 5);

		// process the choice
		switch (choice) {

		case 1:
			ATM.showTransactionHistory(theUser, sc);
			break;
		case 2:
			ATM.withdrawFunds(theUser, sc);
			break;
		case 3:
			ATM.depositFunds(theUser, sc);
			break;
		case 4:
			ATM.transferFunds(theUser, sc);
			break;
		case 5:
			sc.nextLine();
			break;
		}

		// redisplay this menu unless the user wants to quit
		if (choice != 5) {
			ATM.printUserMenu(theUser, sc);
		}

	}

	/**
	 * Process a fund deposit to an account
	 * 
	 * @param theUser
	 * @param sc
	 */
	private static void depositFunds(User theUser, Scanner sc) {

		// variables
		int toAcct;
		double amount;
		double acctBal;
		String memo;

		// get the account to transfer from
		do {

			System.out.printf("Enter the number (1-%d) of the account to deposit in: ", theUser.numAccounts());
			toAcct = sc.nextInt() - 1;
			if (toAcct < 0 || toAcct >= theUser.numAccounts()) {
				System.out.println("Invalid account. Please try again.");
			}

		} while (toAcct < 0 || toAcct >= theUser.numAccounts());
		acctBal = theUser.getAcctBalance(toAcct);

		// get the amount to transfer
		do {
			System.out.printf("Enter the amount to transfer (max £%.2f): £", acctBal);
			amount = sc.nextDouble();
			if (amount < 0) {
				System.out.println("Amount must be greater than zero");
			}
		} while (amount < 0);

		// handle rest of previous input line
		sc.nextLine();

		// get the memo
		System.out.print("Enter a memo: ");
		memo = sc.nextLine();

		// do the withdraw
		theUser.addAccountTransaction(toAcct, amount, memo);
	}

	/**
	 * Process a fund withdraw from an account
	 * 
	 * @param theUser
	 * @param sc
	 */
	private static void withdrawFunds(User theUser, Scanner sc) {

		// variables
		int fromAcct;
		double amount;
		double acctBal;
		String memo;

		// get the account to transfer from
		do {

			System.out.printf("Enter the number (1-%d) of the account to withdraw from: ", theUser.numAccounts());
			fromAcct = sc.nextInt() - 1;
			if (fromAcct < 0 || fromAcct >= theUser.numAccounts()) {
				System.out.println("Invalid account. Please try again.");
			}

		} while (fromAcct < 0 || fromAcct >= theUser.numAccounts());
		acctBal = theUser.getAcctBalance(fromAcct);

		// get the amount to transfer
		do {
			System.out.printf("Enter the amount to withdraw (max £%.2f): £", acctBal);
			amount = sc.nextDouble();
			if (amount < 0) {
				System.out.println("Amount must be greater than zero");
			} else if (amount > acctBal) {
				System.out.printf("Amount must not be greater than\n" + "balance of £%0.2f.\n", acctBal);
			}
		} while (amount < 0 || amount > acctBal);

		// handle rest of previous input line
		sc.nextLine();

		// get the memo
		System.out.print("Enter a memo: ");
		memo = sc.nextLine();

		// do the withdraw
		theUser.addAccountTransaction(fromAcct, -1 * amount, memo);

	}

	/**
	 * Process transferring funds from one account to another
	 * 
	 * @param theUser
	 * @param sc
	 */
	private static void transferFunds(User theUser, Scanner sc) {

		// variables
		int fromAcct;
		int toAcct;
		double amount;
		double acctBal;

		// get the account to transfer from
		do {

			System.out.printf("Enter the number (1-%d) of the account to transfer from: ", theUser.numAccounts());
			fromAcct = sc.nextInt() - 1;
			if (fromAcct < 0 || fromAcct >= theUser.numAccounts()) {
				System.out.println("Invalid account. Please try again.");
			}

		} while (fromAcct < 0 || fromAcct >= theUser.numAccounts());
		acctBal = theUser.getAcctBalance(fromAcct);

		// get the account to transfer to
		do {

			System.out.printf("Enter the number (1-%d) of the account to transfer to: ", theUser.numAccounts());
			toAcct = sc.nextInt() - 1;
			if (toAcct < 0 || toAcct >= theUser.numAccounts()) {
				System.out.println("Invalid account. Please try again.");
			}

		} while (toAcct < 0 || toAcct >= theUser.numAccounts());

		// get the amount to transfer
		do {
			System.out.printf("Enter the amount to transfer (max £%.2f): £", acctBal);
			amount = sc.nextDouble();
			if (amount < 0) {
				System.out.println("Amount must be greater than zero");
			} else if (amount > acctBal) {
				System.out.printf("Amount must not be greater than\n" + "balance of £%0.2f.\n", acctBal);
			}
		} while (amount < 0 || amount > acctBal);

		// finally, do the transfer
		theUser.addAccountTransaction(fromAcct, -1 * amount,
				String.format("Transfer to account %s", theUser.getAccountUUID(toAcct)));
		theUser.addAccountTransaction(toAcct, amount,
				String.format("Transfer to account %s", theUser.getAccountUUID(fromAcct)));
	}

	/**
	 * Show the transaction history for an account
	 * 
	 * @param theUser
	 * @param sc
	 */
	private static void showTransactionHistory(User theUser, Scanner sc) {

		// variables
		int theAcct;

		// get account whose transaction history to look at
		do {
			System.out.printf("Enter the number (1-%d) of the account\nwhose transactions you want to see: ",
					theUser.numAccounts());
			theAcct = sc.nextInt() - 1;
			if (theAcct < 0 || theAcct >= theUser.numAccounts()) {
				System.out.println("Invalid account. Please try again");
			}

		} while (theAcct < 0 || theAcct >= theUser.numAccounts());

		// print the transaction history
		theUser.printAcctTransactionHistory(theAcct);

	}

}
