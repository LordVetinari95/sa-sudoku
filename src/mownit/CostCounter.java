package mownit;

public class CostCounter {

    private static final int size = 9;

    public static int countCost(int[][] sudoku) {
        return countRowCost(sudoku) + countColumnCost(sudoku) + count3x3Cost(sudoku);
    }

    private static int count3x3Cost(int[][] sudoku) {
        int cost = 0;
        for (int i = 0; i < size; i += 3) {
            for (int j = 0; j < size; j += 3) {
                boolean[] check = new boolean[size];
                for (int k = i; k < i + 3; k++) {
                    for (int l = j; l < j + 3; l++) {
                        if (sudoku[k][l] != 0) check[sudoku[k][l] - 1] = true;
                    }
                }
                for (boolean k : check) {
                    if (!k) cost++;
                }
            }

        }
        return cost;
    }

    private static int countColumnCost(int[][] sudoku) {
        int cost = 0;
        for (int i = 0; i < size; i++) {
            boolean[] check = new boolean[size];
            for (int j = 0; j < size; j++) {
                if (sudoku[j][i] != 0) check[sudoku[j][i] - 1] = true;
            }
            for (boolean k : check) {
                if (!k) cost++;
            }
        }
        return cost;
    }

    private static int countRowCost(int[][] sudoku) {
        int cost = 0;
        for (int[] row : sudoku) {
            boolean[] check = new boolean[size];
            for (int x : row) {
                if (x != 0) check[x - 1] = true;
            }
            for (boolean k : check) {
                if (!k) cost++;
            }
        }
        return cost;
    }
}
