package third_day;

public class Node {
	int value;
	Node left, right, parent;
	
	public Node(int val) {
		this.value = val;
		left = right = parent = null;
	}
}
