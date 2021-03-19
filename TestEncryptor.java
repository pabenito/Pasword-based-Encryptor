/*
 * 
 * 		Created by Pedro Aantonio Benito Rojano
 * 			at 04/11/2020
 * 
 */

import java.io.File;

import prEncriptador.Encryptor;

public class TestEncryptor {
	public static void main(String[] args) {
		String pasword;
		String msg;
		String testFileDirectory;
		String exampleFile;
		String exampleEncrypted;
		String exampleDecrypted;
		
		msg = "This will be encrypted and decrypted."; 		// Paste your message here.
		
		pasword = "Pasword";								// Write your Pasword here.   
		testEncryptor(msg, pasword);
		
		pasword = "Now the encrypt will be different.";		// write your Pasword here.   
		testEncryptor(msg, pasword);
		
		pasword = "lalalalalala";
		testEncryptor(msg, pasword);
		
		pasword = "lala";
		testEncryptor(msg, pasword);
		
		testFileDirectory = "G:/uni/ADA/WorkSpace/prEncryptor/TestFiles/";
		exampleFile = testFileDirectory + "Example.txt";
		exampleEncrypted = testFileDirectory + "ExampleEncrypted.txt";
		exampleDecrypted = testFileDirectory + "ExampleDecrypted.txt";
		
		testEncryptorFile(exampleFile, exampleEncrypted, exampleDecrypted, pasword);
	}
	
	public static void testEncryptor(String msg, String pasword) {
		System.out.println("\nThis is your message:\n" + msg + "\n");
		msg = Encryptor.encrypt(msg, pasword);
		
		System.out.println("\nThis is your message encrypted:\n" + msg + "\n");
		msg = Encryptor.decrypt(msg, pasword);
		
		System.out.println("\nThis must be your message again if everything was right:\n" + msg + "\n\n");
	}
	
	public static void testEncryptorFile(String exampleFile, String exampleEncrypted, String exampleDecrypted, String pasword) {
		testEncryptorFile(new File(exampleFile), new File(exampleEncrypted), new File(exampleDecrypted), pasword);
	}
	
	public static void testEncryptorFile(File exampleFile, File exampleEncrypted, File exampleDecrypted, String pasword) {
		Encryptor.encryptFile(exampleFile, exampleEncrypted, pasword);
		Encryptor.decryptFile(exampleEncrypted, exampleDecrypted, pasword);
	}

}
