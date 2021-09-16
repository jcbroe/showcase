import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Driver {
	static final int VIRTUAL = 10;
	static final int PHYSICAL = 7;
	
	public static void main(String[] args) {
		int physFrameNum = readArgument(args);
		System.out.println("Number of page frames: " + physFrameNum);

		Scanner in = new Scanner(System.in);
		String line;
		Reference ref = null;
		Memory mem;

		// begin main loop
		while (true) {
			System.out.println();
			System.out.println("Select an option:");
			System.out.println("0 - Exit");
			System.out.println("1 - Read reference string");
			System.out.println("2 - Generate reference string");
			System.out.println("3 - Display current reference string");
			System.out.println("4 - Simulate FIFO");
			System.out.println("5 - Simulate OPT");
			System.out.println("6 - Simulate LRU");
			System.out.println("7 - Simulate LFU");
			System.out.println();

			// get input
			line = in.next();
			in.nextLine();
			switch (line) {
				case "0":
				System.out.println("Exiting...");
				System.exit(0);
				break;
				case "1":
				ref = readReference(in);
				checkString(ref);
				break;
				case "2":
				// generate reference string
				// get length
				System.out.println("How long is the reference string?");
				int stringSize = getStringSize(in);
				// generate the string
				ref = generateString(stringSize, VIRTUAL);
				// confirm
				checkString(ref);
				break;
				case "3":
				// print reference string
				if (ref != null) {
					System.out.print("Current reference string: ");
					ref.print();
				} else {
					System.out.println("No reference string entered.");
				}
				break;
				case "4":
				// check that Reference has been set:
				if (checkReference(ref)) {
					// create simulation
					mem = new Memory(ref, physFrameNum, VIRTUAL);
					mem.generate("FIFO");
					mem.print();
				}
				break;
				case "5":
				if (checkReference(ref)) {
					// create simulation
					mem = new Memory(ref, physFrameNum, VIRTUAL);
					mem.generate("OPT");
					mem.print();
				}
				break;
				case "6":
				if (checkReference(ref)) {
					// create simulation 
					mem = new Memory(ref, physFrameNum, VIRTUAL);
					mem.generate("LRU");
					mem.print();
				}
				break;
				case "7":
				if (checkReference(ref)) {
					// create simulation
					mem = new Memory(ref, physFrameNum, VIRTUAL);
					mem.generate("LFU");
					mem.print();
				}
				break;
				default:
				break;
			} 
		} 
	} 

	private static int readArgument(String[] args) {
		// check arguments
		if (args.length < 1) {
			System.out.println("An argument is needed for number of physical frames.");
			System.exit(-1);
		}
		if (args.length > 1) {
			System.out.println("Too many arguments. Using the first entered.");
		}
		// number of physical page frames
		int n = -1;

		// parse
		try {
			n = Integer.parseInt(args[0]);
		} catch(NumberFormatException e) {
			System.out.println("Must be an integer.");
			System.exit(-1);
		}

		// check n
		if (n < 1 || n > PHYSICAL) {
			System.out.println("Must be between 1 and " + (PHYSICAL) + " physical frames.");
			System.exit(-1);
		}

		return n;
	}

	static Reference readReference(Scanner in) {
		System.out.println("Enter a series of numbers: ");
		ArrayList<Integer> series = new ArrayList<Integer>();

		// create Reference
		Reference ref = null;

		do {
			// read in a line
			String line = in.nextLine();
			// create a scanner for that line
			Scanner lineScanner = new Scanner(line);
			// extract ints
			String temp;
			int tempInt = -1;
			boolean isInt;
			while (lineScanner.hasNext()) {
				temp = lineScanner.next();
				isInt = false;
				try {
					tempInt = Integer.parseInt(temp);
					isInt = true;
				} catch (NumberFormatException e) {
					System.out.println("Invalid input.");
				}
				if (isInt && (tempInt < 0 || tempInt >= VIRTUAL)) {
					System.out.println("Invalid input. Must be between 0 and " + (VIRTUAL - 1));
				} else if (isInt) {
					series.add(tempInt);
				}
			}
			if (series.size() < 1) {
				System.out.println("Input must be between 0 and 9. Please try again.");
			}
		} while (series.size() < 1);
		ref = new Reference(series);
		return ref;
	}

	static int getStringSize(Scanner in) {
		//parse int
		int stringSize = 0;
		while (stringSize < 1) {
			try {
				stringSize = in.nextInt();
			}
			catch (InputMismatchException e) {
				System.out.println("Input must be an integer.");
			}
			in.nextLine();
			if (stringSize < 1) {
				System.out.println("Input must be positive.");
			}
		}

		return stringSize;
	}

	static Reference generateString(int n, int max) {
		if (n < 1) {
			System.out.println("Unable to create a reference string shorter than 1.");
			return null;
		}
		Random rand = new Random();

		// create ArrayList for ints
		ArrayList<Integer> ints = new ArrayList<Integer>();
		// generate n random numbers
		for (int i = 0; i < n; i++) {
			ints.add(rand.nextInt(max));
		}
		// create Reference from list
		Reference ref = new Reference(ints);
		return ref;
	}

	static void checkString(Reference ref) {
		if (ref != null) {
			System.out.print("Reference string saved: ");
			ref.print();
		} else {
			System.out.println("Invalid reference string. Try again.");
		}
	}

	static boolean checkReference(Reference ref) {
		if (ref != null) {
			return true;
		}
		System.out.println("Reference string does not exist.");
		return false;
	}
}
