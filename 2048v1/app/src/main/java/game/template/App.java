/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package game.template;

import java.net.URL;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.image.Image;
public class App extends Application
{
    private boolean updatingBoard = false;
    TextField score = new TextField();
    TextField highScore = new TextField();
    private static final int SIZE = 4;
    // this is the size of each square 
    // this must be kept in sync with the size of the images, and the
    // size of the squares in the css file
    private static final int SQUARE_SIZE = 70;
    private VBox root;
    private Board board = new Board();
    
    // using StackPane so that they can hold a rectangle and an image
    // we use the rectangle to color the squares
    // and the image to place the pieces
    private StackPane[][] grid = new StackPane[SIZE][SIZE];

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        root = new VBox();

        root.getChildren().add(createMenuBar());

        GridPane gridPane = new GridPane();
        // preferred size of the gridpane
        gridPane.setPrefSize(SQUARE_SIZE * 4, SQUARE_SIZE * 4);
        
        root.getChildren().add(gridPane);

        // loosely based on https://stackoverflow.com/questions/69339314/how-can-i-draw-over-a-gridpane-of-rectangles-with-an-image-javafx
        for (int row = 0; row < SIZE; row++)
        {
            for (int col = 0; col < SIZE; col++)
            {
                Rectangle rect = new Rectangle(SQUARE_SIZE, SQUARE_SIZE);
                
                    rect.getStyleClass().add("black-square");
                
                StackPane cell = new StackPane(rect);
                
                grid[row][col] = cell;

                // name each cell with its row and column
                // unsure we'll need this
                cell.setId(row + "-" + col);

                // we need to create these extra local final variables
                // I think this has to do with thread safety?
                final int r = row;
                final int c = col;
                // make each cell clickable

                // finally, put the stackpane into the gridpane
                gridPane.add(cell, col, row);
            }
        }

        // don't give a width or height to the scene
        // it will figure it out because there's a menu bar
        // plus each square is a fixed size
        Scene scene = new Scene(root);

        // add style information
        URL styleURL = getClass().getResource("/style.css");
        String stylesheet = styleURL.toExternalForm();
        scene.getStylesheets().add(stylesheet);

        // set title and scene and show to the user
        primaryStage.setTitle("GAME TEMPLATE");
        primaryStage.setScene(scene);
        primaryStage.show();
        root.requestFocus();
        setKeyboardHandler();
        board = Board.loadBoard();
        updateBoard();
        

    }

    private void clearBoard()
    {
        // removes all of the images (pieces) from the board
        for (int row = 0; row < SIZE; row++)
        {
            for (int col = 0; col < SIZE; col++)
            {
                grid[row][col].getChildren().removeIf(child -> child instanceof ImageView);
            }
        }
    }
    private void updateBoard(){
        clearBoard();
        updatingBoard = true;
        for (int row = 0; row < SIZE; row++)
        {
            for (int col = 0; col < SIZE; col++)
            {
                int value = board.get(row, col);    
                if(value != 0)
                    loadImage(value, row, col); 
            }
        }
        updatingBoard = false;
    }
    private void setKeyboardHandler()
    {
        // add this to the root which is a VBox
        root.setOnKeyPressed(event -> {
            System.out.println("Key pressed: " + event.getCode());
            Board newBoard = null;
            boolean moveMade = false;
            switch (event.getCode())
            {
                // check for the key input
                case ESCAPE:
                    // remove focus from the textfields by giving it to the root VBox
                    root.requestFocus();
                    System.out.println("You pressed ESC key");
                    break;
                case W:
                case UP:
                        System.out.println("You pressed W key");
                        
                        newBoard = board.moveUp();
                        moveMade = board.validMove(board, newBoard);
                        
                       
                        break;
                    case S:
                    case DOWN:
                        System.out.println("You pressed S key");
                        newBoard = board.moveDown();
                        moveMade = board.validMove(board, newBoard);
                        break;
                    
                    case A:
                    case LEFT:
                        System.out.println("You pressed ENTER key");
                        newBoard = board.moveLeft();
                        moveMade = board.validMove(board, newBoard);
                        break;
                    
                    case D:
                    case RIGHT:
                        System.out.println("You pressed ENTER key");
                        newBoard = board.moveRight();
                        moveMade = board.validMove(board, newBoard);
                        break;
                default:
                    System.out.println("you typed key: " + event.getCode());
                    break;
                
            }
            if(moveMade){
                board = newBoard;
                board.addNum(board);
                updateBoard();
                score.setText("Score: " + board.getScore());
                highScore.setText("High Score: " + board.getHighScore());
            }
            board.checkWin();
            board.gameOver(board);
        });
    }

    private void loadImage(int value, int row, int col)
    {
        String imageName = value + ".png";
        Image image = new Image(getClass().getResourceAsStream("/assets/" + imageName));
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(SQUARE_SIZE);
        imageView.setFitHeight(SQUARE_SIZE);
        grid[row][col].getChildren().add(imageView);
    }
    


    private MenuBar createMenuBar()
    {
        MenuBar menuBar = new MenuBar();
    	menuBar.getStyleClass().add("menubar");

        //
        // File Menu
        //
    	Menu fileMenu = new Menu("Game");

        addMenuItem(fileMenu, "New Game", () -> {
            System.out.println("New Game");
            clearBoard();
            board = Board.loadBoard();
            score.setText("Score: " + board.getScore());
            updateBoard();
        });
        

        menuBar.getMenus().add(fileMenu);

        Menu scoreMenu = new Menu("Score");
        
        score.setEditable(false);
        score.setText("Score: " + board.getScore());
        scoreMenu.getItems().add(new MenuItem("", score));
        menuBar.getMenus().add(scoreMenu);

        Menu highScoreMenu = new Menu("High Score");
        
        highScore.setEditable(false);
        highScore.setText("High Score: " + board.getHighScore());
        highScoreMenu.getItems().add(new MenuItem("", highScore));
        menuBar.getMenus().add(highScoreMenu);


        return menuBar;
    }
    


    private void addMenuItem(Menu menu, String name, Runnable action)
    {
        MenuItem menuItem = new MenuItem(name);
        menuItem.setOnAction(event -> action.run());
        menu.getItems().add(menuItem);
    }

    public static void main(String[] args) 
    {
        launch(args);
    }
}