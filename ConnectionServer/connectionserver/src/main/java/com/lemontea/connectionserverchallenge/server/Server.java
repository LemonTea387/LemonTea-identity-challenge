package com.lemontea.connectionserverchallenge.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import com.lemontea.connectionserverchallenge.gui.ConsolePane;


public class Server extends Thread {
	private ServerSocket serverSocket;
	private ArrayList<ServerWorker> serverWorkers = new ArrayList<ServerWorker>();
	private String logs = "";
	private ConsolePane guiPane;

	private boolean isServerUp;
	public Server(int port) {
		final int PORT = port;
		try {
			serverSocket = new ServerSocket(PORT);
			isServerUp = true;
		} catch (IOException e) {
			isServerUp = false;
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		if(isServerUp){
			handleConnections();
		}else{
			this.addLogs("Server had an error starting :(");
		}
		super.run();
	}

	// Handles all the accepting of connections
	private void handleConnections() {
		this.addLogs("Server is Up to handle connections! Awaiting Connections...");
		try {
			while (true) {
				Socket clientSocket = serverSocket.accept();
				// Initializes a server worker thread to handle each specific connection into
				// server
				addLogs("New Connection : " + clientSocket.toString());
				ServerWorker worker = new ServerWorker(this, clientSocket);
				worker.start();
				serverWorkers.add(worker);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<ServerWorker> getServerWorkers() {
		return serverWorkers;
	}

	public void setGUIController(ConsolePane pane){
		this.guiPane = pane;
	}

	public void addLogs(String logEntry){
        logs += logEntry + "\n";
        // Call to update GUI
        this.guiPane.update();
    }

	public String getLogs(){
		return logs;
	}

	public boolean getServerStatus(){
		return isServerUp;
	}

	public void handleShutdown() throws IOException {
		this.serverSocket.close();
	}
}
