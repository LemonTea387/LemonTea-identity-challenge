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
	public Server(int port) {
		final int PORT = port;
		try {
			serverSocket = new ServerSocket(PORT);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		handleConnections();
		super.run();
	}

	// Handles all the accepting of connections
	private void handleConnections() {
		try {
			this.addLogs("Server is Up to handle connections! Awaiting Connections...");
			while (true) {
				Socket clientSocket = serverSocket.accept();
				// Initializes a server worker thread to handle each specific connection into
				// server
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

	public void handleShutdown() throws IOException {
		for(ServerWorker sw : serverWorkers){
			sw.handleShutdown();
		}
		this.serverSocket.close();
	}
}
