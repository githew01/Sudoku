package com.sodoku;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SudokuValidator {

	public static void main(String[] args) throws Exception {
		if (args.length == 0)
			throw new Exception("File name is not provided!");

		String fileName = args[0];
		List<String> list = new ArrayList<>();

		@SuppressWarnings("resource")
		Stream<String> stream = Files.lines(Paths.get(fileName));
		list = stream.filter(line -> line.trim().length() == 9).collect(Collectors.toList());

		int[][] matrix = makeMatrix(list);

		boolean isValid = validate(matrix);

		if (isValid) {
			System.out.println("This Sudoku file is valid!");
		} else {
			System.out.println("This Sudoku file is NOT valid!");
		}
	}

	private static boolean validate(int[][] matrix) {
		// row checker
		for (int row = 0; row < 9; row++)
			for (int col = 0; col < 8; col++)
				for (int col2 = col + 1; col2 < 9; col2++) {
					if (matrix[row][col] > 9 || matrix[row][col2] > 9)
						return false;
					if (matrix[row][col] == matrix[row][col2])
						return false;
				}

		// column checker
		for (int col = 0; col < 9; col++)
			for (int row = 0; row < 8; row++)
				for (int row2 = row + 1; row2 < 9; row2++) {
					if (matrix[row][col] <= 0 || matrix[row2][col] <= 0)
						return false;
					if (matrix[row][col] == matrix[row2][col])
						return false;
				}

		// grid checker
		for (int row = 0; row < 9; row += 3)
			for (int col = 0; col < 9; col += 3)
				// row, col is start of the 3 by 3 grid
				for (int pos = 0; pos < 8; pos++)
					for (int pos2 = pos + 1; pos2 < 9; pos2++)
						if (matrix[row + pos % 3][col + pos / 3] == matrix[row + pos2 % 3][col + pos2 / 3])
							return false;
		return true;
	}

	private static int[][] makeMatrix(List<String> list) {
		int[][] matrix = new int[9][9];
		int i = 0;
		for (String line : list) {
			matrix[i++] = line.chars().map(x -> x - '0').toArray();
		}
		return matrix;
	}

}
