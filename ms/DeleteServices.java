/******************************************************************************************************************
* File: DeleteServices.java
* Course: 17655
* Project: Assignment A3
* Copyright: Copyright (c) 2018 Carnegie Mellon University
* Versions:
*	1.0 February 2018 - Initial write of assignment 3 (ajl).
*
* Description: This class provides the concrete implementation of the delete micro services. These services run
* in their own process (JVM).
*
* Parameters: None
*
* Internal Methods:
*  String deleteOrder() - deletes an order in the ms_orderinfo database with the user given order ID.
*
* External Dependencies: 
*	- rmiregistry must be running to start this server
*	= MySQL
	- orderinfo database 
******************************************************************************************************************/
import java.rmi.Naming; 
import java.rmi.RemoteException; 
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;

public class DeleteServices extends UnicastRemoteObject implements DeleteServicesAI
{ 
    // Set up the JDBC driver name and database URL
    static final String JDBC_CONNECTOR = "com.mysql.jdbc.Driver";  
    static final String DB_URL = "jdbc:mysql://localhost/ms_orderinfo?autoReconnect=true&useSSL=false";

    // Set up the orderinfo database credentials
    static final String USER = "root";
    static final String PASS = "2909"; //replace with your MySQL root password

    // Do nothing constructor
    public DeleteServices() throws RemoteException {}

    // Main service loop
    public static void main(String args[]) 
    { 	
    	// What we do is bind to rmiregistry, in this case localhost, port 1099. This is the default
    	// RMI port. Note that I use rebind rather than bind. This is better as it lets you start
    	// and restart without having to shut down the rmiregistry. 

        try 
        { 
            DeleteServices obj = new DeleteServices();

            // Bind this object instance to the name RetrieveServices in the rmiregistry 
            Naming.rebind("//localhost:1099/DeleteServices", obj); 

        } catch (Exception e) {

            System.out.println("DeleteServices binding err: " + e.getMessage()); 
            e.printStackTrace();
        } 

    } // main


    // Inplmentation of the abstract classes in DeleteServicesAI happens here.

    // This method deletes the entry from the ms_orderinfo database

    public String deleteOrder(String orderid) throws RemoteException
    {
      	// Local declarations

        Connection conn = null;		                 // connection to the orderinfo database
        Statement stmt = null;		                 // A Statement object is an interface that represents a SQL statement.
        String ReturnString = "Order Deleted";	     // Return string. If everything works you get an 'OK' message
        							                 // if not you get an error string
	
	 try
        {
            // Here we load and initialize the JDBC connector. Essentially a static class
            // that is used to provide access to the database from inside this class.

            Class.forName(JDBC_CONNECTOR);

            //Open the connection to the orderinfo database

            //System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            // Here we create the queery Execute a query. Not that the Statement class is part
            // of the Java.rmi.* package that enables you to submit SQL queries to the database
            // that we are connected to (via JDBC in this case).

            stmt = conn.createStatement();
	   
		  String sql = "DELETE FROM Orders where order_id=" + orderid;
	   
            // execute the update

            stmt.executeUpdate(sql);

            // clean up the environment

            stmt.close();
            conn.close();
            stmt.close(); 
            conn.close();

        } catch(Exception e) {

            ReturnString = e.toString();
        } 
        
        return(ReturnString);

    } //deleteOrder

} //DeleteServices