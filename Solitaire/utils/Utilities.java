package utils;

import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Utilities {
    private static Stage primaryStage;
    private static GridPane gpane;
    private static String timeStr;

    public void setStage(Stage primaryStage){
        this.primaryStage = primaryStage;
    }

    public static Stage getStage(){
        return primaryStage;
    }

    public void setGridPane(GridPane gpane){
        this.gpane = gpane;
    }

    public static GridPane getGridPane(){
        return gpane;
    }

    public static String timeConverter(int time){
        String seconds = Integer.toString(time % 60);
        String minutes = Integer.toString(time / 60 % 60);
        String hours = Integer.toString(time / 3600);

        if(seconds.length() == 1){
            seconds = "0" + seconds;
        }

        if(minutes.length() == 1){
            minutes = "0" + minutes;
        }

        if(hours.length() == 1){
            hours = "0" + hours;
        }
        timeStr = hours + " : " + minutes + " : " + seconds;

        return timeStr;
    }
}
