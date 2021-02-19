package com.lemontea.connectionserverchallenge.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


public class Server extends Thread {
	private ServerSocket serverSocket;
	private ArrayList<ServerWorker> serverWorkers = new ArrayList<ServerWorker>();
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
			while (true) {
				System.out.println("Waiting...");
				Socket clientSocket = serverSocket.accept();
				// Initializes a server worker thread to handle each specific connection into
				// server
				ServerWorker worker = new ServerWorker(this, clientSocket);
				worker.start();
				serverWorkers.add(worker);
				System.out.println("Accepted connections from " + clientSocket);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<ServerWorker> getServerWorkers() {
		return serverWorkers;
	}

	public void handleShutdown() throws IOException {
		for(ServerWorker sw : serverWorkers){
			sw.handleShutdown();
		}
		this.serverSocket.close();
	}
}
