package com.mygdx.game;

import java.io.IOException;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.mygdx.game.model.EstadoJogo;
import com.mygdx.game.visao.EscovaGUI;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main(String[] arg) throws IOException {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setTitle("jbgf");
		config.setWindowedMode(1000, 900);

		JogoEscova j = new JogoEscova(new EstadoJogo());
		j.configuraEstado(new String[] { "PC", "Egrohs" });
//		System.out.println(j);
/////new Lwjgl3Application(new DragNoDrop(), config);
		// new Lwjgl3Application(new DragAndDropExample(), config);
		new Lwjgl3Application(new EscovaGUI(j.getUltimoMemento()), config);
//		{
//			@Override
//			public void exit() {
//				System.out.println("vai exit");
//				//if (((EscovaGUI) getApplicationListener()).canClose())
//					super.exit();
//			}
//		};
	}
}
