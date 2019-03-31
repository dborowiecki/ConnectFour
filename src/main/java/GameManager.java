
import java.util.*;

public class GameManager {
    private Board b;
    private ConnectionsChecker connectionsChecker;
    private int NUMBER_OF_PLYERS = 2;
    public static final int CONNECTED_FOR_WIN = 4;
    private HashMap<String, String> playerColors;
    private List<String> playersMoveOrder;
    private List<Integer> movesList;
    private Scanner sc;
    private int numberOfFields;

    public GameManager(){
        //setUpGame();
        //addPlayers();
    }
    public void startGame(){
        setUpGame();
        addPlayers();
        runGame();

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
        connectionsChecker = new ConnectionsChecker(b);
        movesList = new ArrayList<>();
        playerColors  = new HashMap<>();
        playersMoveOrder = new LinkedList<>();
        numberOfFields = b.getNumberOfColumns()*b.getNumberOfRows();
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
    public void runGame(){
        int i = 0;
        boolean end = false;

    }

    public boolean makeMove(String player, Scanner in) {
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
                case 's': if(!putToken(player, column)) line = "-";
                    break;
            }
        }

       boolean win = checkWin(column);
        if(win){
            announceWinner(player);
            return true;
        }
        return false;
    }

    private boolean checkWin(int lastColumnInput){
        int row = 0;
        boolean isEmpty = true;
        while(isEmpty&&row<b.getNumberOfRows()-1){
            BoardField nextField = b.getField(row, lastColumnInput);
            isEmpty = !b.getPlayerFields().containsKey(nextField);
            row++;
        }
        System.out.println(row+" "+ lastColumnInput);
        return connectionsChecker.checkForConnectedTokens(row, lastColumnInput, CONNECTED_FOR_WIN);
    }
    private void announceWinner(String playerName){
        System.out.println("WiNeR");
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

    private boolean putToken(String player, int column){
        boolean success = b.addToken(player, column);
        if(!success) {
            System.out.println("You can't put token in this column!");
            return false;
        }
        else {
            movesList.add(column);
            return true;
        }
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
    public Board getBoard(){
        return b;
    }
}
