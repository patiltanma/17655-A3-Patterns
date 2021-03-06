
/******************************************************************************************************************
* File:REST.js
* Course: 17655
* Project: Assignment A3
* Copyright: Copyright (c) 2018 Carnegie Mellon University
* Versions:
*   1.0 February 2018 - Initial write of assignment 3 for 2018 architectures course(ajl).
*
* Description: This module provides the restful webservices for the Server.js Node server. This module contains GET,
* and POST services.  
*
* Parameters: 
*   router - this is the URL from the client
*   connection - this is the connection to the database
*   md5 - This is the md5 hashing/parser... included by convention, but not really used 
*
* Internal Methods: 
*   router.get("/"... - returns the system version information
*   router.get("/orders"... - returns a listing of everything in the ws_orderinfo database
*   router.get("/orders/:order_id"... - returns the data associated with order_id
*   router.post("/users?"... - adds the new customer data into the ws_orderinfo database
*   router.delete("/user/:username"... - deletes the customers data from the ws_orderinfo database
*
* External Dependencies: mysql
*
******************************************************************************************************************/

var mysql   = require("mysql");     //Database
var fs = require('fs');			 //File System

function REST_ROUTER(router,connection) {
    var self = this;
    self.handleRoutes(router,connection);
}

// log data function definition
function logdata(content) {

	var date = new Date();
	
	// gets the current time and data and store it in a variable to print
	
	var timestring = date.getHours()+ ':' + date.getMinutes() + ':' + date.getSeconds();
	var datestring = date.getMonth()+ '/' + date.getDate() + '/' + date.getFullYear();
	
	// append the file with the date time stamp and  the logging data
	
	fs.appendFile('ws_log.txt', '\r\n' + datestring + ' \t ' + timestring + ' \t ' + content, function (err) {
		if (err) {
			console.log('Problem with logging content.');
		} 		  
	});
}

// Here is where we define the routes. Essentially a route is a path taken through the code dependent upon the 
// contents of the URL

REST_ROUTER.prototype.handleRoutes= function(router,connection) {

    // GET with no specifier - returns system version information
    // req parameter is the request object
    // res parameter is the response object

    router.get("/",function(req,res){
        res.json({"Message":"Orders Webservices Server Version 1.0"});
    });
    
    
   // GET for /users/username specifier - returns the user information
    // req parameter is the request object
    // res parameter is the response object
     
    router.get("/users/:username",function(req,res){
        console.log("Getting username: ", req.params.username );
	   logdata("Getting username: " + req.params.username );
	   
        var query = "SELECT * FROM ?? WHERE ??=?";
        var table = ["users","username",req.params.username];
        query = mysql.format(query,table);
        connection.query(query,function(err,rows){
            if(err) {
                res.json({"Error" : true, "Message" : "Error executing MySQL query"});
            } else {
                res.json({"Error" : false, "Message" : "Success", "Users" : rows});
            }
        });
    });
    
    
    // POST for /users?username&password - adds users
    // req parameter is the request object - note to get parameters (eg. stuff after the '?') you must use req.body.param
    // res parameter is the response object 
  
    router.post("/users",function(req,res){
        //console.log("url:", req.url);
        //console.log("body:", req.body);
        console.log("Adding to users table ", req.body.username,",",req.body.password);
	   logdata("Adding to users table " + req.body.username,",",req.body.password);
	   
        var query = "INSERT INTO ??(??,??) VALUES (?,?)";
        var table = ["users","username","password",req.body.username,req.body.password];
        query = mysql.format(query,table);
        connection.query(query,function(err,rows){
            if(err) {
                res.json({"Error" : true, "Message" : "Error executing MySQL query"});
            } else {
                res.json({"Error" : false, "Message" : "User Added !"});
            }
        });
    });
    
    
    // GET for /orders specifier - returns all orders currently stored in the database
    // req parameter is the request object
    // res parameter is the response object
  
    router.get("/orders",function(req,res){
        console.log("Getting all orders database entries..." );
	   logdata("Getting all orders database entries..." );
	   
        var query = "SELECT * FROM ??";
        var table = ["orders"];
        query = mysql.format(query,table);
        connection.query(query,function(err,rows){
            if(err) {
                res.json({"Error" : true, "Message" : "Error executing MySQL query"});
            } else {
                res.json({"Error" : false, "Message" : "Success", "Orders" : rows});
            }
        });
    });

    
    // GET for /orders/order id specifier - returns the order for the provided order ID
    // req parameter is the request object
    // res parameter is the response object
     
    router.get("/orders/:order_id",function(req,res){
        console.log("Getting order ID: ", req.params.order_id );
	   logdata("Getting order ID: " + req.params.order_id );
	   
        var query = "SELECT * FROM ?? WHERE ??=?";
        var table = ["orders","order_id",req.params.order_id];
        query = mysql.format(query,table);
        connection.query(query,function(err,rows){
            if(err) {
                res.json({"Error" : true, "Message" : "Error executing MySQL query"});
            } else {
                res.json({"Error" : false, "Message" : "Success", "Users" : rows});
            }
        });
    });

    
    // POST for /orders?order_date&first_name&last_name&address&phone - adds order
    // req parameter is the request object - note to get parameters (eg. stuff after the '?') you must use req.body.param
    // res parameter is the response object 
  
    router.post("/orders",function(req,res){
        //console.log("url:", req.url);
        //console.log("body:", req.body);
        console.log("Adding to orders table ", req.body.order_id,",",req.body.first_name,",",req.body.last_name,",",req.body.address,",",req.body.phone);
	   logdata("Adding to orders table " +req.body.order_id+","+req.body.first_name+","+req.body.last_name+","+req.body.address+","+req.body.phone);
	   	   
        var query = "INSERT INTO ??(??,??,??,??,??) VALUES (?,?,?,?,?)";
        var table = ["orders","order_date","first_name","last_name","address","phone",req.body.order_date,req.body.first_name,req.body.last_name,req.body.address,req.body.phone];
        query = mysql.format(query,table);
        connection.query(query,function(err,rows){
            if(err) {
                res.json({"Error" : true, "Message" : "Error executing MySQL query"});
            } else {
                res.json({"Error" : false, "Message" : "User Added !"});
            }
        });
    });
    
    
    // DELETE for /orders/:order_id - deletes order
    // req parameter is the request object
    // res parameter is the response object 
  
    router.delete("/orders/:order_id",function(req,res){

        console.log("Deleting order ID: ", req.params.order_id );
	   logdata("Deleting order ID: "+ req.params.order_id );
	   
        var query = "DELETE FROM ?? WHERE ??=?";
        var table = ["orders","order_id",req.params.order_id];
        query = mysql.format(query,table);
        connection.query(query,function(err,rows){
            if(err) {
                res.json({"Error" : true, "Message" : "Error executing MySQL query"});
            } else {
                res.json({"Error" : false, "Message" : "User Deleted !"});
            }
        });
    });
    
}

// The next line just makes this module available... thin+va

module.exports = REST_ROUTER;