package com.sri.tcsb.db.util;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import com.sri.tcsb.env.TCSBEnvProp;
import com.sri.tcsb.env.TCSBPassword;
import com.sri.tcsb.env.TCSBReadEnv;
import com.sri.tcsb.logger.TCSBLogger;
import com.sri.tcsb.security.CipherTextInfo;

public class JdbcConnection {
	
	TCSBLogger log = TCSBLogger.getLogger(JdbcConnection.class.getName());
	public Connection getConnection() {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			log.error("Unable to load Driver :" + e.getMessage());
			return null;
		}
		Connection connection = null;
		try {
			String url = TCSBReadEnv.getEnvValue(TCSBEnvProp.DBUrl);
			String uname = TCSBReadEnv.getEnvValue(TCSBEnvProp.DBUserName);
			String pwd = TCSBPassword.getPassword(TCSBEnvProp.DBPwd);
			pwd = CipherTextInfo.decrypt(pwd);
			connection = DriverManager.getConnection(url, uname,pwd);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Connection Failed! Check output console :" + e.getMessage());
		}
		return connection;
	}
	
	public static void main(String[] a) throws Exception {
		JdbcConnection jcon = new JdbcConnection();
		System.out.println("connection is :"+ jcon.getConnection());
		Connection conn = jcon.getConnection();
		
		PreparedStatement pst = conn.prepareStatement("select * from users");
		
		ResultSet rs = pst.executeQuery();
		
		while (rs.next()) {	
			
			System.out.println("User id :" + rs.getInt(1));
			System.out.println("User Name :" + rs.getString(2));
		}
		rs.close();
		pst.close();
		conn.close();
	}
		

}