package com.lemontea.connectionclientchallenge.network;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.lemontea.connectionclientchallenge.gui.ConsolePane;

public class ClientConnection extends Thread {
    private String logs;
    private String hostname;
    private int port;
    private Socket connection;
    private InputStream input;
    private OutputStream output;
    private BufferedReader br;
    private BufferedWriter bw;

    private boolean connected;
    private ConsolePane guiPane;

    public ClientConnection(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
    }

    @Override
    public void run() {
        attemptConnection();
        byte[] digest;
        if (connected) {
            MessageDigest md;
            try {
                md = MessageDigest.getInstance("MD5");
                md.update(getClass().getResource("/layout/client.fxml").toString().getBytes());
                digest = md.digest();
                send(new String(digest,StandardCharsets.UTF_8));
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            handleCommunication();
        }
    }

	public void attemptConnection() {
        this.logs = "";
        try{
            this.connection = new Socket(hostname,port);
			input = connection.getInputStream();
			output = connection.getOutputStream();
			br = new BufferedReader(new InputStreamReader(input));
			bw = new BufferedWriter(new OutputStreamWriter(output));
            connected = true;
            this.logs += "Connected to \nHost : " + hostname + " \nPort : " + port +"\n";
        }catch(IOException e) {
            connected = false;
            e.printStackTrace();
        }finally{
            guiPane.update();
        }
    }

    // Writes a string to the output stream of clientSocket
	public void send(String input) {
		try {
            if(connection != null && !connection.isClosed()){
                bw.write((input + "\n").toCharArray());
    			bw.flush();
                addLogs(input + "\n");
            }
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

    private void handleCommunication(){
		try {
            String input;
			while (!connection.isClosed() && (input = br.readLine().trim()) != null) {
				addLogs(input);
                guiPane.update();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
    }


    public void handleShutdown() {
        if(connection != null){
            try {
                input.close();
                output.close();
                connection.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setGUIController(ConsolePane consolePane){
        this.guiPane = consolePane;
    }    
    
    private void addLogs(String logEntry) {
        logs += logEntry+"\n";
    }

    public String getLogs(){
        return logs;
    }
    
    public boolean isConnected(){
        return connected;
    }

}
