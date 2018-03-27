/******************************************************************************************************************
* File: MSClientAPI.java
* Course: 17655
* Project: Assignment A3
* Copyright: Copyright (c) 2018 Carnegie Mellon University
* Versions:
*	1.0 February 2018 - Initial write of assignment 3 (ajl).
*
* Description: This class provides access to the webservices via RMI. Users of this class need not worry about the
* details of RMI (provided the services are running and registered via rmiregistry).  
*
* Parameters: None
*
* Internal Methods:
*  String retrieveOrders() - gets and returns all the orders in the orderinfo database
*  String retrieveOrders(String id) - gets and returns the order associated with the order id
*  String newOrder(String Date, String FirstName, String LastName, String Address, String Phone) - creates a new 
*  String deleteOrder(String orderid) - deletes an order with order ID
*  order in the orderinfo database
*
*
* External Dependencies: None
******************************************************************************************************************/

import java.rmi.Naming; 
import java.rmi.RemoteException;

public class MSClientAPI
{
	String response = null;

	/********************************************************************************
	* Description: Retrieves all the orders in the orderinfo database. Note 
	*              that this method is serviced by the RetrieveServices server 
	*			   process.
	* Parameters: None
	* Returns: String of all the current orders in the orderinfo database
	********************************************************************************/

	public String retrieveUserInfo(String username) throws Exception
	{
           RetrieveServicesAI obj = (RetrieveServicesAI) Naming.lookup("RetrieveServices");  
           response = obj.retrieveUserInfo(username);
           return(response);
	}
	
	
	/********************************************************************************
	* Description: Creates the new order to the orderinfo database
	* Parameters: None
	* Returns: String that contains the status of the create operation
	********************************************************************************/

   	public String addUserInfo(String username, String password) throws Exception
	{
           CreateServicesAI obj = (CreateServicesAI) Naming.lookup("CreateServices"); 
           response = obj.addUserInfo(username, password);
           return(response);	
		
    }
	
	
	/********************************************************************************
	* Description: Retrieves all the orders in the orderinfo database. Note 
	*              that this method is serviced by the RetrieveServices server 
	*			   process.
	* Parameters: None
	* Returns: String of all the current orders in the orderinfo database
	********************************************************************************/

	public String retrieveOrders() throws Exception
	{
           RetrieveServicesAI obj = (RetrieveServicesAI) Naming.lookup("RetrieveServices");  
           response = obj.retrieveOrders();
           return(response);
	}
	
	/********************************************************************************
	* Description: Retrieves the order based on the id argument provided from the
	*              orderinfo database. Note that this method is serviced by the 
	*			   RetrieveServices server process.
	* Parameters: None
	* Returns: String of all the order corresponding to the order id argument 
	*          in the orderinfo database.
	********************************************************************************/

	public String retrieveOrders(String id) throws Exception
	{
           RetrieveServicesAI obj = (RetrieveServicesAI) Naming.lookup("RetrieveServices");  
           response = obj.retrieveOrders(id);
           return(response);	

	}

	/********************************************************************************
	* Description: Creates the new order to the orderinfo database
	* Parameters: None
	* Returns: String that contains the status of the create operation
	********************************************************************************/

   	public String newOrder(String Date, String FirstName, String LastName, String Address, String Phone) throws Exception
	{
           CreateServicesAI obj = (CreateServicesAI) Naming.lookup("CreateServices"); 
           response = obj.newOrder(Date, FirstName, LastName, Address, Phone);
           return(response);	
		
    }
    
    /********************************************************************************
	* Description: Delete the order from the orderinfo database
	* Parameters: None
	* Returns: String that contains the status of the delete operation
	********************************************************************************/

   	public String deleteOrder(String orderid) throws Exception
	{
           DeleteServicesAI obj = (DeleteServicesAI) Naming.lookup("DeleteServices"); 
           response = obj.deleteOrder(orderid);
           return(response);	
		
    }
    
    

}