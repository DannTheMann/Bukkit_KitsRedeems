package org.hcraid.com.classicredeem;

import java.util.concurrent.TimeUnit;

public class Slim {

	public static long slimSeconds(int seconds) {
		
		TimeUnit.SECONDS.toDays(seconds);        
		 long second = TimeUnit.SECONDS.toSeconds(seconds) - (TimeUnit.SECONDS.toMinutes(seconds) *60);
		 
		 return second;
	}
	
	public static long slimMinutes(int seconds) {
		
		int day = (int)TimeUnit.SECONDS.toDays(seconds);        
		 long hours = TimeUnit.SECONDS.toHours(seconds) - (day *24);
		 long minute = TimeUnit.SECONDS.toMinutes(seconds) - (TimeUnit.SECONDS.toHours(seconds)* 60);
		 long second = TimeUnit.SECONDS.toSeconds(seconds) - (TimeUnit.SECONDS.toMinutes(seconds) *60);
		 return minute;
	}
	
	 /**
		* Converts an integer value taken as minutes 
		* into remaining hours of a day.
		* 
		* @param minutes The minutes to be cut into
		* a hour.
		* 
		* @return hoursRemainder The hours that are left in a day from
		* the minutes specified.
		*/
	public static long slimHours(int seconds) {
		
		int day = (int)TimeUnit.SECONDS.toDays(seconds);        
		 long hours = TimeUnit.SECONDS.toHours(seconds) - (day *24);
		 return hours;
	}
	
	 /**
		* Converts an integer value taken as minutes 
		* into the amount days possible.
		* 
		* @param minutes The minutes to be cut into
		* a day.
		* 
		* @return daysRemainder The amount of days that the minutes
		* specified would take up.
		*/
	public static long slimDays(int seconds) {
		
		int day = (int)TimeUnit.SECONDS.toDays(seconds);        
		 return day;
	}
	
}
