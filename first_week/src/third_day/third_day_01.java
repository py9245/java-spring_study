package third_day;

public class third_day_01 {
	public static void main(String[] args) {
		BinaryTree bt = new BinaryTree();
		int[] arr = {6,4,1,2,6,3,6,88,4,32,4,3,63,24};
		for (int i : arr) {
			bt.insert(i);
		}
		bt.inorderTraversal();
	}
}
