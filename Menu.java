// ATM Simulation Project
//Brooke Higgins, Carlianne Mickle, Kaysa McCluney, Ashlee Kerney
// 2/2/2023
package atm;

import javax.swing.JOptionPane;

public class Menu {

	public static void main(String[] args) {
		
		// Making Accounts/Variables
		String[] cardnumbers = { "11112222", "33334444", "55556666", "77778888" };	//String array for 8-digit card numbers
		String[] pins = { "1234", "4321", "4567", "7654" };	//Integer array for 4-digit pins
		String[] names = { "Brooke Higgins", "Carly Mickle", "Ashlee Kearney", "Kaysa McCluney" }; //String array of first and last names of customers
		int[] savingsaccount = { 100, -1, 300, 400 }; //Integer array for the amount of money in each person's savings account ~ (-1 account balance means they don't have an account)
		int[] checkingsaccount = { 400, 300, -1, 500 }; //Integer array for the amount of money in each person's checking account ~ (-1 account balance means they don't have an account)
		int[] expirationmonth = { 5, 2, 3, 4 }; //Integer array for expiration month dates on the cards
		int[] expirationyear = { 2023, 2023, 2023, 2024 }; //Integer array for expiration year dates on the cards 
		int[] dukeenergy = { 100, 100, 100, -1 }; //Integer array to store bill balances from Duke Energy ~ (-1 account balance means they don't have an account/bill associated with Duke Energy)
		int[] verizon = { -1, 200, 200, -1 }; //Integer array to store bill balances from Verizon ~ (-1 account balance means they don't have an account/bill associated with Verizon)
		int[] spectrum = { -1, -1, 300, -1 }; //Integer array to store bill balances from Spectrum ~ (-1 account balance means they don't have an account/bill associated with Spectrum)
		Account a1 = new Account(); //Creating an instance of the class Account to use it's methods.

		// Asking for Card Number
		System.out.println("Welcome to the Powerhouse Banking ATM!");
		String card = JOptionPane.showInputDialog("Please enter your card number:"); //Variable to keep track of the user's input for their card numbers

		// Verifying Card Number and Checking Expiration
		int accountnum = a1.verifyCard(card, cardnumbers); //Variable to keep track of the user's input for their account number and then verifying the card number
		accountnum = a1.processCard(accountnum, card, expirationmonth, expirationyear, cardnumbers); //Updates account number variable after verifying the card and expiration of the card within three atempts

		// Asking for pin number
		String pin = JOptionPane.showInputDialog("Please enter your pin number:"); //Variable to keep track of the user's input for their pin number
		
		//Verifying Pin Number
		a1.processPin(accountnum, pin, pins); //Processes pin to make sure it's valid and within the three attempts

		// Determining number of accounts
		int numberofaccounts = a1.determineNumberOfAccounts(accountnum, savingsaccount, checkingsaccount); //Variable to determine the number of accounts the user has. 1 = only checking. 2 = only savings. 3 = only savings.

		// Determining number of companies for payment
		//Variable to determine the number of companies the user is associate with. 1 = Duke \ 2 = Verizon \ 3 = Spectrum \ 4 = Duke + Verizon \ 5 = Duke + Spectrum \ 6 = Verizon + Spectrum \ 7 = Duke + Verizon + Spectrum
		int companies = a1.determineNumberOfCompanies(accountnum, dukeenergy, verizon, spectrum); 

		//Making Variable for ATM while Loop
		boolean restart = true; //Variable that keeps track on whether the user wants to go back to the menu or end the program
		int withdrawalamount = 0; //Variable that keeps track of how much the user wants to withdraw
		int depositamount = 0;	//Variable that keeps track of how much the user wants to deposit
		int transferamount = 0; //Variable that keeps track of how much the user wants to transfer
		int fashcashchoice = 0; //Variable that keeps track of which fast cash option they'd like
		int accountchoice = 0; //Variable that keeps track of which account (checking or savings) they'd like to use in whichever option they choose
		int paymentamount = 0; //Variable that keeps track of how much the user wants to pay
		int billchoice = 0; //Variable that keeps track of which bill the user wants to pay.
		int language = 0; //Variable that keep track of which language the user wants to continue with
		int choice = 0; //Variable to determine which banking option they'd like to do today
		
		// Starting Menu
		while (restart) {
			// Welcoming Customer and Determining Language
			language = a1.determineLanguage(); //Assigning language to either 1 (English) and 2 (Spanish)
			if (language == 1) { //English Welcome
				System.out.println("Welcome " + names[accountnum]);
			} else if (language == 2) {  //Spanish Welcome
				System.out.println("Bienvenido " + names[accountnum]);
			}
			// Determining Choice
			choice = a1.determineOption(language, choice); //Assigning choice to one of the available ATM options (1-6) 

			// Check Balances
			if (choice == 1) {
				a1.checkBalances(language, numberofaccounts, accountnum, checkingsaccount, savingsaccount); //Calling checkBalances method to display balances depending on language and number of accounts
				restart = a1.returntoMenu(language); //Assigning restart to whether the user wants to return the menu or not
			}
			// Withdrawing Money
			else if (choice == 2) {
				a1.withdrawMoney(language, accountchoice, numberofaccounts, accountnum, withdrawalamount,
						fashcashchoice, checkingsaccount, savingsaccount); //Calling the withdrawMoney method to allow the user to withdraw money depending on language, withdrawal amounts, and number of accounts
				restart = a1.returntoMenu(language); //Assigning restart to whether the user wants to return the menu or not
			}
			// Depositing Money
			else if (choice == 3) {
				a1.depositMoney(language, accountchoice, numberofaccounts, accountnum, depositamount, checkingsaccount,
						savingsaccount); //Calling the depositMoney method to allow the user to deposit money depending on language, deposit amounts, and number of accounts
				restart = a1.returntoMenu(language); //Assigning restart to whether the user wants to return the menu or not
			}
			// Transferring Money
			else if (choice == 4) {
				a1.transferMoney(language, accountchoice, numberofaccounts, accountnum, transferamount,
						checkingsaccount, savingsaccount); //Calling the transferMoney method to allow user to transfer money between accounts depending on language, transfer amounts, and number of accounts
				restart = a1.returntoMenu(language); //Assigning restart to whether the user wants to return the menu or not
			}
			// Making Payments
			else if (choice == 5) 
			{
				a1.makePayment(language, companies, accountchoice, numberofaccounts, billchoice, paymentamount,
						accountnum, checkingsaccount, savingsaccount, dukeenergy, verizon, spectrum); //Calling the makePayment method to allow user to pay their bills depending on the companies, numbers of accounts, and languages
				restart = a1.returntoMenu(language); //Assigning restart to whether the user wants to return the menu or not
			}
			// Cancel Feature
			else if (choice == 6) { 
				a1.end(language); //Calls method to end the program and display ending message
			}
		}
	}
}