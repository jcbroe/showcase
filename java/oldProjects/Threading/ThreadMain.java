import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;


public class ThreadMain {
	public static void main(String[] args) throws InterruptedException {
		NumberFormat formatter = new DecimalFormat("#0.00000");
				
		//creates 5 IOBound threads
/*		Thread IO1 = new Thread(new IOBound("IO1"));
		Thread IO2 = new Thread(new IOBound("IO2"));
		Thread IO3 = new Thread(new IOBound("IO3"));
		Thread IO4 = new Thread(new IOBound("IO4"));
		Thread IO5 = new Thread(new IOBound("IO5"));
		
		//creates 5 CPUBound threads
		Thread CPU1 = new Thread(new CPUBound("CPU1"));
		Thread CPU2 = new Thread(new CPUBound("CPU2"));
		Thread CPU3 = new Thread(new CPUBound("CPU3"));
		Thread CPU4 = new Thread(new CPUBound("CPU4"));
		Thread CPU5 = new Thread(new CPUBound("CPU5")); */
		
		ArrayList<String> IO = new ArrayList<String>();
		ArrayList<String> CPU = new ArrayList<String>();
		
		IO.add("IO1");
		IO.add("IO2");
		IO.add("IO3");
		IO.add("IO4");
		IO.add("IO5");
		CPU.add("CPU1");
		CPU.add("CPU2");
		CPU.add("CPU3");
		CPU.add("CPU4");
		CPU.add("CPU5");	
		
		long timeStartAll = System.currentTimeMillis();
		
		//these loops simulates FIFO processing
		
		for (int i = 0; i < IO.size(); i++) {
			Thread IOT = new Thread(new IOBound(IO.get(i)));
			IOT.start();
			IOT.join();
		}
		
		for (int i = 0; i < CPU.size(); i++) {
			Thread CPUT = new Thread(new CPUBound(CPU.get(i)));
			CPUT.start();
			CPUT.join();
		}
		
		
		// start the threads		 
/*		IO1.start();
		IO1.join();
		IO2.start();
		IO2.join();
		IO3.start();
		IO3.join();
		IO4.start();
		IO4.join();
		IO5.start();
		IO5.join();
		CPU1.start();
		CPU1.join();
		CPU2.start();
		CPU2.join();
		CPU3.start();
		CPU3.join();
		CPU4.start();
		CPU4.join();
		CPU5.start();
		CPU5.join(); */ //used feedback from Homework 2
		
		long timeEndAll = System.currentTimeMillis();

		System.out.println("Total execution time is " + formatter.format((timeEndAll - timeStartAll) / 1000d) + " seconds");
	}
}

