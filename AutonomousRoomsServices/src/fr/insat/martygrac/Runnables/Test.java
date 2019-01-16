package fr.insat.martygrac.Runnables;

public class Test {
	public static void main(String[] args) {
		SmartGestion smart = new SmartGestion();
		Thread mThread = new Thread(smart);
		mThread.start();
		
		
		try {
			System.out.println("Started");
			Thread.sleep(11000);
			smart.pause();
			System.out.println("Paused");
			Thread.sleep(11000);
			smart.resume();
			System.out.println("Resumed");
			Thread.sleep(11000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
}
