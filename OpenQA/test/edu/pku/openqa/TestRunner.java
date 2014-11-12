package edu.pku.openqa;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestRunner {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Result result = JUnitCore.runClasses(BaiduCrawlerTest.class);
        for (Failure failure : result.getFailures()) { 
            System.out.println(failure.toString()); 
        } 
        System.out.println(result.wasSuccessful()? "all tests passed" : "at least one test failed.");
        
	} // end method main

} // end class TestRunner
