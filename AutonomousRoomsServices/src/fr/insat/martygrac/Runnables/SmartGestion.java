package fr.insat.martygrac.Runnables;

public class SmartGestion implements Runnable {
	
	
	
	
	public volatile boolean threadSuspended = false;

	public void pause() {
		threadSuspended = true;
	}
	
	public void resume() {
		threadSuspended = false;
	}
	
	@Override
	public void run() {
		while(true) {
			try {
                Thread.sleep(5000);
                
                if (threadSuspended) {
                    synchronized(this) {
                        while (threadSuspended);
                            //wait();
                    }
                }
            } catch (InterruptedException e){
            	e.printStackTrace();
            }
			if (true) {
				
			}
			if (true) {
				
			}
			if (true) {
				
			}
			System.out.println("Tourne");
		}
	}
}
