package Mongo;


public class BoardMongo {
    private int rows;
    private int columns;
    private int numberOfMoves;


    public BoardMongo(int columns, int rows){
        setColumns(columns);
        setRows(rows);
        numberOfMoves = 0;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public void incNumberOfMoves(){
        numberOfMoves++;
    }
    public int getNumberOfMoves(){
        return numberOfMoves;
    }

}
