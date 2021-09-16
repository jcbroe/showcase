import java.util.Scanner;

public class Memory {
	//declarations, variable names should be self explanatory
	Reference ref; 
	int[] pageRemoved; 
	int[] pageCalled; 
	boolean[] pageFault; 
	int refLength; 
	int physFrameNum;
	int virtFrameNum;
	int[][] physMemory;
	Frame[] frame;
	String algorithm;
	
	//constructor
	Memory(Reference ref, int physFrameNum, int virtFrameNum) {
		this.ref = ref;
		refLength = ref.getLength();
		pageRemoved = new int[refLength];
		pageCalled = new int[refLength];
		this.physFrameNum = physFrameNum;
		this.virtFrameNum = virtFrameNum;
		physMemory = new int[ref.getLength()][physFrameNum];
		frame = new Frame[virtFrameNum];
		pageFault = new boolean[refLength];
	}
	
	//runs through the simulations
	void generate(String algorithm) {
		initialize();
		this.algorithm = algorithm;
		int cSlice = 0;
		int frameToInsert;
		int empty;
		int frameToReplace;
		int inMemory;
		// goes through each call of the simulation
		while (cSlice < refLength) {
			frameToInsert = ref.getAtIndex(cSlice);
			if (algorithm == "LRU") {
				frame[frameToInsert].setLast(cSlice);
			} else if (algorithm == "LFU") {
				frame[frameToInsert].increaseUsed();
			}
			empty = findIndex(physMemory[cSlice], -1);
			// for the page already being in memory
			inMemory = findIndex(physMemory[cSlice], frameToInsert);
			if (inMemory != -1) {
				//no fault
				pageCalled[cSlice] = inMemory;
				pageFault[cSlice] = false;
			}
			// if there's a space for it and it isn't in memory
			else if (empty >= 0) {
				pageCalled[cSlice] = empty;
				physMemory[cSlice][empty] = frameToInsert;
				frame[frameToInsert].setInsert(cSlice);
			}
			// not in memory and no empty space
			else {
				// find the frame to be pageRemoved depending on the algorithm
				switch (algorithm) {
					case "FIFO":
					// find the oldest
					frameToReplace = findOldest(physMemory[cSlice]);
					// update insertion
					frame[frameToInsert].setInsert(cSlice);
					break;
					case "OPT":
					// calculate next use
					calculateNext(cSlice);
					// find the least optimal
					frameToReplace = findLeastOptimal(physMemory[cSlice]);
					break;
					case "LFU":
					// find least recently used
					frameToReplace = findLfu(physMemory[cSlice]);
					break;
					case "LRU":
					// find least recently used
					frameToReplace = findLru(physMemory[cSlice]);
					// update information for last use
					break;
					default:
					System.out.println("Error: algorithm not recognized!");
					return;
				}
				// save pageRemoved frame
				pageRemoved[cSlice] = physMemory[cSlice][frameToReplace];
				// save new frame spot
				pageCalled[cSlice] = frameToReplace;
				// insert the new frame
				physMemory[cSlice][frameToReplace] = frameToInsert;


			}
			//save the physical memory for the next call
			if ((cSlice + 1) < refLength) {
				for (int i = 0; i < physFrameNum; i ++) {
					physMemory[cSlice +1][i] = physMemory[cSlice][i];
				}
			}
			cSlice += 1;
		}
	}

	// find the first inserted Frame in an array
	int findOldest(int[] a) {
		int oldest = frame[a[0]].getInsert();
		int index = 0;
		int checking;
		for (int i = 1; i < a.length; i++) {
			checking = frame[a[i]].getInsert();
			if (checking < oldest) {
				oldest = checking;
				index = i;
			}
		}
		return index;
	}

	// find least frequently used frame in an array
	int findLfu(int[] a) {
		int index = 0;
		int lfu = frame[a[index]].getUsed();

		for (int i = 1; i < a.length; i++) {
			int temp = a[i];
			int tempUsed = frame[a[i]].getUsed();

			if (tempUsed < lfu) {
				index = i;
				lfu = tempUsed;
			}
		}

		return index;
	}

	// find least recently used frame in an array
	int findLru(int[] a) {
		int index = 0;
		int lru = frame[a[index]].getLast();

		for (int i = 1; i < a.length; i++) {
			int temp = a[i];
			int tempUsed = frame[a[i]].getLast();

			if (tempUsed < lru) {
				index = i;
				lru = tempUsed;
			}
		}
		return index;
	}

	// find least optimal frame in an array
	int findLeastOptimal(int[] a) {
		int leastOptimal = a[0];
		int index = 0;
		int opt = frame[leastOptimal].getNext();
		for (int i = 1; i < a.length; i++) {
			int temp = a[i];
			int tempUsed = frame[temp].getNext();
			if (tempUsed > opt) {
				leastOptimal = temp;
				opt = frame[leastOptimal].getNext();
				index = i;
			}
		}
		return index;
	}

	// runs through each Frame and finds the next use
	void calculateNext(int n) {
		for (int i = 0; i < virtFrameNum; i++) {
			frame[i].setNext(refLength + 1);
		}
		for (int i = refLength - 1; i >= n; i--) {
			int called = ref.getAtIndex(i);
			frame[called].setNext(i);
		}
	}

	// initialize all the arrays used in generate()
	void initialize() {
		for (int i = 0; i < pageFault.length; i++) {
			pageFault[i] = true;
		}
		for (int i = 0; i < pageRemoved.length; i++) {
			pageRemoved[i] = -1;
		}
		for (int i = 0; i < pageCalled.length; i++) {
			pageCalled[i] = -1;
		}
		for (int i = 0; i < virtFrameNum; i++) {
			frame[i] = new Frame(i);
		}
		for (int i = 0; i < refLength; i++) {
			for (int j = 0; j < physFrameNum; j ++) {
				physMemory[i][j] = -1;
			}
		}
		algorithm = "";
	}

	// print the results of the simluation
	void print() {
		System.out.println("Information: ");
		System.out.println("Algorithm: " + algorithm);
		System.out.println("Length of reference string: " + refLength);
		System.out.println("Number of virtual pages: " + virtFrameNum);
		System.out.println("Number of physical pages: " + physFrameNum);
		System.out.println(" ");
		System.out.println("[] around a page number indicate it was called.");
		System.out.println("Press enter to step through snapshots of physical memory after each string call.");
		System.out.println("Or, enter \"q\" at any time to return to main menu.");

		Scanner sc = new Scanner(System.in);
		int sSlice = 0;
		String prompt;
		int frameNum;
		int removedInt;
		while (sSlice < refLength) {
			prompt = sc.nextLine();
			if (prompt.equals("q")) {
				System.out.println("Quitting printout.");
				break;
			}
			System.out.println("Snapshot at call " + (sSlice + 1) + ":");
			System.out.println("Program called virtual frame # " + ref.getAtIndex(sSlice));
			for (int i = 0; i < physFrameNum; i ++) {
				System.out.print("Physical frame " + i + ":");
				frameNum = physMemory[sSlice][i];
				if (frameNum >= 0) {
					if (i == pageCalled[sSlice]) {
						System.out.println("[" + frameNum + "]");
					} else {
						System.out.println(" " + frameNum);
					}
				} else {
					System.out.println("x");
				}
			}
			removedInt = pageRemoved[sSlice];
			System.out.println("Page fault: " + (pageFault[sSlice] ? "Yes." : "No."));
			System.out.println("Victim frame: " + (removedInt == -1 ? "None." : removedInt));
			sSlice += 1;
		}
		System.out.print("Simluation finished. Press enter to continue.");
		sc.nextLine();
	}

	int findIndex(int[] a, int n) {
		for (int i = 0; i < a.length; i++) {
			if (a[i] == n) {
				return i;
			}
		}
		return -1;
	}
}


