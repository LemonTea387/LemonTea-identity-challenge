package com.lemontea.lemonteaidentity.logic.launcher;

import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Testing!");
        primaryStage.show();
    }
}
