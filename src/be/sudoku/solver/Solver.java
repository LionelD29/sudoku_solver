package be.sudoku.solver;

public class Solver {
    public static final int GRID_SIZE = 9;


    public static void main(String[] args) {
        int[][] board = {
                {4, 0, 0, 5, 0, 7, 0, 0, 8},
                {0, 7, 0, 0, 3, 0, 0, 2, 0},
                {0, 0, 0, 0, 9, 0, 0, 0, 0},
                {7, 0, 0, 4, 0, 6, 0, 0, 9},
                {0, 9, 5, 0, 0, 0, 8, 4, 0},
                {3, 0, 0, 9, 0, 8, 0, 0, 5},
                {0, 0, 0, 0, 2, 0, 0, 0, 0},
                {0, 6, 0, 0, 4, 0, 0, 7, 0},
                {2, 0, 0, 6, 0, 4, 0, 0, 4}
        };

        System.out.println("Grille de départ :");
        printBoard(board);
        System.out.println();

        if (solveBoard(board)) {
            System.out.println("*** Résolution en cours ***");
            System.out.println();

            System.out.println("Grille après résolution :");
            printBoard(board);
        } else {
            System.out.println("Cette grille n'a pas de solution !");
        }
    }

    public static boolean isNumberInRow(int[][] board, int numberToCheck, int row) {
        for (int i = 0; i < GRID_SIZE; i++) {
            if (board[row][i] == numberToCheck) {
                return true;
            }
        }
        return false;
    }

    public static boolean isNumberInColumn(int[][] board, int numberToCheck, int column) {
        for (int i = 0; i < GRID_SIZE; i++) {
            if (board[i][column] == numberToCheck) {
                return true;
            }
        }
        return false;
    }

    public static boolean isNumberInBox(int[][] board, int numberToCheck, int row, int column) {
        int boxUpperRow = row - row % 3;
        int boxLeftColumn = column - column % 3;

        for (int localRow = boxUpperRow; localRow < boxUpperRow + 3; localRow++) {
            for (int localColumn = boxLeftColumn; localColumn < boxLeftColumn + 3; localColumn++) {
                if (board[localRow][localColumn] == numberToCheck) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isValidNumber(int[][] board, int numberToCheck, int row, int column) {
        return !isNumberInRow(board, numberToCheck, row) &&
                !isNumberInColumn(board, numberToCheck, column) &&
                !isNumberInBox(board, numberToCheck, row, column);
    }

    public static boolean solveBoard(int[][] board) {
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int column = 0; column < GRID_SIZE; column++) {
                if (board[row][column] == 0) {
                    for (int numberToCheck = 1; numberToCheck <= GRID_SIZE; numberToCheck++) {
                        if (isValidNumber(board, numberToCheck, row, column)) {
                            board[row][column] = numberToCheck;

                            if (solveBoard(board)) {
                                return true;
                            } else {
                                board[row][column] = 0;
                            }
                        }
                    }
                    // Si pour une case vide aucun nombre n'est valide, c'est qu'il y a une erreur
                    // Dans ce cas, on veut revenir en arrière dans la résolution pour annuler
                    // le dernier changement fait. Ainsi, on peut tester d'autres possibilités et
                    // bruteforcer la solution
                    return false;
                }
            }
        }
        // Si plus aucune case vide ne se trouve dans la grille, c'est qu'elle est résolue
        // Car s'il y avait eu des erreurs, on les aurait vues au plus tard lors de la complétion
        // de la toute dernière case.
        // On renvoit donc true ici, pour dire que la grille est complétée
        return true;
    }

    public static void printBoard(int[][] board) {
        for (int row = 0; row < GRID_SIZE; row++) {
            if (row % 3 == 0 && row != 0) {
                System.out.println("---------|-----------|--------");
            }
            for (int column = 0; column < GRID_SIZE; column++) {
                if (column % 3 == 0 && column != 0) {
                    System.out.print("|  ");
                }
                if (board[row][column] == 0) {
                    System.out.print(".  ");
                } else {
                    System.out.print(board[row][column] + "  ");
                }
            }
            System.out.println();
        }
    }
}
