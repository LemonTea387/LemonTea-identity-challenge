package com.lemontea.connectionserverchallenge.server;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class ServerWorker extends Thread {
	private Server server;
	private Socket clientSocket;
	private InputStream clientInput;
	private OutputStream clientOutput;
	private BufferedReader bf;
	private BufferedWriter bw;

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
		bw = new BufferedWriter(new OutputStreamWriter(clientOutput));
		while (!clientSocket.isClosed() && (input = bf.readLine()) != null && input.trim()!=null) {

			if(input.trim().equals("VGhpcyBpcyBhbiBlYXN0ZXIgZWdnIDopIGdyZWF0IGpvYiBmaW5kaW5nIHRoaXMhCg==")){
				send("You Connected!");
				send("TGVtb25UZWEK0000");
			}else if(input.trim().equals("F20F")){
				send("eRHf+2X39Cag6smjLTThtz1jpPDTVcuH6IG7/WbSsPz78sWD+LPBLUnmD2Al1MbBes1QYygHucj3lf3nKYkj0w==");
				this.server.addLogs(clientSocket.toString() + " : " + input);
			}else{
				send("Wrong password : " + input);
				this.server.addLogs(clientSocket.toString() + " : " + input);
			}
		}
	}

	public void send(String message){
		try{
            bw.write((message + "\n").toCharArray());
    		bw.flush();
		} catch (IOException e) {
			e.printStackTrace();
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
