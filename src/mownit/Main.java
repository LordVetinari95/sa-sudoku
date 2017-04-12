package mownit;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Main {

    private static final int size = 9;

    private static int[][] readSudokuFromFile(String filepath){
        List<String> lines = getLines(filepath);
        int[][] sudoku = new int[size][size];
        for(int i = 0 ; i < size ; i++){
            for(int j = 0 ; j < size ; j++){
                if(lines.get(i).charAt(j)=='x') sudoku[i][j] = 0;
                else sudoku[i][j] = Character.getNumericValue(lines.get(i).charAt(j));
            }
        }
        return sudoku;
    }

    private static List<String> getLines(String filepath) {
        try{
            Path path = Paths.get(filepath);
            return Files.readAllLines(path);
        }
        catch(Exception e){
            e.printStackTrace();
            System.exit(-1);
        }
        return null;
    }

    public static void main(String[] args) {
        int[][] sudoku = readSudokuFromFile("sudoku.txt");
        System.out.println(countZeros(sudoku));
        for (int j = 0; j < 5; j++) {
            printSudoku(sudoku);
            int[][] solved;
            int i = 0;
            do {
                Solver solver = new Solver(sudoku);
                solved = solver.solveSudoku();
                i++;
            } while(CostCounter.countCost(solved)!=0);
            System.out.println("\nSudoku solved after " + Integer.toString(i) + " iteration(s) of the algorithm.");
            printSudoku(solved);
        }

    }

    public static void printSudoku(int[][] sudoku) {
        System.out.println("\n___________________");
        for(int i = 0; i < size ; i++){
            System.out.print("|");
            for(int j = 0; j < size; j++){
                System.out.print(sudoku[i][j]);
                if((j+1)%3==0)System.out.print("|");
                else  System.out.print(" ");
            }
            if((i+1)%3==0)System.out.println("\n-------------------");
            else System.out.println();
        }
    }

    private static int countZeros(int[][] sudoku){
        int zeros = 0;
        for (int[] row: sudoku) {
            for (int x: row) {
                if(x==0) zeros++;
            }
        }
        return zeros;
    }
}
