package ConnectFour;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ConnectFour {
	public static void main(String[] args) {
		int[][] grid = new int[6][7];
		displayGrid(grid);
		playGame(grid);
	}
	
	
	public static void playGame(int[][] grid) {
		Scanner s = new Scanner(System.in);
		
		boolean gameOver = false, playersTurn = true;
		int colPos = 0; 
		int player = 0;
		
		while(!gameOver) {
			try {
				if (playersTurn) {
					System.out.print("Player 1's Turn: Enter a column to place chip in (0-6): ");
					player = 1;
				}
				else {
					System.out.print("Player 2's Turn: Enter a column to place chip in (0-6): ");
					player = 2;
				}
				colPos = s.nextInt();
				
				while (colPos < 0 || colPos > 6) {
					System.out.println("Invalid! Try again.");
					colPos = s.nextInt();
				}
				
				playersTurn = !playersTurn;
				
				if (dropChip(grid, colPos, player))
					playersTurn = !playersTurn;
				else {
					displayGrid(grid);
					
					if (gameStatus(grid, colPos, player)) {
						gameOver = true;
						System.out.println("Player " + player + " wins! Game Over!");
						s.close();
					}
					else if (checkTie(grid)) {
						gameOver = true;
						System.out.print("It's a tie!");
					}
				}
			}
			catch(InputMismatchException e) {
				s.nextLine();
				System.out.println("Invalid! Try Again.");
			}
		} 
		s.close();
	}
	
	public static boolean checkTie(int[][] grid) {
		for (int i = 0; i < grid[0].length; i++)
			if (grid[0][i] == 0)
				return false;
		
		return true;
	}
	
	// Return true if there is four-in-a row, column, diagonal
	public static boolean gameStatus(int[][] grid, int colPos, int player) {
		int rowPos = 0;
		
		for (int i = 0; i < grid.length; i++) {
			if (grid[i][colPos] != 0) {
				rowPos = i;
				break;
			}
		}
		
		if (checkHorizontal(grid, colPos, player, rowPos)) {
			return true;
		}
		if (checkVertical(grid, colPos, player, rowPos)) {
			return true;
		}
		if (checkMajorDiagonal(grid, colPos, player, rowPos)) {
			return true;
		}
		if (checkMinorDiagonal(grid, colPos, player, rowPos)) {
			return true;
		}
		
		return false;
	}
	
	public static boolean checkHorizontal(int[][] grid, int colPos, int player, int rowPos) {
		int chipCount = 1;
		
		// check to the left
		for (int i = colPos - 1; i >= 0; i--)
			if (player == grid[rowPos][i])
				chipCount++;
			else
				break;
		
		if (chipCount >= 4)
			return true;
		
		// check to the right
		for (int i = colPos + 1; i < grid[0].length; i++)
			if (player == grid[rowPos][i])
				chipCount++;
			else
				break;
		
		if (chipCount >= 4)
			return true;
		
		return false;
	}
	
	public static boolean checkVertical(int[][] grid, int colPos, int player, int rowPos) {
		int chipCount = 1;
		
		if ((rowPos + 4) <= 6) {
			for (int i = rowPos + 1; i <= (rowPos + 3); i++)
				if (player == grid[i][colPos])
					chipCount++;
				else
					break;
		}
		
		if (chipCount == 4)
			return true;
		return false;
	}
	
	public static boolean checkMajorDiagonal(int[][] grid, int colPos, int player, int rowPos) {
		int chipCount = 1;
		// check left
		for (int i = rowPos - 1, j = colPos - 1; i >= 0 && j >= 0; i--, j--)
			if (player == grid[i][j])
				chipCount++;
			else
				break;
		if (chipCount >= 4)
			return true;
		// check right
		for (int i = rowPos + 1, j = colPos + 1; i < grid.length && j < grid[0].length; i++, j++)
			if (player == grid[i][j])
				chipCount++;
			else
				break;
		if (chipCount >= 4)
			return true;
		
		return false;
	}
	
	public static boolean checkMinorDiagonal(int[][] grid, int colPos, int player, int rowPos) {
		int chipCount = 1;
		
		for (int i = rowPos + 1, j = colPos - 1; i < grid.length && j >= 0; i++, j--)
			if (player == grid[i][j])
				chipCount++;
			else
				break;
		if (chipCount >= 4)
			return true;
		
		for (int i = rowPos - 1, j = colPos + 1; i >= 0 && j < grid[0].length; i--, j++)
			if (player == grid[i][j])
				chipCount++;
			else
				break;
		if (chipCount >= 4)
			return true;
				
		return false;
	}
	
	public static boolean dropChip(int[][] grid, int colPos, int player) {
		for (int i = grid.length - 1; i >= 0; i--)
			if (grid[i][colPos] == 0) {
				grid[i][colPos] = player;
				return false;
			}
		System.out.println("Column full, player " + player + " try again!");
		return true;
	}
	
	public static void displayGrid(int[][] grid) {
		System.out.println("        C O N N E C T");
		System.out.println("           F O U R ");
		String output = "";
		System.out.println("-----------------------------");
		
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				if (grid[i][j] == 0) {
					output = " ";
				}
				else if (grid[i][j] == 1) {
					output = "X";
				}
				else if (grid[i][j] == 2) {
					output = "O";
				}
				
				System.out.print("| " + output + " ");
				if (j == 6) {
					System.out.println("|");
				}
			}
			System.out.println("-----------------------------");
		}
		
		System.out.println();
		System.out.println("  0   1   2   3   4   5   6");
		System.out.println();
	}


}

