package com.sri.tcsb.logger;
import org.apache.logging.log4j.core.Logger;


// This is a logger wrapper class and is required to be used in all the classes where the logging is needed.
public class TCSBLogger extends Logger{

	private static final long serialVersionUID = -3322235225498326070L;

	private TCSBLogger(String fqcn){
		super(TCSBLoggerContext.getContext(),fqcn, TCSBLoggerContext.getMessageFactory(fqcn) );
	}
	 
	public static TCSBLogger getLogger(String fqcn){
		return new TCSBLogger(fqcn);
	}
}