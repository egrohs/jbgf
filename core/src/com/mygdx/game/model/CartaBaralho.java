package com.mygdx.game.model;

import java.awt.Image;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import com.mygdx.game.model.Constantes.Naipes;
import com.mygdx.game.model.Constantes.Valores;
import com.mygdx.game.model.Zona.TipoZona;
import com.mygdx.game.visao.GUIPreferencias;

import lombok.Data;

@Data
//Equivale ao Image do Libgdx

public class CartaBaralho extends Zona {
	private String imgPath;
	private Valores valor;
	private Naipes naipe;

	public CartaBaralho(Valores valor, Naipes naipe) {
		super(valor.toString() + naipe, null, TipoZona.PUBLICA);
		this.valor = valor;
		this.naipe = naipe;
		setaFoto();
		// setBorder(BorderFactory.createEmptyBorder());
		// Mouse mo = new Mouse();
		// addMouseListener(mo);
		// addMouseMotionListener(mo);
	}

	//TODO remover?
	public CartaBaralho(String string) {
		super(string, null, TipoZona.PUBLICA);
		imgPath=string;
	}

	/**
	 * Seta a foto da carta de acordo com visibilidade da mesma.
	 * 
	 */
	public void setaFoto() {
		String foto = "";

		if (!isVisivelPossui(Jogo.nomePlayer)) {
			// Se a carta nao � visivel por esse player sua foto sera o virada.
			// g.drawImage(Constantes.DECK_BACK, 0, 0, null);
			foto = Constantes.DECK_BACK;
		} else {
			switch (naipe) {
			case COPAS:
				foto += "h";
				break;
			case ESPADAS:
				foto += "s";
				break;
			case OUROS:
				foto += "d";
				break;
			case PAUS:
				foto += "c";
				break;
			default:
				foto += "j";
				break;
			}
			foto += valor.ordinal() + ".gif";
		}

//		Icon i0 = new ImageIcon(ClassLoader.getSystemResource(Constantes.BARALHOS + foto));
//		Icon i = new ImageIcon(((ImageIcon) i0).getImage().getScaledInstance(GUIPreferencias.deckX,
//				GUIPreferencias.deckY, Image.SCALE_DEFAULT));
//        setIcon(i);
//        setSize(GUIPreferencias.deckX, GUIPreferencias.deckY);
	}

	public Naipes getNaipe() {
		return naipe;
	}

	public void setNaipe(Naipes naipe) {
		this.naipe = naipe;
	}

	public Valores getValor() {
		return valor;
	}

	public void setValor(Valores valor) {
		this.valor = valor;
	}

	/*
	 * @Override public void move(Zona origem, Zona destino) { // TODO ??? nao deve
	 * ter move? }
	 */

	@Override
	public Zona add(Zona comp) {
		if (!(comp instanceof Zona)) {
			// Nao deve fazer nada
			System.err.println("Uma zona s� pode ter filhos do tipo Zona!");
			return null;
		}
		if (this.getParent() == null) {
			// Nao deve fazer nada
			System.err.println("Uma carta solta n�o pode receber filhos!");
			return null;
		}
		int index = this.getParent().getComponentZOrder(this);
		this.getParent().add(comp, index);
		return comp;
	}

	@Override
	public Zona add(Zona comp, int index) {
		if (!(comp instanceof Zona)) {
			// Nao deve fazer nada
			System.err.println("Uma zona s� pode ter filhos do tipo Zona!");
			return null;
		}
		if (this.getParent() == null) {
			// Nao deve fazer nada
			System.err.println("Uma carta solta n�o pode receber filhos!");
			return null;
		}
		// int index = this.getParent().getComponentZOrder(this);
		this.getParent().add(comp, index);
		return comp;
	}

	@Override
	public String toString() {
		String s = "";
		if (valor != null) {
			s += valor;
		}
		if (naipe != null) {
			s += " de ";
			s += naipe;
		}
		return s;
	}
}