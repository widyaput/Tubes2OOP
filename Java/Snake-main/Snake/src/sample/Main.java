package sample;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import java.io.FileInputStream;
import javafx.scene.image.ImageView;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;


public class Main extends Application {

    private static final int WIDTH = 500;
    private static final int HEIGHT = 500;
    private static final int PLAYER_HEIGHT = 100;
    private static final int PLAYER_WIDTH = 15;
    private boolean gameStarted;
    private static final int ROWS = 16;
    private static final int COLUMNS = ROWS;
    private static final int SQUARE_SIZE = WIDTH / ROWS;
    private static  final String[] MAP_IMAGE = new String[]{"/img/grass.png", "/img/air.png", "/img/ice.png", "/img/rock.png"};
    private static  final String[] ENGIMON_IMAGE = new String[]{"/img/water_ground_up.png", "/img/water_ground_down.png", "/img/electric_up.png",
            "/img/electric_down.png", "/img/fire_up.png", "/img/fire_down.png", "/img/ground_up.png", "/img/ground_down.png",
            "/img/ice_up.png", "/img/ice_down.png", "/img/water_up.png", "/img/water_down.png", "/img/water_ice_up.png",
            "/img/water_ice_down.png", "/img/fire_electric_up.png", "/img/fire_electric_down.png"};
    private static final String[] SKILL_IMAGE = new String[]{
            "/img/skill11.png", "/img/skill12.png", "/img/skill13.png",
            "/img/skill21.png", "/img/skill22.png", "/img/skill23.png",
            "/img/skill31.png", "/img/skill32.png", "/img/skill33.png",
            "/img/skill41.png", "/img/skill42.png", "/img/skill43.png",
            "/img/skill51.png", "/img/skill52.png", "/img/skill53.png",
            "/img/skill61.png", "/img/skill62.png", "/img/skill63.png",
            "/img/skill71.png", "/img/skill72.png", "/img/skill73.png",
            "/img/skill81.png", "/img/skill82.png", "/img/skill83.png",
            "/img/skill91.png", "/img/skill92.png", "/img/skill93.png",
            "/img/skill101.png", "/img/skill102.png", "/img/skill103.png",
            "/img/skill111.png", "/img/skill112.png", "/img/skill113.png",
            "/img/skill121.png", "/img/skill122.png", "/img/skill123.png",
            "/img/skill131.png", "/img/skill132.png", "/img/skill133.png",
            "/img/skill141.png", "/img/skill142.png", "/img/skill143.png",
            "/img/skill151.png", "/img/skill152.png", "/img/skill153.png",
            "/img/skill161.png", "/img/skill162.png", "/img/skill163.png",
            "/img/skill171.png", "/img/skill172.png", "/img/skill173.png",
            "/img/skill181.png", "/img/skill182.png", "/img/skill183.png",
            "/img/skill191.png", "/img/skill192.png", "/img/skill193.png",
            "/img/skill201.png", "/img/skill202.png", "/img/skill203.png",
            "/img/skill211.png", "/img/skill212.png", "/img/skill213.png"
    };
    private int activeLevel = 3;
    private static final int RIGHT = 0;
    private static final int LEFT = 1;
    private static final int UP = 2;
    private static final int DOWN = 3;

    private static final int TURN_MOVE = 3;
    private static final int TURN_EXP = 8;

    private GraphicsContext gc;
    private List<Point> snakeBody = new ArrayList();
    private Point snakeHead;
    private Image mapImage;
    private Image engimonImage;
    private boolean gameOver;
    private int currentDirection;
    private int score = 0;
    private Text ta;
    private HBox hb;
    private VBox icon;
    private int arah;
    private Text invenInfo;

    private boolean player1right = false;
    private boolean player1left = false;
    private boolean player1down = false;
    private boolean player1up = true;
    private boolean play = true;
    private ImageIcon player1;
    private Player p1;
    private Engimon e1;
    private PosisiEngimon enemy;
    private  int turn = 0;
    private Engimon activeEngimon;

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load seluruh window
        FXMLLoader loaderLogin = new FXMLLoader(getClass().getResource("login.fxml"));
        FXMLLoader loaderUsername = new FXMLLoader(getClass().getResource("username.fxml"));

        Parent rootLogin = loaderLogin.load();
        Parent rootUsername = loaderUsername.load();

        Button switchToChoose = (Button) loaderLogin.getNamespace().get("registerButton");
        Button switchToMain = (Button) loaderUsername.getNamespace().get("GoToMain");
        Button switchToLoad = (Button) loaderLogin.getNamespace().get("loadButton");
        ImageView pil1 = (ImageView) loaderUsername.getNamespace().get("pilihan1");

        Scene choosingScene = new Scene(rootUsername, 800, 500);
        switchToChoose.setOnAction(e -> {
            primaryStage.setScene(choosingScene);
        });

        primaryStage.setScene(new Scene(rootLogin, 800,500));
        primaryStage.setTitle("Engimon City");

        ta = new Text();
        invenInfo = new Text();

        Image pic1 = new Image("/img/sedlyf.jpg");
        ImageView gambar1 = new ImageView(pic1);
        gambar1.setFitHeight(100);
        gambar1.setFitWidth(100);
        gambar1.setPreserveRatio(true);
        gambar1.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                ta.setText("Profile");

            }
        });

        Image pic2 = new Image("/img/backpack-removebg-preview.png");
        ImageView gambar2 = new ImageView(pic2);
        gambar2.setFitHeight(100);
        gambar2.setFitWidth(100);
        gambar2.setPreserveRatio(true);
        gambar2.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                ta.setText("Inventory");
            }
        });

        Image pic3 = new Image("img/breeding-removebg-preview.png");
        ImageView gambar3 = new ImageView(pic3);
        gambar3.setFitHeight(100);
        gambar3.setFitWidth(100);
        gambar3.setPreserveRatio(true);
        gambar3.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                ta.setText("Breeding");
            }
        });

        Image pic4 = new Image("img/sword-removebg-preview.png");
        ImageView gambar4 = new ImageView(pic4);
        gambar4.setFitHeight(100);
        gambar4.setFitWidth(100);
        gambar4.setPreserveRatio(true);
        gambar4.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                if(isThereEngimon(p1.getMap())) {
                    ta.setText("Battle");
                }
            }
        });

        icon = new VBox();
        icon.getChildren().addAll(gambar1,gambar2,gambar3,gambar4);

        HBox hbox = new HBox();
        hbox.setSpacing(20);
        hbox.setPadding(new Insets(20, 50, 50, 60));
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        hbox.getChildren().addAll(icon,canvas,ta, invenInfo);
        Group root = new Group(hbox);
        Scene scene = new Scene(root,880,550);

        switchToMain.setOnAction(e -> {
            // String command = "new";
            Peta map = new Peta();
            map.BacaFile("C:\\Users\\ACER\\Downloads\\Snake\\Snake-main\\Snake\\src\\sample\\map.txt");
            p1 = new Player(map);

            snakeBody.add(new Point(p1.getXCoor(), p1.getYCoor()));
            snakeHead = snakeBody.get(0);

            generateEngimon();
            p1.getMap().PrintDaftarEngimon();
            p1.showMap();
//
            primaryStage.setScene(scene);
            runTimeLine();
        });

        switchToLoad.setOnAction(e -> {
            p1 = Player.load();

            snakeBody.add(new Point(p1.getXCoor(), p1.getYCoor()));
            snakeHead = snakeBody.get(0);

            generateEngimon();
            p1.getMap().PrintDaftarEngimon();
            p1.showMap();

            primaryStage.setScene(scene);
            runTimeLine();
        });

        primaryStage.show();
        gc = canvas.getGraphicsContext2D();
        arah = 1;

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                KeyCode code = event.getCode();
                if (code == KeyCode.RIGHT || code == KeyCode.D) {
                    moveRight();
                    ta.setText("right");
                    arah = 1;
                } else if (code == KeyCode.LEFT || code == KeyCode.A) {
                    moveLeft();
                    ta.setText("left");
                    arah = 3;
                } else if (code == KeyCode.UP || code == KeyCode.W) {
                    moveUp();
                    ta.setText("up");
                    arah = 4;
                } else if (code == KeyCode.DOWN || code == KeyCode.S) {
                    moveDown();
                    ta.setText("down");
                    arah = 2;
                }
            }
        });

    }

    private void runTimeLine(){
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(200), e -> run(gc)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
    private void run(GraphicsContext gc)  {
        if (gameOver) {
            gc.setFill(Color.RED);
            gc.setFont(new Font("Digital-7", 70));
            gc.fillText("Game Over", WIDTH / 3.5, HEIGHT / 2);
            return;
        }

        drawBackground(gc, p1.getMap());
        drawEngimonLiar(p1.getMap());
        drawSnake(gc,arah);
    }

    private void loadSkillImage(int idx){
        String path = "img/";
    }

    public ImageView loadSkillImage(int idx, int mL){
        Image img = new Image(SKILL_IMAGE[idx + mL - 1]);
        ImageView imgView = new ImageView(img);
        imgView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                if(isThereEngimon(p1.getMap())) {
                    ta.setText("Battle");
                }
            }
        });

        return imgView;
    }
    private String getColorElement(char e){
        if(e == 'f'){
            return "F1948A";
        } else if (e == 'g'){
            return "ABEBC6";
        } else if (e == 'e'){
            return "F9E79F";
        } else if (e == 'w'){
            return "AED6F1";
        } else if (e == 'i'){
            return "A6ACAF";
        } else if (e == 's'){
            return "154360";
        } else if (e == 'n'){
            return "117A65";
        }
        return "D35400"; //e == 'l'
    }

    private void drawBackground(GraphicsContext gc, Peta map) {
        try{
            for (int i = 0; i < ROWS; i++) {
                for (int j = 0; j < COLUMNS; j++) {
                    if(map.GetElementPeta(j, i) == '-'){
                        // gc.setFill(Color.web("AAD751"));
                        mapImage = new Image(MAP_IMAGE[0]);
                        // gc.fillRect(i * SQUARE_SIZE, j * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
                        gc.drawImage(mapImage, i * SQUARE_SIZE, j * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
                    } else if(map.GetElementPeta(j, i) == 'o'){
                        // gc.setFill(Color.web("2A9DF4"));
                        mapImage = new Image(MAP_IMAGE[1]);
                        // gc.fillRect(i * SQUARE_SIZE, j * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
                        gc.drawImage(mapImage, i * SQUARE_SIZE, j * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
                    } else if(map.GetElementPeta(j, i) == '#'){
                        // gc.setFill(Color.web("E0E0E0"));
                        mapImage = new Image(MAP_IMAGE[2]);
                        // gc.fillRect(i * SQUARE_SIZE, j * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
                        gc.drawImage(mapImage, i * SQUARE_SIZE, j * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
                    } else if(map.GetElementPeta(j, i) == '^'){
                        // gc.setFill(Color.web("757575"));
                        mapImage = new Image(MAP_IMAGE[3]);
                        // gc.fillRect(i * SQUARE_SIZE, j * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
                        gc.drawImage(mapImage, i * SQUARE_SIZE, j * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
                    } else if(map.GetElementPeta(j, i) == 'P'){
                        // untuk player
                    } else {
                        gc.setFill(Color.web(getColorElement(map.GetElementPeta(j, i))));
                        gc.fillRect(i * SQUARE_SIZE, j * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
                    }
                }
            }
        } catch (Exception e){

        }

    }

    private void drawSnake(GraphicsContext gc,int arah) {
//        gc.setFill(Color.web("4674E9"));
//        gc.fillRoundRect(snakeHead.getX() * SQUARE_SIZE, snakeHead.getY() * SQUARE_SIZE, SQUARE_SIZE - 1, SQUARE_SIZE - 1, 35, 35);
        if(arah == 1) {
            Image hero = new Image("img/right.jpg");
            gc.drawImage(hero,snakeHead.getX() * SQUARE_SIZE, snakeHead.getY() * SQUARE_SIZE, SQUARE_SIZE - 1, SQUARE_SIZE - 1);
        }
        else if(arah == 2) {
            Image hero = new Image("img/down.jpg");
            gc.drawImage(hero,snakeHead.getX() * SQUARE_SIZE, snakeHead.getY() * SQUARE_SIZE, SQUARE_SIZE - 1, SQUARE_SIZE - 1);
        }
        else if(arah == 3) {
            Image hero = new Image("img/left.jpg");
            gc.drawImage(hero,snakeHead.getX() * SQUARE_SIZE, snakeHead.getY() * SQUARE_SIZE, SQUARE_SIZE - 1, SQUARE_SIZE - 1);
        }
        else if(arah == 4) {
            Image hero = new Image("img/up.jpg");
            gc.drawImage(hero,snakeHead.getX() * SQUARE_SIZE, snakeHead.getY() * SQUARE_SIZE, SQUARE_SIZE - 1, SQUARE_SIZE - 1);
        }
    }
    private boolean isThereEngimon(Peta map) {
        for(PosisiEngimon e : map.getDaftarEngimons()){
            if(snakeHead.getX() + 1 == e.getKolomPosisi() && snakeHead.getY() == e.getBarisPosisi()) {
                enemy = e;
                return true;
            }
            if(snakeHead.getX() + 1 == e.getKolomPosisi() && snakeHead.getY() == e.getBarisPosisi()) {
                enemy = e;
                return true;
            }
            if(snakeHead.getX() + 1 == e.getKolomPosisi() && snakeHead.getY() == e.getBarisPosisi()) {
                enemy = e;
                return true;
            }
            if(snakeHead.getX() + 1 == e.getKolomPosisi() && snakeHead.getY() == e.getBarisPosisi()) {
                enemy = e;
                return true;
            }
        }
        enemy = null;
        return false;
    }

    private void generateEngimon(){
        while (true){
            p1.spawn();
            break;
        }
    }


    private void drawEngimonLiar(Peta map) {
        for(PosisiEngimon e : map.getDaftarEngimons()){
            if(e.getEngimon().getElement().size() == 2){
                if(e.getEngimon().getElement().get(0).equals(Element.FIRE) &&
                        e.getEngimon().getElement().get(1).equals(Element.ELECTRIC)){
                    if(e.getEngimon().getLevel() < activeLevel){
                        engimonImage = new Image(ENGIMON_IMAGE[15]);
                    } else {
                        engimonImage = new Image(ENGIMON_IMAGE[14]);
                    }
                } else if(e.getEngimon().getElement().get(0).equals(Element.WATER) &&
                        e.getEngimon().getElement().get(1).equals(Element.ICE)){
                    if(e.getEngimon().getLevel() < activeLevel){
                        engimonImage = new Image(ENGIMON_IMAGE[13]);
                    } else {
                        engimonImage = new Image(ENGIMON_IMAGE[12]);
                    }
                } else if(e.getEngimon().getElement().get(0).equals(Element.WATER) &&
                        e.getEngimon().getElement().get(1).equals(Element.GROUND)){
                    if(e.getEngimon().getLevel() < activeLevel){
                        engimonImage = new Image(ENGIMON_IMAGE[1]);
                    } else {
                        engimonImage = new Image(ENGIMON_IMAGE[0]);
                    }
                }
            } else {
                if(e.getEngimon().getElement().get(0).equals(Element.FIRE)){
                    if(e.getEngimon().getLevel() < activeLevel){
                        engimonImage = new Image(ENGIMON_IMAGE[5]);
                    } else {
                        engimonImage = new Image(ENGIMON_IMAGE[4]);
                    }
                } else if(e.getEngimon().getElement().get(0).equals(Element.WATER)){
                    if(e.getEngimon().getLevel() < activeLevel){
                        engimonImage = new Image(ENGIMON_IMAGE[11]);
                    } else {
                        engimonImage = new Image(ENGIMON_IMAGE[10]);
                    }
                } else if(e.getEngimon().getElement().get(0).equals(Element.ICE)){
                    if(e.getEngimon().getLevel() < activeLevel){
                        engimonImage = new Image(ENGIMON_IMAGE[9]);
                    } else {
                        engimonImage = new Image(ENGIMON_IMAGE[8]);
                    }
                } else if(e.getEngimon().getElement().get(0).equals(Element.GROUND)){
                    if(e.getEngimon().getLevel() < activeLevel){
                        engimonImage = new Image(ENGIMON_IMAGE[7]);
                    } else {
                        engimonImage = new Image(ENGIMON_IMAGE[6]);
                    }
                } else if(e.getEngimon().getElement().get(0).equals(Element.ELECTRIC)){
                    if(e.getEngimon().getLevel() < activeLevel){
                        engimonImage = new Image(ENGIMON_IMAGE[3]);
                    } else {
                        engimonImage = new Image(ENGIMON_IMAGE[2]);
                    }
                }
            }
            gc.drawImage(engimonImage, e.getKolomPosisi() * SQUARE_SIZE, e.getBarisPosisi() * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
        }
    }

    private void moveRight() {
        try{
            p1.moveD();
            snakeHead.x++;
            turn++;
            if(turn % TURN_MOVE == 0){
                p1.getMap().GerakinEngimonLiar();
            }
            if(turn % TURN_EXP == 0){
                p1.getMap().addExpEngimonLiar();
            }
            generateEngimon();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
//        p1.getMap().PrintDaftarEngimon();
//        p1.showMap();
    }

    private void moveLeft() {
        try{
            p1.moveA();
            snakeHead.x--;
            turn++;
            if(turn % TURN_MOVE == 0){
                p1.getMap().GerakinEngimonLiar();
            }
            if(turn % TURN_EXP == 0){
                p1.getMap().addExpEngimonLiar();
            }
            generateEngimon();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
//        p1.getMap().PrintDaftarEngimon();
//        p1.showMap();
    }

    private void moveUp() {
        try{
            p1.moveW();
            snakeHead.y--;
            turn++;
            if(turn % TURN_MOVE == 0){
                p1.getMap().GerakinEngimonLiar();
            }
            if(turn % TURN_EXP == 0){
                p1.getMap().addExpEngimonLiar();
            }
            generateEngimon();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
//        p1.getMap().PrintDaftarEngimon();
//        p1.showMap();
    }

    private void moveDown() {
        try{
            p1.moveS();
            snakeHead.y++;
            turn++;
            if(turn % TURN_MOVE == 0){
                p1.getMap().GerakinEngimonLiar();
            }
            if(turn % TURN_EXP == 0){
                p1.getMap().addExpEngimonLiar();
            }
            generateEngimon();
//            PosisiEngimon e = p1.getMap().getDaftarEngimons().get(0);
//            p1.getMap().DeleteEngimon(e);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
//        p1.getMap().PrintDaftarEngimon();
//        p1.showMap();
    }


    public static void main(String[] args) {
        launch(args);
    }
}