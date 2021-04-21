import java.util.ArrayList;

public class Reference {
	ArrayList<Integer> referenceString;
	
	//constructor
	Reference(ArrayList<Integer> referenceString) {
		this.referenceString = referenceString;
	}

	//3 functions for returning information
	int getLength() {
		return referenceString.size();
	}
	int getAtIndex(int i) {
		return referenceString.get(i);
	}
	void print() {
		for (int i = 0; i < referenceString.size() - 1; i++) {
			System.out.print(referenceString.get(i) + " ");
		}
	}
	

}
