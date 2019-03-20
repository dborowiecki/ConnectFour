import java.util.HashMap;

public class Board {
    private int numberOfColumns;
    private int numberOfRows;
    private BoardField[][] boardFields;
    private HashMap<BoardField, String> playerFields;

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
    }

    public boolean addToken(String player, int column){
        if(column<0||column>=numberOfColumns)
            throw new IllegalArgumentException();

        int n=numberOfRows-1;

        while(n>=0){
            if(!playerFields.containsKey(boardFields[n][column])){
                playerFields.put(boardFields[n][column], player);
                return true;
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
                                TerminalColrs.ANSI_RESET+
                        boardField.getPlayerColor()+boardField.PLAYER_CHAR+TerminalColrs.ANSI_RESET+
                                boardField.getBackgroundColor()+
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

    public HashMap<BoardField, String> getPlayerFields() {
        return playerFields;
    }

    public int getNumberOfColumns() {
        return numberOfColumns;
    }
    public int getNumberOfRows(){
        return numberOfRows;
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
