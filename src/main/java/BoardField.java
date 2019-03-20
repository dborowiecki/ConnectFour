public class BoardField{
    public static final String UPPERLINE =  " ◸   ◹";
    public static final String MIDDLE_LINE = "|";
    public static final String LOWERLINE =  " ◺   ◿";
    public static final String PLAYER_CHAR = "●";

    private String backgroundColor;
    private String playerColor;

    public String getBackgroundColor() { return backgroundColor; }

    public void setBackgroundColor(String backgroundColor) {
        if(!checkColorFormat(backgroundColor))
            throw new IllegalArgumentException("Wrong string format");
        this.backgroundColor = backgroundColor; }

    public String getPlayerColor() { return playerColor; }

    public void setPlayerColor(String playerColor) { this.playerColor = playerColor; }

    public BoardField(String fieldColor){
        if(!checkColorFormat(fieldColor))
            throw new IllegalArgumentException("Wrong string format");
        this.backgroundColor = fieldColor;
    }
    public BoardField(){
        this.backgroundColor = TerminalColrs.ANSI_YELLOW;
    }

    private boolean checkColorFormat(String color){
        if(color == null|| color.length()==0) return false;
        return color.matches("\\u001B\\[[0-9]*m");
    }
}
