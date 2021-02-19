package com.lemontea.connectionserverchallenge.server;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class ServerWorker extends Thread {
	private Server server;
	private Socket clientSocket;
	private InputStream clientInput;
	private OutputStream clientOutput;
	private BufferedReader bf;

	ServerWorker(Server server, Socket clientSocket) {
		this.server = server;
		this.clientSocket = clientSocket;
	}

	@Override
	public void run() {
		try {
			handleClients();
		} catch (IOException e) {
			e.printStackTrace();
		}
		super.run();
	}

	// Quick accessible method to send message through outputstream
	public void sendMessage(String message) throws IOException {
		clientOutput.flush();
		clientOutput.write((message + "\n").getBytes());
	}

	// Ran initially and keeps running until quit flag is set
	private void handleClients() throws IOException {
		String input;
		String[] tokens;
		clientInput = clientSocket.getInputStream();
		clientOutput = clientSocket.getOutputStream();
		bf = new BufferedReader(new InputStreamReader(clientInput));
		while (!clientSocket.isClosed() && (input = bf.readLine().trim()) != null) {
            
		}
	}

	// Closes everything properly
	protected void handleShutdown() throws IOException {
		bf.close();
		clientInput.close();
		clientOutput.close();
		clientSocket.close();
	}
}
