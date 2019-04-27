package Mongo;

import Game.TerminalColrs;

import java.rmi.UnknownHostException;
import java.util.*;

public class GameMongo {
    public static final String[] colors = {"RED", "BLUE", "PURPLE","YELLOW","GREEN"};
    BoardMongoI board;
    private GameCollectionI game;
    List<MoveMongoI> moves;
    List<PlayerMongoI> players;

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

    public GameMongo(GameCollectionI game){
        try {
            this.game = game;
        } catch (Throwable e) {
            System.out.println("Exception catch:");
            e.printStackTrace();
        }
        moves = new LinkedList<>();
        players = new LinkedList<>();
    }

    public void addBoard(BoardMongoI board){
        int columns = board.getColumns();
        int rows = board.getRows();

        if(columns<4||rows<4)
            throw new IllegalArgumentException("Rows and colums need to be bigger than 4, now:\n"
                    +"Rows: "+rows
                    +"\nColumns: "+columns);

        this.board = board;
        game.saveBoard(board);
    }


    public void addPlayer(PlayerMongoI player){
        String name = player.getName();
        String color = player.getColor();
        if(name==null || name.isEmpty())
            throw new IllegalArgumentException("Name can't be empty");

        if(!Arrays.asList(colors).contains(color))
            throw new IllegalArgumentException("Color "+color+" is not allowed");

        game.savePlayer(player);
        players.add(player);

    }


    public List<PlayerMongoI> getPlayers(){
        if(players == null)
            return Collections.emptyList();
        else
            return players;
    }


    public List<MoveMongoI> getPlayerMoves(String player){
        PlayerMongoI p = game.findByName(player);
        if (p == null){
            return Collections.emptyList();
        }
        List<MoveMongoI> playerMoves = new LinkedList<MoveMongoI>();

        for(MoveMongoI m: moves){
            if(m.getPlayer()==p)
                playerMoves.add(m);
        }

        return playerMoves;
    }

    public void makeMove(String player, int column){
        PlayerMongoI p = game.findByName(player);

        if(column<0||column>board.getColumns())
            throw  new IllegalArgumentException("Column have to be between 0 and "+board.getColumns());

        MoveMongo m = new MoveMongo(p, column);
        m.setOrder(board.getNumberOfMoves());
        board.incNumberOfMoves();

        moves.add(m);
        game.saveMove(m);
    }

    public void reverseLastMove(String player){
        PlayerMongoI p = game.findByName(player);

        if(moves.size()==0)
            throw new IllegalArgumentException("There were no moves");

        MoveMongoI last;


        for(int i=moves.size();i>0;i--){
            last = moves.get(moves.size()-1);
            if(last.getPlayer().equals(p)){
                moves.remove(last);
                board.decNumberOfMoves();
                game.deleteMove(last);
                break;
            }
        }
    }

    public void endGame(String winnerName){
        PlayerMongoI winner = game.findByName(winnerName);

        if(!players.contains(winner))
            throw new IllegalArgumentException("Player was not in game");

        winner.addWin();
        for(PlayerMongoI p: players){
            if(!p.equals(winner))
               p.addLose();
            game.savePlayer(p);
        }
    }

    public void endGameDraw(){
        for(PlayerMongoI p: players){
            p.addDraw();
            game.savePlayer(p);
        }
    }

    public HashMap<String, Integer> getPlayerScore(String playerName){
        PlayerMongoI p = game.findByName(playerName);
        HashMap<String, Integer> score = new HashMap<>();
        score.put("Win", p.getWins());
        score.put("Lose", p.getLose());
        score.put("Draw", p.getDraw());
        return score;
    }

    public List<MoveMongoI> getMoves() {
        return moves;
    }

    public void setMoves(List<MoveMongoI> moves) {
        this.moves = moves;
    }


    public MoveMongoI getLastMove(){
        return moves.get(moves.size()-1);
    }

    public void setBoard(BoardMongoI b) {this.board = b;}
    public BoardMongoI getBoard() {return board;}
}