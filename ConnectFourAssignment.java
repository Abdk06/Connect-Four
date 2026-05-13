import java.util.Scanner;

/* This program simulates a connect four game by creating
   a while loop in the main method that keeps going until it breaks
   when either red or yellow wins, or when a draw occurs
*/
public class ConnectFourAssignment {  
    public static void main(String[] args) {
        char[][] board = initBoard();
        int movecounter = 0;
        printBoard(board);
        while(!isWon(board, movecounter) && !isDraw(board)){
            dropDisc(currentTurn(movecounter), board);
            printBoard(board);
            movecounter +=1;
        }
    }
    
    /* - Initializes the board by creating a 2D array of the type char
       - @return the board as a 2D array
    */ 
    public static char[][] initBoard() {
        char[][] board = new char[6][7];
        
        // The underscores lets the players know where the discs can be dropped
        for (int i = 5; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = '_';
            }
        }
        return board;
    }
    
    /* - Prints and displays the current status of the board
       - @param board is the array that the board info is taken from
    */
    public static void printBoard(char[][] board) {
        System.out.println("Current board status:\n");
        System.out.println(" 0  1  2  3  4  5  6");
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] != '_' &&  board[i][j] != 'R' && board[i][j] != 'Y'){
                    System.out.print("[ ");
                    System.out.print(board[i][j]);
                    System.out.print("]");
                }
                else {
                    // Sets the color of the discs and spaces in the output
                    String color = "";
                    if (board[i][j] == 'R') {
                        color = "\u001B[31m"; // Red color
                    }
                    else if (board[i][j] == 'Y') {
                        color = "\u001B[33m"; // Yellow color
                    }
                    else {
                        color = "\u001B[32m"; // Green color
                    }
                    System.out.print("[");
                    System.out.print(color + board[i][j] + "\u001B[0m"); // The last part resets the color to normal
                    System.out.print("]");
                }
            }
            System.out.println("");
        }

        System.out.println("------------------------------------------");
    }
    
    /* - Determines which player's turn it is using the
       move counter, if the counter is even it's red's turn, 
       if it's odd then it's yellow's turn
       - @param movecount is the movecount used to determine the turn
       - @return which disc is going to be placed next, either R or Y
    */
    public static char currentTurn(int movecount) {
        if (movecount %2 == 0) {
            return 'R';
        }
        else {
            return 'Y';
        }
    }
    
    /* - Checks if the column is full by checking if there is an 
       available spot in a specific column
       - @param column is the column to be checked
       - @param board is the array that the column info is taken from
       - @return a boolean that indicates if the column is full or not
    */
    public static boolean columnFull(int column, char[][] board) {
        boolean full = true;
        for(int i = 0; i < board.length; i++) {
            if (board[i][column] == '_') {
                full = false;
            }
        }
        return full;
    }
    
    /* - Drops a disc in a specific column based on the user's input
       - @param player is the current player turn, either R or Y
       - @param board is the array of the board that will be changed according to the input
    */
    public static void dropDisc(char player, char[][] board) {
        Scanner input = new Scanner(System.in);
        String color = "";
        boolean validplay = false;
        if(player == 'R') {
            color = "red";
        }
        else {
            color = "yellow";
        }
        
        // Keeps looping until the player chooses a valid non-full column
        while(!validplay) {
            System.out.println("Drop a " + color + " disc at column (0-6):");
            int choice = input.nextInt();
            if (choice < 0 || choice > 6) {
                System.out.println("Invalid choice. Please choose between 0 and 6.");
            }
            else {
                if(columnFull(choice, board)) {
                    System.out.println("This column is full. Please choose another one.");
                }
                else {
                    for(int i = 0; i < board.length; i++) {
                        if(board[i][choice] == '_') { // Finds the available spot
                            board[i][choice] = player;
                            if (i!=0) {
                                board[i-1][choice] = '_'; // Moves the avaiable spot one row up, unless it's at the top row                     
                            }
                            validplay = true;
                        }
                    }
                }
            }
        }
    }
    
    /* - Indicates if the game is over and if there is a winner by
       checking for 4 consecutive discs (R or Y) of the same color in a row
       horizontally, vertically or diagonally.
       @param board is the array of the board that will be checked for a winner
       @param movecount is used to determine who's turn it is to determine which color won
       @return a boolean that indicates if there is a winner or not yet
    */
    public static boolean isWon(char[][] board, int movecount){
        String winner = "";
        if ((movecount-1) % 2 == 0) { // Added -1 because the movecount goes up before the method runs
            winner = "Red!";
        }
        else {
            winner = "Yellow!";
        }
        // This loop checks if there is a winner horizontally
        for(int i = 0; i < board.length; i ++) {
            for(int j = 0; j < 4; j++) { // j stops at 3 so j+3 doesn't go out of bounds
                if(board[i][j] == board[i][j+1] && board[i][j] == board[i][j+2] && board[i][j] == board[i][j+3] && board[i][j] != '_' && board[i][j] != '\u0000') {
                    System.out.println("Game over! The winner is: " + winner);
                    return true;
                }
            }
        }
        
        // This loop checks if there is a winner vertically
        for(int i = 0; i < 3; i ++) { // i stops at 2 so i+3 doesn't go out of bounds
            for(int j = 0; j < board[i].length; j++) {
                if(board[i][j] == board[i+1][j] && board[i][j] == board[i+2][j] && board[i][j] == board[i+3][j] && board[i][j] != '_' && board[i][j] != '\u0000') {
                    System.out.println("Game over! The winner is: " + winner);
                    return true;
                }
            }
        }
        
        // This loop checks if there is a winner diagonally (top left to bottom right)
        for(int i = 0; i < 3; i ++) {
            for(int j = 0; j < 4; j++) { 
                if(board[i][j] == board[i+1][j+1] && board[i][j] == board[i+2][j+2] && board[i][j] == board[i+3][j+3] && board[i][j] != '_' && board[i][j] != '\u0000') {
                    System.out.println("Game over! The winner is: " + winner);
                    return true;
                }
            }
        }
        
        // This loop checks if there is a winner diagonally (top right to bottom left)
        for(int i = 0; i < 3; i++) {
            for(int j = 6; j > 2; j--) { // j decrements because we're checking from the right to the left
                if(board[i][j] == board[i+1][j-1] && board[i][j] == board[i+2][j-2] && board[i][j] == board[i+3][j-3] && board[i][j] != '_' && board[i][j] != '\u0000') {
                    System.out.println("Game over! The winner is: " + winner);
                    return true;
                }
            }
        }
        
        return false;
    }
    
    /* - Indicates if the game is over as a draw by checking 
       every possible 4-cell window horizontally, vertically
       and diagonally. If any window has 3 or more empty spaces,
       or if any window doesn't have at least one red and one yellow
       disc, then the method returns false and the game continues.
       Otherwise, if all the windows satisfy these conditions, the
       game ends in a draw.
       @param board is the array of the board that gets checked for a draw
       @return a boolean that indicates if the game ended in a draw or not
    */
    public static boolean isDraw(char[][] board) {
        // Checks all the horizontal 4-cell windows
        for(int i = 0; i < board.length; i++) {
            for(int k = 0; k < 4; k++) { // The k counter is added to shift the window to the right
                int redcount = 0;
                int yellowcount = 0;
                int emptycount = 0;
                for(int j = 0; j < 4; j++) {
                    if (board[i][j+k] == 'R') {
                        redcount +=1;
                    }
                    else if (board[i][j+k] == 'Y') {
                        yellowcount +=1;
                    }
                    else if (board[i][j+k] == '\u0000') {
                        emptycount +=1;
                    }
                }
                if (emptycount >=3) {
                    return false;
                }
                else if(redcount == 0 || yellowcount == 0) {
                    return false;
                }
            }
        }
        
        // Checks all the vertical 4-cell windows
        for(int j = 0; j < 7; j++) {
            for(int k = 0; k < 3; k++) { // The k counter is added to shift the window down
                int redcount = 0;
                int yellowcount = 0;
                int emptycount = 0;
                for(int i = 0; i < 4; i++) {
                    if (board[i+k][j] == 'R') {
                        redcount +=1;
                    }
                    else if (board[i+k][j] == 'Y') {
                        yellowcount +=1;
                    }
                    else if (board[i+k][j] == '\u0000') {
                        emptycount +=1;
                    }
                }
                if (emptycount >=3) {
                    return false;
                }
                else if(redcount == 0 || yellowcount ==0) {
                    return false;
                }
            }
        }
        
        // Checks all the diagonal (top left to bottom right) 4-cell windows
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 4; j++) {
                int redcount = 0;
                int yellowcount = 0;
                int emptycount = 0;
                for(int k = 0; k < 4; k++) { // The k counter is added to shift the window down to the right
                    if (board[i+k][j+k] == 'R') {
                        redcount +=1;
                    }
                    else if (board[i+k][j+k] == 'Y') {
                        yellowcount +=1;
                    }
                    else if (board[i+k][j+k] == '\u0000') {
                        emptycount +=1;
                    }
                }
                if (emptycount >=3) {
                    return false;
                }
                else if(redcount == 0 || yellowcount ==0) {
                    return false;
                }
            }
        }
        
        // Checks all the diagonal (top right to bottom left) 4-cell windows
        for(int i = 0; i < 3; i++) {
            for(int j = 6; j > 2; j--) {
                int redcount = 0;
                int yellowcount = 0;
                int emptycount = 0;
                for(int k = 0; k < 4; k++) { // The k counter is added to shift the window down to the left
                    if (board[i+k][j-k] == 'R') {
                        redcount +=1;
                    }
                    else if (board[i+k][j-k] == 'Y') {
                        yellowcount +=1;
                    }
                    else if (board[i+k][j-k] == '\u0000') {
                        emptycount +=1;
                    }
                }
                if (emptycount >=3) {
                    return false;
                }
                else if(redcount == 0 || yellowcount ==0) {
                    return false;
                }
            }
        }
        
        System.out.println("Game over! The game is a draw.");
        return true;
    }
    
}
