package com.lemontea.connectionclientchallenge.gui;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class ConnectPromptPane {
    private URL connectPromptLayout;
    private FXMLLoader fxmlLoader;

    private Stage window;
    
    private boolean connectIntent = false;

    //FXML binding 
    public TextField tFieldHost = null;
    public TextField tFieldPort = null;
    public Button buttonConnect = null;
    
    public ConnectPromptPane(){
        loadFXML();
        init();
    }

    private void init() {
        try {
            HBox root = fxmlLoader.<HBox>load();
            Scene sceneMain = new Scene(root);

            this.window = new Stage();
            this.window.setScene(sceneMain);
            this.window.setTitle("Connect!");
            this.window.setOnCloseRequest( e -> {
                handleExit();
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void loadFXML() {
        try{
            connectPromptLayout = (getClass().getResource("/layout/connectPrompt.fxml").toURI()).toURL();
        }catch(MalformedURLException e){
            e.printStackTrace();
        }catch(URISyntaxException e){
            e.printStackTrace();    
        }
        this.fxmlLoader = new FXMLLoader();
        this.fxmlLoader.setLocation(connectPromptLayout);
        this.fxmlLoader.setController(this);
    }

    @FXML
    public void onButtonConnectClick(){
        String portString = tFieldPort.getText();
        int portInt;
        if(isInteger(portString,10) && (portInt = Integer.parseInt(portString)) > 0 && portInt <= 65535){
            this.connectIntent = true;
            handleExit();
        }else{
            Platform.runLater(()->{
                tFieldPort.setPromptText("Invalid Port Number!");
            });
        }
    }
    
    private void handleExit() {
        this.window.close();
    }

    public static boolean isInteger(String s, int radix) {
        if(s.isEmpty()) return false;
        for(int i = 0; i < s.length(); i++) {
            if(i == 0 && s.charAt(i) == '-') {
                if(s.length() == 1) return false;
                else continue;
            }
            if(Character.digit(s.charAt(i),radix) < 0) return false;
        }
        return true;
    }

    public String getHost(){
        return tFieldHost.getText();
    }
    public int getPort(){
        return Integer.parseInt(tFieldPort.getText());
    }
    public boolean getConnectIntent(){
        return connectIntent;
    }
    public Stage getStage(){
        return window;
    }

}
