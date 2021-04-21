import java.util.Scanner;
import java.io.*;
import java.util.ArrayList;

class Banker {
	//0-9 in an array declaration = 10
	public static final int MAX_PROCESSES = 9;
	public static final int MAX_RESOURCES = 9;
	static int totalSolutions = 0;

	public static void main(String[] args) {
		String fileName;
		// get the file name for input, either from args[] or prompt		
		if (args.length == 0) { // no string entered
			Scanner inType = new Scanner(System.in);
			System.out.printf("Enter name of file: ");
			fileName = inType.nextLine();
		} else { // file name entered
			fileName = args[0];
		}
		
		FileReader f = null;
		
		// open the file
		try {
			f = new FileReader(fileName);
		} catch (FileNotFoundException e) {
			System.out.println(e);
		}
	
		Scanner inFile = new Scanner(f);
		String s; // needed for reading the file
	 	int[] resArray = new int[MAX_RESOURCES];
		int curRes = 0; // resources at start
		Process[] procArray = new Process[MAX_PROCESSES];
		int processNo = 0;
	
		// read the file and data
		while (inFile.hasNextLine()) {
			s = inFile.nextLine();
			if ((s.length() > 0) && (s.charAt(0) == 'R')) { // is a resource line
				s = s.substring(4);
				if (s.charAt(0) == ' ') {
					s = s.substring(1); // this gets rid of any extra space
				}
				resArray[curRes] = Integer.parseInt(s);
				curRes += 1;
			}
			if ((s.length() > 0) && (s.charAt(0) == 'P')) { // process thread
				s = s.replaceAll("[^\\d]", " ");
				int[] pHeld = new int[MAX_RESOURCES];
				int[] pMax = new int[MAX_RESOURCES];
				Scanner p = new Scanner(s);
				p.nextInt();
				for (int i = 0; i < curRes; i++) {
					pHeld[i] = p.nextInt();
				}
				for (int i = 0; i < curRes; i++) {
					pMax[i] = p.nextInt();
				}
				procArray[processNo] = new Process(pHeld, pMax, processNo, curRes);

				processNo += 1;

			}
		}

		// calculate starting resources
		int[] currentresArray = resArray.clone();		
		for (int i = 0; i < processNo; i++) {
			int[] pa = procArray[i].gethResources();
			for (int j = 0; j < curRes; j++) {
				currentresArray[j] -= pa[j];
			}
		}
		
		//setup for the recursivelyCheck algorithm
		ArrayList<Process> processList = new ArrayList<Process>(); 
		ArrayList<Process> hist = new ArrayList<Process>(); 
		for (int i = 0; i < processNo; i++) {
			processList.add(procArray[i]);
		}
		
		// calculate safe paths
		System.out.println("Safe paths: ");
		long startTime = System.currentTimeMillis();
		recursivelyCheck(currentresArray, processList, hist);
		long endTime = System.currentTimeMillis();
		
		// print time elapsed
		if (totalSolutions == 0) {
			System.out.println("No solutions found.");
		} else {
			long totalTime = endTime - startTime;
			if ((totalTime < 1000)) {
				System.out.println(Integer.toString(totalSolutions) + " solution" +(totalSolutions > 1 ? "s": "") + " found in " + Long.toString(totalTime) + " milliseconds.");
			} else {
				double totalTimeInSeconds = (double) totalTime / 1000;
				System.out.println(Integer.toString(totalSolutions) + " solution" +(totalSolutions > 1 ? "s": "") + " found in " + Double.toString(totalTimeInSeconds) + " seconds.");
			}
		}	
	}

	//thank you to wikipedia and rosetta code for the help
	static void recursivelyCheck(int[] curRess, ArrayList<Process> processes, ArrayList<Process> hist) {
		for (Process p : processes) {
			// if the process can run and there are other processes on the list that need to be run
			if (p.canRun(curRess) && (processes.size() > 1)) {
				int[] newResources = curRess.clone();
				int[] proccessResources = p.gethResources();
				ArrayList<Process> newHist = new ArrayList<Process>(hist);
				newHist.add(p);
				ArrayList<Process> newProcesses = new ArrayList<Process>(processes);
				newProcesses.remove(p);
				for (int i = 0; i < curRess.length; i++) {
					newResources[i] = curRess[i] + proccessResources[i];
				}
				recursivelyCheck(newResources, newProcesses, newHist);
			// else if this process can run and it's the last process on the list	
			} else if (p.canRun(curRess)) {
				hist.add(p);
				totalSolutions += 1;
				for (Process proc : hist) {
					System.out.printf("P" + Integer.toString(proc.getNameAsInt() + 1) + " -> ");
				}
				System.out.printf("finished");
				System.out.println();
			}
		}
	}
}