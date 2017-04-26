package tripleDES;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;

public class TripleDES {

	private byte[] vektorDES;
	private byte[] keyDES1;
	private byte[] keyDES2;
	private byte[] keyDES3;
	DES desKey1;
	DES desKey2;
	DES desKey3;

	private IOController controller;

	private static final int BLOCK_SIZE = 8;

	public TripleDES() {
		super();
	}

	public void start(String pathIn, String pathKey, String pathOut, String operation)
			throws FileNotFoundException, IOException {

		this.initTripleDES(pathKey);

		if (operation.equals("encrpyt")) {
			this.cryptForward(pathIn, pathOut, true);

		} else if (operation.equals("decrypt")) {
			this.cryptForward(pathIn, pathOut, false);

		} else {
			System.out.println("Operation must be encrypt or decrypt");
		}

	}

	private void initTripleDES(String pathKey) throws FileNotFoundException, IOException {
		this.controller = new IOController(pathKey);
		LinkedList<byte[]> keyFile = this.controller.readFileInBlockList(pathKey);
		
		this.keyDES1 = keyFile.get(0);
		this.keyDES2 = keyFile.get(1);
		this.keyDES3 = keyFile.get(2);
		this.vektorDES = keyFile.get(3);

		this.desKey1 = new DES(keyDES1);
		this.desKey2 = new DES(keyDES2);
		this.desKey3 = new DES(keyDES3);
	}

	private void cryptForward(String pathIn, String pathOut, boolean encrpyt)
			throws FileNotFoundException, IOException {
		LinkedList<byte[]> fileInBlocks = this.controller.readFileInBlockList(pathIn);
		LinkedList<byte[]> toWriteBockes = new LinkedList<byte[]>();


		for (int i = 0; i < fileInBlocks.size(); i++) {

			byte[] leftBlock;
			if(i != 0){
				leftBlock = fileInBlocks.get(i - 1);
			} else {
				leftBlock = this.vektorDES;
			}

			byte[] rightBlock = fileInBlocks.get(i);

			byte[] leftBlockTripleCrypted;

			leftBlockTripleCrypted = this.cryptEDE(leftBlock);	

			byte[] newBlock = new byte[BLOCK_SIZE];

			for (int i2 = 0; i2 < BLOCK_SIZE; i2++) {
				newBlock[i2] = (byte) (leftBlockTripleCrypted[i2] ^ rightBlock[i2]);
			}

			toWriteBockes.add(newBlock);
		}

		this.controller.writeFileBlockList(toWriteBockes, pathOut);
	}

	private byte[] cryptEDE(byte[] plainText) {
		byte[] tripleCryptedCipherText = new byte[8];

		this.desKey1.encrypt(plainText, 0, tripleCryptedCipherText, 0);
		this.desKey2.decrypt(tripleCryptedCipherText, 0, tripleCryptedCipherText, 0);
		this.desKey3.encrypt(tripleCryptedCipherText, 0, tripleCryptedCipherText, 0);

		return tripleCryptedCipherText;
	}

	private byte[] cryptDED(byte[] chiffreText) {
		byte[] tripleCryptedCipherText = new byte[8];

		this.desKey3.decrypt(chiffreText, 0, tripleCryptedCipherText, 0);
		this.desKey2.encrypt(tripleCryptedCipherText, 0, tripleCryptedCipherText, 0);
		this.desKey1.decrypt(tripleCryptedCipherText, 0, tripleCryptedCipherText, 0);

		return tripleCryptedCipherText;
	}

	public static void main(String[] args) {
		if (args.length == 4) {
			TripleDES tripleDES = new TripleDES();

			try {
				tripleDES.start(args[0], args[1], args[2], args[3]);

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			throw new IllegalArgumentException("parameter: pathFile, pathKey, pathOut, operation");
		}
	}

}
