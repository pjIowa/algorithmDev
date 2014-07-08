import java.util.*;
import java.io.*;

public class sudokuSolverImproved {

	private static int[][] board;
	private static int[][] domainSize;
	static final int empty = 0;
	static final int success = -100;
	static final int fail = -1;
	static long calls = 0;
	static long count = 0;

	static void displaySudoku() {
		for (int i = 0; i < 9; i++) {
			System.out.println(" -------------------------------------");
			for (int j = 0; j < 9; j++) { 
				if (board[i][j]>0) System.out.print(" | " + board[i][j]);
				else System.out.print(" |  ");
			}
			System.out.println(" |");
		}      
		System.out.println(" -------------------------------------");
	}

	static void displayDomain(){
		for (int i = 0; i < 9; i++) {
			System.out.println(" -------------------------------------");
			for (int j = 0; j < 9; j++) { 
				System.out.print(" | " + domainSize[i][j]);
			}
			System.out.println(" |");
		}      
		System.out.println(" -------------------------------------");
	}

	static int next() {
		// pos: the last four bits are column number and the next four bits are row number.
		// look for next open position 
		// pos: the last four bits are column number and the next four bits are row number.

		refreshDomain();
		int minDomain = 100;
		int minRow, minCol;
		minRow=minCol=-1;

		for(int i = 0; i < 9; i++){
			for(int j = 0; j < 9; j++){
				if(domainSize[i][j] < minDomain){
					minDomain = domainSize[i][j];
					minRow = i;
					minCol = j;
				}
			}
		}

		if(minDomain == 100) return success;
		else return (minRow<<4)+minCol;
	}



	static void refreshDomain(){
		for(int i=0; i < 9; i++){
			for(int j =0; j < 9; j++){
				//updates domain for places where value has not yet been set
				if(board[i][j] == 0){
					int newDomain = 0;
					for(int k =1 ; k <10; k++){
						if(feasible(i, j, k))
							newDomain++;
					}
					domainSize[i][j]=newDomain;
				}
				else
					domainSize[i][j]=100;
			}
		}
	}

	static boolean feasible (int row, int col, int k) {
		count++;
		// check if k is feasible at position <row, col>	
		for (int i = 0; i < 9; i++) {
			if (board[i][col] == k) return false;  // used in the same column
			if (board[row][i] == k) return false;  // used in the same row
		}

		int row0 = (row/3)*3;
		int col0 = (col/3)*3;
		for (int i = 0; i < 3; i++) for (int j = 0; j < 3; j++) 
			if (board[i+row0][j+col0] == k) return false; // used in the same region

		return true;	
	}

	static int backtrack (int pos) {
		// backtrack procedure
		calls++;
		if (pos == success) return success;

		// pos: the last four bits are column number and the next four bits are row number.
		int col = pos&15;
		int row = (pos >> 4)&15;

		for (int k = 1; k <= 9; k++){ 
			if (feasible(row, col, k)){
				board[row][col] = k;
				if (backtrack(next()) == success) return success;
			}
		}

		board[row][col] = empty;
		return fail;
	}

	public static void main(String[] args) {

		board = new int[9][9];
		domainSize = new int[9][9];
		boolean validPuzzle = true;

		// read in a puzzle
		try {
			BufferedReader read = new BufferedReader(new InputStreamReader(System.in));

			System.out.print("Please enter sudoku puzzle file name: ");
			String filename = read.readLine();

			Scanner scanner = new Scanner(new File(filename));
			for (int i = 0; i < 9; i++)
				for (int j = 0; j < 9; j++) {
					board[i][j] = scanner.nextInt();
					domainSize[i][j] = (board[i][j]>0)? 1 : 9;
				}      
			System.out.println("Read in:");
			displaySudoku();
		}
		catch(Exception ex){
			ex.printStackTrace();
		}

		// Solve the puzzle by backtrack search and display the solution if there is one.
		if (backtrack(next()) == success) {
			System.out.println("\nSolution:");
			displaySudoku();
		} else {
			System.out.println("no solution");
		}
		System.out.println("recursive calls = "+calls+ " feasible count = "+ count);
	}
}

