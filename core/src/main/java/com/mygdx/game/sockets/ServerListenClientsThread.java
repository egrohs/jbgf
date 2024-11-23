package com.mygdx.game.sockets;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.ServerSocket;
import com.badlogic.gdx.net.ServerSocketHints;

public class ServerListenClientsThread extends Thread {
	static List<ServerReadMsgsThread> clients = new ArrayList<>();

	@Override
	public void run() {
		ServerSocketHints serverSocketHint = new ServerSocketHints();
		// 0 means no timeout. Probably not the greatest idea in production!
		serverSocketHint.acceptTimeout = 0;
		ServerSocket serverSocket = Gdx.net.newServerSocket(Net.Protocol.TCP, 9021, serverSocketHint);
		// System.out.println("rodando");
		while (true) {
			ServerReadMsgsThread c = new ServerReadMsgsThread(serverSocket.accept(null));
			clients.add(c);
			// new thread for a client
			c.start();
		}
	}
}
