package com.mygdx.game.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.Data;

@Data
//Equivale ao Group do Libgdx
public class Zona implements Cloneable {
	// ---criados para compatibilidade temporaria---
	protected String name;
	protected Zona parent;
	protected List<Zona> filhos = new ArrayList<>();

	public Zona getComponent(int i) {
		return filhos.get(i);
	}

	protected List<Zona> getComponents() {
		return filhos;
	}

	protected int getComponentZOrder(Zona z) {
		return 0;
	}

	// ---------------------------------------------
	public static enum TipoZona {
		/* NINGUEM */PROTEGIDA, /* DONOS */PRIVADA, /* TODOS */PUBLICA;
	}

	protected TipoZona visivelPor;

	protected List<String> donos;

	transient protected boolean selecionada;

	// TODO fazer as zonas terem props como minDistX e minDistY passadas no
	// contrutor
	public Zona(String nome, String[] donos, /* Zona conteudo, */TipoZona visivelPor) {
		name = nome;
		if (donos != null) {
			this.donos = Arrays.asList(donos);
		}
		// TODO remover isso???
		// setName(nome);
		if (!(this instanceof CartaBaralho)) {
			// setBorder(BorderFactory.createTitledBorder(nome));
		}
		this.visivelPor = visivelPor;
		// setMinimumSize(new Dimension(GUIPreferencias.deckX, GUIPreferencias.deckY));
	}

	public EstadoJogo getEstado() {
		Zona c = this;
		do {
			if (c instanceof EstadoJogo)
				return (EstadoJogo) c;
			c = c.getParent();
		} while (c != null);
		return null;
	}

	public Zona getZonaPrimeiraOrdem() {
		Zona c = this;
		do {
			if (c.getParent() instanceof EstadoJogo)
				return (Zona) c;
			c = c.getParent();
		} while (c != null);
		return null;
	}

	// @Override
	public Zona add(Zona comp) {
		return add(comp, 0);// TODO 0 ou size-1?
	}

	// @Override
	public Zona add(Zona comp, int index) {
		if (!(comp instanceof Zona)) {
			// Nao deve fazer nada
			System.err.println("Uma zona s� pode ter filhos do tipo Zona!");
			return null;
		}
		Zona antesMove = comp.getParent();
		// super.add(comp, index);
		// TODO filhos.add(index, comp);
		filhos.add(comp);
		Zona novaZona = (Zona) comp;
		// Quando uma zona muda de local ela muda de dono.
		novaZona.setDonos(donos);
		novaZona.visivelPor = visivelPor;
		// TODO reorganiza();
		// TODO firePropertyChange("moveu", antesMove, this);
		return comp;
	}

	// @Override
	public void remove(Zona comp) {
		// super.remove(comp);
		filhos.remove(comp);
		// TODO reorganiza();
	}

	public void setDonos(List<String> donos) {
		this.donos = donos;
	}

	public void setDonos(String[] donos) {
		this.donos = Arrays.asList(donos);
	}

	public boolean isSelecionada() {
		return selecionada;
	}

	public void setSelecionada(boolean selecionada) {
		this.selecionada = selecionada;
	}

	public boolean isVisivelPossui(String pName) {
		return visivelPor == TipoZona.PUBLICA || possuida(pName);
	}

	public boolean possuida(String pName) {
		return donos != null && donos.contains(pName);
	}
}