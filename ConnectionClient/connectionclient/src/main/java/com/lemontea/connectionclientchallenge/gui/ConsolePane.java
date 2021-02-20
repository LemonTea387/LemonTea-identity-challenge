package com.lemontea.connectionclientchallenge.gui;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

import com.lemontea.connectionclientchallenge.network.ClientConnection;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ConsolePane implements Updateable{
    private URL consoleLayout;
    private FXMLLoader fxmlLoader;

    private ClientConnection connection;
    private Stage window;

    // Binding components to Nodes declared in FXML
    public TextArea tAreaClientConsole = null;
    public TextField tFieldSendBox = null;
    public Button buttonSend = null;
    public Button buttonReconnect = null;

    private String clientConsoleText;
    private String sendBoxText;
    private String hostname;
    private int port;

    public ConsolePane(ClientConnection connection){
        this.connection = connection;
        this.hostname = connection.getHost();
        this.port = connection.getPort();
        loadFXML();
        init();
    }

    private void init() {
            try {
            VBox root = fxmlLoader.<VBox>load();
            Scene sceneMain = new Scene(root);

            this.window = new Stage();
            this.window.setScene(sceneMain);
            this.window.setTitle("Client Console");
            this.window.setOnCloseRequest( e -> {
                handleExit();
            });
            sendBoxText = "";
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleExit() {
        this.connection.handleShutdown();
        this.window.close();
    }

    private void loadFXML() {
        try{
            consoleLayout = (getClass().getResource("/layout/client.fxml").toURI()).toURL();
        }catch(MalformedURLException e){
            e.printStackTrace();
        }catch(URISyntaxException e){
            e.printStackTrace();    
        }
        this.fxmlLoader = new FXMLLoader();
        this.fxmlLoader.setLocation(consoleLayout);
        this.fxmlLoader.setController(this);
    }

    public Stage getStage(){
        return window;
    }

    @FXML
    private void onButtonSendClick(){
        sendBoxText = tFieldSendBox.getText();
        connection.send(sendBoxText);
        sendBoxText = "";  
        this.update();
    }

    @FXML
    private void onButtonReconnectClick() throws InterruptedException {
        connection = new ClientConnection(hostname, port);
        connection.start();
        Thread.sleep(1000);
        this.update();
    }

    @Override
    public void update() {
        if(connection.isConnected()){
            clientConsoleText = connection.getLogs();
            Platform.runLater(() -> {
                tFieldSendBox.setText(sendBoxText);
                tAreaClientConsole.setText(clientConsoleText);
            });
        }else{
            Platform.runLater(()-> {
                tAreaClientConsole.setText("Not Connected!");
            });
        }
    }
}
