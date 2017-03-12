/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectbudgettracker;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;

import java.util.Properties;



/**
 *
 * @author Travis
 * @author Glory
 */
public class SQLClass {
//variables to hold values for the users name and address to insert into the database

    String fname;
    String lname;
    String address;
    static Connection conn;
    String userID;
    String month;
    int trxYear;
    int day;
    String trxType;
    double trxAmount;
    String trxDesc;
    int envID;
//Constructor to create the object
    private String envName;

    public SQLClass() {

    }


    //method to create a connection to the database
    public void connectDB() throws SQLException, ClassNotFoundException, FileNotFoundException, IOException, InterruptedException {
        Class.forName("org.apache.derby.jdbc.ClientDriver");
        conn = DriverManager.getConnection("jdbc:derby://localhost:1527/BudgetManager;create=true;user=Manager;password=Password");
        System.out.print("Database Created");
        
        
      //  return conn;
    }
    //Added this method to allow the Database to Create a default user
    public void createDefaultUser(){
        try{
                PreparedStatement stmt = conn.prepareStatement("insert into manager.users "
                        + "(fname, username, lname, password, msalary) "
                        + "values (?, ?, ?, ?, ?)");
              
                stmt.setString(1, "user");
                stmt.setString(2, "user");
                stmt.setString(3, "user");
                stmt.setString(4, "password");
                stmt.setString(5, "0");
                stmt.executeUpdate();
                
                stmt.close();
                conn.commit();
              //  conn.close();
            } 
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    //method to create a new envelope when the user requests to create an envelope
    public void createEnvelope(int userID, String title, double budget,double txTotal) throws SQLException{


            try{
                PreparedStatement stmt = conn.prepareStatement("insert into manager.envelopes "
                    + "(userid, datecreated, envname,budget, trxtotal) values (?, ?, ?, ?, ?)");

                java.sql.Date sqlDate = new java.sql.Date(new java.util.Date().getTime());
                     
                stmt.setInt(1, userID);
                stmt.setDate(2, sqlDate);
                stmt.setString(3, title);
                stmt.setDouble(4, budget);
                stmt.setDouble(5,txTotal);
                stmt.executeUpdate();
                
                stmt.close();
                conn.commit();
              //  conn.close();
            } 
        catch (SQLException e) {
            e.printStackTrace();
        }

    }

    //method to get information about an envelope using ENVELOPE TITLE
    public LinkedHashMap<String, String> getEnvelope(String title) throws SQLException {

        //hash map to store the column header and data
        LinkedHashMap<String, String> envData = new LinkedHashMap<String, String>();

        try {
            PreparedStatement stmt = conn.prepareStatement("select * from manager.envelopes "
                    + "where envname = ?");
            stmt.setString(1, title);
            ResultSet rs = stmt.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int count = 1;
            //while statement, inside each value from the result is stored in the HashMap
            // The key is the column name from the database, which will be used in the 
            //GUI to get the data from the HashMap that is returned from this method
            while (rs.next()) {
                envData.put("ENVID", Integer.toString(rs.getInt("ENVID")));
                envData.put("USERID", Integer.toString(rs.getInt("USERID")));
                Date date = rs.getDate("DATECREATED");
                DateFormat df = new SimpleDateFormat("dd MMMM yyyy");
                String dfdate = df.format(date);
                envData.put("DATECREATED", dfdate);
                envData.put("ENVNAME", rs.getString("ENVNAME"));
                BigDecimal bd = rs.getBigDecimal("BUDGET");
                String bdString = bd.toString();
                envData.put("BUDGET", bdString);
                bd = rs.getBigDecimal("TRXTOTAL");
                bdString = bd.toString();
                envData.put("TRXTOTAL", bdString);

            }
            rs.close();
            stmt.close();
            conn.commit();
           // conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return envData;
    }
    public int getEnvelope2(String title) throws SQLException {

     
       
            PreparedStatement stmt = conn.prepareStatement("select * from manager.envelopes "
                    + "where envname = ?");
            stmt.setString(1, title);
            ResultSet rs = stmt.executeQuery();
             int envID = 0;
            //while statement, inside each value from the result is stored in the HashMap
            // The key is the column name from the database, which will be used in the 
            //GUI to get the data from the HashMap that is returned from this method
            while (rs.next()) {

                envID = rs.getInt("ENVID");
               

            }
            rs.close();
            stmt.close();
            conn.commit();
           // conn.close();
       


        return envID;
    }
    //Overload method to get information about an envelope using ENVELOPE ID
    public LinkedHashMap<String, String> getEnvelope(int envID) throws SQLException {
        //hash map to store the column header and data
        LinkedHashMap<String, String> envData = new LinkedHashMap<String, String>();

        try {
  
            PreparedStatement stmt = conn.prepareStatement("select * from manager.envelopes "
                    + "where envid = ?");
            stmt.setInt(1, envID);
            ResultSet rs = stmt.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int count = 1;
            //while statement, inside each value from the result is stored in the HashMap
            // The key is the column name from the database, which will be used in the 
            //GUI to get the data from the HashMap that is returned from this method
            while (rs.next()) {
                envData.put("ENVID", Integer.toString(rs.getInt("ENVID")));
                envData.put("USERID", Integer.toString(rs.getInt("USERID")));
                Date date = rs.getDate("DATECREATED");
                DateFormat df = new SimpleDateFormat("dd MMMM yyyy");
                String dfdate = df.format(date);
                envData.put("DATECREATED", dfdate);
                envData.put("ENVNAME", rs.getString("ENVNAME"));
                BigDecimal bd = rs.getBigDecimal("BUDGET");
                String bdString = bd.toString();
                envData.put("BUDGET", bdString);
                bd = rs.getBigDecimal("TRXTOTAL");
                bdString = bd.toString();
                envData.put("TRXTOTAL", bdString);

            }
            rs.close();
            stmt.close();
            conn.commit();
           // conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return envData;
    }

    //method to return a HashMap list of all the envelopes available
    //The envID is the key and the value is the envelope title
    public LinkedHashMap<Integer, String> listEnvelopes() throws SQLException {
        
        LinkedHashMap<Integer, String> listEnv = new LinkedHashMap<Integer, String>();
    
        try {
            PreparedStatement stmt = conn.prepareStatement("select * from manager.envelopes");
            ResultSet rs = stmt.executeQuery();
            ResultSetMetaData rsmd = stmt.getMetaData();
            int count = 1;
            while (rs.next()) {
                //count is the envID (they start at 1 and auto increment)
                // may not always be the ENVID, but is easy to implement in the GUI
                // since the HashMap lists out the envelopes names starting with 1
                listEnv.put(count, rs.getString("ENVNAME"));
                count++;
            }
            rs.close();
            stmt.close();
            conn.commit();
          //  conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listEnv;
    }

      //method to return a HashMap list of alll transactions for the user
    public LinkedHashMap<Integer, Transaction> getUserTransactions(int userID) throws SQLException {
        
        LinkedHashMap<Integer, Transaction> transactionMap = new LinkedHashMap<Integer, Transaction>();
    
        try {
            PreparedStatement stmt = conn.prepareStatement("select * from manager.transactions where userid = ?");
            stmt.setInt(1,userID);
            ResultSet rs = stmt.executeQuery();
            ResultSetMetaData rsmd = stmt.getMetaData();
            int txID = 1;
            while (rs.next()) {
                String category = rs.getString("TRXTYPE");
                String description = rs.getString("TRXDESCRIPTION");
                double amount = rs.getDouble("TRXAMOUNT");
                
                String date = rs.getString("MONTH")+"/"+String.valueOf(rs.getString("DAY")+"/"+String.valueOf(rs.getString("TRXYEAR")));
                Transaction newTrans = new Transaction(category, description, date, amount);
                transactionMap.put(txID, newTrans);
                txID++;
            }
            rs.close();
            stmt.close();
            conn.commit();
          //  conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactionMap;
    }
    
    
    //method to return a HashMap list of alll transactions for the user
    public LinkedHashMap<Integer, Envelope> getUserAllEnvelopes(int userID) throws SQLException {
        
        LinkedHashMap<Integer, Envelope> envelopeMap = new LinkedHashMap<Integer, Envelope>();
    
        try {
            PreparedStatement stmt = conn.prepareStatement("select * from manager.envelopes where userid = ?");
            stmt.setInt(1,userID);
            ResultSet rs = stmt.executeQuery();
            ResultSetMetaData rsmd = stmt.getMetaData();
            while (rs.next()) {
                int envID = rs.getInt("ENVID");
                String envName = rs.getString("ENVNAME");
                double budget = rs.getDouble("BUDGET");
                double trxtotal = rs.getDouble("TRXTOTAL");
                Envelope newEnv = new Envelope(envName, budget, trxtotal);
                envelopeMap.put(envID,newEnv);
            }
            rs.close();
            stmt.close();
            conn.commit();
          //  conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return envelopeMap;
    }
    
    
    //method to add an item to an envelope (assigns an envelope ID to a transaction in the table)
    //Returns true if the statement executed properly and affected a row.
    public boolean addToEnvelope(int trxID, int envID) throws SQLException {

        boolean updateComplete = false;
        int returnValue;
        try {
            PreparedStatement stmt = conn.prepareStatement("update manager.transactions "
                    + "set ENVID = ? where TRXID = ?");
            stmt.setInt(1, envID);
            stmt.setInt(2, trxID);
            returnValue = stmt.executeUpdate();
            if (returnValue >= 1) {
                updateComplete = true;
            }
            stmt.close();
         //   conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return updateComplete;
    }

    //method to remove an item from an envelope (clears the db column in transactions for envelope id)
    public boolean removeFromEnvelope(int trxID, int envID) throws SQLException, ClassNotFoundException, IOException {
        boolean updateComplete = false;
        int returnValue;
        try {
            PreparedStatement stmt = conn.prepareStatement("update manager.transactions "
                    + "set ENVID = ? where TRXID = ?");
            //ENVELOPE id is set to 0, which means no envelope
            stmt.setInt(1, 0);
            stmt.setInt(2, trxID);
            returnValue = stmt.executeUpdate();
            if (returnValue >= 1) {
                updateComplete = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
            return updateComplete;
    }
   
    public boolean setUserSalary(int userID, double salary) throws SQLException, ClassNotFoundException, IOException {
        boolean updateComplete = false;
        int returnValue;
        try {
            PreparedStatement stmt = conn.prepareStatement("update manager.users "
                    + "set msalary = ? where userid = ?");
            //ENVELOPE id is set to 0, which means no envelope
            stmt.setDouble(1, salary);
            stmt.setInt(2, userID);
            returnValue = stmt.executeUpdate();
            
            if (returnValue >= 1) {
                updateComplete = true;
            }
            else
            {
                conn.rollback();
            }
            stmt.close();
            conn.commit();
   
        } catch (SQLException e) {
            e.printStackTrace();
        }
            return updateComplete;
    }
    
     public boolean setEnvelopeBudget(String envName, double budget) throws SQLException, ClassNotFoundException, IOException {
        boolean updateComplete = false;
        int returnValue;
        try {
            PreparedStatement stmt = conn.prepareStatement("update manager.envelopes "
                    + "set budget = ? where envname = ?");
            //ENVELOPE id is set to 0, which means no envelope
            stmt.setDouble(1, budget);
            stmt.setString(2, envName);
            returnValue = stmt.executeUpdate();
            if (returnValue >= 1) {
                updateComplete = true;
            }
            else
            {
                conn.rollback();
            }
            stmt.close();
            conn.commit();
   
        } catch (SQLException e) {
            e.printStackTrace();
        }
            return updateComplete;
    }
    
    
    //method to delete an envelope from the envelope table and remove it from all transactions
    public boolean deleteEnvelope(int envID) throws SQLException, ClassNotFoundException, IOException {
    
        this.envID = envID;
        boolean updateComplete = false;
        try {
      
            //in order to be able to rollback the transaction
          //  conn.setAutoCommit(false);
            //statement to set column ENVID to 0 for all transactions that reference this envelope that was deleted

            PreparedStatement stmt = conn.prepareStatement("update manager.transactions "
                    + "set envid = ? where envid = ?");
            stmt.setInt(1, 0);
       
            stmt.setInt(2, envID);
            int setEnvToZero = stmt.executeUpdate();
            stmt.executeUpdate();
          
            //statement to delete the record in the ENVELOPES table
            PreparedStatement stmt2 = conn.prepareStatement("delete from manager.envelopes "
                    + "where envID = ?");
    
            stmt2.setInt(1, envID);
  
            int deleteEnv = stmt.executeUpdate();

            //if all records associated with this envID have their envID set to 0 (no envelope)
            //AND if the envelope is properly deleted
          
            if (setEnvToZero >= 1 && deleteEnv >= 1) {
                updateComplete = true;
            } else {
                //rollback the transactions so that the envelope is not deleted
                //and the transactions still have their envelope id
                //could add functionality later to return error code for which part did not work
                //would require changing the return type of this method
                conn.rollback();
            }
         
            stmt2.close();
            conn.commit();
            //conn.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
            return updateComplete;
    }
   //method to delete an envelope from the envelope table and remove it from all transactions
    public void deleteEnvelope2(String envName, int envID) throws SQLException, ClassNotFoundException, IOException {
    
        this.envName = envName;
        this.envID = envID;
   
            PreparedStatement stmt = conn.prepareStatement("update manager.transactions "
                    + "set envid = ? where envID = ?");
            stmt.setInt(1, 0);
       
            stmt.setInt(2, envID);
            stmt.executeUpdate();
  
            //statement to delete the record in the ENVELOPES table
            PreparedStatement stmt2 = conn.prepareStatement("delete from manager.envelopes "
                    + "where envName = ?");
    
            stmt2.setString(1, envName);
  
            stmt2.executeUpdate();
  
    
         
            stmt2.close();
            conn.commit();
 
            
        
    }

    public int getUserFromDB(String userName) throws SQLException, ClassNotFoundException, IOException
     {
        // Create result set to return the effect of the following
        String sql2 = "SELECT userId FROM users WHERE users.username = ?";
        
        try{
            PreparedStatement usrPstmt = conn.prepareStatement(sql2);        
            usrPstmt.setString(1, userName);
            ResultSet rs = usrPstmt.executeQuery();
            //Get the user ID for the user name and return it  
            while (rs.next()) {
              return rs.getInt("USERID");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
           
        return -1;
               
     }
    
    public double getUserSalary(int userID) throws SQLException, ClassNotFoundException, IOException
     {
        // Create result set to return the effect of the following
        String sql2 = "SELECT msalary FROM users WHERE users.userid = ?";
        
        try{
            PreparedStatement usrPstmt = conn.prepareStatement(sql2);        
            usrPstmt.setInt(1, userID);
            ResultSet rs = usrPstmt.executeQuery();
            //Get the user ID for the user name and return it  
            while (rs.next()) {
              return rs.getDouble("MSALARY");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
           
        return 0;
               
     }
    
    
     public int createNewUser(User newUser) throws ClassNotFoundException, IOException
    {
        
        //Insert User details into Database
        try{

                PreparedStatement stmt = conn.prepareStatement("insert into manager.users "
                        + "(fname, username, lname, password) "
                        + "values (?, ?, ?, ?)");
              
                stmt.setString(1, newUser.firstName);
                stmt.setString(2, newUser.userName);
                stmt.setString(3, newUser.lastName);
                stmt.setString(4, newUser.password);

                stmt.executeUpdate();
                conn.commit();
                conn.close();
       
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
        //IF DB insert is successful return 0
        //Else, return -1
        return 0;
    }
     
    //method to add a transaction
    public void addTransaction(int userID, String month, int trxYear, int day,
            String trxType, double trxAmount, String trxDesc) throws SQLException, ClassNotFoundException, IOException {
       
        
        //use prepared statements to add the items to the transactions table
        //transaction id autoincrements 
        try{
        PreparedStatement stmt = conn.prepareStatement("insert into manager.transactions "
                + "(userid, month, trxyear, day, envid, trxtype, trxamount, trxdescription) "
                + "values (?, ?, ?, ?, ?, ?, ?, ?)");
        stmt.setInt(1, userID);
        stmt.setString(2, month);
        stmt.setInt(3, trxYear);
        stmt.setInt(4, day);
        LinkedHashMap<String,String> envDetMap = getEnvelope(trxType);
        int envID = Integer.parseInt(envDetMap.get("ENVID"));
        
        stmt.setInt(5, envID);
        stmt.setString(6, trxType);
        stmt.setDouble(7, trxAmount);
        stmt.setString(8, trxDesc);
        stmt.executeUpdate();
        conn.commit();
      //  conn.close();
       
        } catch (SQLException e) {
            e.printStackTrace();
        }
            
    }
    
    public void createTables() throws SQLException
    {
         try{
                this.conn.setAutoCommit(true);
                
                Statement createUsersTblStmt = conn.createStatement();
                createUsersTblStmt.addBatch("CREATE TABLE USERS (USERID "
                + "INTEGER GENERATED ALWAYS AS IDENTITY " +
                "(START WITH 1, INCREMENT BY 1) not null primary key," +
                "FNAME VARCHAR(50) not null," +
                "LNAME VARCHAR(50)," 
                +"MSALARY DECIMAL(9,2) not null," 
                + "USERNAME VARCHAR(50) not null, " +
                "PASSWORD VARCHAR(50) not null)");
                
                Statement createTrxTblStmt = conn.createStatement();
                createTrxTblStmt.addBatch("CREATE TABLE TRANSACTIONS (TRXID "
                + "INTEGER GENERATED ALWAYS AS IDENTITY " +
                "(START WITH 1, INCREMENT BY 1) not null, " +
                "USERID INTEGER not null "
                + " REFERENCES USERS(USERID), " +
                "MONTH VARCHAR(10) not null, " +
                "TRXYEAR INTEGER not null, " +
                "DAY INTEGER not null, " +
                "ENVID INTEGER not null "
                + " REFERENCES ENVELOPES(ENVID), " +
                "TRXTYPE VARCHAR(50), " +
                "TRXAMOUNT DECIMAL(8,2), " +
                "TRXDESCRIPTION VARCHAR(250))");
                 
                Statement createEnvTblStmt = conn.createStatement();
                createEnvTblStmt.addBatch("CREATE TABLE ENVELOPES " +
                "(ENVID INTEGER GENERATED ALWAYS AS IDENTITY " +
                "(START WITH 1, INCREMENT BY 1) not null primary key, " +
                "USERID INTEGER not null "
                + " REFERENCES USERS(USERID), " +
                "DATECREATED DATE, " +
                "ENVNAME VARCHAR(50) not null, " +
                "BUDGET DECIMAL(9,2), " +
                "TRXTOTAL DECIMAL(9,2))");
                
                Statement createHxTblStmt = conn.createStatement();
                createHxTblStmt.addBatch("CREATE TABLE HISTORIES(" +
                "MONTH INTEGER not null, " +
                "TRXYEAR INTEGER not null, " +
                "TOTALBUDGET DECIMAL(9,2) not null, " +
                "ENVTOTALS DECIMAL(9,2) not null, " +
                "USERID INTEGER not null "
                + " REFERENCES USERS(USERID), " +
                "primary key (MONTH, TRXYEAR))");
                
                
                //ONLY CREATE TABLES IF THEY DONT EXIST
                DatabaseMetaData dbmd = conn.getMetaData();
                ResultSet rs = dbmd.getTables(null, "MANAGER", "USERS", null);
                if(!rs.next())
                {
                     createUsersTblStmt.executeBatch();
                }
                
                ResultSet rs2 = dbmd.getTables(null, "MANAGER", "ENVELOPES", null);
                if(!rs2.next())
                {
                     createEnvTblStmt.executeBatch();
                }
                
                
                ResultSet rs3 = dbmd.getTables(null, "MANAGER", "TRANSACTIONS", null);
                if(!rs3.next())
                {
                      createTrxTblStmt.executeBatch();
                }
                
                ResultSet rs4 = dbmd.getTables(null, "MANAGER", "HISTORIES", null);
                if(!rs4.next())
                {
                    createHxTblStmt.executeBatch();
                }             
                
                
                conn.commit();
                        
            }
           
            catch( SQLException e ) {
                          conn.rollback();
                           System.out.println(e.getNextException().toString());
                           e.printStackTrace();
                 }      
        
    }

}
