package sample;

import javafx.animation.Animation;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SnakeBoard extends Application {

    public int rand;
    public Label randResult;
    public int player1Score=1;
    public int player2Score=1;

    public static int size = 60;
    public static int hight =10;
    public static int width = 10;

    public Circle player1;
    public Circle player2;

    public int player1RowPos = 0;
    public int player2RowPos = 0;

    public static boolean ladder = false;
    public static boolean snake = false;

    public boolean isPlayer1Turn = false;
    public boolean isPlayer2Turn = false;

    public static int player1Xpos =30;
    public static int player1Ypos =570;

    public static int player2Xpos =30;
    public static int player2Ypos =570;

    Map<Integer, Snake> snakes = new HashMap<Integer, Snake>();
    Map<Integer, Ladder> ladders = new HashMap<Integer, Ladder>();
    Map<Integer,Pair> position_score = new HashMap<Integer,Pair>();
    Map<Integer,Integer> position_row = new HashMap<Integer, Integer>();


    public boolean isGameStarted = false;
    public Button gameButton;


    private Group tileGroup = new Group();
    public Group numberSet = new Group();

    private Parent createContent(){
        Pane root = new Pane();
        root.setPrefSize(width*size,(hight*size)+80);



        Image img = new Image("sample/background.jpg");
        ImageView bgImage = new ImageView();
        bgImage.setImage(img);
        bgImage.setFitHeight(600);
        bgImage.setFitWidth(600);

        root.getChildren().addAll(bgImage);

        root.getChildren().addAll(tileGroup);
        int total = 100;
        for (int i=0; i<hight; i++){
            for (int j=0; j<width; j++){
                int xSize = j*size;
                int ySize = i*size;
                Tile tile = new Tile(size,size,"100");
                tile.setTranslateX(xSize);
                tile.setTranslateY(ySize);
                tileGroup.getChildren().add(tile);

            }
        }

        getPosScore();

        player1 = new Circle(30);
        player1.setId("p1");
        Image p1 = new Image("sample/player1.png");
        player1.setFill(new ImagePattern(p1));
        player1.getStyleClass().add("style.css");
        player1.setTranslateX(player1Xpos);
        player1.setTranslateY(player1Ypos);

        player2 = new Circle(30);
        player2.setId("p2");
        Image p2 = new Image("sample/player2.png");
        player2.setFill(new ImagePattern(p2));
        player2.getStyleClass().add("style.css");
        player2.setTranslateX(player2Xpos);
        player2.setTranslateY(player2Ypos);

        //setting up random ladders

        ladders.put(8,new Ladder(8,34));
        ladders.put(17,new Ladder(17,36));
        ladders.put(63,new Ladder(63,82));
        ladders.put(73,new Ladder(73,492));

        //setting up random snakes
        snakes.put(57,new Snake(57,20));
        snakes.put(68,new Snake(68,49));
        snakes.put(77,new Snake(77,45));
        snakes.put(95,new Snake(95,33));


        Button player1Button = new Button("Player 1");
        player1Button.setTranslateX(40);
        player1Button.setTranslateY(625);
        player1Button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (isGameStarted){
                    if (isPlayer1Turn){
                        diesVal();
                        randResult.setText(String.valueOf(rand));
                        isPlayer1Turn = false;
                        isPlayer2Turn = true;
                        movePlayer1();
                        TranslateTransition move = translatePlayer(player1Xpos,player1Ypos,player1);
                        SequentialTransition tSq;
                        if(ladders.containsKey(player1Score)){
                            int endNode = ladders.get(player1Score).end;
                            Pair end = position_score.get(endNode);
                            player1Xpos = end.getX();
                            player1Ypos = end.getY();
                            player1Score = endNode;
                            int rowVal = position_row.get(player1Score);
                            player1RowPos = rowVal;
                            TranslateTransition go = goTo(end,player1);
                            tSq = new SequentialTransition(move,go);
                        }else if (snakes.containsKey(player1Score)){
                            int endNode = snakes.get(player1Score).end;
                            Pair end = position_score.get(endNode);
                            player1Xpos = end.getX();
                            player1Ypos = end.getY();
                            player1Score = endNode;
                            int rowVal = position_row.get(player1Score);
                            player1RowPos = rowVal;
                            TranslateTransition go = goTo(end,player1);
                            tSq = new SequentialTransition(move,go);
                        }else {
                            tSq = new SequentialTransition(move);
                        }

                        tSq.play();

                    }
                }
            }
        });

        Button player2Button = new Button("Player 2");
        player2Button.setTranslateX(450);
        player2Button.setTranslateY(625);
        player2Button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (isGameStarted){
                    if (isPlayer2Turn){
                        diesVal();
                        randResult.setText(String.valueOf(rand));
                        isPlayer2Turn = false;
                        isPlayer1Turn = true;
                        movePlayer2();
                        TranslateTransition move = translatePlayer(player2Xpos,player2Ypos,player2);
                        SequentialTransition tSq;

                        if(ladders.containsKey(player2Score)){
                            int endNode = ladders.get(player2Score).end;
                            Pair end = position_score.get(endNode);
                            player2Xpos = end.getX();
                            player2Ypos = end.getY();
                            player2Score = endNode;
                            int rowVal = position_row.get(player2Score);
                            player2RowPos = rowVal;
                            TranslateTransition go = goTo(end,player2);
                            tSq = new SequentialTransition(move,go);
                        }else if (snakes.containsKey(player2Score)){
                            int endNode = snakes.get(player2Score).end;
                            Pair end = position_score.get(endNode);
                            player2Xpos = end.getX();
                            player2Ypos = end.getY();
                            player2Score = endNode;
                            int rowVal = position_row.get(player2Score);
                            player2RowPos = rowVal;
                            TranslateTransition go = goTo(end,player2);
                            tSq = new SequentialTransition(move,go);
                        }else {
                            tSq = new SequentialTransition(move);
                        }
                        tSq.play();
                    }
                }
            }
        });

        gameButton = new Button("Start");
        gameButton.setTranslateX(280);
        gameButton.setTranslateY(625);
        gameButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                gameButton.setText("Restart");
                player1Xpos = 30;
                player1Ypos = 570;
                player1.setTranslateX(player1Xpos);
                player1.setTranslateY(player1Ypos);

                player2Xpos = 30;
                player2Ypos = 570;
                player1RowPos = 1;
                player2RowPos =1;
                player1Score = 1;
                player2Score = 1;
                player2.setTranslateX(player2Xpos);
                player2.setTranslateY(player2Ypos);

                randResult.setText(String.valueOf(0));
                isGameStarted =true;
                isPlayer1Turn = true;

            }
        });

        randResult = new Label("0");
        randResult.setTranslateX(300);
        randResult.setTranslateY(650);

//        player1Score = new Label("0");
//        player1Score.setTranslateX(110);
//        player1Score.setTranslateY(630);
//        player2Score = new Label("0");
//        player2Score.setTranslateX(420);
//        player2Score.setTranslateY(630);

        tileGroup.getChildren().addAll(numberSet,player1,player2,player1Button,player2Button,gameButton,randResult);
        return root;
    }

    private void diesVal(){
        rand = (int) (Math.random()*6+1);
    }

    private void movePlayer1 (){
        for (int i=0; i < rand; i++){
            player1Score +=1;
            if (player1RowPos % 2 ==1){
                player1Xpos +=60;
            }
            if (player1RowPos % 2 == 0){
                player1Xpos -=60;
            }
            if (player1Xpos > 570 ){
                player1Ypos -= 60;
                player1Xpos -=60;
                player1RowPos++;
            }
            if (player1Xpos < 30){
                player1Ypos -=60;
                player1Xpos +=60;
                player1RowPos++;
            }

            if (player1Xpos <= 30 && player1Ypos <= 30){
                player1Xpos = 30;
                player1Ypos = 30;
                randResult.setText("Player one won !!");
                isGameStarted = false;
                gameButton.setText("Start Again");
             }
        }
    }


    private void movePlayer2 (){
        for (int i=0; i < rand; i++){
            player2Score +=1;
            if (player2RowPos % 2 ==1){
                player2Xpos +=60;
            }
            if (player2RowPos % 2 == 0){
                player2Xpos -=60;
            }
            if (player2Xpos > 570 ){
                player2Ypos -= 60;
                player2Xpos -= 60;
                player2RowPos++;
            }
            if (player2Xpos < 30){
                player2Ypos -=60;
                player2Xpos +=60;
                player2RowPos++;
            }

            if (player2Xpos <= 30 && player2Ypos <= 30){
                player2Xpos = 30;
                player2Ypos = 30;
                randResult.setText("Player two won !!");
                isGameStarted = false;
                gameButton.setText("Start Again");
                break;
            }
        }
    }

    private void getPosScore (){

        int xPos = 30;
        int yPos = 570;
        int rowPos = 1;
        for (int i=0; i < 99; i++){

            Text txt = new Text(String.valueOf(i+1));
            txt.setTranslateX(xPos);
            txt.setTranslateY(yPos);

            if (rowPos % 2 ==1){
                xPos +=60;
            }
            if (rowPos % 2 == 0){
                xPos -=60;
            }
            if (xPos > 570 ){
                yPos -= 60;
                xPos -=60;
                rowPos++;
            }
            if (xPos < 30){
                yPos -=60;
                xPos +=60;
                rowPos++;
            }
            numberSet.getChildren().add(txt);
            position_score.put(i+2,new Pair(xPos,yPos));
            position_row.put(i+2,rowPos);
            if (xPos <= 30 && yPos <= 30){
                xPos = 30;
                yPos = 30;
                break;
            }


        }


    }

    private TranslateTransition goTo(Pair end, Circle b){
        return translatePlayer(end.getX(),end.getY(),b);
    }


    private TranslateTransition translatePlayer (int x, int y, Circle b){
        TranslateTransition animate = new TranslateTransition(Duration.millis(1000),b);
        animate.setToX(x);
        animate.setToY(y);
        animate.setAutoReverse(false);
        return  animate;
    }



    @Override
    public void start(Stage primaryStage) throws Exception{

        Scene scene = new Scene(createContent());
        scene.getStylesheets().add("sample/style.css");
        primaryStage.setTitle("Snake and Ladder");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
