public class Node {

	/*
	 * Node class contains the following types:- 
	 * data of Object type
	 * left pointer
	 * right pointer
	 * pointer to parent
	 */
	private Object token;
	private Object freq;
	private Object code;
	private Node left;
	private Node right;
	private Node parent;
		
	public Node(){
		this(null, null, null, null, null, null);
	}
	public Node(Object e, Object f, Object c, Node l, Node r, Node p){
		token = e;
		freq = f;
		code = c;
		left  = l;
		right = r;
		parent = p;
	}
	/*
	 * return token
	 */
	Object getToken(){
		return token;
	}
	/*
	 * return frequency
	 */
	Object getFreq(){
		return freq;
	}
	/*
	 * return code
	 */
	Object getCode(){
		return code;
	}
	/*
	 * return left child
	 */
	Node getLeft(){
		return left;
	}
	/*
	 * return right child
	 */
	Node getRight(){
		return right;
	}
	/*
	 * return parent of the present child
	 */
	Node getParent(){
		return parent;
	}
	/*
	 * set token for the node
	 */
	void setToken(Object newElem){
		token = newElem;
	}
	/*
	 * set frequency for the node
	 */
	void setFreq(Object newF){
		freq = newF;
	}
	/*
	 * set code for the node
	 */
	void setCode(Object kod){
		code = kod;
	}
	/*
	 * set left child of a node
	 */
	void setLeft(Node newLeft){
		left = newLeft;
	}
	/*
	 * set right child of the node
	 */
	void setRight(Node newRight){
		right = newRight;
	}
	/*
	 * set parent of the current node.
	 */
	void setParent(Node newParent){
		parent = newParent;
	}
}

