package Mongo;

import Game.TerminalColrs;

import java.rmi.UnknownHostException;
import java.util.*;

public class GameMongo {
    public static final String[] colors = {"RED", "BLUE", "PURPLE","YELLOW","GREEN"};
    BoardMongo board;
    private GameCollection game;
    List<MoveMongo> moves;
    List<PlayerMongo> players;

    public GameMongo(){
        try {
            game = new GameCollection();
        } catch (Throwable e) {
            System.out.println("Exception catch:");
            e.printStackTrace();
        }
        moves = new LinkedList<>();
        players = new LinkedList<>();
    }

    public GameMongo(GameCollection game){
        try {
            this.game = game;
        } catch (Throwable e) {
            System.out.println("Exception catch:");
            e.printStackTrace();
        }
        moves = new LinkedList<>();
        players = new LinkedList<>();
    }

    public void addBoard(BoardMongo board){
        int columns = board.getColumns();
        int rows = board.getRows();

        if(columns<4||rows<4)
            throw new IllegalArgumentException("Rows and colums need to be bigger than 4, now:\n"
                    +"Rows: "+rows
                    +"\nColumns: "+columns);

        this.board = board;
        game.saveBoard(board);
    }


    public void addPlayer(PlayerMongo player){
        String name = player.getName();
        String color = player.getColor();
        if(name==null || name.isEmpty())
            throw new IllegalArgumentException("Name can't be empty");

        if(!Arrays.asList(colors).contains(color))
            throw new IllegalArgumentException("Color "+color+" is not allowed");

        game.savePlayer(player);
        players.add(player);

    }


    public List<PlayerMongo> getPlayers(){
        if(players == null)
            return Collections.emptyList();
        else
            return players;
    }


    public List<MoveMongo> getPlayerMoves(String player){
        PlayerMongo p = game.findByName(player);
        if (p == null){
            return Collections.emptyList();
        }
        List<MoveMongo> playerMoves = new LinkedList<MoveMongo>();

        for(MoveMongo m: moves){
            if(m.player==p)
                playerMoves.add(m);
        }

        return playerMoves;
    }

    public void makeMove(String player, int column){
        PlayerMongo p = game.findByName(player);

        if(column<0||column>board.getColumns())
            throw  new IllegalArgumentException("Column have to be between 0 and "+board.getColumns());

        MoveMongo m = new MoveMongo(p, column);
        m.setOrder(board.getNumberOfMoves());
        board.incNumberOfMoves();

        moves.add(m);
        game.saveMove(m);

    }

    public void endGame(String winnerName){
        PlayerMongo winner = game.findByName(winnerName);
        winner.addScore(Score.WIN);
        for(PlayerMongo p: players){
            if(!p.equals(winner))
                p.addScore(Score.LOSE);

            game.savePlayer(p);
        }
    }

    public void endGameDraw(){
        for(PlayerMongo p: players){
            p.addScore(Score.DRAW);
            game.savePlayer(p);
        }
    }

    public int getPlayerScore(String playerName){
        PlayerMongo p = game.findByName(playerName);
        return p.getScore();
    }

    public List<MoveMongo> getMoves() {
        return moves;
    }

    public void setMoves(List<MoveMongo> moves) {
        this.moves = moves;
    }


    public MoveMongo getLastMove(){
        return moves.get(moves.size()-1);
    }

    public void setBoard(BoardMongo b) {this.board = b;}
    public BoardMongo getBoard() {return board;}
}