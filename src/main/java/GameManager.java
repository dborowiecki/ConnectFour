import java.util.Scanner;

public class GameManager {
    Board b;

    public GameManager(){
        //setUpGame();
        //addPlayers();
    }
    public void startGame(){

    }

    public void setUpGame(){
        Scanner keyboard = new Scanner(System.in);
        System.out.print("hello");
        System.out.print("Default board size? (Y/n)");
        String def = keyboard.nextLine();
        if(def.toLowerCase().contains("y"))
            b = new Board();
        else {
            System.out.print("Number of rows: ");
            int numberOfRows = keyboard.nextInt();
            System.out.print("Number of columns: ");
            int numberOfColumns = keyboard.nextInt();
            b = new Board(numberOfColumns, numberOfRows);
        }

    }

    public void addPlayers(){

    }
    public void addPlayer(){

    }

    public void addPlayersToBoard(){

    }

    public void makeMove(){

    }

    public void updateBoard(){

    }

    public Board getBoard(){
        return b;
    }
}
