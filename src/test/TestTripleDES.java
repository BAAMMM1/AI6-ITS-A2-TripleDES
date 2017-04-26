package test;

import java.io.IOException;

import tripleDES.TripleDES;

public class TestTripleDES {
	
	public void testTripleDES(){
		TripleDES tripleDES = new TripleDES();
		try {
			tripleDES.start("test/3DESTest.enc", "test/3DESTest.key", "test/TestOut3.pdf", "encrpyt");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		TestTripleDES testTripleDES = new TestTripleDES();
		testTripleDES.testTripleDES();		

	}

}
