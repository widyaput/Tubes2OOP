import java.util.Scanner;
import java.io.FileWriter;
import java.io.PrintWriter;

public class Main {
    public static void main(String args[]){
        Scanner scanner = new Scanner(System.in);
        System.out.println("New or load?");
        String command = scanner.nextLine();
        Player player1;
        if (command.equals("new")){
            Peta map = new Peta();
            map.BacaFile("map.txt");
            player1 = new Player(map);
            player1.starterPack();
            try {
                player1.activateEngimon(0);
            } catch (Exception e) {
                //TODO: handle exception
            }
            player1.spawn();
        }else{
            player1 = Player.load();
            if (player1 == null){
                System.out.println("Couldnt load game");
                scanner.close();
                System.exit(1);
            }
        }
        
        command = scanner.nextLine();
        boolean isLastCommandMove = false;
        boolean isGameOver = false;
        while(!command.equals("exit") && !isGameOver){
            try {
                if (command.equals("save")){
                    player1.save();
                }
                if (command.equals("d")){
                    player1.moveD();
                    isLastCommandMove = true;
                }
                if (command.equals("w")){
                    player1.moveW();
                    isLastCommandMove = true;
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
                if (command.equals("rename")){
                    player1.viewListEng();
                    System.out.println("Choose engimon (use number): ");
                    int number = scanner.nextInt();
                    scanner.nextLine();
                    String nama = scanner.nextLine();
                    player1.getListEng().getElement(number-1).rename(nama);
                }

                if (command.equals("remove")){
                    player1.viewListSkill();
                    System.out.println("Choose skill item (use number): ");
                    int number = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("How many items you want to remove?");
                    int amount = scanner.nextInt();
                    scanner.nextLine();
                    player1.getListSkill().removeXElement(number-1, amount);

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

                if (command.equals("help")){
                    player1.listCommand();
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
                isGameOver = player1.isGameOver();

            } catch (Exception e) {
                e.printStackTrace();
            }
            isLastCommandMove = false;
            if (!isGameOver) {
                System.out.println("Enter command : ");
                command = scanner.nextLine();
            }
        }
        scanner.close();
    }

}
