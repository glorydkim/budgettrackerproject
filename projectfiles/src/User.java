/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectbudgettracker;

import java.util.ArrayList;

/**
 *
 * @author glorykim
 */
public class User {
    
    String  userName, password, firstName, lastName;
    int userID;
    ArrayList<Transaction> userTransactionList;
       
    User()
    {
        userTransactionList = new ArrayList<Transaction>();    
    }
    
    User(String userName, int userID)
    {
        this();
        this.userName = userName;
        this.userID = userID;
    }
    
    User(int userID, String userName,  String firstName, String LastName, String password)
    {
        this();
        this.userName = userName;
        this.password = password;
        this.firstName = firstName;
        this.lastName = LastName;
        this.userID = userID;
    }
    
   
    /*
    public void addNewTransaction(Transaction newTransaction)
    {
        
        userTransactionList.add(newTransaction);
        //Add this transaction into DB
    }
    
    public ArrayList<Transaction> getUserTransactions()
    {
        ArrayList<Transaction> transactionList = new ArrayList<Transaction>();
        Transaction t1 = new Transaction("Cat1", "desc1", "02/2/2017", 100);
        Transaction t2 = new Transaction("Cat2", "desc2", "02/2/2017", 1000);
        transactionList.add(t1);
        transactionList.add(t2);
        //Populate above list from DB and return
        return transactionList;
    }*/

   
}
