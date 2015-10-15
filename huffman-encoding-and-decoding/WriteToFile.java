import java.io.*;
import java.util.*;
import java.lang.*;
public class WriteToFile{
	private Formatter outputFile;
	public void openFile(String fname){
		try{
			//open this file to write
			outputFile = new Formatter(fname);
		}
		catch(Exception e){
			System.out.println("Could not open the file to write.");
		}
	}
	public void writeToFile(String msg){	
		try{
			//write to file the decoded or encoded message
			outputFile.format("%s\n", msg);
		}
		catch(Exception e){
			System.out.println("Could not write to the file.");
		}
	}
	public void closeFile(){	
		try{
			//close the file when done
			outputFile.close();
		}
		catch(Exception e){
			System.out.println("File unclosed!!");
		}
	}
}
