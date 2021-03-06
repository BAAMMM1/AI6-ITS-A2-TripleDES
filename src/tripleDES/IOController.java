package tripleDES;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class IOController {

	InputStream inBlockStream;
	
	private static final int BLOCK_SIZE = 8;

	public IOController(String KeyPath) throws FileNotFoundException {
		inBlockStream = new FileInputStream(KeyPath);
	}
	

	public LinkedList<byte[]> readFileInBlockList(String path) throws FileNotFoundException, IOException {
		LinkedList<byte[]> toReturn = new LinkedList<byte[]>();
		
		InputStream inStream = new FileInputStream(path);

		while (inStream.available() != 0) {
			//byte[] buffer = new byte[BLOCK_SIZE];
			byte[] buffer;
			if(inStream.available() < BLOCK_SIZE){
				buffer = new byte[BLOCK_SIZE];
			} else {
				buffer = new byte[BLOCK_SIZE];
			}			

			inStream.read(buffer);
			toReturn.add(buffer);
		}
		
		inStream.close();

		return toReturn;
	}


	public void writeFileBlockList(List<byte[]> file, String path) throws FileNotFoundException, IOException {
		OutputStream outStream = new FileOutputStream(path);
		
		byte[] bytes = new byte[file.size()*BLOCK_SIZE];

		int counter = 0;
		for (int i = 0; i < file.size(); i++) {
			
			for (int i2 = 0; i2 < file.get(i).length; i2++) {				
				bytes[counter] = file.get(i)[i2];
				counter++;
			}			
		}
		
		outStream.write(bytes);
		outStream.close();
		System.out.println("Beende Write");
	}

}
