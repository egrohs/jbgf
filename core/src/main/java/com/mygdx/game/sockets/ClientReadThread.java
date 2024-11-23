package com.mygdx.game.sockets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.Protocol;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.net.SocketHints;
import com.badlogic.gdx.utils.Json;
import com.mygdx.game.model.EstadoJogo;

public class ClientReadThread extends Thread {
	@Override
	public void run() {
		SocketHints socketHints = new SocketHints();
		// Socket will time our in 4 seconds
		socketHints.connectTimeout = 0;
		Socket socket = Gdx.net.newClientSocket(Protocol.TCP, "127.0.0.1", 9021, socketHints);

		OutputStream os = socket.getOutputStream();
		// InputStream is = socket.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		try {
			Json j = new Json();
			EstadoJogo e = new EstadoJogo();
			e.setPlayerVez("myself");
			String msg = j.toJson(e) + "\n";
			// System.out.println(msg);
			Thread.sleep(5000);
			os.write(msg.getBytes());
			// System.out.println("enviou");

			while (true) {
				System.out.println("CLIENTE RECEBEU: " + br.readLine());
				// System.out.println(is.readAllBytes());
			}

		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
