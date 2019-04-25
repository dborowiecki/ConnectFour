package Mongo;

import java.net.UnknownHostException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class GameMongo {
    private GameCollection game;
    BoardMongo board;
    List<MoveMongo> moves;
    List<PlayerMongo> players;

    public GameMongo(){
        try {
            game = new GameCollection();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

    }

    public void createBoard(int rows, int columns){
        board = new BoardMongo(columns, rows);
        game.saveBoard(board);
    }

    public void addPlayer(String name, String color, Integer score){
        PlayerMongo p = new PlayerMongo(name, color, score);
        game.savePlayer(p);
        players.add(p);

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

}