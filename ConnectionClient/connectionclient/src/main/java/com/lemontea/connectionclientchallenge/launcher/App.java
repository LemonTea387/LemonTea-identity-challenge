package com.lemontea.connectionclientchallenge.launcher;

import java.io.IOException;

import com.lemontea.connectionclientchallenge.gui.ConnectPromptPane;
import com.lemontea.connectionclientchallenge.gui.ConsolePane;
import com.lemontea.connectionclientchallenge.network.ClientConnection;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

public class App extends Application{
    private ClientConnection connection;
    private ConsolePane consolePane;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        ConnectPromptPane prompt = new ConnectPromptPane();
        prompt.getStage().showAndWait();
        if(prompt.getConnectIntent()){
            connection = new ClientConnection(prompt.getHost(),prompt.getPort());
            consolePane = new ConsolePane(connection);
            connection.setGUIController(consolePane);
            connection.start();
            consolePane.getStage().show();
        }else{
            Platform.exit();
        }
    }

    @Override
    public void stop(){
        connection.handleShutdown();
    }
}
