import java.util.*;

public class huffman {
	
	private static Scanner input;
	private static Node[] hash, hash2;
	private static Node[] heap;
	private static int maxr = 1000;
	private static int maxc = 2;
	private static int sz;
	
	public static void main(String[] args){
		System.out.println("Please give the input");
		input = new Scanner(System.in);
		String content = "";
		String tokn = "";
		int ascii = 0;
		q1 object = new q1();
		ReadFromFile reader = null;
		WriteToFile writer = null;
		String userInput="";
		while(input.hasNext())
			userInput = userInput+input.next()+" ";
		
		String job = String.valueOf(userInput.charAt(0));

		if(job.equals("e")){
		String[] file = userInput.split(" ");
		String fname = file[1]+".txt";//this file will be a text file
		reader = new ReadFromFile();
		reader.openFile(fname);
		content = reader.readFile();
		reader.closeFile();
		/*
		 * now the file is stored as such in the string content.
		 * next step is to create hash table to keep track of tokens
		 */
		int i = 0;
		tokn = "";
		ascii = 0;
		int duplicate = 0;
		hash = new Node[maxr];
		
		for(int r=0;r<maxr;r++)
			hash[r] = new Node();

		while(i<content.length()){
			char c = content.charAt(i);
			if(c>=65&&c<=90||c>=97&&c<=122){
				tokn+=c;
				ascii+=c;
			}
			else{
				int index;
				Node elem;
				if(tokn!=""){
					index = ascii%maxr;
					elem = new Node();
					elem.setToken(tokn);
					elem.setFreq(1);
					if(hash[index].getRight()==null){
						hash[index].setRight(elem);
					}
					else{
						Node top = hash[index];
						while(top.getRight()!=null){
							top = top.getRight();
							if(String.valueOf(top.getToken()).equals(String.valueOf(elem.getToken()))){
								top.setFreq(object.toInt(top.getFreq())+1);
								duplicate = 1;
								break;
							}
						}
					if(duplicate==0) top.setRight(elem);
					else duplicate = 0;
					}
					tokn = "";
					ascii = 0;
				}
				
				index = c%maxr;
				elem =  new Node();
				elem.setToken(c);
				elem.setFreq(1);
				if(hash[index].getRight()==null){
					hash[index].setRight(elem);
				}
				else{
					Node top = hash[index];
					while(top.getRight()!=null){
						top = top.getRight();
						if(String.valueOf(top.getToken()).equals(String.valueOf(elem.getToken()))){
							top.setFreq(object.toInt(top.getFreq())+1);
							duplicate = 1;
							break;
						}
					}
					if(duplicate==0) top.setRight(elem);
					else duplicate = 0;
				}
			}
			i++;
		}
		
		Node root = object.createHuffmanTree();
		root.setCode("");
		System.out.println("Generated Huffman Tree: ");
		object.printTree(root, "root", 0);
		/*
		 * at this stage...
		 * all the codes are stored in the hash table corresponding...
		 * to the token
		 */
		System.out.println();
		/*
		 * output this code to a text file...
		 * again read the text file...
		 * encode it using the code generated...
		 * output this code to binary file
		 */
		writer = new WriteToFile();
		writer.openFile("GeneratedCodes.txt");//this file will be a text file
		for(int g = 0; g<maxr; g++){
			if(hash[g].getRight()!=null){
				Node movable = hash[g];
				while(movable.getRight()!=null){
					movable = movable.getRight();
					String token = String.valueOf(movable.getToken());
					String code = String.valueOf(movable.getCode());
					String encoding = token+" "+code;
					writer.writeToFile(encoding);
				}
			}
		}
		writer.closeFile();
		/*
		 * The codes along with the token values have been stored...
		 * in the file named generatedCodes.txt
		 * this file will be inputted during decoding
		 */
		
		/*
		 * reading the file to be encoded again...
		 * this is however stored in the string content...
		 * use the code in the hash table to...
		 * output the encoding of content onto a binary file
		 */
		i=0;
		String encodedMsg = "";
		while(i<content.length()){
			char c = content.charAt(i);
			if(c>=65&&c<=90||c>=97&&c<=122){
				tokn+=c;
				ascii+=c;
			}
			else{
				int index;
				Node movable;
				if(tokn!=""){
					index = ascii%maxr;//at this index the token lies
					movable = hash[index];
					while(movable.getRight()!=null){
						movable = movable.getRight();
						if(String.valueOf(movable.getToken()).equals(tokn)){
							encodedMsg+=String.valueOf(movable.getCode());
							break;
						}
					}
					tokn = "";
					ascii = 0;
				}
				
				index = c%maxr;
				movable = hash[index];
				while(movable.getRight()!=null){
					movable = movable.getRight();
					if(String.valueOf(movable.getToken()).equals(String.valueOf(c))){
						encodedMsg+=String.valueOf(movable.getCode());
						break;
					}
				}
			}
			i++;
		}
		
		writer = new WriteToFile();
		writer.openFile("EncodedMessage.bin");//this file will be a binary file
		writer.writeToFile(encodedMsg);
		writer.closeFile();
		}
		/*
		 * now the encoding is done...
		 * and the output is written onto a binary file...
		 * the output could also be generated onto a text file
		 */
		
		/*
		 * now will perform decoding of given input file...
		 * 
		 */
		else if(job.equals("d")){
		String[] filenames = userInput.split(" ");
		String encodedFile = filenames[1]+".bin";//this will be a binary file
		String codes = filenames[2]+".txt";//this will be a text file
		/*
		 * read the binary file bit by bit...
		 * create a hash table with hash function based on binary code...
		 * obtain the corresponding token from the hash table using huffman tree outputted on text file...
		 * output the decoded information onto a text file
		 */
		
		reader = new ReadFromFile();
		reader.openFile(encodedFile);
		String encMsg = reader.readFile();//reading encoded message in this string
		reader.closeFile();
		
		reader = new ReadFromFile();
		reader.openFile(codes);
		String cod = reader.readFile();//reading codes here
		reader.closeFile();
		
		//organise cod into an array of two cols, one for token and one for code
		String[][] huff = new String[maxr][maxc];
		int r = 0, c = 0;
		for(r=0;r<maxr;r++)
			for(c=0;c<maxc;c++)
				huff[r][c] = "";
		r=0; c=0;
		
		for(int pos=0;pos<cod.length();pos++)
		{
			if(cod.charAt(pos)!=' '&&cod.charAt(pos)!=10)
				/*
				 * we need to check that the curr char is not newline o/w the else condition would never run!!
				 */
				huff[r][c] += cod.charAt(pos);/*populating the array with the text document*/
			else if(cod.charAt(pos)==' ')
			{ 
				c=1;
				/*
				 * if the next character is also space that means...
				 * we are dealing with space token...
				 * we need to insert this into the array then
				 */
				if(cod.charAt(pos+1)==' '){
					huff[r][0] += " ";
				}
			}
			else//newline character
			{		
				if(cod.charAt(pos+1)==' '&&cod.charAt(pos+2)!=' '){
					huff[r][0]+="\n";
				}
				else{
					r++;
					c=0;
				}
			}
		}
		/*
		 * no. of rows = r+1...
		 * col1 stores tokens and col2 stores the code...
		 * this is read from the text file...
		 */
		int i = 0;
		hash2 = new Node[maxr];
		for(i=0;i<maxr;i++)
			hash2[i] = new Node();
		
		for(i=0;i<=r;i++){
			int map = object.getIndex(huff[i][1])%maxr;
			Node elem2 = new Node();
			elem2.setToken(huff[i][0]);
			elem2.setCode(huff[i][1]);
			if(hash2[map].getRight()==null){
				hash2[map].setRight(elem2);
			}
			else{
				Node top2 = hash2[map];
				while(top2.getRight()!=null){
					top2 = top2.getRight();
				}
				top2.setRight(elem2);
			}
		}
		
		/*
		 * this concludes hashing the codes...
		 * on the basis of binary codes
		 * now will decode
		 */
		
		String guessCode = "";
		String reconstruct = "";
		i=0;
		while(i<encMsg.length()){
			guessCode+=encMsg.charAt(i);
			int possibleIndex = object.getIndex(guessCode)%maxr;
			Node t = hash2[possibleIndex];
			while(t.getRight()!=null){
				t = t.getRight();
				if(String.valueOf(t.getCode()).equals(guessCode)){
					reconstruct+=t.getToken();
					guessCode = "";
					break;
				}
			}
			i++;
		}
		
		writer = new WriteToFile();
		writer.openFile("ReconstructedFile.txt");
		writer.writeToFile(reconstruct);
		writer.closeFile();
		}
		input.close();
	}
	
	private Node createHuffmanTree(){
		heap = new Node[maxr];
		sz=0;
		Node newNode = null;
		for(int i=0; i<hash.length; i++){
			Node top = hash[i];
			while(top.getRight()!=null){
				newNode = new Node();
				newNode.setToken(top.getRight().getToken());
				newNode.setFreq(top.getRight().getFreq());
				top = top.getRight();
				heap[sz++] = newNode;
			}
		}
		//currently sz, these many nodes in heap array
		buildHeap();
		/*
		 * now we have single node trees...
		 * in the priority queue...
		 * implemented using array based heap data structure
		 */
		while(true){
			/*
			 * when the array has only one element...
			 * that is the complete tree...
			 * break and return the tree
			 */
			
			if(sz==1)
				break;
		
			int f1 = toInt(heap[0].getFreq());
			//String t1 = String.valueOf(heap[0].getToken());
			newNode = new Node();
			newNode.setFreq(f1);
			newNode.setLeft(heap[0]);
			heap[0].setParent(newNode);
			delete(0);
			
			f1 = toInt(heap[0].getFreq());
			//String t2 = String.valueOf(heap[0].getToken());
			newNode.setFreq(toInt(newNode.getFreq())+f1);
			newNode.setRight(heap[0]);
			heap[0].setParent(newNode);
			delete(0);
		
			/*
			 * insert newNode into the heap
			 */
			insert(newNode);
		
		}
		return heap[0];
	}
	private void buildHeap(){
		for(int i=sz-1;i>=0;i--){
			heapify(i);
		}
	}
	private void heapify(int i){
		int l = left(i);
		int r = right(i);
		int min = i;
		if(l<sz)
			if(toInt(heap[l].getFreq())<toInt(heap[i].getFreq()))
			min = l;
		if(r<sz)
			if(toInt(heap[r].getFreq())<toInt(heap[min].getFreq()))
				min = r;
		if(min!=i){
			swap(min, i);
			heapify(min);
		}
	}
	
	private int left(int i){
		return 2*i+1;
	}
	
	private int right(int i){
		return 2*i+2;
	}
	
	private void swap(int i, int j){
		Node temp = heap[i];
		heap[i] = heap[j];
		heap[j] = temp;
	}
	
	private void delete(int position){
		/*
		 * removes minimum frequency node from heap array
		 * followed by heapifying the existing heap
		 * everytime remove a node from heap, reduce sz by one
		 */
		heap[position] = heap[sz-1];
		heap[sz-1] = null;
		sz=sz-1;
		heapify(position);
	}
	
	private void insert(Node node){
		/*
		 * inserts new node into the heap
		 * followed by heapifying the existing heap
		 * everytime insert a node into the heap, increase sz by one 
		 */
		heap[sz] = node;
		sz=sz+1;
		buildHeap();
	}
	
	private void printTree(Node node, String position, int spacing){
		/*
		 * printing the huffman tree in preOrder
		 */
		if(node==null) return;
		/*
		 * indentation followed by printing nodes in preorder 
		 */
		for(int i=0;i<spacing;i++)
			System.out.print("\t");
		
		/*
		 * add a zero to the code of the parent if left subtree
		 * else add a one to it if right subtree
		 */
		if(position.equals("left")){
			node.setCode(node.getParent().getCode()+"0");
			/*
			 * search this token in hash and...
			 * set this code for this node...
			 * using setCode() property.
			 */
			int loc = searchHash(String.valueOf(node.getToken()));
			Node top = hash[loc];
			while(top.getRight()!=null){
				top = top.getRight();
				if(String.valueOf(top.getToken()).equals(String.valueOf(node.getToken())))
					top.setCode(node.getCode());
			}
		}
		else if(position.equals("right")){
			node.setCode(node.getParent().getCode()+"1");
			/*
			 * search this token in hash and...
			 * set this code for this node...
			 * using setCode() property.
			 */
			int loc = searchHash(String.valueOf(node.getToken()));
			Node top = hash[loc];
			while(top.getRight()!=null){
				top = top.getRight();
				if(String.valueOf(top.getToken()).equals(String.valueOf(node.getToken())))
					top.setCode(node.getCode());
			}
			
		}
		
		System.out.print("("+position+", ");
		System.out.println(node.getToken()+" "+node.getFreq()+" "+node.getCode()+")");
		spacing++;
		/*
		 * pass the same depth for each of the siblings through the variable spacing
		 * pass the position of the node as left or as right
		 * as the recursion calls parent, since spacing is a local variable, the previous value is popped
		 * and the child get the same depth and the parent reside at a depth one less than their childs.
		 */
		printTree(node.getLeft(), "left", spacing);
		printTree(node.getRight(), "right", spacing);
	} 
	
	private int searchHash(String token){
		int i=0;
		int sum=0;
		while(i<token.length()){
			sum+=token.charAt(i++);
		}
		return sum%maxr;
	}
	
	private int getIndex(String val){
		int sum = 0;
		int i=0;
		while(i<val.length()){
			sum+=val.charAt(i);
			i++;
		}
		return sum;
	}
	
	private int toInt(Object f){
		return Integer.parseInt(String.valueOf(f));
	}
}

