package Mongo;

public interface PlayerMongoI{
    String getName();
    String getColor();
    Integer getWins();
    Integer getLose();
    Integer getDraw();
    void setColor(String color);
    void setName(String name);
    void addWin();
    void addLose();
    void addDraw();
}
