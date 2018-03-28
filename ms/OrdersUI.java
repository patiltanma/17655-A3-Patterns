/******************************************************************************************************************
* File:OrdersUI.java
* Course: 17655
* Project: Assignment A3
* Copyright: Copyright (c) 2018 Carnegie Mellon University
* Versions:
*	1.0 February 2018 - Initial write of assignment 3 (ajl).
*
* Description: This class is the console for the an orders database. This interface uses a webservices or microservice
* client class to update the ms_orderinfo MySQL database. 
*
* Parameters: None
*
* Internal Methods: None
*
* External Dependencies (one of the following):
*	- MSlientAPI - this class provides an interface to a set of microservices
*	- RetrieveServices - this is the server-side micro service for retrieving info from the ms_orders database
*	- CreateServices - this is the server-side micro service for creating new orders in the ms_orders database
*	- DeleteServices - this is the server-side micro service for creating new orders in the ms_orders database
*	- LoggingServices - this is the server-side micro service for creating new orders in the ms_orders database
*
******************************************************************************************************************/

import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.io.Console;

public class OrdersUI
{
	static private MSClientAPI api = new MSClientAPI();	// RESTful api object
	
	public static void main(String args[])
	{
		boolean done = false;						// main loop flag
		boolean error = false;						// error flag
		char    option;								// Menu choice from user
		Console c = System.console();				// Press any key
		String  date = null;						// order date
		String  first = null;						// customer first name
		String  last = null;						// customer last name
		String  address = null;						// customer address
		String  phone = null;						// customer phone number
		String  orderid = null;						// order ID
		String 	response = null;					// response string from REST 
		Scanner keyboard = new Scanner(System.in);	// keyboard scanner object for user input
		DateTimeFormatter dtf = null;				// Date object formatter
		LocalDate localDate = null;					// Date object
	
		
		/////////////////////////////////////////////////////////////////////////////////
		// User Login
		/////////////////////////////////////////////////////////////////////////////////
		
		System.out.println( "\n\n" );
		System.out.println( "User Authentication\n" );
		if(AuthenticateUser()==false)
		{
			// inform the user that the authentication failed and exit the program
			
			System.out.println( "\nUser Authentication failed.\n" );
			System.out.println( "Please try again by restarting the application!\n" );
			return;
		} else {
			
			// inform the user that the authentication was successful
			
			System.out.println( "\nUser Authenticated Successfully." );
		}	

		/////////////////////////////////////////////////////////////////////////////////
		// Main UI loop
		/////////////////////////////////////////////////////////////////////////////////

		while (!done)
		{	
	
			// Here, is the main menu set of choices

			System.out.println( "\n\n\n\n" );
			System.out.println( "Orders Database User Interface: \n" );
			System.out.println( "Select an Option: \n" );
			System.out.println( "1: Retrieve all orders in the order database." );
			System.out.println( "2: Retrieve an order by ID." );
			System.out.println( "3: Add a new order to the order database." );	
			System.out.println( "4: Delete an order by ID." );		
			System.out.println( "X: Exit\n" );
			System.out.print( "\n>>>> " );
			option = keyboard.next().charAt(0);	
			keyboard.nextLine();	// Removes data from keyboard buffer. If you don't clear the buffer, you blow 
									// through the next call to nextLine()

			//////////// option 1 ////////////

			if ( option == '1' )
			{
				// Here we retrieve all the orders in the ms_orderinfo database

				System.out.println( "\nRetrieving All Orders::" );
				try
				{
					response = api.retrieveOrders();
					System.out.println(response);

				} catch (Exception e) {

					System.out.println("Request failed:: " + e);

				}

				System.out.println("\nPress enter to continue..." );
				c.readLine();

			} // if

			//////////// option 2 ////////////

			if ( option == '2' )
			{
				// Here we get the order ID from the user

				error = true;

				while (error)
				{
					System.out.print( "\nEnter the order ID: " );
					orderid = keyboard.nextLine();

					try
					{
						Integer.parseInt(orderid);
						error = false;
					} catch (NumberFormatException e) {

						System.out.println( "Not a number, please try again..." );
						System.out.println("\nPress enter to continue..." );

					} // if

				} // while

				try
				{
					response = api.retrieveOrders(orderid);
					System.out.println(response);

				} catch (Exception e) {

					System.out.println("Request failed:: " + e);
					
				}

				System.out.println("\nPress enter to continue..." );
				c.readLine();

			} // if

			//////////// option 3 ////////////

			if ( option == '3' )
			{
				// Here we create a new order entry in the database

				dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				localDate = LocalDate.now();
				date = localDate.format(dtf);

				System.out.println("Enter first name:");
				first = keyboard.nextLine();

				System.out.println("Enter last name:");
				last = keyboard.nextLine();
		
				System.out.println("Enter address:");
				address = keyboard.nextLine();

				System.out.println("Enter phone:");
				phone = keyboard.nextLine();

				System.out.println("Creating the following order:");
				System.out.println("==============================");
				System.out.println(" Date:" + date);		
				System.out.println(" First name:" + first);
				System.out.println(" Last name:" + last);
				System.out.println(" Address:" + address);
				System.out.println(" Phone:" + phone);
				System.out.println("==============================");					
				System.out.println("\nPress 'y' to create this order:");

				option = keyboard.next().charAt(0);

				if (( option == 'y') || (option == 'Y'))
				{
					try
					{
						System.out.println("\nCreating order...");
						response = api.newOrder(date, first, last, address, phone);
						System.out.println(response);

					} catch(Exception e) {

						System.out.println("Request failed:: " + e);

					}

				} else {

					System.out.println("\nOrder not created...");
				}

				System.out.println("\nPress enter to continue..." );
				c.readLine();

				option = ' '; //Clearing option. This incase the user enterd X/x the program will not exit.

			} // if
			
			
			
			//////////// option 4 ////////////

			if ( option == '4' )
			{
				// Here we get the order ID from the user

				error = true;

				while (error)
				{
					System.out.print( "\nEnter the order ID you want to delete: " );
					orderid = keyboard.nextLine();

					try
					{
						Integer.parseInt(orderid);
						error = false;
					} catch (NumberFormatException e) {

						System.out.println( "Not a number, please try again..." );
						System.out.println("\nPress enter to continue..." );

					} // if

				} // while

				try
				{
					response = api.retrieveOrders(orderid);

				} catch (Exception e) {

					System.out.println("Request failed:: " + e);
					
				}
				
				// If order not found, inform the user
				// If order found, then delete it after verifying it from the user
				
				
				if(response.contains("[]"))
				{
					System.out.println("\nEntry not found. Please enter an order ID that is available in the database.");
					System.out.println(response);
					
				} else {
					
					System.out.println("\nIs this the order you want to delete?\n" + response);
					System.out.println("\nPress 'y' to delete:");
					option = keyboard.next().charAt(0);
					
					if (( option == 'y') || (option == 'Y'))
					{
						try
						{
							System.out.println("\nDeleting order...");
							response = api.deleteOrder(orderid);
							System.out.println(response);

						} catch(Exception e) {

							System.out.println("Request failed:: " + e);

						}

					} else {

						System.out.println("\nOrder not deleted...");
					}
					
				}

				System.out.println("\nPress enter to continue..." );
				c.readLine();

				option = ' '; //Clearing option. This incase the user enterd X/x the program will not exit.

			} // if

			//////////// option X ////////////

			if ( ( option == 'X' ) || ( option == 'x' ))
			{
				// Here the user is done, so we set the Done flag and halt the system

				done = true;
				System.out.println( "\nDone...\n\n" );

			} // if

		} // while

  	} // main
	
	
		
	/********************************************************************************
	* Description: Gets the username from the user. Checks if available in database.
	*			Adds username and password if not existing. Checks if username and
	*			password match.
	* Parameters: None
	* Returns: tells if authentication passed of failes.
	********************************************************************************/
	
	static private boolean AuthenticateUser() 
	{
		String username = null;				// user name for authorization
		String password = null;				// password for authorization
		String response = null;				// general response from server
		char    option;
		boolean done = false;				// loop flag
		Scanner keyboard = new Scanner(System.in);	// keyboard scanner object for user input
		
		// ask the user to input the username
		
		System.out.println( "Please enter your user-name:\n" );
		username = keyboard.next();	
			
		// check if username is in the database
			
		System.out.println( "\nChecking database for user-name..." );
		if(isUsernamePresent(username)==false) 
		{
						
			System.out.println("\nUser-name is not in the database.");
			System.out.println("You will have to register to access all the services.");
			System.out.println("Registration will require a user-name and a password.");
			System.out.println( "\n" );
			System.out.print("If you think you have already registered and have made a mistake in typing the username, ");
			System.out.println("Press anything on the keyboard except 'y' to exit, so that you can restart the program.");
			System.out.println( "\n" );
			System.out.println("Do you want to proceed with the registration?");
			System.out.println("Press 'y' to register and type anything else to close this application.");

			// is username is not present in the database, ask the user if they want to register.
			// user can access database only if they register
			
			option = keyboard.next().charAt(0);

			if (( option == 'y') || (option == 'Y'))
			{
				// if user says yes then keep checking till the user enters a unique username not available in 
				// the database for him/her to register.
				
				while(!done)
				{
					System.out.println( "\nPlease enter your user-name:" );
					username = keyboard.next();
					
										
					if(isUsernamePresent(username)==true) 
					{
						System.out.println( "This username already exists. Please try a new one.\n" );
						done = false;
					} else {
						
						// if the username is unique ask for the password for registration
						
						System.out.println( "\nPlease enter your password:" );
						password = keyboard.next();
						
						// add the username and password to the database
												
						try
						{
							System.out.println("\nAdding your username...");
							response = api.addUserInfo(username, password);
							done = true;

						} catch(Exception e) {

							System.out.println("Request failed:: " + e);
							System.out.println("\nSome error occurred. Please try again.\n");
							return false;

						}
					}
				}
				
				return true;
			}
			else {
				
				// if user says anthing except 'y' inform main about it so that program terminates
				System.out.println( "You choose to exit." );
				return false;
			}

		} else {
			
			// if username is found in the database ask the user to enter the password
			
			System.out.println( "Please enter your password:\n" );
			password = keyboard.next();
			
			// retrieve password for the username from the database
			
			try
			{
				System.out.println("\nChecking username and password...");
				response = api.retrieveUserInfo(username);
				done = true;
				
			} catch(Exception e) {

				System.out.println("Request failed:: " + e);
				System.out.println("\n\nSome error occurred. Please try again.\n");
				return false;

			}
			
			// check if the password is correct
			
			if(response.contains("password:"+password)) 
			{
				return true;
				
			} else {

				System.out.println("Username and password didnot match.");
				return false;

			}
		}
	}
	
	
	/********************************************************************************
	* Description: Checks if username is present in the database.
	* Parameters: username as string
	* Returns: tells if username is present or absent.
	********************************************************************************/
	
	static private boolean isUsernamePresent(String username)
	{
		String response = null;				// general response from server
		
		// get the username from the database
		try
		{

			response = api.retrieveUserInfo(username);	

		} catch (Exception e) {

			System.out.println("Request failed:: " + e);
			return false;
					
		}
				
		// check the response and inform the calling method about it			
		if(response.contains("[]"))
		{
			return false;
					
		} else {
			
			return true;
		}
					
	}

} // OrdersUI
