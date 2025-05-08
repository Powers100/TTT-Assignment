/*  
   Assignment: Tic-Tac-Toe PA2 Assignment
   TTTPowers.java
   Name: Donovan Powers
   Description: An application that allows you to play a game of Tic-Tac-Toe with a computer. 
                Uses a number of functions to handle the needed actions for the game. 
   Due Date: March 16, 2025
   File Created: March 6, 2025
*/

import java.util.Scanner;

public class TTTPowers {

    static int currentTurnNum = 1; // Global variable to track turn count
    
    // initBoard initalizes the game board to default values of 0 throughout the 2D array
    public static int[][] initBoard() {
        int[][] board2DArray = new int[3][3];
        return board2DArray;
    }

    // gameBoard takes the current game board as input and prints either a blank space, 'X', or 'O' depending on the
    // current variable stored in the 2D array.
    public static void gameBoard(int[][] currentBoard) {
        for (int i = 0; i < currentBoard.length; i++) {
            for (int j = 0; j < currentBoard[0].length; j++) {
                // checks to see if in the middle column, if so, then dont inclue extra '| |'
                if (j != 1) {    
                    // if the value in the array is -1, set an 'O' to the spot
                    if (currentBoard[i][j] == -1) {
                        System.out.print("|O|");
                    }
                    // if the value in the array is 1, set an 'X' to the spot
                    else if (currentBoard[i][j] == 1) {
                        System.out.print("|X|");
                    }
                    // if neither, set a blank space to the spot
                    else {
                        System.out.print("| |");
                    }
                }
                // if in the middle column, do the same as above, but without the extra '| |'
                else {
                    if (currentBoard[i][j] == -1) {
                        System.out.print("O");
                    }
                    else if (currentBoard[i][j] == 1) {
                        System.out.print("X");
                    }
                    else {
                        System.out.print(" ");
                    }
                }
                // if on the third column, print to a new line
                if (j == currentBoard[0].length - 1) {
                    System.out.println("");
                }
            }
        }
    }

    // yourTurn displays the current board, and prompts the user to enter a coordinate to place their 'X'
    public static void yourTurn(int[][] currentBoard, Scanner scnr) {
        boolean turnComplete = false;

        // if this is the first turn output "Let's begin!" along with the current blank board
        if (currentTurnNum == 1) {
            System.out.println("Let's begin!");
        }
        // while the turn is not finished ask the user for the coordinates of their location
        while (turnComplete != true) {
            gameBoard(currentBoard);
            System.out.println("Please enter coordinate of your location (x):");
            
            // takes input as a string spliting the input at the ',' then turning the strings into ints
            String inputPositions = scnr.nextLine();
            String[] posNums = inputPositions.split(",");

            int xPos = Integer.parseInt(posNums[0]);
            int yPos = Integer.parseInt(posNums[1]);

            // checks to see if the provided x and y positions are in the range of the array, and that the spot is blank
            if ((xPos >= 0) && (xPos < currentBoard.length) && (yPos >= 0) && (yPos < currentBoard.length) && (currentBoard[xPos][yPos] == 0)) {
                currentBoard[xPos][yPos] = 1;
                currentTurnNum++;
                turnComplete = true;
            }
                
            // if spot is not blank, x, or y position not in range of array, prints an error
            else {
                System.out.println("Invalid coordinate, enter another coordinate.");
            }    
        }
    }

    // machineTurn searches for a random blank spot on the current board and if it finds one, places a -1, representing an 'O'
    // into the slot
    public static void machineTurn(int[][] currentBoard) {
        boolean isFull = true;
        // check to make sure the board is not full
        for (int i = 0; i < currentBoard.length; i++) {
            for (int j = 0; j < currentBoard[i].length; j++) {
                // if there is at least one empty space, the board is not full
                if (currentBoard[i][j] == 0) {
                    isFull = false;
                    break;
                }
            }
            if (isFull != true) break;
        }

        // if the board is full, exit the method to prevent infinite loop
        if (isFull == true) return;

        // Check for a winning move or a blocking move
        int[] bestMove = findBestMove(currentBoard, -1); // machine tries to win
        if (bestMove == null) {
            bestMove = findBestMove(currentBoard, 1); // machine tries to block the player
        }

        // If no immediate win or block, take center if available
        if (bestMove == null && currentBoard[1][1] == 0) {
            bestMove = new int[]{1, 1};
        }

        // If center is not available, try a corner
        if (bestMove == null) {
            int[][] corners = {{0, 0}, {0, 2}, {2, 0}, {2, 2}};
            for (int[] corner : corners) {
                if (currentBoard[corner[0]][corner[1]] == 0) {
                    bestMove = corner;
                    break;
                }
            }
        }
    

        // If no better move is found, pick a random available spot
        if (bestMove == null) {
            for (int i = 0; i < currentBoard.length; i++) {
                for (int j = 0; j < currentBoard[i].length; j++) {
                    if (currentBoard[i][j] == 0) {
                        bestMove = new int[]{i, j};
                        break;
                    }
                }
                if (bestMove != null) break;
            }
        }

        // Make the move
        if (bestMove != null) {
            currentBoard[bestMove[0]][bestMove[1]] = -1;
            currentTurnNum++;
        }
    }

    // helper function for machineTurn() that finds the best move for winning or blocking
    public static int[] findBestMove(int[][] currentBoard, int player) {
        for (int i = 0; i < 3; i++) {
            // Check rows
            if (currentBoard[i][0] == player && currentBoard[i][1] == player && currentBoard[i][2] == 0) return new int[]{i, 2};
            if (currentBoard[i][0] == player && currentBoard[i][2] == player && currentBoard[i][1] == 0) return new int[]{i, 1};
            if (currentBoard[i][1] == player && currentBoard[i][2] == player && currentBoard[i][0] == 0) return new int[]{i, 0};
    
            // Check columns
            if (currentBoard[0][i] == player && currentBoard[1][i] == player && currentBoard[2][i] == 0) return new int[]{2, i};
            if (currentBoard[0][i] == player && currentBoard[2][i] == player && currentBoard[1][i] == 0) return new int[]{1, i};
            if (currentBoard[1][i] == player && currentBoard[2][i] == player && currentBoard[0][i] == 0) return new int[]{0, i};
        }
    
        // Check diagonals
        if (currentBoard[0][0] == player && currentBoard[1][1] == player && currentBoard[2][2] == 0) return new int[]{2, 2};
        if (currentBoard[0][0] == player && currentBoard[2][2] == player && currentBoard[1][1] == 0) return new int[]{1, 1};
        if (currentBoard[1][1] == player && currentBoard[2][2] == player && currentBoard[0][0] == 0) return new int[]{0, 0};
    
        if (currentBoard[0][2] == player && currentBoard[1][1] == player && currentBoard[2][0] == 0) return new int[]{2, 0};
        if (currentBoard[0][2] == player && currentBoard[2][0] == player && currentBoard[1][1] == 0) return new int[]{1, 1};
        if (currentBoard[1][1] == player && currentBoard[2][0] == player && currentBoard[0][2] == 0) return new int[]{0, 2};
    
        return null; // No winning or blocking move found
    }

    // checkWinner checks all posible win conditions to see if the player or the computer has won, if not it returns neither
    public static String checkWinner(int[][] currentBoard) {
        
        for (int i = 0; i < currentBoard.length; i++) {
            // check rows for wins
            if ((currentBoard[i][0] != 0) && (currentBoard[i][0] == currentBoard[i][1]) && (currentBoard[i][1] == currentBoard[i][2])) {
                return (currentBoard[i][0] == 1) ? "player" : "computer";
            }

            // check columns for wins
            if ((currentBoard[0][i] != 0) && (currentBoard[0][i] == currentBoard[1][i]) && (currentBoard[1][i] == currentBoard[2][i])) {
                return (currentBoard[0][i] == 1) ? "player" : "computer";
            }
        } 

        // check top left to bottom left diagonal
        if ((currentBoard[0][0] != 0) && (currentBoard[0][0] == currentBoard[1][1]) && (currentBoard[1][1] == currentBoard[2][2])) {
            return (currentBoard[0][0] == 1) ? "player" : "computer";
        }

        // check top right to bottom right digonal
        if ((currentBoard[0][2] != 0) && (currentBoard[0][2] == currentBoard[1][1]) && (currentBoard[1][1] == currentBoard[2][0])) {
            return (currentBoard[0][2] == 1) ? "player" : "computer";
        }

        // check to make sure the board is not full
        for (int i = 0; i < currentBoard.length; i++) {
            for (int j = 0; j < currentBoard[i].length; j++) {
                // if there are empty spaces, and the previous win conditions did not return, the game is still going
                // there is no winner or tie yet
                if (currentBoard[i][j] == 0) {
                    return "neither";
                }
            }
        }
            
        // if no empty spaces, and no winner, there is a tie
        return "tie";
    }

    // main uses the methods previously defined to initialize the board, prompt the user to take turns, have the computer
    // take turns, and output when a player wins
    public static void main(String[] args) {
        Scanner scnr = new Scanner(System.in);

        // initalize the game board and set winCondition to false
        int[][] currentInitBoard = initBoard();
        boolean winCondition = false;

        // while neither player has won, have the player and machine take turns
        while (winCondition != true) {
            yourTurn(currentInitBoard, scnr);
            
            // check to see if the player has won, and output the result to the user
            switch (checkWinner(currentInitBoard)) {
                case "player":
                    gameBoard(currentInitBoard);
                    System.out.println("You won!");
                    winCondition = true;
                    break;

                case "tie":
                    gameBoard(currentInitBoard);  
                    System.out.println("You tied!");
                    winCondition = true;
                    break;
                    
                default:
                    break;
            }

            if (winCondition == true) {
                break; // exit the loop if the game is over
            }

            machineTurn(currentInitBoard);

            // check to see if the computer has won, and output the result to the user
            switch (checkWinner(currentInitBoard)) {
                case "computer":
                    gameBoard(currentInitBoard);    
                    System.out.println("You lost!");
                    winCondition = true;
                    break;

                case "tie":
                    gameBoard(currentInitBoard);  
                    System.out.println("You tied!");
                    winCondition = true;
                    break;

                default:
                    break;
            }
        }
    }
}
