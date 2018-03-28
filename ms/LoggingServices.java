/******************************************************************************************************************
* File: LoggingServices.java
* Course: 17655
* Project: Assignment A3
* Copyright: Copyright (c) 2018 Carnegie Mellon University
* Versions:
*	1.0 February 2018 - Initial write of assignment 3 (ajl).
*
* Description: This class provides the concrete implementation of the create micro services. These services run
* in their own process (JVM).
*
* Parameters: None
*
* Internal Methods:
*  String Log(String data) - generates a log file and saves it to the system
*
* External Dependencies: 
*	- rmiregistry must be running to start this server
*	= MySQL
	- orderinfo database 
******************************************************************************************************************/
import java.rmi.Naming; 
import java.rmi.RemoteException; 
import java.rmi.server.UnicastRemoteObject;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Calendar;
import java.text.SimpleDateFormat;


public class LoggingServices extends UnicastRemoteObject implements LoggingServicesAI
{ 
    // Do nothing constructor
    public LoggingServices() throws RemoteException {}

    // Main service loop
    public static void main(String args[]) 
    { 	
    	// What we do is bind to rmiregistry, in this case localhost, port 1099. This is the default
    	// RMI port. Note that I use rebind rather than bind. This is better as it lets you start
    	// and restart without having to shut down the rmiregistry. 

        try 
        { 
            LoggingServices obj = new LoggingServices();

            // Bind this object instance to the name RetrieveServices in the rmiregistry 
            Naming.rebind("//localhost:1099/LoggingServices", obj); 

        } catch (Exception e) {

            System.out.println("LoggingServices binding err: " + e.getMessage()); 
            e.printStackTrace();
        } 

    } // main


    // Implementation of the abstract classes in RetrieveServicesAI happens here.

    // This method add the entry into the ms_orderinfo database

    public String LogData(String data) throws RemoteException
    {
      	
        String ReturnString = "Data Logged";	     	// Return string. If everything works you get an 'OK' message
        							                 
      
		// create buffered writers and file writer
		
		BufferedWriter bw = null;
		FileWriter fw = null;

		try {

			// open the file in the 
			File file = new File("."+ File.separator + "ms_log.txt");
	
			// if file does not exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			// true = append file
			fw = new FileWriter(file.getAbsoluteFile(), true);
			bw = new BufferedWriter(fw);

			String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
			
			bw.write("\r\n"+timeStamp+" \t "+data);


		} catch (IOException e) {

			System.out.println("Unable to write log to the file..." + e.toString());

		} finally {

			try {

				if (bw != null)
					bw.close();

				if (fw != null)
					fw.close();

			} catch (IOException ex) {

				System.out.println("Unable to close the file..." + ex.toString());

			}
		} 

        return(ReturnString);

    } //Log
    

} //LoggingServices