package com.sri.tcsb.env;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

import com.sri.tcsb.logger.TCSBLogger;

public class TCSBPassword {
	
	static TCSBLogger log = TCSBLogger.getLogger(TCSBPassword.class.getName());
	
	public static Properties getAllPasswords() {
		Properties prop = new Properties();
        try
        {
			String pwdFileName = TCSBReadEnv.getEnvValue(TCSBEnvProp.PasswordFile);
			File pwdFile = new File(pwdFileName);
			if (!pwdFile.exists()){
				log.debug("Password File '" + pwdFile.getAbsolutePath() + "' not found!");
			}
            prop.load(new FileInputStream(pwdFile));
            if (!prop.containsKey(TCSBEnvProp.DBPwd)){
            	prop.setProperty(TCSBEnvProp.DBPwd , "");
            } 
            if (!prop.containsKey(TCSBEnvProp.EmailPwd)){
            	prop.setProperty(TCSBEnvProp.EmailPwd , "");
            } 
            if (!prop.containsKey(TCSBEnvProp.SmsPwd)){
            	prop.setProperty(TCSBEnvProp.SmsPwd , "");
            } 
        }
        catch(IOException ex)
        {
        	log.error("Unable to fetch passwords from password file" + ex.getMessage());
        }
		return prop;
	}
	
	public static String getPassword(String pwdType) {
		String pwd = null;
		Properties prop = new Properties();
        try
        {
			String pwdFileName = TCSBReadEnv.getEnvValue(TCSBEnvProp.PasswordFile);
			File pwdFile = new File(pwdFileName);
			if (!pwdFile.exists()){
				log.debug("Password File '" + pwdFile.getAbsolutePath() + "' not found!");
				return null;
			}
            prop.load(new FileInputStream(pwdFile));
            if (prop.containsKey(pwdType)){
            	pwd = prop.getProperty(pwdType);
            } 
        }
        catch(IOException ex)
        {
        	log.error("Unable to fetch password from password file" + ex.getMessage());
        }
		return pwd;
	}
	
	public static boolean savePasswords(Properties passwords) {
		boolean status = false;
		try{
			String pwdFileName = TCSBReadEnv.getEnvValue(TCSBEnvProp.PasswordFile);
			File pwdFile = new File(pwdFileName);
			if (!pwdFile.exists()){
				pwdFile.createNewFile();
			}
			
			String pwdLines="";
			if (passwords.containsKey(TCSBEnvProp.DBPwd)) {
				pwdLines = TCSBEnvProp.DBPwd + "=" + passwords.get(TCSBEnvProp.DBPwd) + "\n";
			} else {
				pwdLines = TCSBEnvProp.DBPwd + "="  + "\n";
			}
			if (passwords.containsKey(TCSBEnvProp.EmailPwd)) {
				pwdLines = pwdLines + TCSBEnvProp.EmailPwd + "=" + passwords.get(TCSBEnvProp.EmailPwd) + "\n";
			} else {
				pwdLines = pwdLines + TCSBEnvProp.EmailPwd + "=" + "\n";
			}
			if (passwords.containsKey(TCSBEnvProp.SmsPwd)) {
				pwdLines = pwdLines + TCSBEnvProp.SmsPwd + "=" + passwords.get(TCSBEnvProp.SmsPwd) + "\n";
			} else {
				pwdLines = pwdLines + TCSBEnvProp.SmsPwd + "=" + "\n";
			}
			writeToFile(pwdFileName,pwdLines);
		}catch(Exception e){
        	log.error("Unable to save passwords into password file" + e.getMessage());
		}
		return status;
	}
	
	private static boolean writeToFile(String fileName,String pwdLines) throws Exception{
		boolean status=false;
		FileWriter fw = new FileWriter(fileName,false);
		fw.write(pwdLines);
        fw.close();
		return status;
	}
	

}
