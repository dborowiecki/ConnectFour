import java.util.Scanner;

public class Main {
    public static final String LEADBOARD_PATH = "leadboard.csv";

    public static void main(String[] args) {
            Scanner sc = new Scanner(System.in);
            startGame(sc);
    }

    private static void startGame(Scanner sc){
        GameManager gm;
        boolean next = true;
        while(next){
            System.out.println("1. New game");
            System.out.println("2. Load save");
            System.out.println("3. Show leadboard");
            System.out.println("4. Exit");
            String action = readPlayerInput(sc);

            switch (action.charAt(0)) {
                case '1':
                    gm = new GameManager();
                    gm.startGame();
                    break;
                case '2':
                    gm = GameManager.loadGame();
                    if (gm != null) { gm.runGame(); }
                    break;
                case '3':
                    readScores(LEADBOARD_PATH);
                    break;
                case '4':
                    next = false;
                    break;

            }
        }
    }

    private static String readPlayerInput(Scanner sc){
        sc.reset();
        String input;
        do {
            input = sc.nextLine().toLowerCase();
        } while (!correctInput(input));
        return input;
    }

    private static boolean correctInput(String input){
        if(input.length()==0) return false;
        if(input.charAt(0)!='1'&&input.charAt(0)!='2'&&input.charAt(0)!='3'&&input.charAt(0)!='4'){
            System.out.println("This is not correct input");
            return false;
        }
        return true;
    }

    private static void readScores(String path){
        ScoreCsv sc = new ScoreCsv(path);
        System.out.println(sc.readLeaderBoard());
    }

}
