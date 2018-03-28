/******************************************************************************************************************
* File: UserLoginServicesAI.java
* Course: 17655
* Project: Assignment A3
* Copyright: Copyright (c) 2018 Carnegie Mellon University
* Versions:
*	1.0 February 2018 - Initial write of assignment 3 (ajl).
*
* Description: This class provides the abstract interface for the create micro services, CreateServices.
* The implementation of these abstract interfaces can be found in the CreateServices.java class.
* The micro services are partitioned as Create, Retrieve, Update, Delete (CRUD) service packages. Each service 
* is its own process (eg. executing in a separate JVM). It would be a good practice to follow this convention
* when adding and modifying services. Note that services can be duplicated and differentiated by IP
* and/or port# they are hosted on. For this assignment, create and retrieve services have been provided and are
* services are hosted on the local host, on the default RMI port (1099). 
*
* Parameters: None
*
* Internal Methods:
*  String addUserInfo(String username, String password) - adds a new user information like username and password to the database
*  String retrieveUserInfo(String username) - gets and returns the user information
*
* External Dependencies: None
******************************************************************************************************************/

import java.rmi.*;
		
public interface UserLoginServicesAI extends java.rmi.Remote
{

	/*******************************************************
	* Creates a new entry in the database for new user registered
	* with the username and password.
	*******************************************************/
	
	String addUserInfo(String username, String password) throws RemoteException;
	
	/*******************************************************
	* Retrieves the user info corresponding to the username in 
	* method argument form the orderinfo database and 
	* returns the userinfo in the form of a string in ordered 
	* pairs format.
	*******************************************************/	

	String retrieveUserInfo(String username) throws RemoteException;

}