package sample;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.image.Image;
import sample.SnakeBoard;

class Tile extends Rectangle {
    public Tile(int x, int y,String text){
        setWidth(SnakeBoard.size);
        setHeight(SnakeBoard.size);
        //Image img = new Image("sample/background.jpg");
        //setFill(new ImagePattern(img));
        setFill(Color.TRANSPARENT);
        setStroke(Color.BLACK);
    }

    /*Rectangle node2 =
            RectangleBuilder.create()
                    .x(0)
                    .y(0)
                    .width(500)
                    .height(500)
                    .fill(
                            new ImagePattern(
                                    new Image("file:sample/background.png"), 0, 0, 1, 1, true
                            )
                    )
                    .build();*/
}