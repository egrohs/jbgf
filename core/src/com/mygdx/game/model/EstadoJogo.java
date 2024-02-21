package com.mygdx.game.model;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Arrays;
import java.util.EventListener;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;

import com.mygdx.game.model.Constantes.Valores;
import com.mygdx.game.visao.ILayoutStrategy;

import lombok.Data;

@Data
//Equivale ao Scene? do LibGdx
//TODO eh zona provisoriamente, depois remover isso
public class EstadoJogo extends Zona implements Cloneable {
	// TODO String ou Player? Deve ser salvo!
	// TODO public static ?
	public static String playerVez;

	// TODO public static ?
	public static String[] playerNames;

	protected Zona deck;

	protected Map<Valores, Integer> valores;

	// TODO Deve ficar aqui ou na visao ?
	private ILayoutStrategy mesaStrategy;

	public EstadoJogo() {
		//TODO remover
		super("estado", null, TipoZona.PUBLICA);
		//TODO setPreferredSize(new Dimension(400, 400));
	}

	public void setDeck(Zona deck) {
		this.deck = deck;
	}

	public void setPlayerNames(String[] playerNames) {
		this.playerNames = playerNames;
	}
	
	public Zona getDeck() {
		return deck;
	}

	public void printEstado() {
		for (Zona zona : getComponents()) {
			System.out.println(zona.getName() + ((Zona) zona).getComponents().size());
		}
	}

	public String[] getPlayerNames() {
		return playerNames;
	}

	public void setPlayerVez(String playerVez) {
		String old = this.playerVez;
		this.playerVez = playerVez;
		//TODO firePropertyChange("playerVez", old, playerVez);
	}

	public void setValores(Map<Valores, Integer> valores) {
		this.valores = valores;
	}

	public Map<Valores, Integer> getValores() {
		return valores;
	}

	public final Zona getZonaByName(String nomeZona) {
		return getZonaByNameRecursivo(nomeZona, this);
	}

	private Zona getZonaByNameRecursivo(String nomeZona, Zona origem) {
		if (nomeZona != null) {
			if (nomeZona.equals(origem.getName())) {
				return (Zona) origem;
			}
			for (Zona c : origem.getComponents()) {
				Zona zona = getZonaByNameRecursivo(nomeZona, (Zona) c);
				if (zona != null) {
					return zona;
				}
			}
		}
		return null;
	}

	/**
	 * Seta a foto e TODO Coloca os mouse listeners em todas as cartas do estado.
	 * Listeners parecem ser transients nao seralizados oa envia-los perde-se os
	 * listeners das cartas.
	 * 
	 * @param comp
	 */
	public final void preparaEstadoRecursivo(List<EventListener> listeners, Zona comp, boolean compress) {
		if (comp instanceof Zona) {
			Zona zona = (Zona) comp;
			//zona.setaFoto();
			if (compress) {
				// Apaga a foto para reduzir o tamanho seriazilado da zona.
				// zona.setIcon(null);
				//Zona.criaBorda(zona, false);
			} else {
				// if (zona instanceof CartaBaralho)
				{
					/*
					 * Recoloca os listeners pois eles se perdem na serializa��o.
					 */
					if (listeners != null) {
						for (EventListener listener : listeners) {
							/*
							 * Mangue para evitar problema de duplica��o de listeners.
							 */
//							if (listener instanceof MouseListener
//									&& !Arrays.asList(zona.getMouseListeners()).contains(listener)) {
//								zona.addMouseListener((MouseListener) listener);
//							}
//							if (listener instanceof MouseMotionListener
//									&& !Arrays.asList(zona.getMouseMotionListeners()).contains(listener)) {
//								zona.addMouseMotionListener((MouseMotionListener) listener);
//							}
						}
					}
				}
			}
		}
		for (Zona c : comp.getComponents()) {
			preparaEstadoRecursivo(listeners, (Zona) c, compress);
		}
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}
}