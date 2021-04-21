import java.text.DecimalFormat;
import java.text.NumberFormat;


public class IOBound extends Thread implements Runnable {

	//static holding the number of times to iterate through
	private static final int ITERATIONCOUNT = 1000;
	
	//this is the output string
	private String outStr = "All work and no play makes Jack a dull boy.";
	
	//this will keep track of the thread name
	private String threadName = "";
		
	//constructor, gets the thread name
	public IOBound(String threadName) {
		this.threadName = threadName;
	}
	
	//run method
	public void run() {
		//save start time
		long startTime = System.currentTimeMillis();
		//for formatting
		NumberFormat formatter = new DecimalFormat("#0.00000");
		//output the start time
		System.out.println("Starting thread "  + threadName + " at " + formatter.format((startTime) / 1000d) + " seconds");
		
		//IO loop
		for (int i = 0; i < ITERATIONCOUNT; i++) {			
			System.out.println(outStr);
		}
		
		//save the end time
		long endTime = System.currentTimeMillis();
		//output the end time
		System.out.println("Ending thread "  + threadName + " at " + formatter.format((endTime) / 1000d) + " seconds");
		//calculate/out the time it took to execute
        System.out.println("Execution time for " + threadName + " is " + formatter.format((endTime - startTime) / 1000d) + " seconds");
	}
}
