package com.mygdx.game.sockets;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import com.badlogic.gdx.net.Socket;

public class ServerReadMsgsThread extends Thread {
	protected Socket socket;
	BufferedReader brinp = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

	public ServerReadMsgsThread(Socket clientSocket) {
		this.socket = clientSocket;
	}

	@Override
	public void run() {
		String line;
		while (true) {
			try {
				line = brinp.readLine();
				if ((line == null) || line.equalsIgnoreCase("QUIT")) {
					// socket.dispose();
					return;
				} else {
					for (ServerReadMsgsThread c : ServerListenClientsThread.clients) {
						// broadcast
						c.recebe(line);
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}
		}
	}

	public void recebe(String line) throws IOException {
		dos.writeBytes(line + "\n\r");
		dos.flush();
	}
}