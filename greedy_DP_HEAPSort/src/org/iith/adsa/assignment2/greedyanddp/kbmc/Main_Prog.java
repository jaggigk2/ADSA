/**
 * 
 */
package org.iith.adsa.assignment2.greedyanddp.kbmc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * @author cs21mds14015 (Jagadeesh Krishnan)
 *
 */
public class Main_Prog {

	private int n;
	private int M;
	private int[] arrEffortsGreedy;
	private int[] arrEffortsDP;
	private int[] arrPointsDP;

	private String prtMessGreedy;


	public String getPrtMessGreedy() {
		return prtMessGreedy;
	}


	void heapify(int array2sort[], int n, int i)
	{
		int maxRoot = i;  // Initialize root
		int left = 2*i + 1;  
		int right = 2*i + 2;  

		// when left child is greater 
		if (left < n && array2sort[left] > array2sort[maxRoot])
			maxRoot = left;

		// when right child is large
		if (right < n && array2sort[right] > array2sort[maxRoot])
			maxRoot = right;

		// wen  root is max
		if (maxRoot != i)
		{
			int swap = array2sort[i];
			array2sort[i] = array2sort[maxRoot];
			array2sort[maxRoot] = swap;

			heapify(array2sort, n, maxRoot);
		}
	}
	
	public void sort(int arr[])
	{
		int n = arr.length;

		// arrange the array into heap
		for (int i = n / 2 - 1; i >= 0; i--)
			heapify(arr, n, i);

		for (int i=n-1; i>=0; i--)
		{
			// change the root to last 
			int temp = arr[0];
			arr[0] = arr[i];
			arr[i] = temp;

			// reduced heapify
			heapify(arr, i, 0);
		}
	}


	//method to take input for Greedy algorithm
	private void getInput4Greedy(){

		// get the number of dishes from the user
		Scanner input1 = new Scanner(System.in);
		while (true) {
			System.out.println("How many number of Dishes? n = ");
			try {
				this.n = input1.nextInt();
				break;
			}
			catch (java.util.InputMismatchException e) {
				System.out.println("Invalid input, re-Enter again");
				input1.next();
			}
		}

		// get the maximum effort possible from the user
		Scanner input2 = new Scanner(System.in);
		while (true) {
			System.out.println("What is the maximum effort? M = ");
			try {
				this.M = input2.nextInt();
				break;
			}
			catch (java.util.InputMismatchException e) {
				System.out.println("Invalid input, re-Enter again");
				input2.next();
			}
		}

		this.arrEffortsGreedy = new int[this.n];
		
		System.out.println("Enter the " + Integer.toString(this.n) + " efforts one by one for Greedy algorithm\n");

		for (int i = 0; i < arrEffortsGreedy.length; i++) {
			Scanner input3 = new Scanner(System.in);

			int eff;

			while (true) {
				try {
					System.out.println("effort"+Integer.toString(i+1)+" : ");
					eff = input3.nextInt();
					break;
				}
				catch (java.util.InputMismatchException e) {
					System.out.println("Invalid input, re-Enter again");
					input3.next();
				}
			}

			this.arrEffortsGreedy[i] = eff;
		}
	}

	// take input for DP, the different points and corresponding efforts
	private void getInput4DP() {

		this.arrPointsDP = new int[this.n+1];

		this.arrPointsDP[0] = 0;
		
		System.out.println("Enter the " + Integer.toString(this.n) + " points one by one for DP algorithm now\n");

		for (int i = 1; i < arrPointsDP.length; i++) {
			Scanner input3 = new Scanner(System.in);

			int points;

			while (true) {
				try {
					System.out.println("point"+Integer.toString(i)+" : ");
					points = input3.nextInt();
					break;
				}
				catch (java.util.InputMismatchException e) {
					System.out.println("Invalid input, re-Enter again");
					input3.next();
				}
			}

			this.arrPointsDP[i] = points;
		}

		// efforts
		this.arrEffortsDP = new int[this.n+1];
		this.arrPointsDP[0] = 0;

		for (int i = 1; i < arrEffortsDP.length; i++) {
			Scanner input4 = new Scanner(System.in);

			System.out.println("Enter the " + Integer.toString(this.n) + " efforts one by one");
			int eff;

			while (true) {
				try {
					System.out.println("effort"+Integer.toString(i)+" : ");
					eff = input4.nextInt();
					break;
				}
				catch (java.util.InputMismatchException e) {
					System.out.println("Invalid input, re-Enter again");
					input4.next();
				}
			}

			this.arrEffortsDP[i] = eff;
		}
	}

	//solveGreedy
	public void solveGreedy() {
		getInput4Greedy();
		sort(this.arrEffortsGreedy);

		int effort = 0;
		prtMessGreedy = new String();   // print message

		for(int i=0;i<this.n;i++) {

			effort = effort + this.arrEffortsGreedy[i];

			if(effort>=this.M) {
				effort = effort - this.arrEffortsGreedy[i];
				break;
			}
			prtMessGreedy = prtMessGreedy + Integer.toString(this.arrEffortsGreedy[i]) + " + ";
		}

		prtMessGreedy = prtMessGreedy.substring(0, prtMessGreedy.length()-2);
		prtMessGreedy = "+++ Part 1 (Greedy) \n Minimum effort = " + Integer.toString(effort) +" = " + prtMessGreedy + "\n";

	}

	//solveDP tabluar method
	public void solveDP() {
		getInput4DP();

		int P= Arrays.stream(this.arrPointsDP).sum();

		// table for storing the result of smaller computed problems
		int[][] T = new int[this.n+1][P+1];

		//T[i][0] = 0 when the dish is not prepared
		for(int i=0; i<=this.n; i++) {
			T[i][0] = 0;
		}

		//T[0][p] = inf (be it 5000)
		for(int p=1; p<=P; p++) {
			T[0][p] = 5000;
		}

		//take 1 <= i <= n and 1 <= p <= P
		for(int i=1; i<=this.n; i++) {
			int pi = this.arrPointsDP[i];
			for(int p=1; p<=P; p++) {
				if(p<pi) {
					T[i][p] = T[i-1][p];
				}
				else {
					int m0 = T[i - 1][p];
					int m1 = this.arrEffortsDP[i] + T[i - 1][p - pi];
					if(m1>this.M) {
						T[i][p] = m0;
					}
					else {
						T[i][p] = Math.min(m0, m1);
					}
				}
			}
		} // end of for outer
		int[] pos = findSecondMaxIndicies(T);

		String prtPoints = new String(); // print message.
		String prtEfforts = new String(); // print message.

		List<Integer> dishChoices = findDishChoice(T,pos);

		int effort = 0;
		int points = 0;

		for (int i = dishChoices.size()-1;i>=0;i--) {
			prtPoints = prtPoints + Integer.toString(this.arrPointsDP[dishChoices.get(i)]) + " + ";
			prtEfforts = prtEfforts + Integer.toString(this.arrEffortsDP[dishChoices.get(i)]) + " + ";

			points = points + this.arrPointsDP[dishChoices.get(i)];
			effort = effort + this.arrEffortsDP[dishChoices.get(i)];
		}
		prtPoints = prtPoints.substring(0, prtPoints.length()-2);
		prtPoints = "+++ Part 2 (DP) \n\n Maximum points = " + Integer.toString(points) +" = " + prtPoints;

		prtEfforts = prtEfforts.substring(0, prtEfforts.length()-2);
		prtEfforts = "\n Minimum Efforts = " + Integer.toString(effort) +" = " + prtEfforts;
		
		System.out.println(prtPoints);
		System.out.println(prtEfforts);
		
	}

	// Method to find second max indices
	private int[] findSecondMaxIndicies(int[][] matrix){

		int maxNum = 0;
		int[] pos = new int [2];
		for (int i = 0; i < matrix.length; i++) {
			for (int j = matrix[i].length-1; j >0 ; j--) {
				int matVal = matrix[i][j];
				if(matVal!=5000)
					if(maxNum < matVal){
						maxNum = matVal;
						pos[0] = i;
						pos[1] = j;
						break;
					}
			}
		}
		// there can be minimum effort for the same point, check that. Update i
		for(int i =0;i<=this.n;i++) {
			if(maxNum>matrix[i][pos[1]]) {   // something lesser effort with the same point is found
				pos[0] = i;
				break;
			}
		}
		return pos;
	}

	//bottom up - part 2 extension
	private List<Integer> findDishChoice(int[][] matrix,int[] pos){

		int i = pos[0];
		int j =pos[1];
		List<Integer> dishChoices = new ArrayList<Integer>();

		dishChoices.add(i);

		//
		int currEffort = matrix[i][j];
		int currDish = i; // index
		int currP = j; 
		int currDishPoints = this.arrPointsDP[currDish]; 
		int currDishEffort = this.arrEffortsDP[currDish];  

		while(currDish>0) {

			int nextP = currP - currDishPoints; 
			int nextEffort = currEffort - currDishEffort; 
			int k;

			if(nextEffort==0) {
				break;
			}

			for(k = 0;k<currDish;k++) {
				if(matrix[k][nextP] == nextEffort) {
					dishChoices.add(k);
					break;
				}
			}  // this for loop
			currP = nextP;
			currEffort = nextEffort;
			currDish = k;
			currDishPoints = this.arrPointsDP[currDish];
			currDishEffort = this.arrEffortsDP[currDish];
		}
		return dishChoices;
	}


	// main method
	public static void main(String[] args) {

		Main_Prog obj = new Main_Prog();
		obj.solveGreedy();
		System.out.println(obj.getPrtMessGreedy());
		
		obj.solveDP();
	}
}
