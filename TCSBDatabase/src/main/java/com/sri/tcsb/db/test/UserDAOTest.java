package com.sri.tcsb.db.test;


import java.util.List;

import com.sri.tcsb.db.dao.UserDAO;
import com.sri.tcsb.db.dto.User;
import com.sri.tcsb.logger.TCSBLogger;



public class UserDAOTest {
	
	static TCSBLogger log = TCSBLogger.getLogger(UserDAOTest.class.getName());
	public static void main(String a[]) throws Exception{
		
		log.error("In User DAO  Test");
		addUser();
		dispAllcustomers();
		//updateCustomer();
		System.exit(0);
		
	}
	
	


	
	private static void dispAllcustomers()  throws Exception{
		UserDAO uDao = new UserDAO();
		List<User> users =  uDao.getAllUsers();
		User user = null;
		if (users!=null) {
		  int totCount = users.size();
		  for (int i = 0; i < totCount; i++) {  
			  user = (User) users.get(i);
			   System.out.println(" User id:" + user.getUid());
			   System.out.println(" Name :" + user.getUserName());
			   System.out.println(" Full Name :" + user.getFullName());
			   System.out.println(" Email :" + user.getEmail());
			   System.out.println(" Mobile :" + user.getPhone() );
			   System.out.println(" pwd :" + user.getPwd() );
			   System.out.println("_________________");
			  }
		}
	}
	

	private static void addUser() throws Exception {
		UserDAO uDao = new UserDAO();

		User user = new User();
		user.setUserName("Gopal");
		user.setFullName("Venugopal Durshetty");
		
		if (uDao.addUser(user)) {
			System.out.println("User Added successfully.");
		} 
	}
	
	
}
