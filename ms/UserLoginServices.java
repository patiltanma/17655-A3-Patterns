/******************************************************************************************************************
* File: UserLoginServices.java
* Course: 17655
* Project: Assignment A3
* Copyright: Copyright (c) 2018 Carnegie Mellon University
* Versions:
*	1.0 February 2018 - Initial write of assignment 3 (ajl).
*
* Description: This class provides the concrete implementation of the user login micro services. These services run
* in their own process (JVM).
*
* Parameters: None
*
* Internal Methods:
*  String addUserInfo(String username, String password) - adds new user with his username and password to the database
*  String retrieveUserInfo(String username) - gets and returns the user information
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

public class UserLoginServices extends UnicastRemoteObject implements UserLoginServicesAI
{ 
    // Set up the JDBC driver name and database URL
    static final String JDBC_CONNECTOR = "com.mysql.jdbc.Driver";  
    static final String DB_URL = "jdbc:mysql://localhost/ms_orderinfo?autoReconnect=true&useSSL=false";

    // Set up the orderinfo database credentials
    static final String USER = "root";
    static final String PASS = "2909"; //replace with your MySQL root password

    // Do nothing constructor
    public UserLoginServices() throws RemoteException {}

    // Main service loop
    public static void main(String args[]) 
    { 	
    	// What we do is bind to rmiregistry, in this case localhost, port 1099. This is the default
    	// RMI port. Note that I use rebind rather than bind. This is better as it lets you start
    	// and restart without having to shut down the rmiregistry. 

        try 
        { 
            UserLoginServices obj = new UserLoginServices();

            // Bind this object instance to the name RetrieveServices in the rmiregistry 
            Naming.rebind("//localhost:1099/UserLoginServices", obj); 

        } catch (Exception e) {

            System.out.println("UserLoginServices binding err: " + e.getMessage()); 
            e.printStackTrace();
        } 

    } // main
 
    // This method add the user information into the ms_orderinfo database

    public String addUserInfo(String username, String password) throws RemoteException
    {
      	// Local declarations

        Connection conn = null;		                 // connection to the orderinfo database
        Statement stmt = null;		                 // A Statement object is an interface that represents a SQL statement.
        String ReturnString = "User Info Added.";	     // Return string. If everything works you get an 'OK' message
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
            
            String sql = "INSERT INTO USERS(username, password) VALUES (\""+username+"\",\""+password+"\")";
		  
		  
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

    } //addUserInfo
    
    
    // This method will returns the user information in the orderinfo database corresponding to the id
    // provided in the argument.

    public String retrieveUserInfo(String username) throws RemoteException
    {
      	// Local declarations

        Connection conn = null;		// connection to the orderinfo database
        Statement stmt = null;		// A Statement object is an interface that represents a SQL statement.
        String ReturnString = "[";	// Return string. If everything works you get an ordered pair of data
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

             //System.out.println("Creating statement...");
            stmt = conn.createStatement();
            
            String sql;
		  sql = "SELECT * FROM Users where username='" + username + "'";
            ResultSet rs = stmt.executeQuery(sql);

            // Extract data from result set. Note there should only be one for this method.
            // I used a while loop should there every be a case where there might be multiple
            // orders for a single ID.

            while(rs.next())
            {
                //Retrieve by column name
                String buffer = rs.getString("username");
                String password = rs.getString("password");

                ReturnString = ReturnString +"{username:"+username+", password:"+password+"}";
            }

            ReturnString = ReturnString +"]";

            //Clean-up environment

            rs.close();
            stmt.close();
            conn.close();
            stmt.close(); 
            conn.close();

        } catch(Exception e) {

            ReturnString = e.toString();
		  
        } 

	   System.out.println(ReturnString);
        return(ReturnString);

    } //retrieve order by id
    
} //UserLoginServices