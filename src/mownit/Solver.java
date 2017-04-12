package mownit;

import java.util.*;

public class Solver {
    private int[][] sudoku;
    private int size;
    private Random random;
    private double temp;
    private double coolingRate;

    public Solver(int[][] sudoku) {
        this.sudoku = sudoku;
        this.size = sudoku.length;
        this.random = new Random();
        this.temp = 35000.0;
        this.coolingRate = 0.001;
    }

    private boolean canChange(int x1, int y1, int x2, int y2){
        if(sudoku[x1][y1]!=0 || sudoku[x2][y2]!=0) return false;
        else return !(x1 == x2 && y1 == y2);
    }

    private boolean countProbability(int currentCost, int consideredCost){
        if(consideredCost < currentCost) return true;
        else return Math.exp((currentCost-consideredCost)/temp) > random.nextDouble();
    }

    private int[][] copyArray(int[][] toCopy){
        int[][] copyArray = new int[size][];
        for (int i = 0; i < size; i++) {
            copyArray[i] = toCopy[i].clone();
        }
        return copyArray;
    }

    public int[][] solveSudoku(){
        int iter = 0;
        int[][] current = copyArray(sudoku);
        fillTable(current);
        int[][] best = copyArray(current);
        int[][] swap;


        while(CostCounter.countCost(best)!=0 && temp > 0.0001){
            swap = copyArray(current);
            createNextState(swap);

            if(countProbability(CostCounter.countCost(current),CostCounter.countCost(swap))){
                current = copyArray(swap);
            }

            if(CostCounter.countCost(best) > CostCounter.countCost(current)) best = copyArray(current);
            temp = temp*(1-coolingRate);
            iter++;
        }
        //System.out.println("Algorithm generated " + Integer.toString(iter) + " state(s) before it has finished.");
        return best;
    }

    private void fillTable(int[][] current) {
        for (int i = 0; i < size; i+=3) {
            for (int j = 0; j < size; j+=3) {
                boolean[] check = new boolean[size];
                int counter = 0;
                for(int k = i; k < i+3 ; k++){
                    for (int l = j; l < j+3; l++) {
                        if(sudoku[k][l]!=0) check[sudoku[k][l]-1] = true;
                    }
                }
                for(int k = i; k < i+3 ; k++){
                    for (int l = j; l < j+3; l++) {
                        if(current[k][l] == 0){
                            while(check[counter]) counter++;
                            check[counter] = true;
                            current[k][l] = counter+1;
                        }
                    }
                }
            }
        }
    }

    private void createNextState(int[][] current) {
        int x,y,x1,y1,x2,y2;
        do {
            x = random.nextInt(3)*3;
            y = random.nextInt(3)*3;
            x1 = x + random.nextInt(3);
            y1 = y + random.nextInt(3);
            x2 = x + random.nextInt(3);
            y2 = y + random.nextInt(3);

        }while(!canChange(x1,y1,x2,y2));
        int tmp = current[x1][y1];
        current[x1][y1] = current[x2][y2];
        current[x2][y2] = tmp;
    }

}
