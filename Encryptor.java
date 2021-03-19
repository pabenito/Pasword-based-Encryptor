/*
 * 
 * 		Created by Pedro Aantonio Benito Rojano
 * 			at 04/11/2020
 * 
 */

package prEncriptador;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Encryptor {	
	
	// Class attributes
	
	private static final int ASCII_ESPACIO = 32;
	private static final int ASCII_COMA = 44;
	private static final int ASCII_PUNTO = 46;
	private static final int ASCII_0 = 48;
	private static final int ASCII_9 = 57;
	private static final int ASCII_UPPER_CASE_A = 65;
	private static final int ASCII_UPPER_CASE_Z = 90;
	private static final int ASCII_LOWER_CASE_A = 97;
	private static final int ASCII_LOWER_CASE_Z = 122;
	private static final int [] ASCIIS = 
			{32, 44, 46, 48, 49, 50, 51, 52, 53, 54, 55, 56, 
		     57, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 
		     75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 
		     86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 
		     102, 103, 104, 105, 106, 107, 108, 109, 
		     110, 111, 112, 113, 114, 115, 116, 117, 
		     118, 119, 120, 121, 122};
	
	// Variables
	
	private String pasword;
	
	// Constructors
	
	public Encryptor(String pasword) {
		setPasword(pasword);
	}
	
	// Getter & setters
	
	public String getPasword() {
		return pasword;
	}
	
	public void setPasword(String pasword) {		
		if(correctPasword(pasword)) {
			this.pasword = pasword;
		}else {
			throw new RuntimeException("Error. Pasword only accepts letters, numbers, points and spaces.");
		}
	}
	
	// Public methods
	
	public static void encryptFile(String directoryFileIn, String directoryFileOut, String pasword) {		
		encryptFile(new File(directoryFileIn), new File(directoryFileOut), pasword);
	}
	
	public static void decryptFile(String directoryFileIn, String directoryFileOut, String pasword) {		
		decryptFile(new File(directoryFileIn), new File(directoryFileOut), pasword);
	}
	
	public void encryptFile(String directoryFileIn, String directoryFileOut) {		
		encryptFile(new File(directoryFileIn), new File(directoryFileOut), pasword);
	}
	
	public void decryptFile(String directoryFileIn, String directoryFileOut) {		
		decryptFile(new File(directoryFileIn), new File(directoryFileOut), pasword);
	}
	
	public static void encryptFile(File fileIn, File fileOut, String pasword) {
		String line;
		String notProcessedYet = null;
		
		try(Scanner sc = new Scanner(fileIn)){
			try(PrintWriter pw = new PrintWriter(fileOut)){			
				while (sc.hasNextLine()) {
					line = sc.nextLine();
					if(notProcessedYet == null) {
						notProcessedYet = processLineEncrypt(pw, line, pasword) + "\n";
					}else {
						notProcessedYet = processLineEncrypt(pw, notProcessedYet + line, pasword) + "\n";
					}
				}
				pw.println(encrypt(notProcessedYet, pasword));
			}catch(IOException e) {
				System.out.println("Error. Cannot read output file.");
			}
		} catch (IOException e) {
			System.out.println("Error. Cannot read input file.");
		}
	}
	
	public static void decryptFile(File fileIn, File fileOut, String pasword) {
		String line;
		String notProcessedYet = null;
		
		try(Scanner sc = new Scanner(fileIn)){
			try(PrintWriter pw = new PrintWriter(fileOut)){			
				while (sc.hasNextLine()) {
					line = sc.nextLine();
					if(notProcessedYet == null) {
						notProcessedYet = processLineDecrypt(pw, line, pasword) + "\n";
					}else {
						notProcessedYet = processLineDecrypt(pw, notProcessedYet + line, pasword) + "\n";
					}
				}
				pw.println(decrypt(notProcessedYet, pasword));
			}catch(IOException e) {
				System.out.println("Error. Cannot read output file.");
			}
		} catch (IOException e) {
			System.out.println("Error. Cannot read input file.");
		}
	}
	
	public String encrypt(String msg) {
		return encrypt(msg, pasword);
	}
	
	public String decrypt(String msg) {
		return decrypt(msg, pasword);
	}
	
	public static String encrypt(String msg, String pasword) {
		char [] charMsg = msg.toCharArray();
		char [] charEncrypted = new char[charMsg.length];
		int [] shift = shift(pasword, charMsg.length);
		
		for(int pos = 0; pos < charMsg.length; pos++){
			charEncrypted[pos] = encrypt(charMsg[pos], shift[pos]);
		}
		
		return new String(charEncrypted);
	}
	
	public static String decrypt(String msg, String pasword) {
		char [] charMsg = msg.toCharArray();
		char [] charEncrypted = new char[charMsg.length];
		int [] shift = shift(pasword, charMsg.length);
		
		for(int pos = 0; pos < charMsg.length; pos++){
			charEncrypted[pos] = decrypt(charMsg[pos], shift[pos]);
		}
		
		return new String(charEncrypted);
	}
	
	public static char encrypt(char chr, int shift) {
		int ord;
		
		if(correctChar(chr)) {
			if(shift<0) {
				shift += ASCIIS.length;
			}
			ord = posOrdinal(chr) + shift;
			ord %= ASCIIS.length;
			chr = (char) ASCIIS[ord];
		}
		
		return chr;
	}
	
	public static char decrypt(char chr, int shift) {
		return encrypt(chr, -shift);
	}
	
	// Private methods
	
	public static String processLineEncrypt(PrintWriter pw, String line, String pasword) {
		while(line.length() > pasword.length()) {
			pw.print(encrypt(line.substring(0, pasword.length()), pasword));
			line = line.substring(pasword.length());
		}
		
		return line;
	}
	
	public static String processLineDecrypt(PrintWriter pw, String line, String pasword) {
		while(line.length() > pasword.length()) {
			pw.print(decrypt(line.substring(0, pasword.length()), pasword));
			line = line.substring(pasword.length());
		}
		
		return line;
	}
	
	private static boolean correctPasword(String pasword) {
		boolean correct = true;
		int posChar = 0;
		
		while(correct && posChar<pasword.length()) {
			if(!correctChar(pasword.charAt(posChar))) {
				correct = false;
			}
			posChar++;
		}
		
		return correct;
	}
	
	private static boolean correctChar(char chr) {
		int ord = chr;
		boolean correct = false;
		
		if(ord >= ASCII_LOWER_CASE_A && ord <= ASCII_LOWER_CASE_Z) {
			correct = true;
		}else if(ord >= ASCII_UPPER_CASE_A && ord <= ASCII_UPPER_CASE_Z) {
			correct = true;
		}else if(ord >= ASCII_0 && ord <= ASCII_9) {
			correct = true;
		}else if(ord == ASCII_ESPACIO || ord == ASCII_COMA || ord == ASCII_PUNTO) {
			correct = true;
		}
		
		return correct;
	}
	
	private static int [] shift (String pasword, int length) {
		int [] shift = new int [length];
		
		for(int pos = 0; pos < pasword.length(); pos++) {
			shift[pos] = posOrdinal(pasword.charAt(pos));
		}
		
		for(int pos = pasword.length(); pos < length; pos++) {
			shift[pos] = shift[pos-pasword.length()];
		}
		
		return shift;
	}
	
	private static int posOrdinal(char chr) {
		return posOrdinal(chr, 0, ASCIIS.length-1);
	}
	
	private static int posOrdinal(char chr, int lower, int upper) {
		int pos = (upper + lower)/2;
		int compare = Character.compare(chr, (char) ASCIIS[pos]);
		
		if(compare > 0){
			pos = posOrdinal(chr, pos+1, upper);
		}else if(compare <0){
			pos = posOrdinal(chr, lower, pos-1);
		}
		
		return pos;
	}
}
