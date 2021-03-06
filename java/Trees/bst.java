    
/* Binary Search Tree
 * This is just the structure - no main for testing
 * To Do:
 * Add Comments
*/ 

import java.util.NoSuchElementException;
import java.util.LinkedList;
import java.util.Queue;

public class bst <Key extends Comparable<Key>, Value> {
	private Node root;
	
	private class Node {
		private Key key;
		private Value val;
		private Node left, right;
		private int size;
		
		public Node (Key key, Value val, int size) {
			this.key = key;
			this.val = val;
			this.size = size;
		}
	}
	
	public bst() {
		//empty table
	}
	
	public boolean isEmpty() {
		return size() == 0;
	}
	
	public int size() {
		return size(root);
	}
	
	private int size(Node x) {
		if (x == null) {
			return 0;
		} else {
			return x.size;
		}
	}
	
	public boolean contains(Key key) {
		if (key == null) {
			throw new IllegalArgumentException("argument to contains() is null");
		}
		return get(key) != null;
	}
	
	public Value get(Key key) {
		return get(root, key);
	}
	
	private Value get(Node x, Key key) {
		if (key == null) {
			throw new IllegalArgumentException("calls get() with a null key");
		}
		if (x == null) {
			return null;
		}
		int compare = key.compareTo(x.key);
		if (compare < 0) {
			return get(x.left, key);
		} else if (compare > 0) {
			return get(x.right, key);
		} else {
			return x.val;
		}
	}
	
	public void put(Key key, Value val) {
		if (key == null) {
			throw new IllegalArgumentException("calls put() with a null key");
		}
		if (val == null) {
			delete(key);
			return;
		}
		root = put(root, key, val);
		assert check();
	}
	
	private Node put(Node x, Key key, Value val) {
		if (x == null) {
			return new Node(key, val, 1);
		}
		int compare = key.compareTo(x.key);
		if (compare < 0) {
			x.left = put(x.left, key, val);
		} else if (compare > 0) {
			x.right = put(x.right, key, val);
		} else {
			x.val = val;
		}
		x.size = 1 + size(x.left) + size(x.right);
		return x;
	}
	
	public void deleteMin() {
		if (isEmpty()) {
			throw new NoSuchElementException("Symbol table underflow");
		}
		root = deleteMin(root);
		assert check();
	}
	
	private Node deleteMin(Node x) {
		if (x.left == null) {
			return x.right;
		}
		x.left = deleteMin(x.left);
		x.size = size(x.left) + size(x.right) + 1;
		return x;
	}
	
	public void deleteMax() {
		if (isEmpty()) {
			throw new NoSuchElementException("Symbol table underflow");
		}
		root = deleteMax(root);
		assert check();
	}
	
	private Node deleteMax(Node x) {
		if (x.right == null) {
			return x.left;
		}
		x.right = deleteMax(x.right);
		x.size = size(x.left) + size(x.right) + 1;
		return x;
	}
	
	public void delete(Key key) {
		if (key == null) {
			throw new IllegalArgumentException("calls delete() with a null key");
		}
		root = delete(root, key);
		assert check();
	}
	
	private Node delete(Node x, Key key) {
		if (x == null) {
			return null;
		}
		int compare = key.compareTo(x.key);
		if (compare < 0) {
			x.left = delete(x.left, key);
		} else if (compare > 0) {
			x.right = delete(x.right, key);
		} else {
			if (x.right == null) {
				return x.left;
			}
			if (x.left == null) {
				return x.right;
			}
			Node t = x;
			x = min(t.right);
			x.right = deleteMin(t.right);
			x.left = t.left;
		}
		x.size = size(x.left) + size(x.right) + 1;
		return x;
	}
	
	public Key min() {
		if (isEmpty()) {
			throw new NoSuchElementException("calls min() with empty symbol table");
		}
		return min(root).key;
	}
	
	private Node min(Node x) {
		if (x.left == null) {
			return x;
		} else {
			return min(x.left);
		}
	}
	
	public Key max() {
		if (isEmpty()) {
			throw new NoSuchElementException("calls max() with empty symbol table");
		}
		return max(root).key;
	}
	
	private Node max(Node x) {
		if (x.right == null) {
			return x;
		} else {
			return max(x.right);
		}
	}
	
	public Key floor(Key key) {
		if (key == null) {
			throw new IllegalArgumentException("argument to floor() is null");
		}
		if (isEmpty()) {
			throw new NoSuchElementException("calls floor() with empty symbol table");
		}
		Node x = floor(root, key);
		if (x == null) {
			return null;
		} else {
			return x.key;
		}
	}
	
	private Node floor(Node x, Key key) {
		if (x == null) {
			return null;
		}
		int compare = key.compareTo(x.key);
		if (compare == 0) {
			return x;
		}
		if (compare < 0) {
			return floor(x.left, key);
		}
		Node t = floor(x.right, key);
		if (t != null) {
			return t;
		} else {
			return x;
		}
	}
	
	public Key floor2(Key key) {
		return floor2(root, key, null);
	}
	
	private Key floor2(Node x, Key key, Key best) {
		if (x == null) {
			return best;
		}
		int compare = key.compareTo(x.key);
		if (compare < 0) {
			return floor2(x.left, key, best);
		} else if(compare > 0) {
			return floor2(x.right, key, x.key);
		} else {
			return x.key;
		}
	}

	public Key ceiling(Key key) {
		if (key == null) {
			throw new IllegalArgumentException("argument to ceiling() is null");
		}
		if (isEmpty()) {
			throw new NoSuchElementException("calls ceiling() with empty symbol table");
		}
		Node x = ceiling(root, key);
		if (x == null) {
			return null;
		} else {
			return x.key;
		}
	}
	
	private Node ceiling(Node x, Key key) {
		if (x == null) {
			return null;
		}
		int compare = key.compareTo(x.key);
		if (compare == 0) {
			return x;
		}
		if (compare < 0) {
			Node t = ceiling(x.left, key);
			if (t != null) {
				return t;
			} else {
				return x;
			}
		}
		return ceiling(x.right, key);
	}
	
	public Key select(int k) {
		if (k < 0 || k >= size()) {
			throw new IllegalArgumentException("argument to select() is invalid: " + k);
		}
		Node x = select(root, k);
		return x.key;
	}
	
	private Node select(Node x, int k) {
		if (x == null) {
			return null;
		}
		int t = size(x.left);
		if (t > k) {
			return select(x.left, k);
		} else if (t < k) {
			return select(x.right, k - t - 1);
		} else {
			return x;
		}
	}
	
	public int rank(Key key) {
		if (key == null) {
			throw new IllegalArgumentException("argument to rank() is null");
		}
		return rank(key, root);
	}
	
	private int rank(Key key, Node x) {
		if (x == null) {
			return 0;
		}
		int compare = key.compareTo(x.key);
		if (compare < 0) {
			return rank(key, x.left);
		} else if (compare > 0) {
			return 1 + size(x.left) + rank(key, x.right);
		} else {
			return size(x.left);
		}
	}
	
	public Iterable<Key> keys() {
		if (isEmpty()) {
			return new LinkedList<Key>();
		}
		return keys(min(), max());
	}
	
	public Iterable<Key> keys(Key lo, Key hi) {
		if (lo == null) {
			throw new IllegalArgumentException("first argument to keys() is null");
		}
		if (hi == null) {
			throw new IllegalArgumentException("second argument to keys() is null");
		}
		
		Queue<Key> queue = new LinkedList<Key>();
		keys(root, queue, lo, hi);
		return queue;
	}
	
	private void keys(Node x, Queue<Key> queue, Key lo, Key hi) {
		if (x == null) {
			return;
		}
		int compareLo = lo.compareTo(x.key);
		int compareHi = hi.compareTo(x.key);
		if (compareLo < 0) {
			keys(x.left, queue, lo, hi);
		}
		if (compareLo <= 0 && compareHi >= 0) {
			queue.add(x.key);
		}
		if (compareHi > 0) {
			keys(x.right, queue, lo, hi);
		}
	}
	
	public int size(Key lo, Key hi) {
		if (lo == null) {
			throw new IllegalArgumentException("first argument to size() is null");
		}
		if (hi == null) {
			throw new IllegalArgumentException("second argument to size() is null");
		}
		if (lo.compareTo(hi) > 0) {
			return 0;
		}
		if (contains(hi)) {
			return rank(hi) - rank(lo) + 1;
		} else {
			return rank(hi) - rank(lo);
		}
	}
	
	
	public int height() {
		return height(root);
	}
	
	private int height(Node x) {
		if (x == null) {
			return -1;
		}
		return 1 + Math.max(height(x.left), height(x.right));
	}
	
	public Iterable<Key> levelOrder() {
		Queue<Key> keys = new LinkedList<Key>();
		Queue<Node> queue = new LinkedList<Node>();
		queue.add(root);
		while(!queue.isEmpty()) {
			Node x = queue.remove();
			if (x == null) {
				continue;
			}
			keys.add(x.key);
			queue.add(x.left);
			queue.add(x.right);
		}
		return keys;
	}
	
	private boolean check() {
		if (!isBST()) {
			System.out.println("Not in symmetric order");
		}
		if (!isSizeConsistent()) {
			System.out.println("Subtree counts not consistent");
		}
		if (!isRankConsistent()) {
			System.out.println("Ranks not consistent");
		}
		return isBST() && isSizeConsistent() && isRankConsistent();
	}
	
	private boolean isBST () {
		return isBST(root, null, null);
	}
	private boolean isBST(Node x, Key min, Key max) {
		if (x == null) {
			return true;
		}
		if (min != null && x.key.compareTo(min) <= 0) {
			return false;
		}
		if (max != null && x.key.compareTo(max) >= 0) {
			return false;
		}
		return isBST(x.left, min, x.key) && isBST(x.right, x.key, max);
	}
	
	private boolean isSizeConsistent() {
		return isSizeConsistent(root);
	}
	
	private boolean isSizeConsistent(Node x) {
		if (x == null) {
			return true;
		}
		if (x.size != size(x.left) + size(x.right) + 1) {
			return false;
		}
		return isSizeConsistent(x.left) && isSizeConsistent(x.right);
	}
	
	private boolean isRankConsistent() {
		for (int i = 0; i < size(); i++) {
			if (i != rank(select(i)))  {
				return false;
			}
		}
		for (Key key : keys()) {
			if (key.compareTo(select(rank(key))) != 0) {
				return false;
			}
		}
		return true;
	}
}

