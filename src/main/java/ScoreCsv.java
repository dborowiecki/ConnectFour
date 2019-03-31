import com.opencsv.CSVWriter;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;


public class ScoreCsv {
    public static String WIN  = ",1,0,0";
    public static String DRAW = ",0,1,0";
    public static String LOSE = ",0,0,1";

    String csvFile;
    String separator = ",";
    public ScoreCsv(String path){
        csvFile = path;
    }

    public void addScore(String player, int result){
        FileWriter fileWriter = null;
        try{
            fileWriter = new FileWriter(csvFile, true);
            }
        catch (Exception e){
            e.printStackTrace();
        }
            PrintWriter printWriter = new PrintWriter(fileWriter);

            switch (result){
                case 0:  printWriter.print(player+DRAW+"\n");
                break;
                case 1:  printWriter.print(player+WIN+"\n");
                    break;
                case -1: printWriter.print(player+LOSE+"\n");
                    break;
                default:
                    printWriter.close();
                    throw new IllegalArgumentException("Result "+result+" cant be handled, use -1, 0 or 1 instead");
            }
            printWriter.close();


    }

    public String readLeaderBoard(){
        HashMap<String, Integer[]> playersResult = new HashMap<>();
        StringBuilder builder = new StringBuilder("PLAYER-WIN-DRAW-LOST\n");
        String line = "";
        try {
            FileReader fileReader = new FileReader(csvFile);

            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
                String[] choped = line.split(separator);
                String player = choped[0];
                Integer win = Integer.parseInt(choped[1]);
                Integer draw= Integer.parseInt(choped[2]);
                Integer lose= Integer.parseInt(choped[3]);

                if(!playersResult.containsKey(choped[0])) {
                    Integer[] playerRes = new Integer[3];
                    playerRes[0] = 0;
                    playerRes[1] = 0;
                    playerRes[2] = 0;
                    playersResult.put(player, playerRes);
                }

                Integer[] playerScore = playersResult.get(player);
                playerScore[0]+=win;
                playerScore[1]+=draw;
                playerScore[2]+=lose;
            }
            System.out.println("PLAYER-WIN-DRAW-LOST");
            for(String player:playersResult.keySet()){
                Integer[] r = playersResult.get(player);
                builder.append(player).append(" ")
                        .append(r[0]).append(" ")
                        .append(r[1]).append(" ")
                        .append(r[2]).append("\n");
            }
            // Always close files.
            bufferedReader.close();
        }
        catch(Exception e) {
            System.out.println("Something went wrong with reading leaderboard");
        }
        return builder.toString();
    }

}
