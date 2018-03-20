package com.sri.tcsb.logger;

import com.sri.tcsb.env.TCSBEnvProp;
import com.sri.tcsb.env.TCSBReadEnv;

//import org.apache.logging.log4j.ThreadContext;




public class LoggerTest {
	
	private static TCSBLogger log = TCSBLogger.getLogger(LoggerTest.class.getName());
	
		
	public static void main(String a[]) throws Exception{
		System.out.println("Log File Name is:" + TCSBReadEnv.getEnvValue(TCSBEnvProp.LoggerFileName));
		
	  for (int i=0;i<100;i++){
		log.debug("Hello");
	  log.debug("My Test Log Message");
	  }
	  System.out.println("Venu1 Logger Test Aysnc Logger - End");
	}

}
