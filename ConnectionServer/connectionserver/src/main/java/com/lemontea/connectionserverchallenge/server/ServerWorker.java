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
		clientInput = clientSocket.getInputStream();
		clientOutput = clientSocket.getOutputStream();
		bf = new BufferedReader(new InputStreamReader(clientInput));
		while (!clientSocket.isClosed() && (input = bf.readLine()) != null && input.trim()!=null) {
			this.server.addLogs(clientSocket.toString() + " : " + input);
			
		}
	}

	// Closes everything properly
	protected void handleShutdown() throws IOException {
		this.server.addLogs("Lost Connection to : " + this.clientSocket.toString());
		bf.close();
		clientInput.close();
		clientOutput.close();
		clientSocket.close();
	}
}
