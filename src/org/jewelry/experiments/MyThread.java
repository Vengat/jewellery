package org.jewelry.experiments;

public class MyThread implements Runnable{

	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("Thread running ");
		System.out.println(Thread.currentThread().getName());
	}
	
	public static void main(String[] args) throws Exception{
		Thread th = new Thread(new MyThread(),"Nagama Naidu");
		Thread th1 = new Thread(new MyThread(),"Ramar");
		Thread th2 = new Thread(new MyThread(),"Harini");
		th.start();
		th1.start();
		th2.start();
		
		System.out.println(Thread.currentThread().getName());

		th.stop();
		th1.stop();
		th2.stop();
	
	}

}
