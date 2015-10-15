import java.io.*;
import java.util.*;
import java.lang.*;
public class ReadFromFile {
	private Scanner reader;
	public void openFile(String fname){
		try{
			//open this file to read
			reader = new Scanner(new File(fname));
		}
		catch(Exception e){
			System.out.println("File not found!!. Exiting...");
			System.exit(0);
		}
	}
	public String readFile(){
		String content = "";
		try{
			//read the file
			while(reader.hasNextLine())
				content+=reader.nextLine()+"\n";
		}
		catch(Exception e){
			System.out.println("Could not read from the file");
		}
		content = content.substring(0, content.length()-1);
		return content;
	}
	public void closeFile(){	
		try{
			//close the file when done
			reader.close();
		}
		catch(Exception e){
			System.out.println("File unclosed!!");
		}
	}
}
