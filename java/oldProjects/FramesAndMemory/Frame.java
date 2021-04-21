//This is the frame object
class Frame {
	int number;
	int insert;
	int next;
	int last;
	int used;

	//constructor
	Frame(int n) {
		number = n;
		insert = -1;
		next = -1;
		last = -1;
		used = 0;
	}

	//functions for setting and getting attributes of frame object
	void setNumber(int number) {
		this.number = number;
	}
	int getNumber() {
		return number;
	}
	void setInsert(int insert) {
		this.insert = insert;
	}
	int getInsert() {
		return insert;
	}
	void setNext(int next) {
		this.next = next;
	}
	int getNext() {
		return next;
	}
	void setLast(int last) {
		this.last = last;
	}
	int getLast() {
		return last;
	}
	void increaseUsed() {
		used += 1;
	}
	int getUsed() {
		return used;
	}
}
