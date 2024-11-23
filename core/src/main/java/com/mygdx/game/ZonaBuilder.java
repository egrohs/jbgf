package com.mygdx.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.mygdx.game.model.CartaBaralho;
import com.mygdx.game.model.Constantes.Naipes;
import com.mygdx.game.model.Constantes.Valores;
import com.mygdx.game.model.EstadoJogo;
import com.mygdx.game.model.Zona;
import com.mygdx.game.model.Zona.TipoZona;

public class ZonaBuilder {
	// private int numDecks;

	// private List<Valores> removidas;

	private static ZonaBuilder zonaBuilder;

	// TODO Singleton?
	public static ZonaBuilder getIntancia() {
		if (zonaBuilder == null) {
			zonaBuilder = new ZonaBuilder();
		}
		return zonaBuilder;
	}

	private ZonaBuilder() {
	}

	/**
	 * Cria uma zona com "qntCartas" cartas caso o estado passado n�o seja nulo e
	 * possua um deck.
	 * 
	 * @param donos      Donos da zona criada.
	 * @param estado     Estado que possui o deck fonte de onde ser�o tiradas as
	 *                   cartas para dar conteudo � zona criada.
	 * @param qntCartas  Quantidade de cartas tiradas da fonte.
	 * @param visivelPor Define quem podera ver a zona sera criada. //// *
	 * @param tipoZona   Define quem ser�o os donos e por quem a zona sera visivel.
	 * @return
	 */
	public static Zona buildZona(String nome, String[] donos, EstadoJogo estado, int qntCartas, TipoZona visivelPor) {
		Zona zona = null;
		if (estado != null) {
			zona = new Zona(nome, donos, visivelPor);
			// zona.setDonos(donos);
			Zona deck = estado.getDeck();
			for (int i = 0; i < qntCartas; i++) {
				Zona c = deck.getComponent(0);
				zona.add(c);
				deck.remove(c);
			}
			/*
			 * switch (tipoZona) { case PROTEGIDA: break; case PRIVADA: zona.setDonos(new
			 * String[] { estado.playerVez }); break; case PUBLICA:
			 * zona.setDonos(estado.getPlayerNames()); break; }
			 */
		}
		return zona;
	}

	/**
	 * Monta o baralho de jogo em uma zona.
	 * 
	 * @param numDecks  Numero de baralhos usados para montar o baralho de jogo.
	 * @param removidas Indica quais cartas devem ser removidas do baralho de jogo.
	 * @return Zona contendo o baralho gerado.
	 */
	public static Zona buildDeck(int numDecks, Valores[] removidas) {
		int c = 0;
		List<Zona> deck = new ArrayList<>();
		for (int i = 0; i < numDecks; i++) {
			for (Valores valor : Valores.values()) {
				if (!Arrays.asList(removidas).contains(valor)) {
					if (valor != Valores.CORINGA) {
						for (Naipes naipe : Naipes.values()) {
							CartaBaralho carta = new CartaBaralho(valor, naipe);
							deck.add(carta);
						}
					} else {
						CartaBaralho carta = new CartaBaralho(valor, null);
						deck.add(carta);
					}
				}
			}
		}
		Collections.shuffle(deck);
		Zona monte = new Zona("Monte", null, TipoZona.PROTEGIDA);
		for (Zona zona : deck) {
			monte.add(zona);
		}
		return monte;
	}
}