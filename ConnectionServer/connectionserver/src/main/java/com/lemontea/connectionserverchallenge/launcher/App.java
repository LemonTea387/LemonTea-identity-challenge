package com.lemontea.connectionserverchallenge.launcher;

import java.io.IOException;

import com.lemontea.connectionserverchallenge.server.Server;

import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {
    private Server server;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        server = new Server(17170);
        primaryStage.setTitle("Server");
        primaryStage.show();
    }

    @Override
    public void stop() {
        try {
            server.handleShutdown();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
