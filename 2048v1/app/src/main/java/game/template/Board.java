package game.template;
import java.util.Random;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


public class Board {
    private static int score = 0;
    private static int highscore = 0;
    private static boolean win = false;
    private boolean lose = false;
    private int[][] board;
    public Board() {
        board = new int[4][4];
    }
    public static Board loadBoard(){
        //creates a new board with 2 random 2's
        Board b = new Board();
        Random rand1 = new Random();
        Random rand2 = new Random();
        for (int i = 0; i < 2; i++) {
            int rrow = rand1.nextInt(4);
            int rcol = rand2.nextInt(4);
            b.board[rrow][rcol] = 2;
        }
        score = 0;
        return b; 

    }
    public boolean validMove(Board b, Board c){
        //checks if the board has changed
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if(b.board[i][j] != c.board[i][j]){
                    return true;
                }
            }
        }
        return false;
    }
    public int forOrTwo(){
        //randomly chooses between 2 and 4
        Random rand = new Random();
        int r = rand.nextInt(20);
        if(r < 19){
            return 2;
        }
        else{
            return 4;
        }
    }
    public void addNum(Board b){
        Random rand1 = new Random();
        Random rand2 = new Random();
        boolean placed = false;
        while(placed == false){
            int rrow = rand1.nextInt(4);
            int rcol = rand2.nextInt(4);
            if(b.board[rrow][rcol] == 0){
                b.board[rrow][rcol] = forOrTwo();
                placed = true;
            }
        }
            
    }

    public Board moveLeft(){
        Board b = new Board();
        for (int i = 0; i < 4; i++) {
            //grabs the row
            int[] row = board[i];
            int[] newRow = new int[4];
            int index = 0;
            //moves all numbers to the left
            for (int j = 0; j < 4; j++) {
                if (row[j] != 0) {
                    newRow[index] = row[j];
                    index++;
                }
            }
            //combines numbers
            for (int j = 0; j < 3; j++) {
                if (newRow[j] == newRow[j + 1]) {
                    newRow[j] *= 2;
                    score += newRow[j];
                    setHighScore();
                    newRow[j + 1] = 0;
                }
            }
            index = 0;
            //moves all numbers to the left again
            for (int j = 0; j < 4; j++) {
                if (newRow[j] != 0) {
                    b.board[i][index] = newRow[j];
                    index++;
                }
            }
        }
       return b;
    }
    public Board moveRight(){
        Board b = new Board();
        for (int i = 0; i < 4; i++) {
            //grabs the row
            int[] row = board[i];
            int[] newRow = new int[4];
            int index = 3;
            //moves all numbers to the right
            for (int j = 3; j >= 0; j--) {
                if (row[j] != 0) {
                    newRow[index] = row[j];
                    index--;
                }
            }
            //combines numbers
            for (int j = 3; j > 0; j--) {
                if (newRow[j] == newRow[j - 1]) {
                    
                    newRow[j] *= 2;
                    score += newRow[j];
                    setHighScore();
                    newRow[j - 1] = 0;
                }
            }
            index = 3;
            //moves all numbers to the right again
            for (int j = 3; j >= 0; j--) {
                if (newRow[j] != 0) {
                    b.board[i][index] = newRow[j];
                    index--;
                }
            }
        }
        
        return b;
    }
    public Board moveUp(){
        Board b = new Board();
        for (int i = 0; i < 4; i++) {
            //grabs the column
            int[] col = new int[4];
            for (int j = 0; j < 4; j++) {
                col[j] = board[j][i];
            }
            int[] newCol = new int[4];
            int index = 0;
            //moves all numbers up
            for (int j = 0; j < 4; j++) {
                if (col[j] != 0) {
                    newCol[index] = col[j];
                    index++;
                }
            }
            //combines numbers
            for (int j = 0; j < 3; j++) {
                if (newCol[j] == newCol[j + 1]) {
                    newCol[j] *= 2;
                    score += newCol[j];
                    setHighScore();
                    newCol[j + 1] = 0;
                }
            }
            index = 0;
            //moves all numbers up again
            for (int j = 0; j < 4; j++) {
                if (newCol[j] != 0) {
                    b.board[index][i] = newCol[j];
                    index++;
                }
            }
        }
        return b;
    }
    public Board moveDown(){
        Board b = new Board();
        for (int i = 0; i < 4; i++) {
            //grabs the column
            int[] col = new int[4];
            for (int j = 0; j < 4; j++) {
                col[j] = board[j][i];
            }
            int[] newCol = new int[4];
            int index = 3;
            //moves all numbers down
            for (int j = 3; j >= 0; j--) {
                if (col[j] != 0) {
                    newCol[index] = col[j];
                    index--;
                }
            }
            //combines numbers
            for (int j = 3; j > 0; j--) {
                if (newCol[j] == newCol[j - 1]) {
                    newCol[j] *= 2;
                    score += newCol[j];
                    setHighScore();
                    newCol[j - 1] = 0;
                }
            }
            index = 3;
            //moves all numbers down again
            for (int j = 3; j >= 0; j--) {
                if (newCol[j] != 0) {
                    b.board[index][i] = newCol[j];
                    index--;
                }
            }
        }
        return b;
    }
    public int get(int row, int col){
        return board[row][col];
    }
    public int getScore(){
        return score;
    }
    public int getHighScore(){
        return highscore;
    }
    public void setHighScore(){
        if(score > highscore){
            highscore = score;
        }
    }
    public void checkWin(){
        
        if (win == false){
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    if(board[i][j] == 2048){
                        win = true;
                        
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("You Win!");
                        alert.setHeaderText("Congratulations!");
                        alert.setContentText("You have reached 2048!");
                        alert.showAndWait();
                    }
                }
            }
        }
        
    }
    public void gameOver(Board b){
        Board c = new Board();
        if(lose == false){
            c = b.moveLeft();
            if(validMove(b, c) == false){
                c = b.moveRight();
                if(validMove(b, c) == false){
                    c = b.moveUp();
                    if(validMove(b, c) == false){
                        c = b.moveDown();
                        if(validMove(b, c) == false){
                            lose = true;
                            Alert alert = new Alert (AlertType.ERROR);
                            alert.setTitle("Game Over");
                            alert.setHeaderText("You have no more moves left!");
                            alert.setContentText("Your score was: " + score);
                            alert.showAndWait();
                        }
                    }
                }
            }
        }
    }

}

