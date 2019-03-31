
import java.util.*;

public class GameManager {
    Board b;
    private int NUMBER_OF_PLYERS = 2;
    HashMap<String, String> playerColors;
    List<String> playersMoveOrder;
    List<Integer> movesList;
    Scanner sc;

    public GameManager(){
        //setUpGame();
        //addPlayers();
    }
    public void startGame(){

    }

    public void setUpGame(){
        sc = new Scanner(System.in);
        System.out.print("HELLO!\n");
        System.out.print("Default board size? (Y/n)");
        String def = sc.nextLine();
        if(def.toLowerCase().contains("y")) {
            b = new Board();
        }
        else {
            System.out.print("Number of rows: ");
            int numberOfRows = sc.nextInt();
            System.out.print("Number of columns: ");
            int numberOfColumns = sc.nextInt();
            b = new Board(numberOfColumns, numberOfRows);
        }
        movesList = new ArrayList<>();
        playerColors  = new HashMap<>();
        playersMoveOrder = new LinkedList<>();
    }

    public void addPlayers(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Available colors:\n"+
                TerminalColrs.ANSI_BLUE   +"BLUE, "  +
                TerminalColrs.ANSI_RED    +"RED, "   +
                TerminalColrs.ANSI_GREEN  +"GREEN, " +
                TerminalColrs.ANSI_PURPLE +"PURPLE, "+
                TerminalColrs.ANSI_YELLOW +"YELLOW"+TerminalColrs.ANSI_RESET);
        int j=0;
        for(int i=0; i<NUMBER_OF_PLYERS;i++){
            try {
                addPlayer(sc);
            }
            catch (Exception e){
                System.out.println("Error while adding player: "+e.getMessage());
                System.out.println("Do you want to try again? Y/n");
                String def = sc.nextLine();
                if(def.toLowerCase().contains("y"))
                   i--;
            }
        }

    }
    private void addPlayer(Scanner sc){



        System.out.println("Player name: ");
        String pName = sc.nextLine();

        System.out.println("Player color: ");
        String pColor = sc.nextLine();
        String asciiFormatColor = TerminalColrs.translateColor(pColor);

        playerColors.put(pName,asciiFormatColor);
        playersMoveOrder.add(pName);
        b.addPlayer(pName, asciiFormatColor);
    }

    public void makeMove(String player, Scanner in) {
        int column = 0;

        String line = "-";
        while(!line.startsWith("s")) {
            printBoardInterface(player, column);

            line = in.nextLine().toLowerCase();
            switch (line.charAt(0)){
                case 'a': column = column > 0 ? column - 1 : column;
                break;
                case 'd': column = column >= getBoard().getNumberOfColumns()-1 ? column : column+1;
                    break;
                case 'z': turnBackMove(player, in);
                    break;
                case 's': putToken(player, column);
                    break;
            }
        }
    }

    public void updateBoard(){

    }

    public void removeLastMove(){
        int lastIndex = movesList.size()-1;
        if(movesList.isEmpty())
            return;
        Integer lastMove = movesList.get(lastIndex);

        boolean success = b.removeTokenFromColumn(lastMove);
        if(success)
            movesList = movesList.subList(0, lastIndex);

    }

    private void putToken(String player, int column){
        b.addToken(player, column);
        movesList.add(column);
    }

    private void turnBackMove(String currentPlayer, Scanner in){
        removeLastMove();
        String previousPlayer = "";
        Integer playerWithRemovedMove = playersMoveOrder.indexOf(currentPlayer);
        if(playerWithRemovedMove==0)
            previousPlayer = playersMoveOrder.get(playersMoveOrder.size()-1);
        else
        if(playerWithRemovedMove==(playersMoveOrder.size()-1))
            previousPlayer = playersMoveOrder.get(0);
        else
            previousPlayer = playersMoveOrder.get(playersMoveOrder.indexOf(currentPlayer)-1);


        makeMove(previousPlayer, in);
    }

    private String getDecidingLine(String player, int column){
        StringBuilder build = new StringBuilder("   ");
        for(int i=0;i<column;i++)
            build.append("       ");

        build.append(playerColors.get(player)+BoardField.PLAYER_CHAR+TerminalColrs.ANSI_RESET);

        return build.toString();
    }

    private void printBoardInterface(String player, int move){
        System.out.println("PLAYER: "+player);
        System.out.println(getDecidingLine(player, move));
        System.out.println(b.getBoard());
        printInsructions();
    }

    private void printInsructions(){
        System.out.println(getInstructions());
    }
    private String getInstructions(){
        return ("D - token to right\n" +
                "A - token to left" +
                "S - drop to column"+
                "Z - return last move");
    }
    public Board getBoard(){
        return b;
    }
}
