package com.lemontea.connectionserverchallenge.gui;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

import com.lemontea.connectionserverchallenge.server.Server;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ConsolePane implements Updateable{
    private URL consoleLayout;
    private FXMLLoader fxmlLoader;
    private Server server;
    private Stage windowConsole;
    
    // FXML Components binding
    public TextArea tAreaServerConsole = null;

    // Utilities for gui
    private Timer time;
    private String toBeUpdated;
    public ConsolePane(Server server) {
        this.server = server;
        loadFXML();
        init();
    }

    private void init() {
        try {
            VBox root = fxmlLoader.<VBox>load();
            Scene sceneMain = new Scene(root);

            this.windowConsole = new Stage();
            this.windowConsole.setScene(sceneMain);
            this.windowConsole.setTitle("Server Console");
            this.windowConsole.setOnCloseRequest( e -> {
                handleExit();
            });
            this.toBeUpdated = "";
            this.tAreaServerConsole.setEditable(false);
            this.time = new Timer();
            time.schedule(new ScheduledTask(this),0 ,1000);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void handleExit() {
        try {
            if(this.server.getServerStatus()){
                this.server.handleShutdown();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.windowConsole.close();
        Platform.exit();
        System.exit(0);
    }

    private void loadFXML() {
        try{
            consoleLayout = (getClass().getResource("/layout/server.fxml").toURI()).toURL();
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
        return windowConsole;
    }

    public void addLogs(String logEntry){
        this.toBeUpdated += logEntry + "\n";
    }

    @Override
    public void update() {
        Platform.runLater(()->  {
            this.tAreaServerConsole.appendText(this.toBeUpdated);
            this.toBeUpdated = "";
        });
    }
}

class ScheduledTask extends TimerTask {
    private ConsolePane guiPane;
    public ScheduledTask(ConsolePane guiPane){
        this.guiPane = guiPane;
    }
    public void run() {
        // To be executed periodically
        this.guiPane.update();   
    }
}