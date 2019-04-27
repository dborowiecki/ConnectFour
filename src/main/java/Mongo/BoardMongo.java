package Mongo;

interface BoardMongoI{
    int getRows();
    int getColumns();
    void incNumberOfMoves();
    void decNumberOfMoves();
    int getNumberOfMoves();

}

public class BoardMongo implements BoardMongoI{
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
    public void decNumberOfMoves(){
        numberOfMoves--;
    }
    public int getNumberOfMoves(){
        return numberOfMoves;
    }

}
