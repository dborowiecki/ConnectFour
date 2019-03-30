
import java.util.List;
import java.util.Scanner;

public class GameManager {
    Board b;
    private int NUMBER_OF_PLYERS = 2;
    List<String> players;

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
        Scanner sc = new Scanner(System.in);
        for(int i=0; i<NUMBER_OF_PLYERS;i++){
          //  try {
                addPlayer(sc);
//            }
//            catch (Exception e){
//                System.out.println("Error while adding player: "+e.getMessage());
//                //i--;
//            }
        }

    }
    private void addPlayer(Scanner sc){

        System.out.println("Available colors:\n"+
                TerminalColrs.ANSI_BLUE   +"BLUE"  +TerminalColrs.ANSI_RESET+"\n"+
                TerminalColrs.ANSI_RED    +"RED"   +TerminalColrs.ANSI_RESET+"\n"+
                TerminalColrs.ANSI_GREEN  +"GREEN" +TerminalColrs.ANSI_RESET+"\n"+
                TerminalColrs.ANSI_PURPLE +"PURPLE"+TerminalColrs.ANSI_RESET+"\n"+
                TerminalColrs.ANSI_YELLOW +"YELLOW"+TerminalColrs.ANSI_RESET);

        System.out.println("Player name: ");
        String pName = sc.nextLine();

        System.out.println("Player color: ");
        String pColor = sc.nextLine();
        String asciiFormatColor = TerminalColrs.translateColor(pColor);
        b.addPlayer(pName, asciiFormatColor);
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
