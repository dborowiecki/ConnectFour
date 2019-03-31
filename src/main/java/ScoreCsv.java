import com.opencsv.CSVWriter;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;


public class ScoreCsv {
    public static String WIN  = ";1;0;0";
    public static String DRAW = ";0;1;0";
    public static String LOSE = ";0;0;1";

    String csvFile;
    String separator = ";";
    public ScoreCsv(String path){
        csvFile = path;
    }

    public void addScore(String player, int result){
        try {
            FileWriter fileWriter = new FileWriter(csvFile);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            switch (result){
                case 0: bufferedWriter.write(player+DRAW);
                break;
                case 1: bufferedWriter.write(player+WIN);
                    break;
                case -1: bufferedWriter.write(player+LOSE);
                    break;
                default:
                    throw new IllegalArgumentException("Result "+result+" cant be handled");
            }

            bufferedWriter.close();
        }
        catch(IOException ex) {
            System.out.println("Error with saving match result");
        }
    }

    public void readLeaderBoard(){
        HashMap<String, Integer[]> playersResult = new HashMap<>();
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

                if(!playersResult.containsKey(choped[0]))
                    playersResult.put(player, new Integer[3]);

                Integer[] playerScore = playersResult.get(player);
                playerScore[0]+=win;
                playerScore[1]+=draw;
                playerScore[2]+=lose;
            }
            System.out.println("PLAYER-WIN-DRAW-LOST");
            for(String player:playersResult.keySet()){
                Integer[] r = playersResult.get(player);
                System.out.println(player+" "+r[0]+" "+r[1]+" "+r[2]);
            }
            // Always close files.
            bufferedReader.close();
        }
        catch(Exception e) {
            System.out.println("Error while reading leaderboard");
        }

    }

}
