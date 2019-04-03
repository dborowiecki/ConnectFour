import java.beans.Transient;
import java.io.*;
import java.util.*;

public class GameManager implements Serializable {
    public static final int CONNECTED_FOR_WIN = 4;
    public static final int NUMBER_OF_PLYERS = 2;
    
    private Board b;
    private String leadboard;
    private ConnectionsChecker connectionsChecker;
    private HashMap<String, String> playerColors;
    private List<String> playersMoveOrder;
    private List<Integer> movesList;
    private transient Scanner sc;
    private int numberOfFields;


    @Transient
    public void startGame(){
        setUpGame();
        addPlayers();
        runGame();
    }


    public static GameManager loadGame(){
        Scanner sc = new Scanner(System.in);
        String saveName;
        boolean continueReading = true;

        while(continueReading) {
            System.out.println("Save path: ");
            do {
                saveName = sc.nextLine().toLowerCase();
            } while (!(saveName.length() > 0));

            try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(saveName))) {
                GameManager game = (GameManager) inputStream.readObject();
                game.sc = new Scanner(System.in);
                return game;
            }
            catch (Exception e){
                System.out.println("Sorry, cannot load file: "+saveName);
                System.out.println("Want to try again? (Y/n)");
                do {
                    saveName = sc.nextLine().toLowerCase();
                } while (!(saveName.length() > 0));
                if(saveName.toUpperCase().charAt(0)!='Y')
                    continueReading = false;
            }
        }
        System.out.println("Could not read file");
        return null;
    }

    public void setUpGame(){
        sc = new Scanner(System.in);
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
        if(leadboard==null)
         setLeadboard("leadboard.csv");

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
    @Transient
    public void runGame(){
        int i = 0;
        Scanner sc = new Scanner(System.in);
        while(true){
            String playerMove = playersMoveOrder.get(i%NUMBER_OF_PLYERS);

            boolean win = makeMove(playerMove, sc);
            i++;
            boolean draw = (i == numberOfFields);
            if(win){
                saveScore(playerMove);
                break;
            }
            if(draw){
                printDraw();
                break;
            }
        }
        sc.close();
    }

    @Transient
    public boolean makeMove(String player, Scanner in) {
        System.out.flush();
        int column = 0;

        String line = "-";
        while(!line.startsWith("s")) {
            printBoardInterface(player, column);
            do {
                line = in.nextLine().toLowerCase();
            } while(!(line.length()>0));

            switch (line.charAt(0)){
                case 'a': column = column > 0 ? column - 1 : column;
                break;
                case 'd': column = column >= getBoard().getNumberOfColumns()-1 ? column : column+1;
                    break;
                case 'z': turnBackMove(player, in);
                    break;
                case 's': if(!putToken(player, column)) line = "-";
                    break;
                case 'o': if(saveGame(in)) System.out.println("Game saved!");
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

    public void removeLastMove(){
        if(movesList.isEmpty())
            return;

        int lastIndex = movesList.size()-1;
        Integer lastMove = movesList.get(lastIndex);
        boolean success = b.removeTokenFromColumn(lastMove);

        if(success)
            movesList = movesList.subList(0, lastIndex);
    }

    private boolean checkWin(int lastColumnInput){
        int row = 0;
        boolean isEmpty;
        isEmpty = !b.getPlayerFields().containsKey(b.getField(row, lastColumnInput));
        while(isEmpty){
            row++;
            BoardField nextField = b.getField(row, lastColumnInput);
            isEmpty = !b.getPlayerFields().containsKey(nextField);
        }
        System.out.println(row+" "+ lastColumnInput);
        return connectionsChecker.checkForConnectedTokens(row, lastColumnInput, CONNECTED_FOR_WIN);
    }

    private void announceWinner(String playerName){
        System.out.flush();
        System.out.println(b.getBoard());
        System.out.println(playerColors.get(playerName)+"PLAYER "+playerName+" WON!");
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

    private void printDraw(){
        System.out.flush();
        System.out.println("IT'S A DRAW, THANKS FOR PLAYING");

        saveDraw();
    }

    private void saveScore(String playerName){
        ScoreCsv sc = new ScoreCsv(leadboard);
        sc.addScore(playerName, 1);
        for(String player:playersMoveOrder)
            if(!player.equals(playerName))
                sc.addScore(player, -1);

    }

    private void saveDraw(){
        ScoreCsv sc = new ScoreCsv(leadboard);
        for(String player: playersMoveOrder)
            sc.addScore(player, 0);
    }

    @Transient
    private boolean saveGame(Scanner sc){
        System.out.println("Name for next save: ");
        String saveName;

        do {
            saveName = sc.nextLine().toLowerCase();
        } while(!(saveName.length()>0));
        try (ObjectOutputStream outputStream = new ObjectOutputStream(
                new FileOutputStream(saveName))) {
            outputStream.writeObject(this);
            outputStream.close();
            return true;
        } catch (Exception e){
            System.out.println("Something went wrong with saving file, error message:\n");
            e.printStackTrace();
            return false;
        }
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
                "S - drop to column "+
                "Z - return last move "+
                "o - save game");
    }


    public Board getBoard(){
        return b;
    }

    public void setLeadboard(String path){
        leadboard = path;
    }
}
