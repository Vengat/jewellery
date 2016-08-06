package org.jewelry.experiments;

public class StaticUnderstand {
	
	public static String tster = "Hello";
	
	protected static String tt = null;
	
	public void display(){
		System.out.println(tt);
		System.out.println(tster);
	}
	
	public static final String hehe(){
		tt="Hi";
		return tt;
	}
	
	public static void main(String[] args){
		StaticUnderstand.hehe();
		StaticUnderstand su = new StaticUnderstand();
		su.display();
		StaticUnderstand.tt="Shanmuga Priya";
		su.display();
		
	}
	

}


