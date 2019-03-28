import java.util.HashMap;

public class ConnectionsChecker {
    Board board;
    HashMap<BoardField, String> playerFields;

    public ConnectionsChecker(Board board){
        this.board=board;
        this.playerFields = board.getPlayerFields();
    }

    public boolean checkForConnectedTokens(int row, int column, int numberOfConnections){
            int x = row;
            int y = column;

            if(checkForHorizontal(x, y, numberOfConnections))return true;
            if(checkForVertical(x, y,numberOfConnections))  return true;
            if(checkForDiagonal(x, y, numberOfConnections))  return true;
            return false;
    }

    public boolean checkForHorizontal(int fieldRow, int fielColumn, int numberInRow){
        Integer firstInRow = getFirstPlayerTokenInRow(fieldRow, fielColumn);
        Integer lastInRow  = getLastPlayerTokenInRow(fieldRow, firstInRow);

        if((lastInRow+1)-firstInRow>=numberInRow)
            return true;
        else
            return false;
    }

    public boolean checkForVertical(int fieldRow, int fielColumn, int numberInColumn){
        Integer firstInColumn = getFirstPlayerTokenInColumn(fieldRow, fielColumn);
        Integer lastInColumn  = getLastPlayerTokenInColumn(firstInColumn, fielColumn);

        if((lastInColumn+1)-firstInColumn>=numberInColumn)
            return true;
        else
            return false;
    }

    public boolean checkForDiagonal(int fieldRow, int fielColumn, int numberInLine){
        boolean firstDiagonal = checkFirstDiagonal(fieldRow, fielColumn, numberInLine);
        if(firstDiagonal)
            return true;
        boolean secondDiagonal = checkSecondDiagonal(fieldRow, fielColumn, numberInLine);
        if(secondDiagonal)
            return true;

        return false;
    }



    private int getFirstPlayerTokenInRow(int fieldRow, int fielColumn){
        int firstInLeft = fielColumn;
        BoardField next = board.getField(fieldRow,fielColumn);
        String player = board.getFieldOwner(next);

        if(firstInLeft==0)
            return 0;

        while(firstInLeft>=0) {
            if(!(playerFields.containsKey(next)&&playerFields.get(next).equals(player))) {
                firstInLeft++;
                break;
            }
            if(--firstInLeft<0){
                firstInLeft++;
                break;
            }
            next = board.getField(fieldRow,firstInLeft);
        }
        return firstInLeft;
    }

    private int getLastPlayerTokenInRow(int fieldRow, int fielColumn){
        BoardField temp = board.getField(fieldRow,fielColumn);
        String player = board.getFieldOwner(temp);

        while((fielColumn)<board.getNumberOfColumns()){
            if(!(playerFields.containsKey(temp)&&playerFields.get(temp).equals(player))){
                fielColumn--;
                break;
            }
            if(++fielColumn>=board.getNumberOfColumns()){
                fielColumn--;
                break;
            }
            temp = board.getField(fieldRow, fielColumn);
        }
        return fielColumn;
    }

    private int getFirstPlayerTokenInColumn(int fieldRow, int fielColumn){
        BoardField next = board.getField(fieldRow,fielColumn);
        String player = playerFields.get(next);

        if(fieldRow==0)
            return fieldRow;

        while(fieldRow>=0) {
            if(!(playerFields.containsKey(next)&&playerFields.get(next).equals(player))) {
                fieldRow++;
                break;
            }
            if(--fieldRow<0){
                fieldRow++;
                break;
            }
            next = board.getField(fieldRow,fielColumn);
        }

        return fieldRow;
    }

    private int getLastPlayerTokenInColumn(int fieldRow, int fielColumn){
        BoardField temp   = board.getField(fieldRow,fielColumn);
        String     player = playerFields.get(temp);

        while((fieldRow)<board.getNumberOfRows()){
            if(!(playerFields.containsKey(temp)&&playerFields.get(temp).equals(player))){
                fieldRow--;
                break;
            }
            if(++fieldRow>=board.getNumberOfRows()){
                fieldRow--;
                break;
            }
            temp = board.getField(fieldRow, fielColumn);
        }
        return fieldRow;
    }

    private boolean checkFirstDiagonal(int fieldRow, int fielColumn, int numberInLine){
        int counter = 0;
        BoardField next = board.getField(fieldRow,fielColumn);
        String player = playerFields.get(next);

        while(playerFields.containsKey(next)&&playerFields.get(next).equals(player)&&fieldRow<board.getNumberOfRows()&&fielColumn>0){
            next = board.getField(fieldRow++,fielColumn--);
        }

        while(fieldRow>0&&fielColumn<board.getNumberOfColumns()){
            next=board.getField(fieldRow--,fielColumn++);

            if(!(playerFields.containsKey(next)&&playerFields.get(next).equals(player)))
                break;

            counter++;
        }
        return counter>=numberInLine;//>=4;
    }

    private boolean checkSecondDiagonal(int fieldRow, int fielColumn, int numberInLine){
        int counter = 0;
        BoardField next = board.getField(fieldRow,fielColumn);
        String player = playerFields.get(next);


        while(playerFields.containsKey(next)&&playerFields.get(next).equals(player)&&fieldRow>0&&fielColumn>0){
            next = board.getField(fieldRow--,fielColumn--);
        }


        while(fieldRow<board.getNumberOfRows()&&fielColumn<board.getNumberOfColumns()){
            next=board.getField(fieldRow++,fielColumn++);

            if(!(playerFields.containsKey(next)&&playerFields.get(next).equals(player)))
                break;
            counter++;
        }

        return counter>=numberInLine;
    }
}
