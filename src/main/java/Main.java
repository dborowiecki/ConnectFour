import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        GameManager gm;

        System.out.println("1. New game");
        System.out.println("2. Load save");

        Scanner sc = new Scanner(System.in);
        String action = readPlayerInput(sc);

        switch (action.charAt(0)){
            case '1': gm = new GameManager();
                 gm.startGame();
            break;
            case '2':gm = GameManager.loadGame();
                if(gm!=null) {
                    gm.runGame();
                    break;
                }
            default: gm= new GameManager();
                gm.runGame();
        }
    }

    public static String readPlayerInput(Scanner sc){
        String input;
        do {
            input = sc.nextLine().toLowerCase();
        } while (!correctInput(input));
        return input;
    }
    public static boolean correctInput(String input){
        if(input.length()==0) return false;
        if(input.charAt(0)!='1'&&input.charAt(0)!='2'){
            System.out.println("This is not correct input");
            return false;
        }
        return true;
    }
}
