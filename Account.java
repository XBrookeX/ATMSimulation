// ATM Simulation Project
//Brooke Higgins, Carlianne Mickle, Kaysa McCluney, Ashlee Kerney
// 2/2/2023
package atm;
import java.time.LocalDate;
import java.util.Scanner;
import javax.swing.JOptionPane;

public class Account {
	// Method to verify card
	public int verifyCard(String cardnum, String[] array) {
		for (int i = 0; i < array.length; i++) {
			if (cardnum.equals(array[i])) {
				return i;
			}
		}
		return -1;
	}

	// Method to verify pin
	public boolean verifyPin(String pin, String correctpin) {
		return pin.equals(correctpin);
	}

	// Method to see if card is expired
	public boolean isExpired(int month, int year) {
		LocalDate todaysDate = LocalDate.now();
		int currentMonth = todaysDate.getMonthValue();
		int currentYear = todaysDate.getYear();
		if (year < currentYear) {
			return true;
		} else if (month <= currentMonth) {
			return true;
		}
		return false;
	}

	// Method to end program based on the language
	public void end(int language) {
		if (language == 1) {
			System.out.println("Ending Processes....\nThank you for choosing our Powerhouse Banking!\nPlease remember to take your card.");
		} else {
			System.out.println("Finalización de procesos....\n¡Gracias por elegir nuestra Powerhouse Banking!\nRecuerde llevar su tarjeta.");
		}
		System.exit(0);
	}

	// Method to determine if the user has either a checking accounts, a savings
	// account, or both
	public int determineNumberOfAccounts(int accountnum, int[] savingsaccount, int[] checkingsaccount) {
		int numberofaccounts = 0;
		if (savingsaccount[accountnum] == -1) // Only have checking account
		{
			numberofaccounts = 1;
		} else if (checkingsaccount[accountnum] == -1) // Only have savings account
		{
			numberofaccounts = 2;
		} else {
			numberofaccounts = 3;
		}
		return numberofaccounts;
	}

	// Method to determine the number of companies the user is associated with and
	// therefore, has bills associated with it.
	public int determineNumberOfCompanies(int accountnum, int[] dukeenergy, int[] verizon, int[] spectrum) {
		int companies = 0;
		if (dukeenergy[accountnum] >= 0) {
			companies = 1;
			if (verizon[accountnum] >= 0) {
				companies = 4;
				if (spectrum[accountnum] >= 0) {
					companies = 7;
				}
			} else if (spectrum[accountnum] >= 0) {
				companies = 5;
			}
		} else if (verizon[accountnum] >= 0) // Only have savings account
		{
			companies = 2;
			if (spectrum[accountnum] >= 0) {
				companies = 6;
			}
		} else if (spectrum[accountnum] >= 0) {
			companies = 3;
		}
		return companies;
	}

	// Method to determine the language to use and welcome them in that language
	public int determineLanguage() {
		Scanner input = new Scanner(System.in);
		System.out.println("What language do you prefer?\nEnglish (1) or Spanish? (2)");
		int language = input.nextInt();
		while (language < 1 || language > 2) {
			System.out.println("Please choose either: English (1) or Spanish (2)");
			language = input.nextInt();
		}
		return language;
	}

	// Method to determine the option they'd like to
	public int determineOption(int language, int choice) {
		Scanner input = new Scanner(System.in);
		if (language == 1) {
			System.out.println(
					"What would you like to do today?\nCheck Balance(1)\tWithdrawal(2)\tDeposit(3)\tTransfer Money(4)\tMake Payment(5)\tCancel(6)");
			choice = input.nextInt();
		} else if (language == 2) {
			System.out.println(
					"¿Qué te gustaría hacer hoy?\nConsultar Saldo(1)\tRetiro\t(2)\tDepósito(3)\tTransferir Dinero(4)\tHacer el Pago(5)\tCancelar(6)");
			choice = input.nextInt();
		}
		// Determining Option
		while (choice < 1 || choice > 6) {
			if (language == 1) {
				System.out.println(
						"Please select the one of these options:\nCheck Balance(1)\tWithdrawal(2)\tDeposit(3)\tTransfer Money(4)\tMake Payment(5)\tCancel(6)");
				choice = input.nextInt();
			} else {
				System.out.println(
						"Seleccione una de estas opciones:\nConsultar Saldo(1)\tRetiro(2)\tDepósito(3)\tTransferir Dinero(4)\tHacer el Pago(5)\tCancelar(6)");
				choice = input.nextInt();
			}
		}
		return choice;
	}

	// Method to ask pin
	public void processPin(int accountnum, String pin, String pins[]) {
		int attempts = 0;
		// Verifying Pin Number
		attempts = 1;
		while ((!(verifyPin(pin, pins[accountnum])))) {
			if (attempts == 1) {
				pin = JOptionPane.showInputDialog("Incorrect pin number. Please re-enter.\nYou have two more attempts remaining.");
			}
			if (attempts == 2) {
				pin = JOptionPane.showInputDialog("Incorrect pin number. Please re-enter.\nYou have one more attempt remaining. Failure to enter a correct pin number will result in the witholding of your card.");
			}
			if (attempts == 3) {
				System.out.println("You have tried too many times. Your card is withheld. Please see a bank associate.");
				System.exit(0);
			}
			attempts++;
		}
	}

	public int processCard(int accountnum, String card, int[] expirationmonth, int[] expirationyear,
			String[] cardnumbers) {
		int attempts = 0;
		Scanner input = new Scanner(System.in);
		while (accountnum == -1) {
			attempts++;
			if (attempts == 3) {
				System.out.println("You have tried too many times. Your card is withheld. Please see a bank associate.");
				System.exit(0);
			}
			//card = JOptionPane.showInputDialog("Incorrect card number. Please re-enter.\nYou have two more attempts remaining.");

			if (attempts == 1) {
				card = JOptionPane.showInputDialog("Incorrect card number. Please re-enter.\nYou have two more attempts remaining.");
			}
			if (attempts == 2) {
				card = JOptionPane.showInputDialog("Incorrect card number. Please re-enter.\nYou have one more attempt remaining. Failure to enter a correct card number will result in the witholding of your card.");
			}
			accountnum = verifyCard(card, cardnumbers);
		}
		while ((isExpired(expirationmonth[accountnum], expirationyear[accountnum]))) {
			System.out.println("Sorry, this card is expired. Please enter another card number:");
			attempts = 0;
			card = input.nextLine();
			accountnum = verifyCard(card, cardnumbers);
			while (accountnum == -1) {
				attempts++;
				if (attempts == 3) {
					System.out.println("You have tried too many times. Your card is withheld. Please see a bank associate.");
					System.exit(0);
				}
				System.out.println("Incorrect card number. Please re-enter");
				if (attempts == 1) {
					System.out.println("You have two more attempts remaining.");
				}
				if (attempts == 2) {
					System.out.println(
							"You have one more attempt remaining. Failure to enter a correct card number will result in the witholding of your card.");
				}
				card = input.nextLine();
				accountnum = verifyCard(card, cardnumbers);
			}
		}
		return accountnum;
	}

	// Method to determine if the user wants to return to the menu
	public boolean returntoMenu(int language) {
		Scanner input = new Scanner(System.in);
		if (language == 1) {
			System.out.println("Would you like to return to the menu?\nYes(1)\tNo(2)");
			int answer = input.nextInt();
			while (answer < 1 || answer > 2) {
				System.out.println(
						"Please select one of the options on whether you'd like to return to the menu:\nYes(1)\tNo(2)");
				answer = input.nextInt();
			}
			if (answer == 1) {
				return true;
			} else {
				end(language);
				return false;
			}
		} else {
			System.out.println("¿Desea volver al menú?\nSí(1)\tNo(2)");
			int answer = input.nextInt();
			while (answer < 1 || answer > 2) {
				System.out.println("Seleccione una de las opciones sobre si desea volver al menú:\nSí(1)\tNo(2)");
				answer = input.nextInt();
			}
			if (answer == 1) {
				return true;
			} else {
				end(language);
				return false;
			}
		}
	}

	// Method to confirm choice selection
	public boolean confirmSeletion(int language) {
		Scanner input = new Scanner(System.in);
		int answer = 0;
		if (language == 1) {
			System.out.println("Is this this correct selection?\nYes(1)\tNo(2)");
			answer = input.nextInt();
			while (answer < 1 || answer > 2) {
				System.out.println(
						"Please select one of the options on whether this is the correct selection:\nYes(1)\tNo(2)");
				answer = input.nextInt();
			}
			if (answer == 1) {
				return true;
			} else {
				// returntoMenu(language);
				return false;
			}
		} else // Spanish
		{
			System.out.println("¿Es esta la selección correcta?\nSí(1)\tNo(2)");
			answer = input.nextInt();
			while (answer < 1 || answer > 2) {
				System.out.println(
						"Seleccione una de las opciones sobre si esta es la selección correcta:\nSí(1)\tNo(2)");
				answer = input.nextInt();
			}
			if (answer == 1) {
				return true;
			} else {
				// returntoMenu(language);
				return false;
			}
		}
	}

	// Method for checking balances
	public void checkBalances(int language, int numberofaccounts, int accountnum, int[] checkingsaccount,
			int[] savingsaccount) {
		if (language == 1) {
			System.out.println("You have selected 'Check Balance'.");
			boolean correctSelection = confirmSeletion(language);
			if (correctSelection) {
				if (numberofaccounts == 1) {
					System.out.println(
							"The current balance of your checkings account is: $" + checkingsaccount[accountnum]);
				} else if (numberofaccounts == 2) {
					System.out
							.println("The current balance of your savings account is: $" + savingsaccount[accountnum]);
				} else {
					System.out.println(
							"The current balance of your checkings account is: $" + checkingsaccount[accountnum]);
					System.out
							.println("The current balance of your savings account is: $" + savingsaccount[accountnum]);
				}

			}
		} else {
			System.out.println("Ha seleccionado 'Comprobar Saldo'.");
			boolean correctSelection = confirmSeletion(language);
			if (correctSelection) {
				if (numberofaccounts == 1) {
					System.out.println("El saldo actual de su cuenta corriente es: $" + checkingsaccount[accountnum]);
				} else if (numberofaccounts == 2) {
					System.out.println("El saldo actual de su cuenta de ahorros es: $" + savingsaccount[accountnum]);
				} else {
					System.out.println("El saldo actual de su cuenta corriente es: $" + checkingsaccount[accountnum]);
					System.out.println("El saldo actual de su cuenta de ahorros es: $" + savingsaccount[accountnum]);
				}

			}
		}
	}

	// Method to withdrawing money
	public void withdrawMoney(int language, int accountchoice, int numberofaccounts, int accountnum,
			int withdrawalamount, int ramcashchoice, int[] checkingsaccount, int[] savingsaccount) {
		Scanner input = new Scanner(System.in);
		if (language == 1) { // Selecting Accounts
			System.out.println("You have selected 'Withdrawal'.");
			boolean correctSelection = confirmSeletion(language);
			if (correctSelection) {
				accountchoice = numberofaccounts;
				if (accountchoice == 3) {
					System.out.println(
							"The current balance of your checkings account is: $" + checkingsaccount[accountnum]);
					System.out
							.println("The current balance of your savings account is: $" + savingsaccount[accountnum]);
					System.out.println("Which account would you like to withdraw from? Checking(1)\tSavings(2)");
					accountchoice = input.nextInt();
					while (accountchoice < 1 || accountchoice > 2) {
						System.out.println("Please select the one of these account options:\nCheckings(1)\tSavings(2)");
						accountchoice = input.nextInt();
					}
				}
				if (accountchoice == 1) { // Checking Account ~ English
					System.out.println("How much would like to withdraw fom your checking account balance of $"
							+ checkingsaccount[accountnum]);
					System.out.println(
							"Select one of these options: $20(1)\t$40(2)\t$60(3)\t$80(4)\t$100(5)\tOther Amount(6)");
					ramcashchoice = input.nextInt();
					while (ramcashchoice < 1 || ramcashchoice > 6) {
						System.out.println(
								"Please select the one of these RamCash options:\n$20(1)\t$40(2)\t$60(3)\t$80(4)\t$100(5)\tOther Amount(6)");
						ramcashchoice = input.nextInt();
					}
					boolean withdrawed = false;
					while (!withdrawed) {
						if (ramcashchoice == 1) {
							if (checkingsaccount[accountnum] - 20 < 0) {
								ramcashchoice = 0;
								System.out.println(
										"Please select a amount that doesn't exceed your current checking account balance of: $"
												+ checkingsaccount[accountnum]);
								System.out.println(
										"Please select the one of these RamCash options:\n$20(1)\t$40(2)\t$60(3)\t$80(4)\t$100(5)\tOther Amount(6)");
								ramcashchoice = input.nextInt();
								while (ramcashchoice < 1 || ramcashchoice > 6) {
									System.out.println(
											"Please select the one of these RamCash options:\n$20(1)\t$40(2)\t$60(3)\t$80(4)\t$100(5)\tOther Amount(6)");
									ramcashchoice = input.nextInt();
								}
							} else {
								checkingsaccount[accountnum] -= 20;
								System.out.println("Now withdrawing $20. Your checkings account balance is now: $"
										+ checkingsaccount[accountnum]);
								withdrawed = true;
							}
						}
						if (ramcashchoice == 2) {
							if (checkingsaccount[accountnum] - 40 < 0) {
								ramcashchoice = 0;
								System.out.println(
										"Please select a amount that doesn't exceed your current checking account balance of: $"
												+ checkingsaccount[accountnum]);
								System.out.println(
										"Please select the one of these RamCash options:\n$20(1)\t$40(2)\t$60(3)\t$80(4)\t$100(5)\tOther Amount(6)");
								ramcashchoice = input.nextInt();
								while (ramcashchoice < 1 || ramcashchoice > 6) {
									System.out.println(
											"Please select the one of these RamCash options:\n$20(1)\t$40(2)\t$60(3)\t$80(4)\t$100(5)\tOther Amount(6)");
									ramcashchoice = input.nextInt();
								}
							} else {
								checkingsaccount[accountnum] -= 40;
								System.out.println("Now withdrawing $40. Your checkings account balance is now: $"
										+ checkingsaccount[accountnum]);
								withdrawed = true;
							}
						}
						if (ramcashchoice == 3) {
							if (checkingsaccount[accountnum] - 60 < 0) {
								ramcashchoice = 0;
								System.out.println(
										"Please select a amount that doesn't exceed your current checking account balance of: $"
												+ checkingsaccount[accountnum]);
								System.out.println(
										"Please select the one of these RamCash options:\n$20(1)\t$40(2)\t$60(3)\t$80(4)\t$100(5)\tOther Amount(6)");
								ramcashchoice = input.nextInt();
								while (ramcashchoice < 1 || ramcashchoice > 6) {
									System.out.println(
											"Please select the one of these RamCash options:\n$20(1)\t$40(2)\t$60(3)\t$80(4)\t$100(5)\tOther Amount(6)");
									ramcashchoice = input.nextInt();
								}
							} else {
								checkingsaccount[accountnum] -= 60;
								System.out.println("Now withdrawing $60. Your checkings account balance is now: $"
										+ checkingsaccount[accountnum]);
								withdrawed = true;
							}
						}
						if (ramcashchoice == 4) {
							if (checkingsaccount[accountnum] - 80 < 0) {
								ramcashchoice = 0;
								System.out.println(
										"Please select a amount that doesn't exceed your current checking account balance of: $"
												+ checkingsaccount[accountnum]);
								System.out.println(
										"Please select the one of these RamCash options:\n$20(1)\t$40(2)\t$60(3)\t$80(4)\t$100(5)\tOther Amount(6)");
								ramcashchoice = input.nextInt();
								while (ramcashchoice < 1 || ramcashchoice > 6) {
									System.out.println(
											"Please select the one of these RamCash options:\n$20(1)\t$40(2)\t$60(3)\t$80(4)\t$100(5)\tOther Amount(6)");
									ramcashchoice = input.nextInt();
								}
							} else {
								checkingsaccount[accountnum] -= 80;
								System.out.println("Now withdrawing $80. Your checkings account balance is now: $"
										+ checkingsaccount[accountnum]);
								withdrawed = true;
							}
						}
						if (ramcashchoice == 5) {
							if (checkingsaccount[accountnum] - 100 < 0) {
								ramcashchoice = 0;
								System.out.println(
										"Please select a amount that doesn't exceed your current checking account balance of: $"
												+ checkingsaccount[accountnum]);
								System.out.println(
										"Please select the one of these RamCash options:\n$20(1)\t$40(2)\t$60(3)\t$80(4)\t$100(5)\tOther Amount(6)");
								ramcashchoice = input.nextInt();
								while (ramcashchoice < 1 || ramcashchoice > 6) {
									System.out.println(
											"Please select the one of these RamCash options:\n$20(1)\t$40(2)\t$60(3)\t$80(4)\t$100(5)\tOther Amount(6)");
									ramcashchoice = input.nextInt();
								}
							} else {
								checkingsaccount[accountnum] -= 100;
								System.out.println("Now withdrawing $100. Your checkings account balance is now: $"
										+ checkingsaccount[accountnum]);
								withdrawed = true;
							}
						}
						if (ramcashchoice == 6) {
							System.out.println("How much would you like to withdraw?");
							withdrawalamount = input.nextInt();
							while (checkingsaccount[accountnum] - withdrawalamount < 0 || withdrawalamount < 0) {
								System.out.println(
										"Please enter an positive amount that doesn't exceed your current checking account balance of: "
												+ checkingsaccount[accountnum]);
								withdrawalamount = input.nextInt();
							}

							checkingsaccount[accountnum] -= withdrawalamount;
							System.out.println("Now withdrawing $" + withdrawalamount
									+ ". Your checkings account balance is now: $" + checkingsaccount[accountnum]);
							withdrawed = true;
						}
					}
				} else { // Savings Account ~ English
					System.out.println("How much would like to withdraw fom your savings account balance of $"
							+ savingsaccount[accountnum]);
					System.out.println(
							"Select one of these options: $20(1)\t$40(2)\t$60(3)\t$80(4)\t$100(5)\tOther Amount(6)");
					ramcashchoice = input.nextInt();
					while (ramcashchoice < 1 || ramcashchoice > 6) {
						System.out.println(
								"Please select the one of these RamCash options:\n$20(1)\t$40(2)\t$60(3)\t$80(4)\t$100(5)\tOther Amount(6)");
						ramcashchoice = input.nextInt();
					}
					boolean withdrawed = false;
					while (!withdrawed) {
						if (ramcashchoice == 1) {
							if (savingsaccount[accountnum] - 20 < 0) {
								ramcashchoice = 0;
								System.out.println(
										"Please select a amount that doesn't exceed your current savings account balance of: $"
												+ savingsaccount[accountnum]);
								System.out.println(
										"Please select the one of these RamCash options:\n$20(1)\t$40(2)\t$60(3)\t$80(4)\t$100(5)\tOther Amount(6)");
								ramcashchoice = input.nextInt();
								while (ramcashchoice < 1 || ramcashchoice > 6) {
									System.out.println(
											"Please select the one of these RamCash options:\n$20(1)\t$40(2)\t$60(3)\t$80(4)\t$100(5)\tOther Amount(6)");
									ramcashchoice = input.nextInt();
								}
							} else {
								savingsaccount[accountnum] -= 20;
								System.out.println("Now withdrawing $20. Your savings account balance is now: $"
										+ savingsaccount[accountnum]);
								withdrawed = true;
							}
						}
						if (ramcashchoice == 2) {
							if (savingsaccount[accountnum] - 40 < 0) {
								ramcashchoice = 0;
								System.out.println(
										"Please select a amount that doesn't exceed your current savings account balance of: $"
												+ savingsaccount[accountnum]);
								System.out.println(
										"Please select the one of these RamCash options:\n$20(1)\t$40(2)\t$60(3)\t$80(4)\t$100(5)\tOther Amount(6)");
								ramcashchoice = input.nextInt();
								while (ramcashchoice < 1 || ramcashchoice > 6) {
									System.out.println(
											"Please select the one of these RamCash options:\n$20(1)\t$40(2)\t$60(3)\t$80(4)\t$100(5)\tOther Amount(6)");
									ramcashchoice = input.nextInt();
								}
							} else {
								savingsaccount[accountnum] -= 40;
								System.out.println("Now withdrawing $40. Your savings account balance is now: $"
										+ savingsaccount[accountnum]);
								withdrawed = true;
							}
						}
						if (ramcashchoice == 3) {
							if (savingsaccount[accountnum] - 60 < 0) {
								ramcashchoice = 0;
								System.out.println(
										"Please select a amount that doesn't exceed your current savings account balance of: $"
												+ savingsaccount[accountnum]);
								System.out.println(
										"Please select the one of these RamCash options:\n$20(1)\t$40(2)\t$60(3)\t$80(4)\t$100(5)\tOther Amount(6)");
								ramcashchoice = input.nextInt();
								while (ramcashchoice < 1 || ramcashchoice > 6) {
									System.out.println(
											"Please select the one of these RamCash options:\n$20(1)\t$40(2)\t$60(3)\t$80(4)\t$100(5)\tOther Amount(6)");
									ramcashchoice = input.nextInt();
								}
							} else {
								savingsaccount[accountnum] -= 60;
								System.out.println("Now withdrawing $60. Your savings account balance is now: $"
										+ savingsaccount[accountnum]);
								withdrawed = true;
							}
						}
						if (ramcashchoice == 4) {
							if (savingsaccount[accountnum] - 80 < 0) {
								ramcashchoice = 0;
								System.out.println(
										"Please select a amount that doesn't exceed your current savings account balance of: $"
												+ savingsaccount[accountnum]);
								System.out.println(
										"Please select the one of these RamCash options:\n$20(1)\t$40(2)\t$60(3)\t$80(4)\t$100(5)\tOther Amount(6)");
								ramcashchoice = input.nextInt();
								while (ramcashchoice < 1 || ramcashchoice > 6) {
									System.out.println(
											"Please select the one of these RamCash options:\n$20(1)\t$40(2)\t$60(3)\t$80(4)\t$100(5)\tOther Amount(6)");
									ramcashchoice = input.nextInt();
								}
							} else {
								savingsaccount[accountnum] -= 80;
								System.out.println("Now withdrawing $80. Your savings account balance is now: $"
										+ savingsaccount[accountnum]);
								withdrawed = true;
							}
						}
						if (ramcashchoice == 5) {
							if (savingsaccount[accountnum] - 100 < 0) {
								ramcashchoice = 0;
								System.out.println(
										"Please select a amount that doesn't exceed your current savings account balance of: $"
												+ savingsaccount[accountnum]);
								System.out.println(
										"Please select the one of these RamCash options:\n$20(1)\t$40(2)\t$60(3)\t$80(4)\t$100(5)\tOther Amount(6)");
								ramcashchoice = input.nextInt();
								while (ramcashchoice < 1 || ramcashchoice > 6) {
									System.out.println(
											"Please select the one of these RamCash options:\n$20(1)\t$40(2)\t$60(3)\t$80(4)\t$100(5)\tOther Amount(6)");
									ramcashchoice = input.nextInt();
								}
							} else {
								savingsaccount[accountnum] -= 100;
								System.out.println("Now withdrawing $100. Your savings account balance is now: $"
										+ savingsaccount[accountnum]);
								withdrawed = true;
							}
						}
						if (ramcashchoice == 6) {
							System.out.println("How much would you like to withdraw?");
							withdrawalamount = input.nextInt();
							while (savingsaccount[accountnum] - withdrawalamount < 0 || withdrawalamount < 0) {
								System.out.println(
										"Please enter an positive amount that doesn't exceed your current savings account balance of: $"
												+ checkingsaccount[accountnum]);
								withdrawalamount = input.nextInt();
							}

							savingsaccount[accountnum] -= withdrawalamount;
							System.out.println("Now withdrawing $" + withdrawalamount
									+ ". Your savings account balance is now: $" + savingsaccount[accountnum]);
							withdrawed = true;
						}
					}
				}
			}
		}

		else { // Spanish Withdrawing Accounts
			System.out.println("Ha seleccionado 'Retiro'.");
			boolean correctSelection = confirmSeletion(language);
			if (correctSelection) {
				accountchoice = numberofaccounts;
				if (accountchoice == 3) {
					System.out.println("El saldo actual de su cuenta corriente es: $" + checkingsaccount[accountnum]);
					System.out.println("El saldo actual de su cuenta de ahorros es: $" + savingsaccount[accountnum]);
					System.out.println("¿De qué cuenta le gustaría retirar dinero? Cuenta de Cheques(1)\tAhorros(2)");
					accountchoice = input.nextInt();
					while (accountchoice < 1 || accountchoice > 2) {
						System.out.println("Seleccione una de estas opciones de cuenta:\nCheques(1)\tAhorros(2)");
						accountchoice = input.nextInt();
					}
				}
				if (accountchoice == 1) { // Checking Account ~ Spanish
					System.out.println("¿Cuánto le gustaría retirar del saldo de su cuenta corriente de $"
							+ checkingsaccount[accountnum]);
					System.out.println(
							"Seleccione una de estas opciones: $20(1)\t$40(2)\t$60(3)\t$80(4)\t$100(5)\tOtra cantidad(6)");
					ramcashchoice = input.nextInt();
					while (ramcashchoice < 1 || ramcashchoice > 6) {
						System.out.println(
								"Seleccione una de estas opciones de RamCash:\n$20(1)\t$40(2)\t$60(3)\t$80(4)\t$100(5)\tOtra cantidad(6)");
						ramcashchoice = input.nextInt();
					}
					boolean withdrawed = false;
					while (!withdrawed) {
						if (ramcashchoice == 1) {
							if (checkingsaccount[accountnum] - 20 < 0) {
								ramcashchoice = 0;
								System.out.println(
										"Seleccione una cantidad que no exceda el saldo actual de su cuenta corriente de: $"
												+ checkingsaccount[accountnum]);
								System.out.println(
										"Seleccione una de estas opciones de RamCash:\n$20(1)\t$40(2)\t$60(3)\t$80(4)\t$100(5)\tOtra cantidad(6)");
								ramcashchoice = input.nextInt();
								while (ramcashchoice < 1 || ramcashchoice > 6) {
									System.out.println(
											"Seleccione una de estas opciones de RamCash:\n$20(1)\t$40(2)\t$60(3)\t$80(4)\t$100(5)\tOtra cantidad(6)");
									ramcashchoice = input.nextInt();
								}
							} else {
								checkingsaccount[accountnum] -= 20;
								System.out.println("Ahora retirando $20. El saldo de su cuenta corriente es ahora: $"
										+ checkingsaccount[accountnum]);
								withdrawed = true;
							}
						}
						if (ramcashchoice == 2) {
							if (checkingsaccount[accountnum] - 40 < 0) {
								ramcashchoice = 0;
								System.out.println(
										"Seleccione una cantidad que no exceda el saldo actual de su cuenta corriente de: $"
												+ checkingsaccount[accountnum]);
								System.out.println(
										"Seleccione una de estas opciones de RamCash:\n$20(1)\t$40(2)\t$60(3)\t$80(4)\t$100(5)\tOtra cantidad(6)");
								ramcashchoice = input.nextInt();
								while (ramcashchoice < 1 || ramcashchoice > 6) {
									System.out.println(
											"Seleccione una de estas opciones de RamCash:\n$20(1)\t$40(2)\t$60(3)\t$80(4)\t$100(5)\tOtra cantidad(6)");
									ramcashchoice = input.nextInt();
								}
							} else {
								checkingsaccount[accountnum] -= 40;
								System.out.println("Ahora retirando $40. El saldo de su cuenta corriente es ahora: $"
										+ checkingsaccount[accountnum]);
								withdrawed = true;
							}
						}
						if (ramcashchoice == 3) {
							if (checkingsaccount[accountnum] - 60 < 0) {
								ramcashchoice = 0;
								System.out.println(
										"Seleccione una cantidad que no exceda el saldo actual de su cuenta corriente de: $"
												+ checkingsaccount[accountnum]);
								System.out.println(
										"Seleccione una de estas opciones de RamCash:\n$20(1)\t$40(2)\t$60(3)\t$80(4)\t$100(5)\tOtra cantidad(6)");
								ramcashchoice = input.nextInt();
								while (ramcashchoice < 1 || ramcashchoice > 6) {
									System.out.println(
											"Seleccione una de estas opciones de RamCash:\n$20(1)\t$40(2)\t$60(3)\t$80(4)\t$100(5)\tOtra cantidad(6)");
									ramcashchoice = input.nextInt();
								}
							} else {
								checkingsaccount[accountnum] -= 60;
								System.out.println("Ahora retirando $60. El saldo de su cuenta corriente es ahora: $"
										+ checkingsaccount[accountnum]);
								withdrawed = true;
							}
						}
						if (ramcashchoice == 4) {
							if (checkingsaccount[accountnum] - 80 < 0) {
								ramcashchoice = 0;
								System.out.println(
										"Seleccione una cantidad que no exceda el saldo actual de su cuenta corriente de: $"
												+ checkingsaccount[accountnum]);
								System.out.println(
										"Seleccione una de estas opciones de RamCash:\n$20(1)\t$40(2)\t$60(3)\t$80(4)\t$100(5)\tOtra cantidad(6)");
								ramcashchoice = input.nextInt();
								while (ramcashchoice < 1 || ramcashchoice > 6) {
									System.out.println(
											"Seleccione una de estas opciones de RamCash:\n$20(1)\t$40(2)\t$60(3)\t$80(4)\t$100(5)\tOtra cantidad(6)");
									ramcashchoice = input.nextInt();
								}
							} else {
								checkingsaccount[accountnum] -= 80;
								System.out.println("Ahora retirando $80. El saldo de su cuenta corriente es ahora: $"
										+ checkingsaccount[accountnum]);
								withdrawed = true;
							}
						}
						if (ramcashchoice == 5) {
							if (checkingsaccount[accountnum] - 100 < 0) {
								ramcashchoice = 0;
								System.out.println(
										"Seleccione una cantidad que no exceda el saldo actual de su cuenta corriente de: $"
												+ checkingsaccount[accountnum]);
								System.out.println(
										"Seleccione una de estas opciones de RamCash:\n$20(1)\t$40(2)\t$60(3)\t$80(4)\t$100(5)\tOtra cantidad(6)");
								ramcashchoice = input.nextInt();
								while (ramcashchoice < 1 || ramcashchoice > 6) {
									System.out.println(
											"Seleccione una de estas opciones de RamCash:\n$20(1)\t$40(2)\t$60(3)\t$80(4)\t$100(5)\tOtra cantidad(6)");
									ramcashchoice = input.nextInt();
								}
							} else {
								checkingsaccount[accountnum] -= 100;
								System.out.println("Ahora retirando $100. El saldo de su cuenta corriente es ahora: $"
										+ checkingsaccount[accountnum]);
								withdrawed = true;
							}
						}
						if (ramcashchoice == 6) {
							System.out.println("¿Cuánto te gustaría retirar?");
							withdrawalamount = input.nextInt();
							while (checkingsaccount[accountnum] - withdrawalamount < 0 || withdrawalamount < 0) {
								System.out.println(
										"Seleccione una cantidad que no exceda el saldo actual de su cuenta corriente de: $"
												+ checkingsaccount[accountnum]);
								withdrawalamount = input.nextInt();
							}

							checkingsaccount[accountnum] -= withdrawalamount;
							System.out.println("Ahora retirando $" + withdrawalamount
									+ ". El saldo de su cuenta corriente es ahora: $" + checkingsaccount[accountnum]);
							withdrawed = true;
						}
					}
				} else // Savings account ~ Spanish
				{
					System.out.println("¿Cuánto le gustaría retirar del saldo de su cuenta corriente de $"
							+ savingsaccount[accountnum]);
					System.out.println(
							"Seleccione una de estas opciones: $20(1)\t$40(2)\t$60(3)\t$80(4)\t$100(5)\tOtra cantidad(6)");
					ramcashchoice = input.nextInt();
					while (ramcashchoice < 1 || ramcashchoice > 6) {
						System.out.println(
								"Seleccione una de estas opciones de RamCash:\n$20(1)\t$40(2)\t$60(3)\t$80(4)\t$100(5)\tOtra cantidad(6)");
						ramcashchoice = input.nextInt();
					}
					boolean withdrawed = false;
					while (!withdrawed) {
						if (ramcashchoice == 1) {
							if (savingsaccount[accountnum] - 20 < 0) {
								ramcashchoice = 0;
								System.out.println(
										"Seleccione una cantidad que no exceda el saldo actual de su cuenta de ahorros de: $"
												+ savingsaccount[accountnum]);
								System.out.println(
										"Seleccione una de estas opciones de RamCash:\n$20(1)\t$40(2)\t$60(3)\t$80(4)\t$100(5)\tOtra cantidad(6)");
								ramcashchoice = input.nextInt();
								while (ramcashchoice < 1 || ramcashchoice > 6) {
									System.out.println(
											"Seleccione una de estas opciones de RamCash:\n$20(1)\t$40(2)\t$60(3)\t$80(4)\t$100(5)\tOtra cantidad(6)");
									ramcashchoice = input.nextInt();
								}
							} else {
								savingsaccount[accountnum] -= 20;
								System.out.println("Ahora retirando $20. El saldo de su cuenta de ahorros ahora es: $"
										+ savingsaccount[accountnum]);
								withdrawed = true;
							}
						}
						if (ramcashchoice == 2) {
							if (savingsaccount[accountnum] - 40 < 0) {
								ramcashchoice = 0;
								System.out.println(
										"Seleccione una cantidad que no exceda el saldo actual de su cuenta de ahorros de: $"
												+ savingsaccount[accountnum]);
								System.out.println(
										"Seleccione una de estas opciones de RamCash:\n$20(1)\t$40(2)\t$60(3)\t$80(4)\t$100(5)\tOtra cantidad(6)");
								ramcashchoice = input.nextInt();
								while (ramcashchoice < 1 || ramcashchoice > 6) {
									System.out.println(
											"Seleccione una de estas opciones de RamCash:\n$20(1)\t$40(2)\t$60(3)\t$80(4)\t$100(5)\tOtra cantidad(6)");
									ramcashchoice = input.nextInt();
								}
							} else {
								savingsaccount[accountnum] -= 40;
								System.out.println("Ahora retirando $40. El saldo de su cuenta de ahorros ahora es: $"
										+ savingsaccount[accountnum]);
								withdrawed = true;
							}
						}
						if (ramcashchoice == 3) {
							if (savingsaccount[accountnum] - 60 < 0) {
								ramcashchoice = 0;
								System.out.println(
										"Seleccione una cantidad que no exceda el saldo actual de su cuenta de ahorros de: $"
												+ savingsaccount[accountnum]);
								System.out.println(
										"Seleccione una de estas opciones de RamCash:\n$20(1)\t$40(2)\t$60(3)\t$80(4)\t$100(5)\tOtra cantidad(6)");
								ramcashchoice = input.nextInt();
								while (ramcashchoice < 1 || ramcashchoice > 6) {
									System.out.println(
											"Seleccione una de estas opciones de RamCash:\n$20(1)\t$40(2)\t$60(3)\t$80(4)\t$100(5)\tOtra cantidad(6)");
									ramcashchoice = input.nextInt();
								}
							} else {
								savingsaccount[accountnum] -= 60;
								System.out.println("Ahora retirando $60. El saldo de su cuenta de ahorros ahora es: $"
										+ savingsaccount[accountnum]);
								withdrawed = true;
							}
						}
						if (ramcashchoice == 4) {
							if (savingsaccount[accountnum] - 80 < 0) {
								ramcashchoice = 0;
								System.out.println(
										"Seleccione una cantidad que no exceda el saldo actual de su cuenta de ahorros de: $"
												+ savingsaccount[accountnum]);
								System.out.println(
										"Seleccione una de estas opciones de RamCash:\n$20(1)\t$40(2)\t$60(3)\t$80(4)\t$100(5)\tOtra cantidad(6)");
								ramcashchoice = input.nextInt();
								while (ramcashchoice < 1 || ramcashchoice > 6) {
									System.out.println(
											"Seleccione una de estas opciones de RamCash:\n$20(1)\t$40(2)\t$60(3)\t$80(4)\t$100(5)\tOtra cantidad(6)");
									ramcashchoice = input.nextInt();
								}
							} else {
								savingsaccount[accountnum] -= 80;
								System.out.println("Ahora retirando $80. El saldo de su cuenta de ahorros ahora es: $"
										+ savingsaccount[accountnum]);
								withdrawed = true;
							}
						}
						if (ramcashchoice == 5) {
							if (savingsaccount[accountnum] - 100 < 0) {
								ramcashchoice = 0;
								System.out.println(
										"Seleccione una cantidad que no exceda el saldo actual de su cuenta de ahorros de: $"
												+ savingsaccount[accountnum]);
								System.out.println(
										"Seleccione una de estas opciones de RamCash:\n$20(1)\t$40(2)\t$60(3)\t$80(4)\t$100(5)\tOtra cantidad(6)");
								ramcashchoice = input.nextInt();
								while (ramcashchoice < 1 || ramcashchoice > 6) {
									System.out.println(
											"Seleccione una de estas opciones de RamCash:\n$20(1)\t$40(2)\t$60(3)\t$80(4)\t$100(5)\tOtra cantidad(6)");
									ramcashchoice = input.nextInt();
								}
							} else {
								savingsaccount[accountnum] -= 100;
								System.out.println("Ahora retirando $100. El saldo de su cuenta de ahorros ahora es: $"
										+ savingsaccount[accountnum]);
								withdrawed = true;
							}
						}
						if (ramcashchoice == 6) {
							System.out.println("¿Cuánto te gustaría retirar?");
							withdrawalamount = input.nextInt();
							while (savingsaccount[accountnum] - withdrawalamount < 0 || withdrawalamount < 0) {
								System.out.println(
										"Seleccione una cantidad que no exceda el saldo actual de su cuenta de ahorros de: $"
												+ savingsaccount[accountnum]);
								withdrawalamount = input.nextInt();
							}

							savingsaccount[accountnum] -= withdrawalamount;
							System.out.println("Ahora retirando $" + withdrawalamount
									+ ". El saldo de su cuenta de ahorros ahora es: $" + savingsaccount[accountnum]);
							withdrawed = true;
						}
					}
				}

			}
		}
	}

	// Method for depositing money
	public void depositMoney(int language, int accountchoice, int numberofaccounts, int accountnum, int depositamount,
			int[] checkingsaccount, int[] savingsaccount) {
		Scanner input = new Scanner(System.in);
		if (language == 1) { // Selecting account
			System.out.println("You have selected 'Deposit'.");
			boolean correctSelection = confirmSeletion(language);
			if (correctSelection) {

				accountchoice = numberofaccounts;
				if (accountchoice == 3) {
					System.out.println(
							"The current balance of your checkings account is: $" + checkingsaccount[accountnum]);
					System.out
							.println("The current balance of your savings account is: $" + savingsaccount[accountnum]);
					System.out.println("Which account would you like to deposit to? Checking(1)\tSavings(2)");
					accountchoice = input.nextInt();
					while (accountchoice < 1 || accountchoice > 2) {
						System.out.println("Please select the one of these account options:\nCheckings(1)\tSavings(2)");
						accountchoice = input.nextInt();
					}
				}
				if (accountchoice == 1) // Depositing Checking ~ English
				{
					System.out.println("How much would you like to deposit into your checkings?");
					depositamount = input.nextInt();
					while (depositamount <= 0) {
						System.out.println("Please enter an positive amount.");
						depositamount = input.nextInt();
					}

					checkingsaccount[accountnum] += depositamount;
					System.out.println("Now depositing $" + depositamount + ". Your checkings account balance is now: $"
							+ checkingsaccount[accountnum]);
				} else // Depositing Savings ~ English
				{
					System.out.println("How much would you like to deposit into your savings?");
					depositamount = input.nextInt();
					while (depositamount <= 0) {
						System.out.println("Please enter an positive amount.");
						depositamount = input.nextInt();
					}

					savingsaccount[accountnum] += depositamount;
					System.out.println("Now depositing $" + depositamount + ". Your savings account balance is now: $"
							+ savingsaccount[accountnum]);
				}
			}
		} else { // Depositing ~ Spanish
			System.out.println("Ha seleccionado 'Depósito'.");
			boolean correctSelection = confirmSeletion(language);
			if (correctSelection) {
				accountchoice = numberofaccounts;
				if (accountchoice == 3) // If you have two accounts
				{
					System.out.println("El saldo actual de su cuenta corriente es: $" + checkingsaccount[accountnum]);
					System.out.println("El saldo actual de su cuenta de ahorros es: $" + savingsaccount[accountnum]);
					System.out.println("¿De qué cuenta le gustaría retirar dinero? Cuenta de cheques(1)\tAhorros(2)");
					accountchoice = input.nextInt();
					while (accountchoice < 1 || accountchoice > 2) {
						System.out.println("Seleccione una de estas opciones de cuenta:\nCheques(1)\tAhorros(2)");
						accountchoice = input.nextInt();
					}
				}
				if (accountchoice == 1) // Depositing Checking ~ Spanish
				{
					System.out.println("¿Cuánto le gustaría depositar en sus cheques?");
					depositamount = input.nextInt();
					while (depositamount <= 0) {
						System.out.println("Ingrese una cantidad positiva.");
						depositamount = input.nextInt();
					}

					checkingsaccount[accountnum] += depositamount;
					System.out.println("Ahora depositando $" + depositamount
							+ ". El saldo de su cuenta corriente ahora es: $" + checkingsaccount[accountnum]);
				} else // Depositing Savings ~ Spanish
				{
					System.out.println("¿Cuánto te gustaría depositar en tus ahorros?");
					depositamount = input.nextInt();
					while (depositamount <= 0) {
						System.out.println("Ingrese una cantidad positiva.");
						depositamount = input.nextInt();
					}

					savingsaccount[accountnum] += depositamount;
					System.out.println("Ahora depositando $" + depositamount
							+ ". El saldo de su cuenta de ahorros ahora es: $" + savingsaccount[accountnum]);
				}
			}
		}
	}

	// Method for transferring money
	public void transferMoney(int language, int accountchoice, int numberofaccounts, int accountnum, int transferamount,
			int[] checkingsaccount, int[] savingsaccount) {
		Scanner input = new Scanner(System.in);
		if (language == 1) {
			if (numberofaccounts == 1 || numberofaccounts == 2) {
				System.out.println("You only have one account. You can not transfer money.");
			} else {
				System.out.println("You have selected 'Transfer Money'.");
				boolean correctSelection = confirmSeletion(language);
				if (correctSelection) {
					System.out.println("Which account would you like to transfer from?\nChecking(1)\tSavings(2)");

					accountchoice = input.nextInt();
					while (accountchoice < 1 || accountchoice > 2) {
						System.out.println("Please select the one of these account options:\nCheckings(1)\tSavings(2)");
						accountchoice = input.nextInt();
					}
					if (accountchoice == 1) {
						System.out.println(
								"You have chosen to transfer from your checkings account to your savings account.\nYour current balance from your checkings account is: $"
										+ checkingsaccount[accountnum]);
						System.out.println("How much money would you like to transfer to your savings account?");
						transferamount = input.nextInt();
						while (transferamount <= 0 || transferamount > checkingsaccount[accountnum]) {
							System.out.println("Please enter an amount that falls within your account balance of $"
									+ checkingsaccount[accountnum]);
							transferamount = input.nextInt();
						}
						System.out.println("Now transferring $" + transferamount + " to your savings account.");
						checkingsaccount[accountnum] -= transferamount;
						savingsaccount[accountnum] += transferamount;
						System.out.println("Your new balance for your checkings account is: $"
								+ checkingsaccount[accountnum] + "\nYour new balance for your savings account is: $"
								+ savingsaccount[accountnum]);
					} else {
						System.out.println(
								"You have chosen to transfer from your savings account to your checkings account.\nYour current balance from your savings account is: $"
										+ savingsaccount[accountnum]);
						System.out.println("How much money would you like to transfer to your checkings account?");
						transferamount = input.nextInt();
						while (transferamount <= 0 || transferamount > savingsaccount[accountnum]) {
							System.out.println("Please enter an amount that falls within your account balance of $"
									+ savingsaccount[accountnum]);
							transferamount = input.nextInt();
						}
						System.out.println("Now transfering $" + transferamount + " to your checkings account.");
						checkingsaccount[accountnum] += transferamount;
						savingsaccount[accountnum] -= transferamount;
						System.out.println("Your new balance for your checkings account is: $"
								+ checkingsaccount[accountnum] + "\nYour new balance for your savings account is: $"
								+ savingsaccount[accountnum]);
					}
				}
			}
		} else {
			if (numberofaccounts == 1 || numberofaccounts == 2) {
				System.out.println("Solo tienes una cuenta. No puedes transferir dinero.");
			} else {
				System.out.println("Ha seleccionado 'Transferir Dinero'.");
				boolean correctSelection = confirmSeletion(language);
				if (correctSelection) {
					System.out.println("¿Desde qué cuenta desea transferir?\nCuenta Corriente(1)\tAhorros(2)");
					accountchoice = input.nextInt();
					while (accountchoice < 1 || accountchoice > 2) {
						System.out.println("Seleccione una de estas opciones de cuenta:\nCheques(1)\tAhorros(2)");
						accountchoice = input.nextInt();
					}
					if (accountchoice == 1) {
						System.out.println(
								"Ha elegido transferir de su cuenta de cheques a su cuenta de ahorros.\nEl saldo actual de su cuenta de cheques es: $"
										+ checkingsaccount[accountnum]);
						System.out.println("¿Cuánto dinero le gustaría transferir a su cuenta de ahorros?");
						while (transferamount <= 0 || transferamount > checkingsaccount[accountnum]) {
							System.out
									.println("Ingrese una cantidad que se encuentre dentro del saldo de su cuenta de $"
											+ checkingsaccount[accountnum]);
							transferamount = input.nextInt();
						}
						System.out.println("Ahora transfiriendo $" + transferamount + " a su cuenta de ahorros.");
						checkingsaccount[accountnum] -= transferamount;
						savingsaccount[accountnum] += transferamount;
						System.out.println("El nuevo saldo de su cuenta corriente es: $" + checkingsaccount[accountnum]
								+ "\nEl nuevo saldo de su cuenta de ahorros es: $" + savingsaccount[accountnum]);
					} else {
						System.out.println(
								"Ha elegido transferir de su cuenta de ahorros a su cuenta de cheques.\nEl saldo actual de su cuenta de ahorros es: $"
										+ savingsaccount[accountnum]);
						System.out.println("¿Cuánto dinero le gustaría transferir a su cuenta corriente?");
						while (transferamount <= 0 || transferamount > savingsaccount[accountnum]) {
							System.out
									.println("Ingrese una cantidad que se encuentre dentro del saldo de su cuenta de $"
											+ savingsaccount[accountnum]);
							transferamount = input.nextInt();
						}
						System.out.println("Ahora transfiriendo $" + transferamount + " a su cuenta de cheques.");
						checkingsaccount[accountnum] += transferamount;
						savingsaccount[accountnum] -= transferamount;
						System.out.println("El nuevo saldo de su cuenta corriente es: $" + checkingsaccount[accountnum]
								+ "\nEl nuevo saldo de su cuenta de ahorros es: $" + savingsaccount[accountnum]);
					}
				}
			}
		}
	}

	// Method for making payments
	public void makePayment(int language, int companies, int accountchoice, int numberofaccounts, int billchoice,
			int paymentamount, int accountnum, int[] checkingsaccount, int[] savingsaccount, int[] dukeenergy,
			int[] verizon, int[] spectrum) {
		Scanner input = new Scanner(System.in);
		if (language == 1) // Making Payments ~ English
		{
			System.out.println("You have selected 'Make Payment'");
			boolean correctSelection = confirmSeletion(language);
			if (correctSelection) {
				if (companies == 0) // No companies
				{
					System.out.println("You do not have any companies set up to make a payment to.");
				} else if (companies == 1) // Only Duke
				{
					System.out.println("Your bill for Duke Energy is: $" + dukeenergy[accountnum]);
					if (dukeenergy[accountnum] == 0) // Bill amount due is 0
					{
						System.out.println("You do not owe anything.");
					} else if (numberofaccounts == 1) // Duke Bill w/checkings
					{
						System.out.println(
								"The current balance of your checkings account is: $" + checkingsaccount[accountnum]);
						paymentamount = dukeCheckings(language, checkingsaccount, accountnum, dukeenergy);
						checkingsaccount[accountnum] -= dukeenergy[accountnum];
					} else if (numberofaccounts == 2) // Duke Bill w/savings only
					{
						System.out.println(
								"The current balance of your savings account is: $" + savingsaccount[accountnum]);
						paymentamount = dukeSavings(language, savingsaccount, accountnum, dukeenergy);
						savingsaccount[accountnum] -= dukeenergy[accountnum];
					} else // Duke Bill w/Both
					{
						System.out.println(
								"The current balance of your checkings account is: $" + checkingsaccount[accountnum]);
						System.out.println(
								"The current balance of your savings account is: $" + savingsaccount[accountnum]);
						System.out.println("Which account would you like to pay from? Checking(1)\tSavings(2)");
						accountchoice = input.nextInt();
						while (accountchoice < 1 || accountchoice > 2) {
							System.out.println(
									"Please select the one of these account options:\nCheckings(1)\tSavings(2)");
							accountchoice = input.nextInt();
						}
						if (accountchoice == 1) // Duke Bill w/checking
						{
							paymentamount = dukeCheckings(language, checkingsaccount, accountnum, dukeenergy);
							checkingsaccount[accountnum] -= dukeenergy[accountnum];
						} else {
							paymentamount = dukeSavings(language, savingsaccount, accountnum, dukeenergy);
							savingsaccount[accountnum] -= dukeenergy[accountnum];
						}
					}
				}

				else if (companies == 2) // Only Verizon
				{
					System.out.println("Your bill for Verizon is: $ " + verizon[accountnum]);
					if (verizon[accountnum] == 0) // Bill amount due is 0
					{
						System.out.println("You do not owe anything.");
					} else if (numberofaccounts == 1) // Verizon Bill w/checkings
					{
						System.out.println(
								"The current balance of your checkings account is: $" + checkingsaccount[accountnum]);
						paymentamount = verizonCheckings(language, checkingsaccount, accountnum, verizon);
						checkingsaccount[accountnum] -= verizon[accountnum];
					} else if (numberofaccounts == 2) // Verizon Bill w/savings only
					{
						System.out.println(
								"The current balance of your savings account is: $" + savingsaccount[accountnum]);
						paymentamount = verizonSavings(language, savingsaccount, accountnum, verizon);
						savingsaccount[accountnum] -= verizon[accountnum];
					} else // Verizon Bill w/Both
					{
						System.out.println(
								"The current balance of your checkings account is: $" + checkingsaccount[accountnum]);
						System.out.println(
								"The current balance of your savings account is: $" + savingsaccount[accountnum]);
						System.out.println("Which account would you like to pay from? Checking(1)\tSavings(2)");
						accountchoice = input.nextInt();
						while (accountchoice < 1 || accountchoice > 2) {
							System.out.println(
									"Please select the one of these account options:\nCheckings(1)\tSavings(2)");
							accountchoice = input.nextInt();
						}
						if (accountchoice == 1) // Verizon Bill w/checking
						{
							paymentamount = verizonCheckings(language, checkingsaccount, accountnum, verizon);
							checkingsaccount[accountnum] -= verizon[accountnum];
						} else {
							paymentamount = verizonSavings(language, savingsaccount, accountnum, verizon);
							savingsaccount[accountnum] -= verizon[accountnum];
						}
					}
				} else if (companies == 3) // Only Spectrum
				{
					System.out.println("Your bill for Spectrum is: $ " + spectrum[accountnum]);
					if (spectrum[accountnum] == 0) // Bill amount due is 0
					{
						System.out.println("You do not owe anything.");
					} else if (numberofaccounts == 1) // Spectrum Bill w/checkings
					{
						System.out.println(
								"The current balance of your checkings account is: $" + checkingsaccount[accountnum]);
						paymentamount = spectrumCheckings(language, checkingsaccount, accountnum, spectrum);
						checkingsaccount[accountnum] -= spectrum[accountnum];
					} else if (numberofaccounts == 2) // Spectrum Bill w/savings only
					{
						System.out.println(
								"The current balance of your savings account is: $" + savingsaccount[accountnum]);
						paymentamount = spectrumSavings(language, savingsaccount, accountnum, spectrum);
						savingsaccount[accountnum] -= spectrum[accountnum];
					} else // Spectrum Bill w/Both
					{
						System.out.println(
								"The current balance of your checkings account is: $" + checkingsaccount[accountnum]);
						System.out.println(
								"The current balance of your savings account is: $" + savingsaccount[accountnum]);
						System.out.println("Which account would you like to pay from? Checking(1)\tSavings(2)");
						accountchoice = input.nextInt();
						while (accountchoice < 1 || accountchoice > 2) {
							System.out.println(
									"Please select the one of these account options:\nCheckings(1)\tSavings(2)");
							accountchoice = input.nextInt();
						}
						if (accountchoice == 1) // Spectrum Bill w/checking
						{
							paymentamount = spectrumSavings(language, checkingsaccount, accountnum, spectrum);
							checkingsaccount[accountnum] -= spectrum[accountnum];
						} else {
							paymentamount = spectrumSavings(language, savingsaccount, accountnum, spectrum);
							savingsaccount[accountnum] -= spectrum[accountnum];
						}
					}
				} else if (companies == 4) // Duke and Verizon
				{
					System.out.println(
							"You have two bills for Duke and Verizon. Which one would you like to pay? Duke(1) Verizon(2)");
					billchoice = input.nextInt();
					while (billchoice < 1 || billchoice > 2) {
						System.out.println("Please select the one of these billing options:\nDuke(1)\tVerizon(2)");
						billchoice = input.nextInt();
					}
					if (billchoice == 1) {
						System.out.println("Your bill for Duke Energy is: $" + dukeenergy[accountnum]);
						if (dukeenergy[accountnum] == 0) // Bill amount due is 0
						{
							System.out.println("You do not owe anything.");
						} else if (numberofaccounts == 1) // Duke Bill w/checkings
						{
							System.out.println("The current balance of your checkings account is: $"
									+ checkingsaccount[accountnum]);
							paymentamount = dukeCheckings(language, checkingsaccount, accountnum, dukeenergy);
							checkingsaccount[accountnum] -= dukeenergy[accountnum];
						} else if (numberofaccounts == 2) // Duke Bill w/savings only
						{
							System.out.println(
									"The current balance of your savings account is: $" + savingsaccount[accountnum]);
							paymentamount = dukeSavings(language, savingsaccount, accountnum, dukeenergy);
							savingsaccount[accountnum] -= dukeenergy[accountnum];
						} else // Duke Bill w/Both
						{
							System.out.println("The current balance of your checkings account is: $"
									+ checkingsaccount[accountnum]);
							System.out.println(
									"The current balance of your savings account is: $" + savingsaccount[accountnum]);
							System.out
									.println("Which account would you like to pay from? Checking(1)\tSavings(2)");
							accountchoice = input.nextInt();
							while (accountchoice < 1 || accountchoice > 2) {
								System.out.println(
										"Please select the one of these account options:\nCheckings(1)\tSavings(2)");
								accountchoice = input.nextInt();
							}
							if (accountchoice == 1) // Duke Bill w/checking
							{
								paymentamount = dukeCheckings(language, checkingsaccount, accountnum, dukeenergy);
								checkingsaccount[accountnum] -= dukeenergy[accountnum];
							} else {
								paymentamount = dukeSavings(language, savingsaccount, accountnum, dukeenergy);
								savingsaccount[accountnum] -= dukeenergy[accountnum];
							}
						}
					} else {
						System.out.println("Your bill for Verizon is: $ " + verizon[accountnum]);
						if (verizon[accountnum] == 0) // Bill amount due is 0
						{
							System.out.println("You do not owe anything.");
						} else if (numberofaccounts == 1) // Verizon Bill w/checkings
						{
							System.out.println("The current balance of your checkings account is: $"
									+ checkingsaccount[accountnum]);
							paymentamount = verizonCheckings(language, checkingsaccount, accountnum, verizon);
							checkingsaccount[accountnum] -= verizon[accountnum];
						} else if (numberofaccounts == 2) // Verizon Bill w/savings only
						{
							System.out.println(
									"The current balance of your savings account is: $" + savingsaccount[accountnum]);
							paymentamount = verizonSavings(language, savingsaccount, accountnum, verizon);
							savingsaccount[accountnum] -= verizon[accountnum];
						} else // Verizon Bill w/Both
						{
							System.out.println("The current balance of your checkings account is: $"
									+ checkingsaccount[accountnum]);
							System.out.println(
									"The current balance of your savings account is: $" + savingsaccount[accountnum]);
							System.out
									.println("Which account would you like to pay from? Checking(1)\tSavings(2)");
							accountchoice = input.nextInt();
							while (accountchoice < 1 || accountchoice > 2) {
								System.out.println(
										"Please select the one of these account options:\nCheckings(1)\tSavings(2)");
								accountchoice = input.nextInt();
							}
							if (accountchoice == 1) // Verizon Bill w/checking
							{
								paymentamount = verizonCheckings(language, checkingsaccount, accountnum, verizon);
								checkingsaccount[accountnum] -= verizon[accountnum];
							} else {
								paymentamount = verizonSavings(language, savingsaccount, accountnum, verizon);
								savingsaccount[accountnum] -= verizon[accountnum];
							}
						}
					}

				} else if (companies == 5) // Duke + Spectrum
				{

					System.out.println(
							"You have two bills for Duke and Spectrum. Which one would you like to pay? Duke(1) Spectrum(2)");
					billchoice = input.nextInt();
					while (billchoice < 1 || billchoice > 2) {
						System.out.println("Please select the one of these billing options:\nDuke(1)\tSpectrum(2)");
						billchoice = input.nextInt();
					}
					if (billchoice == 1) {
						System.out.println("Your bill for Duke Energy is: $" + dukeenergy[accountnum]);
						if (dukeenergy[accountnum] == 0) // Bill amount due is 0
						{
							System.out.println("You do not owe anything.");
						} else if (numberofaccounts == 1) // Duke Bill w/checkings
						{
							System.out.println("The current balance of your checkings account is: $"
									+ checkingsaccount[accountnum]);
							paymentamount = dukeCheckings(language, checkingsaccount, accountnum, dukeenergy);
							checkingsaccount[accountnum] -= dukeenergy[accountnum];
						} else if (numberofaccounts == 2) // Duke Bill w/savings only
						{
							System.out.println(
									"The current balance of your savings account is: $" + savingsaccount[accountnum]);
							paymentamount = dukeSavings(language, savingsaccount, accountnum, dukeenergy);
							savingsaccount[accountnum] -= dukeenergy[accountnum];
						} else // Duke Bill w/Both
						{
							System.out.println("The current balance of your checkings account is: $"
									+ checkingsaccount[accountnum]);
							System.out.println(
									"The current balance of your savings account is: $" + savingsaccount[accountnum]);
							System.out
									.println("Which account would you like to pay from? Checking(1)\tSavings(2)");
							accountchoice = input.nextInt();
							while (accountchoice < 1 || accountchoice > 2) {
								System.out.println(
										"Please select the one of these account options:\nCheckings(1)\tSavings(2)");
								accountchoice = input.nextInt();
							}
							if (accountchoice == 1) // Duke Bill w/checking
							{
								paymentamount = dukeCheckings(language, checkingsaccount, accountnum, dukeenergy);
								checkingsaccount[accountnum] -= dukeenergy[accountnum];
							} else {
								paymentamount = dukeSavings(language, savingsaccount, accountnum, dukeenergy);
								savingsaccount[accountnum] -= dukeenergy[accountnum];
							}
						}
					} else // Spectrum
					{
						System.out.println("Your bill for Spectrum is: $ " + spectrum[accountnum]);
						if (spectrum[accountnum] == 0) // Bill amount due is 0
						{
							System.out.println("You do not owe anything.");
						} else if (numberofaccounts == 1) // Spectrum Bill w/checkings
						{
							System.out.println("The current balance of your checkings account is: $"
									+ checkingsaccount[accountnum]);
							paymentamount = spectrumCheckings(language, checkingsaccount, accountnum, spectrum);
							checkingsaccount[accountnum] -= spectrum[accountnum];
						} else if (numberofaccounts == 2) // Spectrum Bill w/savings only
						{
							System.out.println(
									"The current balance of your savings account is: $" + savingsaccount[accountnum]);
							paymentamount = spectrumSavings(language, savingsaccount, accountnum, spectrum);
							savingsaccount[accountnum] -= spectrum[accountnum];
						} else // Spectrum Bill w/Both
						{
							System.out.println("The current balance of your checkings account is: $"
									+ checkingsaccount[accountnum]);
							System.out.println(
									"The current balance of your savings account is: $" + savingsaccount[accountnum]);
							System.out
									.println("Which account would you like to pay from? Checking(1)\tSavings(2)");
							accountchoice = input.nextInt();
							while (accountchoice < 1 || accountchoice > 2) {
								System.out.println(
										"Please select the one of these account options:\nCheckings(1)\tSavings(2)");
								accountchoice = input.nextInt();
							}
							if (accountchoice == 1) // Spectrum Bill w/checking
							{
								paymentamount = spectrumCheckings(language, checkingsaccount, accountnum, spectrum);
								checkingsaccount[accountnum] -= spectrum[accountnum];
							} else {
								paymentamount = spectrumSavings(language, savingsaccount, accountnum, spectrum);
								savingsaccount[accountnum] -= spectrum[accountnum];
							}
						}
					}

				} else if (companies == 6) // Verizon + Spectrum
				{

					System.out.println(
							"You have two bills for Verizon and Spectrum. Which one would you like to pay? Verizon(1) Spectrum(2)");
					billchoice = input.nextInt();
					while (billchoice < 1 || billchoice > 2) {
						System.out.println("Please select the one of these billing options:\nVerizon(1)\tSpectrum(2)");
						billchoice = input.nextInt();
					}
					if (billchoice == 1) // Verizon
					{
						System.out.println("Your bill for Verizon is: $ " + verizon[accountnum]);
						if (verizon[accountnum] == 0) // Bill amount due is 0
						{
							System.out.println("You do not owe anything.");
						} else if (numberofaccounts == 1) // Verizon Bill w/checkings
						{
							System.out.println("The current balance of your checkings account is: $"
									+ checkingsaccount[accountnum]);
							paymentamount = verizonCheckings(language, checkingsaccount, accountnum, verizon);
							checkingsaccount[accountnum] -= verizon[accountnum];
						} else if (numberofaccounts == 2) // Verizon Bill w/savings only
						{
							System.out.println(
									"The current balance of your savings account is: $" + savingsaccount[accountnum]);
							paymentamount = verizonSavings(language, savingsaccount, accountnum, verizon);
							savingsaccount[accountnum] -= verizon[accountnum];
						} else // Verizon Bill w/Both
						{
							System.out.println("The current balance of your checkings account is: $"
									+ checkingsaccount[accountnum]);
							System.out.println(
									"The current balance of your savings account is: $" + savingsaccount[accountnum]);
							System.out
									.println("Which account would you like to pay from? Checking(1)\tSavings(2)");
							accountchoice = input.nextInt();
							while (accountchoice < 1 || accountchoice > 2) {
								System.out.println(
										"Please select the one of these account options:\nCheckings(1)\tSavings(2)");
								accountchoice = input.nextInt();
							}
							if (accountchoice == 1) // Verizon Bill w/checking
							{
								paymentamount = verizonCheckings(language, checkingsaccount, accountnum, verizon);
								checkingsaccount[accountnum] -= verizon[accountnum];
							} else {
								paymentamount = verizonSavings(language, savingsaccount, accountnum, verizon);
								savingsaccount[accountnum] -= verizon[accountnum];
							}
						}
					} else // Spectrum
					{
						System.out.println("Your bill for Spectrum is: $ " + spectrum[accountnum]);
						if (spectrum[accountnum] == 0) // Bill amount due is 0
						{
							System.out.println("You do not owe anything.");
						} else if (numberofaccounts == 1) // Spectrum Bill w/checkings
						{
							System.out.println("The current balance of your checkings account is: $"
									+ checkingsaccount[accountnum]);
							paymentamount = spectrumCheckings(language, checkingsaccount, accountnum, spectrum);
							checkingsaccount[accountnum] -= spectrum[accountnum];
						} else if (numberofaccounts == 2) // Spectrum Bill w/savings only
						{
							System.out.println(
									"The current balance of your savings account is: $" + savingsaccount[accountnum]);
							paymentamount = spectrumSavings(language, savingsaccount, accountnum, spectrum);
							savingsaccount[accountnum] -= spectrum[accountnum];
						} else // Spectrum Bill w/Both
						{
							System.out.println("The current balance of your checkings account is: $"
									+ checkingsaccount[accountnum]);
							System.out.println(
									"The current balance of your savings account is: $" + savingsaccount[accountnum]);
							System.out
									.println("Which account would you like to pay from? Checking(1)\tSavings(2)");
							accountchoice = input.nextInt();
							while (accountchoice < 1 || accountchoice > 2) {
								System.out.println(
										"Please select the one of these account options:\nCheckings(1)\tSavings(2)");
								accountchoice = input.nextInt();
							}
							if (accountchoice == 1) // Spectrum Bill w/checking
							{
								paymentamount = spectrumCheckings(language, checkingsaccount, accountnum, spectrum);
								checkingsaccount[accountnum] -= spectrum[accountnum];
							} else {
								paymentamount = spectrumSavings(language, savingsaccount, accountnum, spectrum);
								savingsaccount[accountnum] -= spectrum[accountnum];
							}
						}
					}

				} else // Duke + Verizon + Spectrum
				{

					System.out.println(
							"You have three bills for Duke, Verizon, and Spectrum. Which one would you like to pay?\tDuke(1)\tVerizon(2)\tSpectrum(3)");
					billchoice = input.nextInt();
					while (billchoice < 1 || billchoice > 3) {
						System.out.println(
								"Please select the one of these billing options:\nDuke(1)\tVerizon(2)\tSpectrum(3)");
						billchoice = input.nextInt();
					}
					if (billchoice == 1) // Duke
					{
						System.out.println("Your bill for Duke Energy is: $" + dukeenergy[accountnum]);
						if (dukeenergy[accountnum] == 0) // Bill amount due is 0
						{
							System.out.println("You do not owe anything.");
						} else if (numberofaccounts == 1) // Duke Bill w/checkings
						{
							System.out.println("The current balance of your checkings account is: $"
									+ checkingsaccount[accountnum]);
							paymentamount = dukeCheckings(language, checkingsaccount, accountnum, dukeenergy);
							checkingsaccount[accountnum] -= dukeenergy[accountnum];
						} else if (numberofaccounts == 2) // Duke Bill w/savings only
						{
							System.out.println(
									"The current balance of your savings account is: $" + savingsaccount[accountnum]);
							paymentamount = dukeSavings(language, savingsaccount, accountnum, dukeenergy);
							savingsaccount[accountnum] -= dukeenergy[accountnum];
						} else // Duke Bill w/Both
						{
							System.out.println("The current balance of your checkings account is: $"
									+ checkingsaccount[accountnum]);
							System.out.println(
									"The current balance of your savings account is: $" + savingsaccount[accountnum]);
							System.out
									.println("Which account would you like to pay from? Checking(1)\tSavings(2)");
							accountchoice = input.nextInt();
							while (accountchoice < 1 || accountchoice > 2) {
								System.out.println(
										"Please select the one of these account options:\nCheckings(1)\tSavings(2)");
								accountchoice = input.nextInt();
							}
							if (accountchoice == 1) // Duke Bill w/checking
							{
								paymentamount = dukeCheckings(language, checkingsaccount, accountnum, dukeenergy);
								checkingsaccount[accountnum] -= dukeenergy[accountnum];
							} else {
								paymentamount = dukeSavings(language, savingsaccount, accountnum, dukeenergy);
								savingsaccount[accountnum] -= dukeenergy[accountnum];
							}
						}
					} else if (billchoice == 2) // Verizon
					{
						System.out.println("Your bill for Verizon is: $ " + verizon[accountnum]);
						if (verizon[accountnum] == 0) // Bill amount due is 0
						{
							System.out.println("You do not owe anything.");
						} else if (numberofaccounts == 1) // Verizon Bill w/checkings
						{
							System.out.println("The current balance of your checkings account is: $"
									+ checkingsaccount[accountnum]);
							paymentamount = verizonCheckings(language, checkingsaccount, accountnum, verizon);
							checkingsaccount[accountnum] -= verizon[accountnum];
						} else if (numberofaccounts == 2) // Verizon Bill w/savings only
						{
							System.out.println(
									"The current balance of your savings account is: $" + savingsaccount[accountnum]);
							paymentamount = verizonSavings(language, savingsaccount, accountnum, verizon);
							savingsaccount[accountnum] -= verizon[accountnum];
						} else // Verizon Bill w/Both
						{
							System.out.println("The current balance of your checkings account is: $"
									+ checkingsaccount[accountnum]);
							System.out.println(
									"The current balance of your savings account is: $" + savingsaccount[accountnum]);
							System.out
									.println("Which account would you like to pay from? Checking(1)\tSavings(2)");
							accountchoice = input.nextInt();
							while (accountchoice < 1 || accountchoice > 2) {
								System.out.println(
										"Please select the one of these account options:\nCheckings(1)\tSavings(2)");
								accountchoice = input.nextInt();
							}
							if (accountchoice == 1) // Verizon Bill w/checking
							{
								paymentamount = verizonCheckings(language, checkingsaccount, accountnum, verizon);
								checkingsaccount[accountnum] -= verizon[accountnum];
							} else {
								paymentamount = verizonSavings(language, savingsaccount, accountnum, verizon);
								savingsaccount[accountnum] -= verizon[accountnum];
							}
						}
					} else // Spectrum
					{
						System.out.println("Your bill for Spectrum is: $ " + spectrum[accountnum]);
						if (spectrum[accountnum] == 0) // Bill amount due is 0
						{
							System.out.println("You do not owe anything.");
						} else if (numberofaccounts == 1) // Spectrum Bill w/checkings
						{
							System.out.println("The current balance of your checkings account is: $"
									+ checkingsaccount[accountnum]);
							paymentamount = spectrumCheckings(language, checkingsaccount, accountnum, spectrum);
							checkingsaccount[accountnum] -= spectrum[accountnum];
						} else if (numberofaccounts == 2) // Spectrum Bill w/savings only
						{
							System.out.println(
									"The current balance of your savings account is: $" + savingsaccount[accountnum]);
							paymentamount = spectrumSavings(language, savingsaccount, accountnum, spectrum);
							savingsaccount[accountnum] -= spectrum[accountnum];
						} else // Spectrum Bill w/Both
						{
							System.out.println("The current balance of your checkings account is: $"
									+ checkingsaccount[accountnum]);
							System.out.println(
									"The current balance of your savings account is: $" + savingsaccount[accountnum]);
							System.out
									.println("Which account would you like to pay from? Checking(1)\tSavings(2)");
							accountchoice = input.nextInt();
							while (accountchoice < 1 || accountchoice > 2) {
								System.out.println(
										"Please select the one of these account options:\nCheckings(1)\tSavings(2)");
								accountchoice = input.nextInt();
							}
							if (accountchoice == 1) // Spectrum Bill w/checking
							{
								paymentamount = spectrumCheckings(language, checkingsaccount, accountnum, spectrum);
								checkingsaccount[accountnum] -= spectrum[accountnum];
							} else {
								paymentamount = spectrumSavings(language, savingsaccount, accountnum, spectrum);
								savingsaccount[accountnum] -= spectrum[accountnum];
							}
						}
					}

				}
			}
		} else // Making Payments ~ Spanish
		{
			System.out.println("Ha seleccionado 'Realizar pago'");
			boolean correctSelection = confirmSeletion(language);
			if (correctSelection) {
				if (companies == 0) // No companies
				{
					System.out.println("No tiene ninguna empresa establecida para realizar un pago.");
				} else if (companies == 1) // Only Duke
				{
					System.out.println("Su factura de Duke Energy es: $" + dukeenergy[accountnum]);
					if (dukeenergy[accountnum] == 0) // Bill amount due is 0
					{
						System.out.println("No debes nada.");
					} else if (numberofaccounts == 1) // Duke Bill w/checkings
					{
						System.out
								.println("El saldo actual de su cuenta corriente es: $" + checkingsaccount[accountnum]);
						paymentamount = dukeCheckings(language, checkingsaccount, accountnum, dukeenergy);
						checkingsaccount[accountnum] -= dukeenergy[accountnum];
					} else if (numberofaccounts == 2) // Duke Bill w/savings only
					{
						System.out
								.println("El saldo actual de su cuenta de ahorros es: $" + savingsaccount[accountnum]);
						paymentamount = dukeSavings(language, savingsaccount, accountnum, dukeenergy);
						savingsaccount[accountnum] -= dukeenergy[accountnum];
					} else // Duke Bill w/Both
					{
						System.out
								.println("El saldo actual de su cuenta corriente es: $" + checkingsaccount[accountnum]);
						System.out
								.println("El saldo actual de su cuenta de ahorros es: $" + savingsaccount[accountnum]);
						System.out.println(
								"¿De qué cuenta le gustaría retirar dinero?\tCuenta de Cheques(1)\tAhorros(2)");
						accountchoice = input.nextInt();
						while (accountchoice < 1 || accountchoice > 2) {
							System.out.println("Seleccione una de estas opciones de cuenta:\nCheques(1)\tAhorros(2)");
							accountchoice = input.nextInt();
						}
						if (accountchoice == 1) // Duke Bill w/checking
						{
							paymentamount = dukeCheckings(language, checkingsaccount, accountnum, dukeenergy);
							checkingsaccount[accountnum] -= dukeenergy[accountnum];
						} else {
							paymentamount = dukeSavings(language, savingsaccount, accountnum, dukeenergy);
							savingsaccount[accountnum] -= dukeenergy[accountnum];
						}
					}
				}

				else if (companies == 2) // Only Verizon
				{
					System.out.println("Tu factura de Verizon es: $ " + verizon[accountnum]);
					if (verizon[accountnum] == 0) // Bill amount due is 0
					{
						System.out.println("No debes nada.");
					} else if (numberofaccounts == 1) // Verizon Bill w/checkings
					{
						System.out
								.println("El saldo actual de su cuenta corriente es: $" + checkingsaccount[accountnum]);
						paymentamount = verizonCheckings(language, checkingsaccount, accountnum, verizon);
						checkingsaccount[accountnum] -= verizon[accountnum];
					} else if (numberofaccounts == 2) // Verizon Bill w/savings only
					{
						System.out
								.println("El saldo actual de su cuenta de ahorros es: $" + savingsaccount[accountnum]);
						paymentamount = verizonSavings(language, savingsaccount, accountnum, verizon);
						savingsaccount[accountnum] -= verizon[accountnum];
					} else // Verizon Bill w/Both
					{
						System.out
								.println("El saldo actual de su cuenta corriente es: $" + checkingsaccount[accountnum]);
						System.out
								.println("El saldo actual de su cuenta de ahorros es: $" + savingsaccount[accountnum]);
						System.out.println(
								"¿De qué cuenta le gustaría retirar dinero?\tCuenta de Cheques(1)\tAhorros(2)");
						accountchoice = input.nextInt();
						while (accountchoice < 1 || accountchoice > 2) {
							System.out.println("Seleccione una de estas opciones de cuenta:\nCheques(1)\tAhorros(2)");
							accountchoice = input.nextInt();
						}
						if (accountchoice == 1) // Verizon Bill w/checking
						{
							paymentamount = verizonCheckings(language, checkingsaccount, accountnum, verizon);
							checkingsaccount[accountnum] -= verizon[accountnum];
						} else {
							paymentamount = verizonSavings(language, savingsaccount, accountnum, verizon);
							savingsaccount[accountnum] -= verizon[accountnum];
						}
					}
				} else if (companies == 3) // Only Spectrum
				{
					System.out.println("Su factura de Spectrum es: $ " + spectrum[accountnum]);
					if (spectrum[accountnum] == 0) // Bill amount due is 0
					{
						System.out.println("No debes nada.");
					} else if (numberofaccounts == 1) // Spectrum Bill w/checkings
					{
						System.out
								.println("El saldo actual de su cuenta corriente es: $" + checkingsaccount[accountnum]);
						paymentamount = spectrumCheckings(language, checkingsaccount, accountnum, spectrum);
						checkingsaccount[accountnum] -= spectrum[accountnum];
					} else if (numberofaccounts == 2) // Spectrum Bill w/savings only
					{
						System.out
								.println("El saldo actual de su cuenta de ahorros es: $" + savingsaccount[accountnum]);
						paymentamount = spectrumSavings(language, savingsaccount, accountnum, spectrum);
						savingsaccount[accountnum] -= spectrum[accountnum];
					} else // Spectrum Bill w/Both
					{
						System.out
								.println("El saldo actual de su cuenta corriente es: $" + checkingsaccount[accountnum]);
						System.out
								.println("El saldo actual de su cuenta de ahorros es: $" + savingsaccount[accountnum]);
						System.out.println(
								"¿De qué cuenta le gustaría retirar dinero?\tCuenta de Cheques(1)\tAhorros(2)");
						accountchoice = input.nextInt();
						while (accountchoice < 1 || accountchoice > 2) {
							System.out.println("Seleccione una de estas opciones de cuenta:\nCheques(1)\tAhorros(2)");
							accountchoice = input.nextInt();
						}
						if (accountchoice == 1) // Spectrum Bill w/checking
						{
							paymentamount = spectrumSavings(language, checkingsaccount, accountnum, spectrum);
							checkingsaccount[accountnum] -= spectrum[accountnum];
						} else {
							paymentamount = spectrumSavings(language, savingsaccount, accountnum, spectrum);
							savingsaccount[accountnum] -= spectrum[accountnum];
						}
					}
				} else if (companies == 4) // Duke and Verizon
				{
					System.out.println(
							"Tienes dos facturas de Duke y Verizon. ¿Cuál te gustaría pagar?\tDuke(1)\tVerizon(2)");
					billchoice = input.nextInt();
					while (billchoice < 1 || billchoice > 2) {
						System.out.println("Seleccione una de estas opciones de facturación:\nDuke(1)\tVerizon(2)");
						billchoice = input.nextInt();
					}
					if (billchoice == 1) {
						System.out.println("Su factura de Duke Energy es: $" + dukeenergy[accountnum]);
						if (dukeenergy[accountnum] == 0) // Bill amount due is 0
						{
							System.out.println("No debes nada.");
						} else if (numberofaccounts == 1) // Duke Bill w/checkings
						{
							System.out.println(
									"El saldo actual de su cuenta corriente es: $" + checkingsaccount[accountnum]);
							paymentamount = dukeCheckings(language, checkingsaccount, accountnum, dukeenergy);
							checkingsaccount[accountnum] -= dukeenergy[accountnum];
						} else if (numberofaccounts == 2) // Duke Bill w/savings only
						{
							System.out.println(
									"El saldo actual de su cuenta de ahorros es: $" + savingsaccount[accountnum]);
							paymentamount = dukeSavings(language, savingsaccount, accountnum, dukeenergy);
							savingsaccount[accountnum] -= dukeenergy[accountnum];
						} else // Duke Bill w/Both
						{
							System.out.println(
									"El saldo actual de su cuenta corriente es: $" + checkingsaccount[accountnum]);
							System.out.println(
									"El saldo actual de su cuenta de ahorros es: $" + savingsaccount[accountnum]);
							System.out.println(
									"¿De qué cuenta le gustaría retirar dinero?\tCuenta de Cheques(1)\tAhorros(2)");
							accountchoice = input.nextInt();
							while (accountchoice < 1 || accountchoice > 2) {
								System.out
										.println("Seleccione una de estas opciones de cuenta:\nCheques(1)\tAhorros(2)");
								accountchoice = input.nextInt();
							}
							if (accountchoice == 1) // Duke Bill w/checking
							{
								paymentamount = dukeCheckings(language, checkingsaccount, accountnum, dukeenergy);
								checkingsaccount[accountnum] -= dukeenergy[accountnum];
							} else {
								paymentamount = dukeSavings(language, savingsaccount, accountnum, dukeenergy);
								savingsaccount[accountnum] -= dukeenergy[accountnum];
							}
						}
					} else {
						System.out.println("Tu factura de Verizon es: $ " + verizon[accountnum]);
						if (verizon[accountnum] == 0) // Bill amount due is 0
						{
							System.out.println("No debes nada.");
						} else if (numberofaccounts == 1) // Verizon Bill w/checkings
						{
							System.out.println(
									"El saldo actual de su cuenta corriente es: $" + checkingsaccount[accountnum]);
							paymentamount = verizonCheckings(language, checkingsaccount, accountnum, verizon);
							checkingsaccount[accountnum] -= verizon[accountnum];
						} else if (numberofaccounts == 2) // Verizon Bill w/savings only
						{
							System.out.println(
									"El saldo actual de su cuenta de ahorros es: $" + savingsaccount[accountnum]);
							paymentamount = verizonSavings(language, savingsaccount, accountnum, verizon);
							savingsaccount[accountnum] -= verizon[accountnum];
						} else // Verizon Bill w/Both
						{
							System.out.println(
									"El saldo actual de su cuenta corriente es: $" + checkingsaccount[accountnum]);
							System.out.println(
									"El saldo actual de su cuenta de ahorros es: $" + savingsaccount[accountnum]);
							System.out.println(
									"¿De qué cuenta le gustaría retirar dinero?\tCuenta de Cheques(1)\tAhorros(2)");
							accountchoice = input.nextInt();
							while (accountchoice < 1 || accountchoice > 2) {
								System.out
										.println("Seleccione una de estas opciones de cuenta:\nCheques(1)\tAhorros(2)");
								accountchoice = input.nextInt();
							}
							if (accountchoice == 1) // Verizon Bill w/checking
							{
								paymentamount = verizonCheckings(language, checkingsaccount, accountnum, verizon);
								checkingsaccount[accountnum] -= verizon[accountnum];
							} else {
								paymentamount = verizonSavings(language, savingsaccount, accountnum, verizon);
								savingsaccount[accountnum] -= verizon[accountnum];
							}
						}
					}

				} else if (companies == 5) // Duke + Spectrum
				{

					System.out.println(
							"Tienes dos facturas de Duke y Spectrum. ¿Cuál te gustaría pagar?\tDuke(1)\tSpectrum(2)");
					billchoice = input.nextInt();
					while (billchoice < 1 || billchoice > 2) {
						System.out.println("Seleccione una de estas opciones de facturación:\nDuke(1)\tSpectrum(2)");
						billchoice = input.nextInt();
					}
					if (billchoice == 1) {
						System.out.println("Su factura de Duke Energy es: $" + dukeenergy[accountnum]);
						if (dukeenergy[accountnum] == 0) // Bill amount due is 0
						{
							System.out.println("No debes nada.");
						} else if (numberofaccounts == 1) // Duke Bill w/checkings
						{
							System.out.println(
									"El saldo actual de su cuenta corriente es: $" + checkingsaccount[accountnum]);
							paymentamount = dukeCheckings(language, checkingsaccount, accountnum, dukeenergy);
							checkingsaccount[accountnum] -= dukeenergy[accountnum];
						} else if (numberofaccounts == 2) // Duke Bill w/savings only
						{
							System.out.println(
									"El saldo actual de su cuenta de ahorros es: $" + savingsaccount[accountnum]);
							paymentamount = dukeSavings(language, savingsaccount, accountnum, dukeenergy);
							savingsaccount[accountnum] -= dukeenergy[accountnum];
						} else // Duke Bill w/Both
						{
							System.out.println(
									"El saldo actual de su cuenta corriente es: $" + checkingsaccount[accountnum]);
							System.out.println(
									"El saldo actual de su cuenta de ahorros es: $" + savingsaccount[accountnum]);
							System.out.println(
									"¿De qué cuenta le gustaría retirar dinero?\tCuenta de Cheques(1)\tAhorros(2)");
							accountchoice = input.nextInt();
							while (accountchoice < 1 || accountchoice > 2) {
								System.out
										.println("Seleccione una de estas opciones de cuenta:\nCheques(1)\tAhorros(2)");
								accountchoice = input.nextInt();
							}
							if (accountchoice == 1) // Duke Bill w/checking
							{
								paymentamount = dukeCheckings(language, checkingsaccount, accountnum, dukeenergy);
								checkingsaccount[accountnum] -= dukeenergy[accountnum];
							} else {
								paymentamount = dukeSavings(language, savingsaccount, accountnum, dukeenergy);
								savingsaccount[accountnum] -= dukeenergy[accountnum];
							}
						}
					} else // Spectrum
					{
						System.out.println("Su factura de Spectrum es: $ " + spectrum[accountnum]);
						if (spectrum[accountnum] == 0) // Bill amount due is 0
						{
							System.out.println("No debes nada.");
						} else if (numberofaccounts == 1) // Spectrum Bill w/checkings
						{
							System.out.println(
									"El saldo actual de su cuenta corriente es: $" + checkingsaccount[accountnum]);
							paymentamount = spectrumCheckings(language, checkingsaccount, accountnum, spectrum);
							checkingsaccount[accountnum] -= spectrum[accountnum];
						} else if (numberofaccounts == 2) // Spectrum Bill w/savings only
						{
							System.out.println(
									"El saldo actual de su cuenta de ahorros es: $" + savingsaccount[accountnum]);
							paymentamount = spectrumSavings(language, savingsaccount, accountnum, spectrum);
							savingsaccount[accountnum] -= spectrum[accountnum];
						} else // Spectrum Bill w/Both
						{
							System.out.println(
									"El saldo actual de su cuenta corriente es: $" + checkingsaccount[accountnum]);
							System.out.println(
									"El saldo actual de su cuenta de ahorros es: $" + savingsaccount[accountnum]);
							System.out.println(
									"¿De qué cuenta le gustaría retirar dinero?\tCuenta de Cheques(1)\tAhorros(2)");
							accountchoice = input.nextInt();
							while (accountchoice < 1 || accountchoice > 2) {
								System.out
										.println("Seleccione una de estas opciones de cuenta:\nCheques(1)\tAhorros(2)");
								accountchoice = input.nextInt();
							}
							if (accountchoice == 1) // Spectrum Bill w/checking
							{
								paymentamount = spectrumSavings(language, checkingsaccount, accountnum, spectrum);
								checkingsaccount[accountnum] -= spectrum[accountnum];
							} else {
								paymentamount = spectrumSavings(language, savingsaccount, accountnum, spectrum);
								savingsaccount[accountnum] -= spectrum[accountnum];
							}
						}
					}

				} else if (companies == 6) // Verizon + Spectrum
				{

					System.out.println(
							"Tienes dos facturas de Verizon y Spectrum. ¿Cuál te gustaría pagar?\tVerizon(1)\tSpectrum(2)");
					billchoice = input.nextInt();
					while (billchoice < 1 || billchoice > 2) {
						System.out.println("Seleccione una de estas opciones de facturación:\nVerizon(1)\tSpectrum(2)");
						billchoice = input.nextInt();
					}
					if (billchoice == 1) // Verizon
					{
						System.out.println("Tu factura de Verizon es: $ " + verizon[accountnum]);
						if (verizon[accountnum] == 0) // Bill amount due is 0
						{
							System.out.println("No debes nada.");
						} else if (numberofaccounts == 1) // Verizon Bill w/checkings
						{
							System.out.println(
									"El saldo actual de su cuenta corriente es: $" + checkingsaccount[accountnum]);
							paymentamount = verizonCheckings(language, checkingsaccount, accountnum, verizon);
							checkingsaccount[accountnum] -= verizon[accountnum];
						} else if (numberofaccounts == 2) // Verizon Bill w/savings only
						{
							System.out.println(
									"El saldo actual de su cuenta de ahorros es: $" + savingsaccount[accountnum]);
							paymentamount = verizonSavings(language, savingsaccount, accountnum, verizon);
							savingsaccount[accountnum] -= verizon[accountnum];
						} else // Verizon Bill w/Both
						{
							System.out.println(
									"El saldo actual de su cuenta corriente es: $" + checkingsaccount[accountnum]);
							System.out.println(
									"El saldo actual de su cuenta de ahorros es: $" + savingsaccount[accountnum]);
							System.out.println(
									"¿De qué cuenta le gustaría retirar dinero?\tCuenta de Cheques(1)\tAhorros(2)");
							accountchoice = input.nextInt();
							while (accountchoice < 1 || accountchoice > 2) {
								System.out
										.println("Seleccione una de estas opciones de cuenta:\nCheques(1)\tAhorros(2)");
								accountchoice = input.nextInt();
							}
							if (accountchoice == 1) // Verizon Bill w/checking
							{
								paymentamount = verizonCheckings(language, checkingsaccount, accountnum, verizon);
								checkingsaccount[accountnum] -= verizon[accountnum];
							} else {
								paymentamount = verizonSavings(language, savingsaccount, accountnum, verizon);
								savingsaccount[accountnum] -= verizon[accountnum];
							}
						}
					} else // Spectrum
					{
						System.out.println("Su factura de Spectrum es: $ " + spectrum[accountnum]);
						if (spectrum[accountnum] == 0) // Bill amount due is 0
						{
							System.out.println("No debes nada.");
						} else if (numberofaccounts == 1) // Spectrum Bill w/checkings
						{
							System.out.println(
									"El saldo actual de su cuenta corriente es: $" + checkingsaccount[accountnum]);
							paymentamount = spectrumCheckings(language, checkingsaccount, accountnum, spectrum);
							checkingsaccount[accountnum] -= spectrum[accountnum];
						} else if (numberofaccounts == 2) // Spectrum Bill w/savings only
						{
							System.out.println(
									"El saldo actual de su cuenta de ahorros es: $" + savingsaccount[accountnum]);
							paymentamount = spectrumSavings(language, savingsaccount, accountnum, spectrum);
							savingsaccount[accountnum] -= spectrum[accountnum];
						} else // Spectrum Bill w/Both
						{
							System.out.println(
									"El saldo actual de su cuenta corriente es: $" + checkingsaccount[accountnum]);
							System.out.println(
									"El saldo actual de su cuenta de ahorros es: $" + savingsaccount[accountnum]);
							System.out.println(
									"¿De qué cuenta le gustaría retirar dinero?\tCuenta de Cheques(1)\tAhorros(2)");
							accountchoice = input.nextInt();
							while (accountchoice < 1 || accountchoice > 2) {
								System.out
										.println("Seleccione una de estas opciones de cuenta:\nCheques(1)\tAhorros(2)");
								accountchoice = input.nextInt();
							}
							if (accountchoice == 1) // Spectrum Bill w/checking
							{
								paymentamount = spectrumSavings(language, checkingsaccount, accountnum, spectrum);
								checkingsaccount[accountnum] -= spectrum[accountnum];
							} else {
								paymentamount = spectrumSavings(language, savingsaccount, accountnum, spectrum);
								savingsaccount[accountnum] -= spectrum[accountnum];
							}
						}
					}

				} else // Duke + Verizon + Spectrum
				{

					System.out.println(
							"Tienes tres facturas de Duke, Verizon y Spectrum. ¿Cuál te gustaría pagar?\tDuke(1)\tVerizon(2)\tSpectrum(3)");
					billchoice = input.nextInt();
					while (billchoice < 1 || billchoice > 3) {
						System.out.println(
								"Seleccione una de estas opciones de facturación:\nDuke(1)\tVerizon(2)\tSpectrum(3)");
						billchoice = input.nextInt();
					}
					if (billchoice == 1) // Duke
					{
						System.out.println("Su factura de Duke Energy es: $" + dukeenergy[accountnum]);
						if (dukeenergy[accountnum] == 0) // Bill amount due is 0
						{
							System.out.println("No debes nada.");
						} else if (numberofaccounts == 1) // Duke Bill w/checkings
						{
							System.out.println(
									"El saldo actual de su cuenta corriente es: $" + checkingsaccount[accountnum]);
							paymentamount = dukeCheckings(language, checkingsaccount, accountnum, dukeenergy);
							checkingsaccount[accountnum] -= dukeenergy[accountnum];
						} else if (numberofaccounts == 2) // Duke Bill w/savings only
						{
							System.out.println(
									"El saldo actual de su cuenta de ahorros es: $" + savingsaccount[accountnum]);
							paymentamount = dukeSavings(language, savingsaccount, accountnum, dukeenergy);
							savingsaccount[accountnum] -= dukeenergy[accountnum];
						} else // Duke Bill w/Both
						{
							System.out.println(
									"El saldo actual de su cuenta corriente es: $" + checkingsaccount[accountnum]);
							System.out.println(
									"El saldo actual de su cuenta de ahorros es: $" + savingsaccount[accountnum]);
							System.out.println(
									"¿De qué cuenta le gustaría retirar dinero?\tCuenta de Cheques(1)\tAhorros(2)");
							accountchoice = input.nextInt();
							while (accountchoice < 1 || accountchoice > 2) {
								System.out
										.println("Seleccione una de estas opciones de cuenta:\nCheques(1)\tAhorros(2)");
								accountchoice = input.nextInt();
							}
							if (accountchoice == 1) // Duke Bill w/checking
							{
								paymentamount = dukeCheckings(language, checkingsaccount, accountnum, dukeenergy);
								checkingsaccount[accountnum] -= dukeenergy[accountnum];
							} else {
								paymentamount = dukeSavings(language, savingsaccount, accountnum, dukeenergy);
								savingsaccount[accountnum] -= dukeenergy[accountnum];
							}
						}
					} else if (billchoice == 2) // Verizon
					{
						System.out.println("Tu factura de Verizon es: $ " + verizon[accountnum]);
						if (verizon[accountnum] == 0) // Bill amount due is 0
						{
							System.out.println("No debes nada.");
						} else if (numberofaccounts == 1) // Verizon Bill w/checkings
						{
							System.out.println(
									"El saldo actual de su cuenta corriente es: $" + checkingsaccount[accountnum]);
							paymentamount = verizonCheckings(language, checkingsaccount, accountnum, verizon);
							checkingsaccount[accountnum] -= verizon[accountnum];
						} else if (numberofaccounts == 2) // Verizon Bill w/savings only
						{
							System.out.println(
									"El saldo actual de su cuenta de ahorros es: $" + savingsaccount[accountnum]);
							paymentamount = verizonSavings(language, savingsaccount, accountnum, verizon);
							savingsaccount[accountnum] -= verizon[accountnum];
						} else // Verizon Bill w/Both
						{
							System.out.println(
									"El saldo actual de su cuenta corriente es: $" + checkingsaccount[accountnum]);
							System.out.println(
									"El saldo actual de su cuenta de ahorros es: $" + savingsaccount[accountnum]);
							System.out.println(
									"¿De qué cuenta le gustaría retirar dinero?\tCuenta de Cheques(1)\tAhorros(2)");
							accountchoice = input.nextInt();
							while (accountchoice < 1 || accountchoice > 2) {
								System.out
										.println("Seleccione una de estas opciones de cuenta:\nCheques(1)\tAhorros(2)");
								accountchoice = input.nextInt();
							}
							if (accountchoice == 1) // Verizon Bill w/checking
							{
								paymentamount = verizonCheckings(language, checkingsaccount, accountnum, verizon);
								checkingsaccount[accountnum] -= verizon[accountnum];
							} else {
								paymentamount = verizonSavings(language, savingsaccount, accountnum, verizon);
								savingsaccount[accountnum] -= verizon[accountnum];
							}
						}
					} else // Spectrum
					{
						System.out.println("Su factura de Spectrum es: $ " + spectrum[accountnum]);
						if (spectrum[accountnum] == 0) // Bill amount due is 0
						{
							System.out.println("No debes nada.");
						} else if (numberofaccounts == 1) // Spectrum Bill w/checkings
						{
							System.out.println(
									"El saldo actual de su cuenta corriente es: $" + checkingsaccount[accountnum]);
							paymentamount = spectrumCheckings(language, checkingsaccount, accountnum, spectrum);
							checkingsaccount[accountnum] -= spectrum[accountnum];
						} else if (numberofaccounts == 2) // Spectrum Bill w/savings only
						{
							System.out.println(
									"El saldo actual de su cuenta de ahorros es: $" + savingsaccount[accountnum]);
							paymentamount = spectrumSavings(language, savingsaccount, accountnum, spectrum);
							savingsaccount[accountnum] -= spectrum[accountnum];
						} else // Spectrum Bill w/Both
						{
							System.out.println(
									"El saldo actual de su cuenta corriente es: $" + checkingsaccount[accountnum]);
							System.out.println(
									"El saldo actual de su cuenta de ahorros es: $" + savingsaccount[accountnum]);
							System.out.println(
									"¿De qué cuenta le gustaría retirar dinero?\tCuenta de Cheques(1)\tAhorros(2)");
							accountchoice = input.nextInt();
							while (accountchoice < 1 || accountchoice > 2) {
								System.out
										.println("Seleccione una de estas opciones de cuenta:\nCheques(1)\tAhorros(2)");
								accountchoice = input.nextInt();
							}
							if (accountchoice == 1) // Spectrum Bill w/checking
							{
								paymentamount = spectrumSavings(language, checkingsaccount, accountnum, spectrum);
								checkingsaccount[accountnum] -= spectrum[accountnum];
							} else {
								paymentamount = spectrumSavings(language, savingsaccount, accountnum, spectrum);
								savingsaccount[accountnum] -= spectrum[accountnum];
							}
						}
					}

				}
			}
		}
	}

	// Method for Duke Bill w/Checkings
	public int dukeCheckings(int language, int[] checkingsaccount, int accountnum, int[] dukeenergy) {
		int paymentamount = 0;
		Scanner input = new Scanner(System.in);
		if (language == 1) {
			System.out.println(
					"How much money would you like to pay from your checkigns account?\tFull Amount(1)\tAnother Amount(2)");
			int paymentchoice = input.nextInt();
			while (paymentchoice < 1 || paymentchoice > 2) {
				System.out.println("Please select one of these options:\nFull Amount(1)\tAnother Amount(2)");
				paymentchoice = input.nextInt();
			}
			if (paymentchoice == 1) // Full Amount w/checking only
			{
				if (checkingsaccount[accountnum] - dukeenergy[accountnum] < 0) {
					System.out.println(
							"Payment could not be completed due to insufficient funds. Please try again or see a bank teller");
				} else {
					paymentamount = dukeenergy[accountnum];
					checkingsaccount[accountnum] -= dukeenergy[accountnum];
					dukeenergy[accountnum] -= paymentamount;
					System.out.println("Transaction complete. Your remaining balance to Duke Energy is $"
							+ dukeenergy[accountnum]);
					System.out.println("The balance of your checkings account is now $" + checkingsaccount[accountnum]);
				}
			} else // Custom amount w/checking
			{
				System.out.println("How much would you like to pay from your checkings account balance of $"
						+ checkingsaccount[accountnum] + "?");
				paymentamount = input.nextInt();
				while (paymentamount <= 0 || paymentamount > checkingsaccount[accountnum]
						|| paymentamount > dukeenergy[accountnum]) {
					System.out.println("Please enter a payment amount that is within your checkings balance of $"
							+ checkingsaccount[accountnum] + " and your bill balance of $ " + dukeenergy[accountnum]);
				}
				if (checkingsaccount[accountnum] - paymentamount < 0) {
					System.out.println(
							"Payment could not be completed due to insufficient funds. Please try again or see a bank teller");
				} else {
					checkingsaccount[accountnum] -= paymentamount;
					dukeenergy[accountnum] -= paymentamount;
					System.out.println("Transaction complete. Your remaining balance to Duke Energy is $"
							+ dukeenergy[accountnum]);
					System.out.println("The balance of your checkings account is now $" + checkingsaccount[accountnum]);
				}
			}
		} else // Duke Bill ~ Spanish Checking
		{
			System.out.println(
					"¿Cuánto dinero le gustaría pagar de su cuenta corriente?\tCantidad total(1)\tOtra cantidad(2)");
			int paymentchoice = input.nextInt();
			while (paymentchoice < 1 || paymentchoice > 2) {
				System.out.println("Seleccione una de estas opciones:\nCantidad total(1)\tOtra cantidad(2)");
				paymentchoice = input.nextInt();
			}
			if (paymentchoice == 1) // Full Amount w/checking only
			{
				if (checkingsaccount[accountnum] - dukeenergy[accountnum] < 0) {
					System.out.println(
							"No se pudo completar el pago debido a fondos insuficientes. Vuelva a intentarlo o consulte a un cajero bancario");
				} else {
					paymentamount = dukeenergy[accountnum];
					checkingsaccount[accountnum] -= dukeenergy[accountnum];
					dukeenergy[accountnum] -= paymentamount;
					System.out.println("Transacción completada. Su saldo restante para Duke Energy es de $"
							+ dukeenergy[accountnum]);
					System.out.println("El saldo de su cuenta corriente ahora es de $" + checkingsaccount[accountnum]);
				}
			} else // Custom amount w/checking
			{
				System.out.println("¿Cuánto le gustaría pagar del saldo de su cuenta corriente de $"
						+ checkingsaccount[accountnum] + "?");
				paymentamount = input.nextInt();
				while (paymentamount <= 0 || paymentamount > checkingsaccount[accountnum]
						|| paymentamount > dukeenergy[accountnum]) {
					System.out.println("Ingrese un monto de pago que esté dentro del saldo de su cuenta corriente de $"
							+ checkingsaccount[accountnum] + " and your bill balance of $ " + dukeenergy[accountnum]);
				}
				if (checkingsaccount[accountnum] - paymentamount < 0) {
					System.out.println(
							"No se pudo completar el pago debido a fondos insuficientes. Vuelva a intentarlo o consulte a un cajero bancario");
				} else {
					checkingsaccount[accountnum] -= paymentamount;
					dukeenergy[accountnum] -= paymentamount;
					System.out.println("Transacción completada. Su saldo restante para Duke Energy es de $"
							+ dukeenergy[accountnum]);
					System.out.println("El saldo de su cuenta corriente ahora es de $" + checkingsaccount[accountnum]);
				}
			}
		}
		return paymentamount;
	}

	// Method for Duke Bills w/savings
	public int dukeSavings(int language, int[] savingsaccount, int accountnum, int[] dukeenergy) {
		int paymentamount = 0;
		Scanner input = new Scanner(System.in);
		if (language == 1) {
			System.out.println(
					"How much money would you like to pay from your savings account?\tFull Amount(1)\tAnother Amount(2)");
			int paymentchoice = input.nextInt();
			while (paymentchoice < 1 || paymentchoice > 2) {
				System.out.println("Please select one of these options:\nFull Amount(1)\tAnother Amount(2)");
				paymentchoice = input.nextInt();
			}
			if (paymentchoice == 1) // Full Amount w/savings
			{
				if (savingsaccount[accountnum] - dukeenergy[accountnum] < 0) {
					System.out.println(
							"Payment could not be completed due to insufficient funds. Please try again or see a bank teller");
				} else {
					paymentamount = dukeenergy[accountnum];
					savingsaccount[accountnum] -= dukeenergy[accountnum];
					dukeenergy[accountnum] -= paymentamount;
					System.out.println("Transaction complete. Your remaining balance to Duke Energy is $"
							+ dukeenergy[accountnum]);
					System.out.println("The balance of your savings account is now $" + savingsaccount[accountnum]);
				}
			} else // Custom amount w/savings
			{
				System.out.println("How much would you like to pay from your savings account balance of $"
						+ savingsaccount[accountnum] + "?");
				paymentamount = input.nextInt();
				while (paymentamount <= 0 || paymentamount > savingsaccount[accountnum]
						|| paymentamount > dukeenergy[accountnum]) {
					System.out.println("Please enter a payment amount that is within your savings balance of $"
							+ savingsaccount[accountnum] + " and your bill balance of $ " + dukeenergy[accountnum]);
					paymentamount = input.nextInt();
				}
				if (savingsaccount[accountnum] - paymentamount < 0) {
					System.out.println(
							"Payment could not be completed due to insufficient funds. Please try again or see a bank teller");
				} else {
					savingsaccount[accountnum] -= paymentamount;
					dukeenergy[accountnum] -= paymentamount;
					System.out.println("Transaction complete. Your remaining balance to Duke Energy is $"
							+ dukeenergy[accountnum]);
					System.out.println("The balance of your savings account is now $" + savingsaccount[accountnum]);
				}
			}
		} else {
			System.out.println(
					"¿Cuánto dinero le gustaría pagar de su cuenta de ahorros?\tCantidad Total(1)\tOtra Cantidad(2)");
			int paymentchoice = input.nextInt();
			while (paymentchoice < 1 || paymentchoice > 2) {
				System.out.println("Seleccione una de estas opciones:\nCantidad total(1)\tOtra cantidad(2)");
				paymentchoice = input.nextInt();
			}
			if (paymentchoice == 1) // Full Amount w/savings
			{
				if (savingsaccount[accountnum] - dukeenergy[accountnum] < 0) {
					System.out.println(
							"No se pudo completar el pago debido a fondos insuficientes. Vuelva a intentarlo o consulte a un cajero bancario");
				} else {
					paymentamount = dukeenergy[accountnum];
					savingsaccount[accountnum] -= dukeenergy[accountnum];
					dukeenergy[accountnum] -= paymentamount;
					System.out.println("Transacción completada. Su saldo restante para Duke Energy es de $"
							+ dukeenergy[accountnum]);
					System.out.println("El saldo de su cuenta de ahorros ahora es $" + savingsaccount[accountnum]);
				}
			} else // Custom amount w/savings
			{
				System.out.println("¿Cuánto le gustaría pagar del saldo de su cuenta de ahorros de $"
						+ savingsaccount[accountnum] + "?");
				paymentamount = input.nextInt();
				while (paymentamount <= 0 || paymentamount > savingsaccount[accountnum]
						|| paymentamount > dukeenergy[accountnum]) {
					System.out.println("Ingrese un monto de pago que esté dentro de su saldo de ahorros de $"
							+ savingsaccount[accountnum] + " and your bill balance of $ " + dukeenergy[accountnum]);
					paymentamount = input.nextInt();
				}
				if (savingsaccount[accountnum] - paymentamount < 0) {
					System.out.println(
							"No se pudo completar el pago debido a fondos insuficientes. Vuelva a intentarlo o consulte a un cajero bancario");
				} else {
					savingsaccount[accountnum] -= paymentamount;
					dukeenergy[accountnum] -= paymentamount;
					System.out.println("Transacción completada. Su saldo restante para Duke Energy es de $"
							+ dukeenergy[accountnum]);
					System.out.println("El saldo de su cuenta de ahorros ahora es $" + savingsaccount[accountnum]);
				}
			}
		}
		return paymentamount;
	}

	// Method for Verizon Checking
	public int verizonCheckings(int language, int[] checkingsaccount, int accountnum, int[] verizon) {
		int paymentamount = 0;
		Scanner input = new Scanner(System.in);
		if (language == 1) {
			System.out.println(
					"How much money would you like to pay from your checkigns account?\tFull Amount(1)\tAnother Amount(2)");
			int paymentchoice = input.nextInt();
			while (paymentchoice < 1 || paymentchoice > 2) {
				System.out.println("Please select one of these options:\nFull Amount(1)\tAnother Amount(2)");
				paymentchoice = input.nextInt();
			}
			if (paymentchoice == 1) // Full Amount w/checking only
			{
				if (checkingsaccount[accountnum] - verizon[accountnum] < 0) {
					System.out.println(
							"Payment could not be completed due to insufficient funds. Please try again or see a bank teller");
				} else {
					paymentamount = verizon[accountnum];
					checkingsaccount[accountnum] -= verizon[accountnum];
					verizon[accountnum] -= paymentamount;
					System.out.println(
							"Transaction complete. Your remaining balance to Verizon is $" + verizon[accountnum]);
					System.out.println("The balance of your checkings account is now $" + checkingsaccount[accountnum]);
				}
			} else // Custom amount w/checking
			{
				System.out.println("How much would you like to pay from your checkings account balance of $"
						+ checkingsaccount[accountnum] + "?");
				paymentamount = input.nextInt();
				while (paymentamount <= 0 || paymentamount > checkingsaccount[accountnum]
						|| paymentamount > verizon[accountnum]) {
					System.out.println("Please enter a payment amount that is within your checkings balance of $"
							+ checkingsaccount[accountnum] + " and your bill balance of $ " + verizon[accountnum]);
				}
				if (checkingsaccount[accountnum] - paymentamount < 0) {
					System.out.println(
							"Payment could not be completed due to insufficient funds. Please try again or see a bank teller");
				} else {
					checkingsaccount[accountnum] -= paymentamount;
					verizon[accountnum] -= paymentamount;
					System.out.println(
							"Transaction complete. Your remaining balance to Verizon is $" + verizon[accountnum]);
					System.out.println("The balance of your checkings account is now $" + checkingsaccount[accountnum]);
				}
			}
		} else // Verizon Bill ~ Spanish Checking
		{
			System.out.println(
					"¿Cuánto dinero le gustaría pagar de su cuenta corriente?\tCantidad total(1)\tOtra cantidad(2)");
			int paymentchoice = input.nextInt();
			while (paymentchoice < 1 || paymentchoice > 2) {
				System.out.println("Seleccione una de estas opciones:\nCantidad total(1)\tOtra cantidad(2)");
				paymentchoice = input.nextInt();
			}
			if (paymentchoice == 1) // Full Amount w/checking only
			{
				if (checkingsaccount[accountnum] - verizon[accountnum] < 0) {
					System.out.println(
							"No se pudo completar el pago debido a fondos insuficientes. Vuelva a intentarlo o consulte a un cajero bancario");
				} else {
					paymentamount = verizon[accountnum];
					checkingsaccount[accountnum] -= verizon[accountnum];
					verizon[accountnum] -= paymentamount;
					System.out.println(
							"Transacción completada. Su saldo restante para Verizon es de $" + verizon[accountnum]);
					System.out.println("El saldo de su cuenta corriente ahora es de $" + checkingsaccount[accountnum]);
				}
			} else // Custom amount w/checking
			{
				System.out.println("¿Cuánto le gustaría pagar del saldo de su cuenta corriente de $"
						+ checkingsaccount[accountnum] + "?");
				paymentamount = input.nextInt();
				while (paymentamount <= 0 || paymentamount > checkingsaccount[accountnum]
						|| paymentamount > verizon[accountnum]) {
					System.out.println("Ingrese un monto de pago que esté dentro del saldo de su cuenta corriente de $"
							+ checkingsaccount[accountnum] + " and your bill balance of $ " + verizon[accountnum]);
				}
				if (checkingsaccount[accountnum] - paymentamount < 0) {
					System.out.println(
							"No se pudo completar el pago debido a fondos insuficientes. Vuelva a intentarlo o consulte a un cajero bancario");
				} else {
					checkingsaccount[accountnum] -= paymentamount;
					verizon[accountnum] -= paymentamount;
					System.out.println(
							"Transacción completada. Su saldo restante para Verizon es de $" + verizon[accountnum]);
					System.out.println("El saldo de su cuenta corriente ahora es de $" + checkingsaccount[accountnum]);
				}
			}
		}
		return paymentamount;
	}

	// Method for Spectrum savings
	public int spectrumSavings(int language, int[] savingsaccount, int accountnum, int[] spectrum) {
		int paymentamount = 0;
		Scanner input = new Scanner(System.in);
		if (language == 1) {
			System.out.println(
					"How much money would you like to pay from your savings account?\tFull Amount(1)\tAnother Amount(2)");
			int paymentchoice = input.nextInt();
			while (paymentchoice < 1 || paymentchoice > 2) {
				System.out.println("Please select one of these options:\nFull Amount(1)\tAnother Amount(2)");
				paymentchoice = input.nextInt();
			}
			if (paymentchoice == 1) // Full Amount w/savings
			{
				if (savingsaccount[accountnum] - spectrum[accountnum] < 0) {
					System.out.println(
							"Payment could not be completed due to insufficient funds. Please try again or see a bank teller");
				} else {
					paymentamount = spectrum[accountnum];
					savingsaccount[accountnum] -= spectrum[accountnum];
					spectrum[accountnum] -= paymentamount;
					System.out.println(
							"Transaction complete. Your remaining balance to Spectrum is $" + spectrum[accountnum]);
					System.out.println("The balance of your savings account is now $" + savingsaccount[accountnum]);
				}
			} else // Custom amount w/savings
			{
				System.out.println("How much would you like to pay from your savings account balance of $"
						+ savingsaccount[accountnum] + "?");
				paymentamount = input.nextInt();
				while (paymentamount <= 0 || paymentamount > savingsaccount[accountnum]
						|| paymentamount > spectrum[accountnum]) {
					System.out.println("Please enter a payment amount that is within your savings balance of $"
							+ savingsaccount[accountnum] + " and your bill balance of $ " + spectrum[accountnum]);
					paymentamount = input.nextInt();
				}
				if (savingsaccount[accountnum] - paymentamount < 0) {
					System.out.println(
							"Payment could not be completed due to insufficient funds. Please try again or see a bank teller");
				} else {
					savingsaccount[accountnum] -= paymentamount;
					spectrum[accountnum] -= paymentamount;
					System.out.println(
							"Transaction complete. Your remaining balance to Spectrum is $" + spectrum[accountnum]);
					System.out.println("The balance of your savings account is now $" + savingsaccount[accountnum]);
				}
			}
		} else {
			System.out.println(
					"¿Cuánto dinero le gustaría pagar de su cuenta de ahorros?\tCantidad Total(1)\tOtra Cantidad(2)");
			int paymentchoice = input.nextInt();
			while (paymentchoice < 1 || paymentchoice > 2) {
				System.out.println("Seleccione una de estas opciones:\nCantidad total(1)\tOtra cantidad(2)");
				paymentchoice = input.nextInt();
			}
			if (paymentchoice == 1) // Full Amount w/savings
			{
				if (savingsaccount[accountnum] - spectrum[accountnum] < 0) {
					System.out.println(
							"No se pudo completar el pago debido a fondos insuficientes. Vuelva a intentarlo o consulte a un cajero bancario");
				} else {
					paymentamount = spectrum[accountnum];
					savingsaccount[accountnum] -= spectrum[accountnum];
					spectrum[accountnum] -= paymentamount;
					System.out.println(
							"Transacción completada. Su saldo restante para Spectrum es de $" + spectrum[accountnum]);
					System.out.println("El saldo de su cuenta de ahorros ahora es $" + savingsaccount[accountnum]);
				}
			} else // Custom amount w/savings
			{
				System.out.println("¿Cuánto le gustaría pagar del saldo de su cuenta de ahorros de $"
						+ savingsaccount[accountnum] + "?");
				paymentamount = input.nextInt();
				while (paymentamount <= 0 || paymentamount > savingsaccount[accountnum]
						|| paymentamount > spectrum[accountnum]) {
					System.out.println("Ingrese un monto de pago que esté dentro de su saldo de ahorros de $"
							+ savingsaccount[accountnum] + " and your bill balance of $ " + spectrum[accountnum]);
					paymentamount = input.nextInt();
				}
				if (savingsaccount[accountnum] - paymentamount < 0) {
					System.out.println(
							"No se pudo completar el pago debido a fondos insuficientes. Vuelva a intentarlo o consulte a un cajero bancario");
				} else {
					savingsaccount[accountnum] -= paymentamount;
					spectrum[accountnum] -= paymentamount;
					System.out.println(
							"Transacción completada. Su saldo restante para Verizon es de $" + spectrum[accountnum]);
					System.out.println("El saldo de su cuenta de ahorros ahora es $" + savingsaccount[accountnum]);
				}
			}
		}
		return paymentamount;
	}

	// Method for Spectrum Checking
	public int spectrumCheckings(int language, int[] checkingsaccount, int accountnum, int[] spectrum) {
		int paymentamount = 0;
		Scanner input = new Scanner(System.in);
		if (language == 1) {
			System.out.println(
					"How much money would you like to pay from your checkigns account?\tFull Amount(1)\tAnother Amount(2)");
			int paymentchoice = input.nextInt();
			while (paymentchoice < 1 || paymentchoice > 2) {
				System.out.println("Please select one of these options:\nFull Amount(1)\tAnother Amount(2)");
				paymentchoice = input.nextInt();
			}
			if (paymentchoice == 1) // Full Amount w/checking only
			{
				if (checkingsaccount[accountnum] - spectrum[accountnum] < 0) {
					System.out.println(
							"Payment could not be completed due to insufficient funds. Please try again or see a bank teller");
				} else {
					paymentamount = spectrum[accountnum];
					checkingsaccount[accountnum] -= spectrum[accountnum];
					spectrum[accountnum] -= paymentamount;
					System.out.println(
							"Transaction complete. Your remaining balance to Spectrum is $" + spectrum[accountnum]);
					System.out.println("The balance of your checkings account is now $" + checkingsaccount[accountnum]);
				}
			} else // Custom amount w/checking
			{
				System.out.println("How much would you like to pay from your checkings account balance of $"
						+ checkingsaccount[accountnum] + "?");
				paymentamount = input.nextInt();
				while (paymentamount <= 0 || paymentamount > checkingsaccount[accountnum]
						|| paymentamount > spectrum[accountnum]) {
					System.out.println("Please enter a payment amount that is within your checkings balance of $"
							+ checkingsaccount[accountnum] + " and your bill balance of $ " + spectrum[accountnum]);
				}
				if (checkingsaccount[accountnum] - paymentamount < 0) {
					System.out.println(
							"Payment could not be completed due to insufficient funds. Please try again or see a bank teller");
				} else {
					checkingsaccount[accountnum] -= paymentamount;
					spectrum[accountnum] -= paymentamount;
					System.out.println(
							"Transaction complete. Your remaining balance to Spectrum is $" + spectrum[accountnum]);
					System.out.println("The balance of your checkings account is now $" + checkingsaccount[accountnum]);
				}
			}
		} else // Verizon Bill ~ Spanish Checking
		{
			System.out.println(
					"¿Cuánto dinero le gustaría pagar de su cuenta corriente?\tCantidad total(1)\tOtra cantidad(2)");
			int paymentchoice = input.nextInt();
			while (paymentchoice < 1 || paymentchoice > 2) {
				System.out.println("Seleccione una de estas opciones:\nCantidad total(1)\tOtra cantidad(2)");
				paymentchoice = input.nextInt();
			}
			if (paymentchoice == 1) // Full Amount w/checking only
			{
				if (checkingsaccount[accountnum] - spectrum[accountnum] < 0) {
					System.out.println(
							"No se pudo completar el pago debido a fondos insuficientes. Vuelva a intentarlo o consulte a un cajero bancario");
				} else {
					paymentamount = spectrum[accountnum];
					checkingsaccount[accountnum] -= spectrum[accountnum];
					spectrum[accountnum] -= paymentamount;
					System.out.println(
							"Transacción completada. Su saldo restante para Spectrum es de $" + spectrum[accountnum]);
					System.out.println("El saldo de su cuenta corriente ahora es de $" + checkingsaccount[accountnum]);
				}
			} else // Custom amount w/checking
			{
				System.out.println("¿Cuánto le gustaría pagar del saldo de su cuenta corriente de $"
						+ checkingsaccount[accountnum] + "?");
				paymentamount = input.nextInt();
				while (paymentamount <= 0 || paymentamount > checkingsaccount[accountnum]
						|| paymentamount > spectrum[accountnum]) {
					System.out.println("Ingrese un monto de pago que esté dentro del saldo de su cuenta corriente de $"
							+ checkingsaccount[accountnum] + " and your bill balance of $ " + spectrum[accountnum]);
				}
				if (checkingsaccount[accountnum] - paymentamount < 0) {
					System.out.println(
							"No se pudo completar el pago debido a fondos insuficientes. Vuelva a intentarlo o consulte a un cajero bancario");
				} else {
					checkingsaccount[accountnum] -= paymentamount;
					spectrum[accountnum] -= paymentamount;
					System.out.println(
							"Transacción completada. Su saldo restante para Spectrum es de $" + spectrum[accountnum]);
					System.out.println("El saldo de su cuenta corriente ahora es de $" + checkingsaccount[accountnum]);
				}
			}
		}
		return paymentamount;
	}

	// Method for Verizon savings
	public int verizonSavings(int language, int[] savingsaccount, int accountnum, int[] verizon) {
		int paymentamount = 0;
		Scanner input = new Scanner(System.in);
		if (language == 1) {
			System.out.println(
					"How much money would you like to pay from your savings account?\tFull Amount(1)\tAnother Amount(2)");
			int paymentchoice = input.nextInt();
			while (paymentchoice < 1 || paymentchoice > 2) {
				System.out.println("Please select one of these options:\nFull Amount(1)\tAnother Amount(2)");
				paymentchoice = input.nextInt();
			}
			if (paymentchoice == 1) // Full Amount w/savings
			{
				if (savingsaccount[accountnum] - verizon[accountnum] < 0) {
					System.out.println(
							"Payment could not be completed due to insufficient funds. Please try again or see a bank teller");
				} else {
					paymentamount = verizon[accountnum];
					savingsaccount[accountnum] -= verizon[accountnum];
					verizon[accountnum] -= paymentamount;
					System.out.println(
							"Transaction complete. Your remaining balance to Verizon is $" + verizon[accountnum]);
					System.out.println("The balance of your savings account is now $" + savingsaccount[accountnum]);
				}
			} else // Custom amount w/savings
			{
				System.out.println("How much would you like to pay from your savings account balance of $"
						+ savingsaccount[accountnum] + "?");
				paymentamount = input.nextInt();
				while (paymentamount <= 0 || paymentamount > savingsaccount[accountnum]
						|| paymentamount > verizon[accountnum]) {
					System.out.println("Please enter a payment amount that is within your savings balance of $"
							+ savingsaccount[accountnum] + " and your bill balance of $ " + verizon[accountnum]);
					paymentamount = input.nextInt();
				}
				if (savingsaccount[accountnum] - paymentamount < 0) {
					System.out.println(
							"Payment could not be completed due to insufficient funds. Please try again or see a bank teller");
				} else {
					savingsaccount[accountnum] -= paymentamount;
					verizon[accountnum] -= paymentamount;
					System.out.println(
							"Transaction complete. Your remaining balance to Verizon is $" + verizon[accountnum]);
					System.out.println("The balance of your savings account is now $" + savingsaccount[accountnum]);
				}
			}
		} else {
			System.out.println(
					"¿Cuánto dinero le gustaría pagar de su cuenta de ahorros?\tCantidad Total(1)\tOtra Cantidad(2)");
			int paymentchoice = input.nextInt();
			while (paymentchoice < 1 || paymentchoice > 2) {
				System.out.println("Seleccione una de estas opciones:\nCantidad total(1)\tOtra cantidad(2)");
				paymentchoice = input.nextInt();
			}
			if (paymentchoice == 1) // Full Amount w/savings
			{
				if (savingsaccount[accountnum] - verizon[accountnum] < 0) {
					System.out.println(
							"No se pudo completar el pago debido a fondos insuficientes. Vuelva a intentarlo o consulte a un cajero bancario");
				} else {
					paymentamount = verizon[accountnum];
					savingsaccount[accountnum] -= verizon[accountnum];
					verizon[accountnum] -= paymentamount;
					System.out.println(
							"Transacción completada. Su saldo restante para Verizon es de $" + verizon[accountnum]);
					System.out.println("El saldo de su cuenta de ahorros ahora es $" + savingsaccount[accountnum]);
				}
			} else // Custom amount w/savings
			{
				System.out.println("¿Cuánto le gustaría pagar del saldo de su cuenta de ahorros de $"
						+ savingsaccount[accountnum] + "?");
				paymentamount = input.nextInt();
				while (paymentamount <= 0 || paymentamount > savingsaccount[accountnum]
						|| paymentamount > verizon[accountnum]) {
					System.out.println("Ingrese un monto de pago que esté dentro de su saldo de ahorros de $"
							+ savingsaccount[accountnum] + " and your bill balance of $ " + verizon[accountnum]);
					paymentamount = input.nextInt();
				}
				if (savingsaccount[accountnum] - paymentamount < 0) {
					System.out.println(
							"No se pudo completar el pago debido a fondos insuficientes. Vuelva a intentarlo o consulte a un cajero bancario");
				} else {
					savingsaccount[accountnum] -= paymentamount;
					verizon[accountnum] -= paymentamount;
					System.out.println(
							"Transacción completada. Su saldo restante para Verizon es de $" + verizon[accountnum]);
					System.out.println("El saldo de su cuenta de ahorros ahora es $" + savingsaccount[accountnum]);
				}
			}
		}
		return paymentamount;
	}
}