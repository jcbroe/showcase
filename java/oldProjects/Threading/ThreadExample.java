

/**
 * This class supports running a thread that adds money
 * to an account balance.  The account balance is a class
 * variable.
 */
public class ThreadExample implements Runnable {
	
	// class variable holding an accountBalance	 
	private static int accountBalance = 0;
	
	// static holding the number of times to increment 
	// the accountBalance
	private static final int ITERATIONCOUNT = 200;
	
	// name of the running thread	 
	private String threadName = "";
	
	/**
	 * Constructor
	 * @param threadName - name of the thread
	 */
	public ThreadExample(String threadName) {
		this.threadName = threadName;
	}
	
	/**
	 * run method
	 */
	public void run() {
		
		accountBalance = 0;
		
		for (int i = 0; i < ITERATIONCOUNT; i++) {			
			accountBalance += 1;
			System.out.println("Thread: " + threadName + " balance= " + accountBalance);
		}
	}

	/**
	 * main - it creates and starts 4 threads and then sleeps
	 *        for 15 seconds
	 * @param args
	 * @throws InterruptedException
	 */
}
