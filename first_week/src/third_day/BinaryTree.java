package third_day;

public class BinaryTree {
	Node root;

	public BinaryTree() {
		this.root = null;
	}

	public void insert(int value) {
		if (root == null) {
			root = new Node(value);
		} else {
			insertRecursive(root, value);
		}
	}

	private void insertRecursive(Node node, int value) {
		if (value < node.value) {
			if (node.left == null) {
				node.left = new Node(value);
				node.left.parent = node;
			} else {
				insertRecursive(node.left, value);
			}
		} else {
			if (node.right == null) {
				node.right = new Node(value);
				node.right.parent = node;
			} else {
				insertRecursive(node.right, value);
			}
		}
	}

    public Node search(int value) {
        return searchRecursive(root, value);
    }

    private Node searchRecursive(Node node, int value) {
        if (node == null || node.value == value) {
            return node;
        }
        if (value < node.value) {
            return searchRecursive(node.left, value);
        } else {
            return searchRecursive(node.right, value);
        }
    }

	public void inorderTraversal() {
		inorderRecursive(root);
		System.out.println();
	}

	private void inorderRecursive(Node node) {
		if (node != null) {
			inorderRecursive(node.left);
			System.out.print(node.value + " ");
			inorderRecursive(node.right);
		}
	}
}
