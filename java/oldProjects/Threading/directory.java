/* Jacob Roe
 * Homework 5
 * CMSC412
 */

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

import javax.xml.bind.DatatypeConverter;


public class directory {
	
	public static void main(String[] args) {

	    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	    Path direct = null;
	    int input;
	    boolean stop = false;
	    
	    //loop until quit
	    while (!stop) {
	    	//display the options
	    	System.out.println("\nSelect Option:\n" +
	    			"\t(0) Exit\n" +
	    			"\t(1) Select Directory\n" +
	    	        "\t(2) List Directory Content (first level)\n" +
	    	        "\t(3) List Directory Content (all levels)\n" +
	    	        "\t(4) Delete File\n" +
	    	        "\t(5) Display File (hexadecimal view)\n" +
	    	        "\t(6) Encrypt File (XOR with password)\n" +
	    	        "\t(7) Decrypt File (XOR with password)");
	    	System.out.print("Choose > ");
	        
	    	//get the option as an int
	    	input = getNumInput(reader);
	 
	    	//to handle the different options cleanly without a mess of conditional statements
	    	switch (input) {
	    	//quit
	    		case 0:
	    			stop = true;
	    			break;
	    		
	    		//Select Directory	
	    		case 1:
	    			System.out.print("Please enter the path to directory > ");
	    			direct = Paths.get(getStrInput(reader));
	    			if (Files.isDirectory(direct)) {
	    				System.out.println("The directory is now: " + direct.toString());
	    			} else {
	    				System.out.println(direct.toString() + " is not a directory.");
	    				direct = null;
	    			}
	    			break;
	    		//tries to navigate to the path that is input
	    		
	    		//List the contents of the current directory
	    		case 2:
	    			if (direct != null) {
	    				try (DirectoryStream<Path> stream = Files.newDirectoryStream(direct)) {
	    					for (Path enter : stream)
	    						System.out.println(enter.getFileName());
	    				} catch (IOException e) {
	    					System.out.println(e.getMessage());
	    				}
	    			} else {
	    				System.out.println("You must select a directory.");
	    			}
	    			break;
	            //pretty self explanatory
	    			
	            //List the contents of the current directory and all subsequent directories
	    		case 3:
	    			if (direct != null) {
	    				try {
	    					Files.walkFileTree(direct, new DirectoryListing());
	    				} catch (IOException e) {
	    					System.out.println(e.getMessage());
	    				}
	    			} else {
	    				System.out.println("You must select a directory.");
	    			}
	    			break;
	    		//uses the DirectoryListing function below to go through the directories

	    		//Delete file	
	    		case 4:
	    			if (direct != null) {
	    				try {
	    					System.out.print("Please enter a target file to delete > ");
	    					Files.delete(direct.resolve(getStrInput(reader)));
	    					System.out.println("File deleted.");
	    				} catch (IOException e) {
	    					System.out.println(e.getMessage() + " does not exist.");
	    				}
	    			} else {
	    				System.out.println("You must select a directory first.");
	    			}
	    			break;
	    			//tries to delete the file with files.delete

	    		//Hexadecimal file display	
	    		case 5:
	    			if (direct != null) {
	    				System.out.print("Enter a target file to display > ");
	    				try (BufferedReader rdr =
	    						Files.newBufferedReader(direct.resolve(getStrInput(reader)), StandardCharsets.UTF_8)) {				
	    					String line;
	    					while ((line = rdr.readLine()) != null)
	    						System.out.println(toHexadecimal(line));
	    				} catch (IOException e) {
	    					System.out.println(e.getMessage() + " does not exist.");
	    				}
	    			} else {
	    				System.out.println("You must select a directory first.");
	    			}
	    			break;
	    			//uses the toHexadecimal from below for the output
	        
	    		//Encryption
	    		case 6:
	    			if (direct != null) {

	    				Path file, destination;
	    				byte[] atxt = null;
	    				byte[] password;

	    				System.out.print("Enter a target file to encrypt > ");
	    				file = direct.resolve(getStrInput(reader));
	    				if (Files.isRegularFile(file)) {
	    					try {
	    						System.out.println("Reading file into memory...");
	    						atxt = Files.readAllBytes(file);
	    					} catch (IOException e) {
	    						System.out.println("Error occurred reading: " + e.getMessage());
	    					}

	    					System.out.print("Enter a destination file > ");
	    					destination = direct.resolve(getStrInput(reader));
	    					try {
	    						Files.createFile(destination);
	    						System.out.print("Enter a password to use > ");
		    					password = getStrInput(reader).getBytes();

		    					if (atxt != null) {
		    						System.out.println("Encrypting...");
		    						byte[] btxt = new byte[atxt.length];

		    						for (int i = 0; i < atxt.length; ++i)
		    							btxt[i] = (byte) (atxt[i] ^ password[i % password.length]);

		    						try {
		    							Files.write(destination, btxt, StandardOpenOption.WRITE);
		    						} catch (IOException e) {
		    							System.out.println("There was a problem writing to: " + destination.toString());
		    						}

		    						System.out.println("Finished Encrypting: " + file.toString() + " -> into -> " + destination.toString());
		    					} else {
			    					System.out.println("There was an error accessing the file: " + file.toString());
			    				}
	    					} catch (IOException e) {
	    						System.out.println("Error occurred creating: " + e.getMessage());
	    					}
	    				} else {
	    					System.out.println(file.toString() + " does not exist.");
	    				}
	    			} else {
	    				System.out.println("Select a directory first.");
	    			}
	    			break;
	    			

	    		//Decryption
	    			case 7:
	    				if (direct != null) {

	    					Path file, destination;
	    					byte[] btxt = null;
	    					byte[] password;

	    					System.out.print("Enter an encrypted file > ");
	    					file = direct.resolve(getStrInput(reader));
	    					if (Files.isRegularFile(file)) {
	    						try {
	    							System.out.println("Reading file into memory...");
	    							btxt = Files.readAllBytes(file);
	    						} catch (IOException e) {
	    							System.out.println("Error occurred reading: " + e.getMessage());
	    						}

	    						System.out.print("Please enter a destination file > ");
	    						destination = direct.resolve(getStrInput(reader));
	    						try {
	    							Files.createFile(destination);
	    							System.out.print("Enter a password to use > ");
		    						password = getStrInput(reader).getBytes();

		    						if (btxt != null) {
		    							System.out.println("Decrypting...");
		    							byte[] atxt = new byte[btxt.length];

		    							for (int i = 0; i < btxt.length; ++i)
		    								atxt[i] = (byte) (btxt[i] ^ password[i % password.length]);
		    							
		    							try {
		    								Files.write(destination, atxt, StandardOpenOption.WRITE);
		    							} catch (IOException e) {
		    								System.out.println("There was a problem writing to: " + destination.toString());
		    							}

		    							System.out.println("Finished Decrypting: " + file.toString() + " -> into -> " + destination.toString());
		    						} else {
		    							System.out.println("There was an error accessing the file: " + file.toString());
		    						}
	    						} catch (IOException e) {
	    							System.out.println("Error occurred creating: " + e.getMessage());
	    						}
	    					} else {
	    						System.out.println(file.toString() + " does not exist!");
	    					}
	    				} else {
	    					System.out.println("Select a directory first.");
	    				}
	    				break;

	    			default:
	    				System.out.println("Invalid option, try again");
	         
	          
	    	}
	    }
	}
	
	//for getting numerical input
	private static int getNumInput(BufferedReader r) {
	    while (true) {
	      try {
	        return Integer.parseInt(r.readLine());
	      } catch (IOException e) {
	    	  System.out.println("Input Error.");
	    	  e.printStackTrace();
	      } catch (NumberFormatException e) {
	    	  System.out.print("Invalid input. Try again > ");
	      }
	    }
	  }
	
	//for getting text input
	private static String getStrInput(BufferedReader r) {
	    String s = null;
	    try {
	      s = r.readLine();
	      if (s.length() == 0) { 
	    	  return null;
	      }
	    } catch (IOException e) {
	    	System.out.println("Error with user input.");
	    	e.printStackTrace();
	    }
	    return s;
	}
	
	//for listing directory and all subsequent directories
	private static class DirectoryListing extends SimpleFileVisitor<Path> {
		   @Override
		   public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
		     System.out.println(file.getFileName());
		     return FileVisitResult.CONTINUE;
		   }
	}
	 
	//for hexadecimal conversion of strings
	public static String toHexadecimal(String text) throws UnsupportedEncodingException {
		byte[] bytes = text.getBytes("UTF-8");
	    return DatatypeConverter.printHexBinary(bytes);
	}
}
