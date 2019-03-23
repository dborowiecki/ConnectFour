import javax.swing.*;
import java.util.HashMap;

public class Board {
    private int numberOfColumns;
    private int numberOfRows;
    private BoardField[][] boardFields;
    private HashMap<BoardField, String> playerFields;
    private HashMap<String, String> playerColors;

    public Board(int numberOfColumns, int numberOfRows){
        this.numberOfRows = numberOfRows;
        this.numberOfColumns = numberOfColumns;
        generateEmptyBoard();
    }
    public Board(){
        numberOfColumns = 7;
        numberOfRows = 6;
        generateEmptyBoard();
    }

    private void generateEmptyBoard(){
        boardFields = new BoardField[numberOfRows][numberOfColumns];
        for(int i=0;i<numberOfRows;i++)
            for(int j=0;j<numberOfColumns;j++)
                boardFields[i][j] = new BoardField();

        playerFields = new HashMap<>();
        playerColors = new HashMap<>();
    }

    public void addPlayer(String playerName, String playerClor){
        if(!checkColorFormat(playerClor))
            throw new IllegalArgumentException("Player color format is wrong");

        if(playerColors.containsKey(playerName))
            throw new IllegalArgumentException("Player with this name alredy exists");

        playerColors.put(playerName, playerClor);

    }
    public boolean addToken(String player, int column){
        if(column<0||column>=numberOfColumns)
            return false;

        int n=numberOfRows-1;
        BoardField field = boardFields[n][column];
        while(n>=0){
            if(!playerFields.containsKey(field)){

                if(playerColors.containsKey(player)) {
                    playerFields.put(field, player);
                    field.setPlayerColor(playerColors.get(player));
                    return true;
                }
                else {
                    throw new IllegalArgumentException("This player was not added to game");
                }
            }
            n--;
        }
        return false;
    }

    public String getBoard(){
        StringBuilder buildBoard = new StringBuilder();

        for(int i=0;i<numberOfRows;i++){
            //Build row upperline
            for(int j=0; j<numberOfColumns;j++){
                buildBoard.append(boardFields[i][j].getBackgroundColor()+BoardField.UPPERLINE);
            }
            buildBoard.append("\n");
            //Build middle
            for(BoardField boardField : boardFields[i]){
                buildBoard.append(
                        boardField.getBackgroundColor()+BoardField.MIDDLE_LINE+"  "+
                                TerminalColrs.ANSI_RESET);

                if(playerFields.containsKey(boardField)) {
                    buildBoard.append(
                            boardField.getPlayerColor() + boardField.PLAYER_CHAR + TerminalColrs.ANSI_RESET);
                }
                else{
                    buildBoard.append(" ");
                }
                buildBoard.append(boardField.getBackgroundColor()+
                        "  "+BoardField.MIDDLE_LINE);
            }
            buildBoard.append("\n");
            //Build lower
            for(int j=0; j<numberOfColumns;j++){
                buildBoard.append(boardFields[i][j].getBackgroundColor()+BoardField.LOWERLINE);
            }
            buildBoard.append("\n");
        }
        return buildBoard.toString();
    }

    public boolean checkFour(BoardField boardField){
        Integer[] position = checkFieldCoordintes(boardField);

        if(position == null) return false;

        int x = position[0];
        int y = position[1];

        if(checkFourHorizontal(boardField, x, y))return true;
        if(checkFourVertical(boardField, x, y))  return true;
        if(checkFourDiagonal(boardField, x, y))  return true;
        return false;
    }

    private boolean checkFourHorizontal(BoardField boardField, int fieldRow, int fielColumn){
        int counter=0;
        int firstInLeft = fielColumn;
        String player = playerFields.get(boardField);
        BoardField next = boardFields[fieldRow][fielColumn];
        BoardField temp;

        while(playerFields.containsKey(next)&&playerFields.get(next).compareTo(player)==0 && firstInLeft>0) {
            firstInLeft--;
            next = boardFields[fieldRow][firstInLeft];
        }

        temp = boardFields[fieldRow][firstInLeft];

        while(playerFields.containsKey(temp)&&playerFields.get(temp).compareTo(player)==0&&fielColumn<=numberOfColumns){
            counter++;
            temp = boardFields[fieldRow][firstInLeft+counter];
        }

        if(counter>=4)
            return true;
        else
            return false;
    }

    private boolean checkFourVertical(BoardField boardField, int fieldRow, int fielColumn){
        return false;
    }

    private boolean checkFourDiagonal(BoardField boardField, int fieldRow, int fielColumn){
        return false;
    }

    private boolean checkColorFormat(String color){
        if(color == null|| color.length()==0) return false;
        return color.matches("\\u001B\\[[0-9]*m");
    }
    private Integer[] checkFieldCoordintes(BoardField b){
        int i=0;
        int j=0;
        for(i=0;i<numberOfRows;i++) {
            for (j = 0; j < numberOfColumns; j++) {
                if (b == boardFields[i][j]) {
                    Integer[] position = {i, j};
                    return position;
                }
            }
        }
        return null;
    }
    public BoardField[][] getBoardFields() {
        return boardFields;
    }

    public HashMap<BoardField, String> getPlayerFields() {
        return playerFields;
    }

    public int getNumberOfColumns() {
        return numberOfColumns;
    }
    public int getNumberOfRows(){
        return numberOfRows;
    }

    public HashMap<String, String> getPlayerColors() {
        return playerColors;
    }
    //private String getUpperLine(int

//    public void drawBoard(){
//            for(int i=0;i<numberOfColumns;i++){
//                for(int j=0;j<numberOfRows;j++){
//                    System.out.println(SINGLEFIELD[0]);
//                    System.out.println(SINGLEFIELD[1]);
//                    System.out.println(SINGLEFIELD[2]);
//                }
//            }
//    }

}
