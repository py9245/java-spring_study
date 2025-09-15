package third_day;

import java.io.FileInputStream;
import java.util.Scanner;

public class Tree_01 {
	static class Node_1 {
		int value;
		Node_1 left, right;

		Node_1(int value) {
			this.value = value;
		}
	}

	static class Tree_1 {
		Node_1 root;

		public Tree_1(int rootval) {
			root = new Node_1(rootval);
		}

		void insert(int pare, int chil) {
			insertRecursive(root, pare, chil);
		}

		private void insertRecursive(Node_1 node, int pare, int chil) {
			if (node == null)
				return;

			if (node.value == pare) {
				if (node.left == null) {
					node.left = new Node_1(chil);
				} else {
					node.right = new Node_1(chil);
					return;
				}
			}
			
			insertRecursive(node.left, pare, chil);
			insertRecursive(node.right, pare, chil);
		}
        void preorder(Node_1 node) {
            if (node == null) return;
            System.out.print(node.value + " ");
            preorder(node.left);
            preorder(node.right);
        }

        void inorder(Node_1 node) {
            if (node == null) return;
            inorder(node.left);
            System.out.print(node.value + " ");
            inorder(node.right);
        }

        void postorder(Node_1 node) {
            if (node == null) return;
            postorder(node.left);
            postorder(node.right);
            System.out.print(node.value + " ");
        }
		
	}

    public static void main(String[] args) throws Exception {
    	System.setIn(new FileInputStream("res/input.txt"));
        Scanner sc = new Scanner(System.in);
        int V = sc.nextInt();

        Tree_1 tree = new Tree_1(1); // 루트는 항상 1번 노드

        for (int i = 0; i < V - 1; i++) {
            int p = sc.nextInt();
            int c = sc.nextInt();
            tree.insert(p, c);
        }

        tree.preorder(tree.root);
        System.out.println();
        tree.inorder(tree.root);
        System.out.println();
        tree.postorder(tree.root);
    }
}
