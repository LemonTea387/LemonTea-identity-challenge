package com.lemontea.connectionserverchallenge.launcher;

import java.io.IOException;

import com.lemontea.connectionserverchallenge.gui.ConsolePane;
import com.lemontea.connectionserverchallenge.server.Server;

import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {
    private Server server;
    private ConsolePane consolePane;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        server = new Server(17170);
        consolePane = new ConsolePane(server);
        consolePane.getStage().show();
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
