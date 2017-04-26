package tripleDES;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class IOController {
	
	private static final int BUFFER_SIZE = 1000000000;
	
	public byte[] readFile(String path) throws FileNotFoundException, IOException{		
		InputStream inStream = new FileInputStream(path);
		
		byte[] buffer;		
		if(inStream.available() < BUFFER_SIZE){
			buffer = new byte[inStream.available()];
		} else {
			buffer = new byte[BUFFER_SIZE];
		}		
		
		inStream.read(buffer);		
		
		return buffer;
	}
	
	public void writeFile(byte[] bytes, String path) throws FileNotFoundException, IOException {		
		OutputStream outStream = new FileOutputStream(path);
		
		outStream.write(bytes);		
	}	
	

}
