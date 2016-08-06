package org.jewelry.experiments;

public class StaticUnderstandSC extends StaticUnderstand{
	
	@Override
	
	public void display(){
		System.out.println("-------------------------------");
		System.out.println(tt);		
		System.out.println(tster);
		System.out.println("-------------------------------");
	}
	
	

	public static void main(String[] args){
		StaticUnderstandSC.hehe();
		StaticUnderstandSC su = new StaticUnderstandSC();
		su.display();
		StaticUnderstand.tt="Shanmuga Priya";
		su.display();
		
	}


}
