package fr.insat.martygrac.Runnables;

public class SmartGestion implements Runnable {
	
	boolean run;
	@Override
	public void run() {
		while(run) {
			try {
				Thread.sleep(5000);
				System.out.println("Tourne");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
