package Mongo;

public interface BoardMongoI{
    int getRows();
    int getColumns();
    void incNumberOfMoves();
    void decNumberOfMoves();
    int getNumberOfMoves();
    void  setRows(int rows);
    void setColumns(int columns);
}
