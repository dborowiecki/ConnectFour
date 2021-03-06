package Game;

import java.io.Serializable;
import java.util.HashMap;

public class Board implements Serializable {
    private int numberOfColumns;
    private int numberOfRows;
    private BoardField[][] boardFields;
    private HashMap<BoardField, String> playerFields;
    private HashMap<String, String> playerColors;

    public Board(int numberOfColumns, int numberOfRows){
        if(numberOfColumns < 1 || numberOfRows < 1){
            throw new IllegalArgumentException("Number of columns or rows is not sufficient");
        }
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
        BoardField field;
        while(n>=0){
            field = boardFields[n][column];
            if(!playerFields.containsKey(field)){

                if(playerColors.containsKey(player)) {
                    playerFields.put(field, player);
                    field.setPlayerColor(playerColors.get(player));
                    return true;
                }
                else {
                    throw new IllegalArgumentException(player+": This player was not added to game");
                }
            }
            n--;
        }
        return false;
    }

    public boolean removeTokenFromColumn(Integer column){
        int i = 0;
        while(i < numberOfRows){
            BoardField bf = boardFields[i][column];
            if(playerFields.containsKey(bf)){
                playerFields.remove(bf);
                return true;
            }
            i++;
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
                            boardField.getPlayerColor() + BoardField.PLAYER_CHAR + TerminalColrs.ANSI_RESET);
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


    public BoardField[][] getBoardFields() {
        return boardFields;
    }

    public BoardField getField(int row, int column){
        return boardFields[row][column];
    }

    public HashMap<BoardField, String> getPlayerFields() {
        return playerFields;
    }

    public String getFieldOwner(BoardField field){
        return playerFields.get(field);
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

    public boolean hasPlayer(String playername){
        if(playerColors.containsKey(playername))
            return true;
        else
            return false;
    }
    private boolean checkColorFormat(String color){
        if(color == null|| color.length()==0) return false;
        return color.matches("\\u001B\\[[0-9]*m");
    }

}
