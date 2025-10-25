package utils;

import java.util.Random;

public class RandomDataGenerator {
	

	    public static String randomName() {
	        String[] names = {"Alex", "Maria", "John", "Sara", "Tom"};
	        return names[new Random().nextInt(names.length)];
	    }
	    
	    public static String generateRandomLastName() {
	        String[] lastNames = {"Smith", "Johnson", "Williams", "Brown", "Jones", "Garcia", "Miller", "Davis", "Martinez", "Lopez"};
	        return lastNames[new Random().nextInt(lastNames.length)];
	    }

	    public static String generatePostalCode() {
	        String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	        String digits = "0123456789";
	        return "" + letters.charAt(new Random().nextInt(letters.length())) +
	                    letters.charAt(new Random().nextInt(letters.length())) +
	                    digits.charAt(new Random().nextInt(digits.length())) +
	                    digits.charAt(new Random().nextInt(digits.length())) +
	                    digits.charAt(new Random().nextInt(digits.length()));
	    }
	}


