import java.text.DecimalFormat;
import java.text.NumberFormat;


public class CPUBound extends Thread implements Runnable {

	//static holding the number of times to iterate through
	private static final int ITERATIONCOUNT = 200;
	
	//this will keep track of the thread name
	private String threadName = "";
	
	//this is used to track start time
	private long timeAtStart = 0;
	
	//this is for the calculation
	private int basicMath = 1;
	
	//override the start to add in the current start time
	@Override
	public synchronized void start() {
        //override start method and save time when start method was invoked
        timeAtStart = System.currentTimeMillis();
        super.start();
    }
	
	//constructor, gets the thread name
	public CPUBound(String threadName) {
		this.threadName = threadName;
	}
	
	//run method
	public void run() {
		//save the time to run
		long startTime = System.currentTimeMillis();
		//for formatting
		NumberFormat formatter = new DecimalFormat("#0.00000");
		//output the start time
		System.out.println("Starting thread "  + threadName + " at " + formatter.format((startTime) / 1000d) + " seconds");
	
		
		//CPU loop
		for (int i = 0; i < ITERATIONCOUNT; i++) {			
			basicMath += i;
		}
		
		//save the end time
		long endTime = System.currentTimeMillis();
		//output the end time
		System.out.println("Ending thread "  + threadName + " at " + formatter.format((endTime) / 1000d) + " seconds");
		//calculate/out the time it took to execute
        System.out.println("Execution time for " + threadName + " is " + formatter.format((endTime - startTime) / 1000d) + " seconds");
	}
}
