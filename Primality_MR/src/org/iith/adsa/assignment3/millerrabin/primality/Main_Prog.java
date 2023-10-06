/**
 * @author cs21mds14015 (Jagadeesh Krishnan)
 *
 */
package org.iith.adsa.assignment3.millerrabin.primality;

import java.util.Scanner;

/**
 * Miller Rabin Primality algorithm. Takes 2 inputs 'n', which is Prime number and 
 * 'r', which is number of iterations. When the Program outputs Prime for all the iterations,
 * then it is a Prime number else it is Composite
 */
public class Main_Prog {

	/**
	 * Check if the number is a prime.
	 * 
	 * @param input number 
	 */
	private boolean Miller_Rabin_Test(long inputNum) {
		long d, s;
		boolean b = false;

		if (inputNum == 2) {
			b = true;
			return b;
		}			
		if (inputNum < 2) {
			b = false;
			return b;
		}


		// until d is odd
		for (d = 0, s = 1; (d & 1) == 0; s++)
			d = (inputNum - 1) / higher_power(2, s);

		// base in the range [2, n-1]
		long base = (long) ((Math.random() * (inputNum - 3)) + 2);

		long x = modular_higher_power(base, d, inputNum);

		if (x == 1 || x == (inputNum - 1))
			return true;

		if(!innerFor(inputNum, s, x)) {
			return false;
		}
		else {
			return true;
		}

	}
	
	// inner loop for number check for the pow of 2
	private boolean innerFor(long numb, long s, long x) {
		for (long j = 0; j < (s - 1); j++) {
			x = modular_higher_power(x, 2, numb);
			if (x == 1)
				return false;
			if (x == (numb - 1))
				return true;
		}
		return false;
	}

	/**
	 * Returns the value of the first argument raised to the power of the second
	 * argument. This runs in Polylog(n) times.
	 * 
	 * @param base
	 * @param expo
	 * @return the value base to the power expo
	 */
	private long higher_power(long base, long expo) {
		int shift = 63; // number of bits shifted raising to the power
		long result = base; 

		// no need of 0 bits to be considered
		while (((expo >> shift--) & 1) == 0)   ;// just skip them
			

		while (shift >= 0) {
			result = result * result;
			if (((expo >> shift--) & 1) == 1)
				result = result * base;
		}

		return result;
	}

	/**
	 * Returns the value of the first argument raised to the power of the second
	 * argument modulo the third argument. This runs in Polylog(n) times.
	 * 
	 * @param base
	 * @param exponent
	 * @param modulo
	 * @return the value base^exponent % modulo
	 */
	private long modular_higher_power(long base, long exponent, long modulo) {
		int shift = 63; // bit position
		long result = base; // (1 * 1) * base = base

		while (((exponent >> shift--) & 1) == 0) 	;

		while (shift >= 0) {
			result = (result * result) % modulo;
			if (((exponent >> shift--) & 1) == 1)
				result = (result * base) % modulo;
		}

		return result;
	}


	//driver method
	public static void main(String[] args) {
		long n;
		int r;

		Main_Prog main = new Main_Prog();
		// get the prime number to be checked
		Scanner input1 = new Scanner(System.in);
		while (true) {
			System.out.println("Enter the number to be checked for Primality = ");
			try {
				n = input1.nextLong();
				break;
			}
			catch (java.util.InputMismatchException e) {
				System.out.println("Invalid input, re-Enter again");
				input1.next();
			}
		}
		// get the number of iterations
		Scanner input2 = new Scanner(System.in);
		while (true) {
			System.out.println("Provide the number of iterations =  ");
			try {
				r = input2.nextInt();
				break;
			}
			catch (java.util.InputMismatchException e) {
				System.out.println("Invalid input, re-Enter again");
				input2.next();
			}
		}
		
		// run the Miller Rabin algo to check the Prime for r-times
		// When the given number 'n' is composite even once during the iteration, then its Composite
		// else its Prime
		boolean isPrime = true;
		for(int k = 0;k<r;k++) {
			if (!main.Miller_Rabin_Test(n)) {
				isPrime = false;
				break;
			}
		}
		if(isPrime) {
			System.out.println(Long.toString(n) + " is a Prime Number");
		}else {
			System.out.println(Long.toString(n) + " is a Composite Number");
		}
	}
}
