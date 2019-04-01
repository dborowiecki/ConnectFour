import java.io.Serializable;

public class BoardField implements Serializable {
    public static final String UPPERLINE   =  " ◸   ◹ ";
    public static final String MIDDLE_LINE =  "|";
    public static final String LOWERLINE   =  " ◺   ◿ ";
    public static final String PLAYER_CHAR = "●";

    private String backgroundColor;
    private String playerColor;

    public BoardField(){
        backgroundColor = "";
        playerColor = "";
    }
    public String getBackgroundColor() { return backgroundColor; }
    public String getPlayerColor() { return playerColor; }



    public void setBackgroundColor(String backgroundColor) {
        if(!checkColorFormat(backgroundColor))
            throw new IllegalArgumentException("Wrong string format");

        this.backgroundColor = backgroundColor;
    }

    public void setPlayerColor(String playerColor) {
        if(!checkColorFormat(playerColor))
            throw new IllegalArgumentException("Wrong string format");

        this.playerColor = playerColor;
    }

    private boolean checkColorFormat(String color){
        if(color == null|| color.length()==0) return false;
        return color.matches("\\u001B\\[[0-9]*m");
    }

}
