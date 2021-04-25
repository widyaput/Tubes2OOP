import java.util.Scanner;

public class Main {
    public static void main(String args[]){
        Peta map = new Peta();
        map.BacaFile("map.txt");
        Player player1 = new Player(map);
        player1.spawn();
        Scanner scanner = new Scanner(System.in);
        String command = scanner.nextLine();
        boolean isLastCommandMove = false;
        while(!command.equals("exit")){
            try {
                if (command.equals("d")){
                    player1.moveD();
                    isLastCommandMove = true;
                }
                if (command.equals("w")){
                    player1.moveW();
                    isLastCommandMove = false;
                }
                if (command.equals("s")){
                    player1.moveS();
                    isLastCommandMove = true;
                }
                if (command.equals("a")){
                    player1.moveA();
                    isLastCommandMove = true;
                }
                if (command.equals("map")){
                    player1.showMap();
                }
                if (command.equals("viewEng")){
                    player1.viewListEng();
                }
                if (command.equals("viewSkill")){
                    player1.viewListSkill();
                }
                if (command.equals("activate")){
                    player1.viewListEng();
                    System.out.println("Choose engimon (use number): ");
                    int number = scanner.nextInt();
                    scanner.nextLine();
                    player1.activateEngimon(number-1);
                    System.out.println("");
                }
                if (command.equals("learn")){
                    if (!player1.getStatusEA()){
                        throw new CustomException("No actie engimon");
                    }
                    player1.viewListSkill();
                    System.out.println("Choose skill item (use number): ");
                    int number = scanner.nextInt();
                    scanner.nextLine();
                    player1.learn(number-1);
                }
                if(command.equals("battle")) {
                    player1.initBattle();
                }

                if (player1.getStatusEA() && isLastCommandMove){
                    try {
                        player1.moveAE(command);
                    } catch (Exception e) {
                        //TODO: handle exception
                        e.printStackTrace();
                        player1.relocateAE();
                        System.out.println(String.format("Relocating to %d %d", player1.getYEA(), player1.getXEA()));
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            isLastCommandMove = false;
            System.out.println("Enter command : ");
            command = scanner.nextLine();
        }
        scanner.close();
    }
}
