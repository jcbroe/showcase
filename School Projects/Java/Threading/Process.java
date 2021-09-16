class Process {
	int processNumber;
	int resources;
	int[] hResources;
	int[] mResources;
	
	Process(int[] held, int[] max, int n, int r) {
		hResources = held;
		mResources = max;
		processNumber = n;
		resources = r;
	}
	
	public int[] gethResources() {
		return hResources;
	}
	
	public int getNameAsInt() {
		return processNumber;
	}
	
	public String getNameAsString() {
		return Integer.toString(processNumber);
	}
	
	boolean canRun(int[] available) {
		for (int i = 0; i < resources; i++) {
			if ((mResources[i] - hResources[i]) > available[i]) {
				return false;
			}
		}
		return true;
	}
	
	public void printInfo() {
		System.out.printf("Held resources for process " + Integer.toString(processNumber + 1) + ": ");
		for (int i = 0; i < resources; i++) {
			System.out.printf(hResources[i] + " ");
		}
		System.out.println();
		System.out.printf("Max resources for process " + Integer.toString(processNumber + 1) + ": ");
		for (int i = 0; i < resources; i++) {
			System.out.printf(mResources[i] + " ");
		}
		System.out.println();
	}
}