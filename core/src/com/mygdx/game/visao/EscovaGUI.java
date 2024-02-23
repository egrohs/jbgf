package com.mygdx.game.visao;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Payload;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Source;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Target;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.SnapshotArray;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.model.CartaBaralho;
import com.mygdx.game.model.EstadoJogo;
import com.mygdx.game.model.Zona;
import com.mygdx.game.sockets.ClientReadThread;
import com.mygdx.game.sockets.ServerListenClientsThread;

public class EscovaGUI extends ApplicationAdapter {
	Stage stage;
	// SpriteBatch batch;
	// Texture img;
	// Image img;
	TextButton bquit;
	TextButton bserver;
	TextButton bclient;
	TextButton bturno;
	EstadoJogo estado;
	VerticalGroup gMao1;
	VerticalGroup gMao2;
	VerticalGroup gMao3;
	// Image back;
	// Stack g = new Stack();//ficam empilhados e stratched ao tamanho do stack
	// WidgetGroup g = new WidgetGroup();//parece na renderizar o WidgetGroup
	// Define o comportamento de arrastar e soltar
	DragAndDrop dragAndDrop = new DragAndDrop();

	private void dragDrop(final Actor actor, Actor t) {
		dragAndDrop.addSource(new Source(actor) {
			@Override
			public Payload dragStart(InputEvent event, float x, float y, int pointer) {
				System.out.println("X=" + x + " Y=" + y);
				System.out.println("actorX=" + actor.getX() + " actorY=" + actor.getY());
				System.out.println("getOriginX=" + actor.getOriginX() + " getOriginY=" + actor.getOriginY());
				//dragAndDrop.setDragActorPosition(8/* actor.getWidth() / 2 */, -50/*-actor.getHeight() / 2*/);
				Payload payload = new Payload();
				// payload.setObject(actor);// ?necessario?
				System.out.println("inicio " + stage.getActors().size);
				VerticalGroup tempGroup = new VerticalGroup();
				tempGroup.space(-75);
				stage.addActor(tempGroup);
				SnapshotArray<Actor> cards = actor.getParent().getChildren();
				int start = cards.indexOf(actor, true);
				int newSize = cards.size - start;

				// tempArray necessario pq o addActor tb remove do grupo anterior
				SnapshotArray<Actor> a = new SnapshotArray<>(true, newSize);
				a.addAll(cards, start, newSize);
				for (Actor actor2 : a) {
					tempGroup.addActor(actor2);
				}
				
//				for (int i = start; i < actor.getParent().getChildren().size; i++) {
//					System.out.println("adicionou " + i);
//					tempGroup.addActor(cards.get(i));
//				}
				payload.setDragActor(tempGroup); // Define o ator a ser arrastado

				System.out.println(actor.getName());
				// System.out.println("startdrag mao1=" + gMao1.getChildren().size + ", mao2=" +
				// gMao2.getChildren().size);
				System.out.println("after start " + stage.getActors().size);
				return payload;
			}
		});

		dragAndDrop.addTarget(new Target(t) {
			@Override
			public boolean drag(Source source, Payload payload, float x, float y, int pointer) {
				// TODO ainda tem um provavel bug no libgdx quando o drag inicia dentro de um
				// grupo com x<>0, ele joga a imagem (o "x" do grupo) a mais para a direita.
				//TODO ao criar o grupo temporario o bug mudou, agora joga 75pix? pra baixo.
//				System.out.println("drag... x=" + x + " y=" + y);
//				System.out.println(source.getActor().getName() + " x=" + source.getActor().getX() + " y="
//						+ source.getActor().getY());
//				// source==payload
//				// 0, 0
//				System.out.println("sourcegetOrigin... x=" + source.getActor().getOriginX() + " y="
//						+ source.getActor().getOriginY());
//				System.out.println("payload.getObject... " + payload.getObject());// null
//				System.out.println("payload.getDragActor()... " + payload.getDragActor());// ok
//				source.getActor().setZIndex(1000);// toFront(); stage.bringToFront(actor);
				// source.getActor()/*actor*/.setPosition(x - actor.getWidth() / 2, y -
				// actor.getHeight() / 2);
				// actor!=source.getActor()
//				source.getActor().setPosition(x, (y - 150));
				return true;
			}

			@Override
			public void drop(Source source, Payload payload, float x, float y, int pointer) {
				// Define o comportamento ao soltar o elemento arrastado
				// actor.setPosition(x - actor.getWidth() / 2, y - actor.getHeight() / 2);
				// Torna o elemento dropado filho do target
				System.out.println("incio drop " + stage.getActors().size);
				// ((Group) this.getActor()).addActor(payload.getDragActor());
				System.out.println(payload.getDragActor().getName());

				Group gSource = (Group) payload.getDragActor();
				Group gTarget = (Group) this.getActor();
				
				
				//int start = cards.indexOf(actor, true);
				int newSize = gSource.getChildren().size;// - start;
				// tempArray necessario pq o addActor tb remove do grupo anterior
				SnapshotArray<Actor> a = new SnapshotArray<>(true, newSize);
				a.addAll(gSource.getChildren());
				for (Actor actor2 : a) {				
					gTarget.addActor(actor2);
				}
				
//				for (Actor a : gSource.getChildren()) {
//					System.out.println("adding "+a);
//					gTarget.addActor(a);
//				}
				// System.out.println(gSource.getChildren().size);
				System.out.println("fim1 drop " + stage.getActors().size);
				gSource.remove();

				System.out.println("fim2 drop " + stage.getActors().size);
				// System.out.println("drop mao1=" + gMao1.getChildren().size + ", mao2=" +
				// gMao2.getChildren().size);
				// Gdx.graphics.requestRendering();
			}
		});
	}

	void estado2Group(EstadoJogo estado) {
		estado.add(new Zona("Mão1", null, null));
		estado.add(new Zona("Mão2", null, null));
		estado.add(new Zona("Mão3", null, null));
		estado.add(new CartaBaralho("c1.gif"));
		estado.add(new CartaBaralho("c2.gif"));
		estado.add(new CartaBaralho("c3.gif"));

		// Actor a = new Actor();
		// a.setBounds(0, 0, 550, 550);
//		Image back = new Image(new Texture("baralhos/border.gif"));
//		back.setFillParent(true);
//		g.addActor(back);
//		g.fill(1);
//		g.wrap();

		// a.setSize(300, 300);
		// a.setColor(Color.BLUE);
		// g.setActor(a);

		// TODO carregar as imagens das cartas, setar o tamanho delas e as bordas,
		// pinta-la negativa se tiver selecionada
		// minsize?
		// TODO zonas tb devem ter name?
		if (estado != null) {
			float stageX = Gdx.graphics.getWidth() - stage.getWidth();
			float stageY = Gdx.graphics.getHeight() - stage.getHeight();
			float delta = 0;
			for (Zona z : estado.getFilhos()) {
				if (z instanceof CartaBaralho) {
					CartaBaralho c = (CartaBaralho) z;
					Image i = new Image(new Texture("baralhos/" + c.getImgPath()));
					// i.setPosition(delta, delta);
					i.setName("carta " + c.getImgPath());
					// i.setDebug(true);
					// i.setPosition(0, 0);
					// delta += 15;
					dragDrop(i, gMao1);
					dragDrop(i, gMao2);
					dragDrop(i, gMao3);
					gMao1.addActor(i);
				} else if (z instanceof Zona) {
					VerticalGroup g = new VerticalGroup();// fica um abaixo do outro
					if (z.getName().equals("Mão1")) {
						gMao1 = g;
					} else if (z.getName().equals("Mão2")) {
						gMao2 = g;
					} else if (z.getName().equals("Mão3")) {
						gMao3 = g;
					}
					// g.setName("mão");
					g.setDebug(true);
					g.space(-75);// vgroup
					g.pad(5);// vgroup
					g.setSize(100, stage.getHeight());
					System.out.println("stageX=" + stageX + " delta=" + delta + " stageY" + stageY);
					g.setPosition(/* stageX + */ delta, 0/* stageY */);
					// g.setX(delta);
					// g.setBounds(delta, 0, 100, 400);

//					g.addListener(new DragListener() {
//						@Override
//						public void drag(InputEvent event, float x, float y, int pointer) {
//							// super.drag(event, x, y, pointer);
//							System.out.println(event.getTarget().getName());
//						}
//					});
					delta += 150;
					stage.addActor(g);
				}
			}
		}
	}

	public static void criaBorda(Zona zona, boolean cria) {
//		if (zona != null && zona.getIcon() != null) {
//			if (cria) {
//				zona.setSize(zona.getIcon().getIconWidth() + GUIPreferencias.larguraBorda * 2,
//						zona.getIcon().getIconHeight() + GUIPreferencias.larguraBorda * 2);
//				zona.setBorder(BorderFactory.createLineBorder(GUIPreferencias.corBorda, GUIPreferencias.larguraBorda));
//			} else {
//				zona.setBorder(null);
//				zona.setSize(zona.getIcon().getIconWidth(), zona.getIcon().getIconHeight());
//			}
//		}
	}

	// TODO reorganiza uma zona, deve ser recursivo? in-out ou out-in?
	public void reorganiza(/* Container comp */) {
//		int nro = this.getComponentCount();
//		if (nro > 0) {
//			this.setPreferredSize(new Dimension((nro + 1) * GUIPreferencias.minDistX + GUIPreferencias.deckX,
//					(nro + 2) * GUIPreferencias.minDistY + GUIPreferencias.deckY));
//			for (int i = 0; i < nro; i++) {
//				Zona zona = (Zona) this.getComponent(i);
//				zona.setLocation((nro - i) * GUIPreferencias.minDistX, (nro - i + 1) * GUIPreferencias.minDistY);
//			}
//		}
	}

	@Override
	public void create() {
		stage = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(stage);

		System.out.println(Gdx.graphics.getWidth() + "," + Gdx.graphics.getHeight());
		estado2Group(new EstadoJogo());
		// g.setLayoutEnabled​(false);
		// g.setSize(300, 300);
		// back = new Image(new Texture("baralhos/border.gif"));
//		g.addActor(back);
		// back.setFillParent(true);

		// stage.addActor(g);
		// g.addActor(new Image(new Texture("badlogic.jpg")));

		TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
		textButtonStyle.font = new BitmapFont();
		textButtonStyle.fontColor = Color.WHITE;

		bquit = new TextButton("Quit", textButtonStyle);
		bquit.setSize(30, 30);
		bquit.setPosition(420, 420);

		bquit.addListener(new ChangeListener() {
			public void changed(ChangeEvent event, Actor actor) {
				System.out.println("quit");
				// Gdx.app.exit(); nao funfa
			}
		});

		bserver = new TextButton("Server", textButtonStyle);
		bserver.setSize(30, 30);
		bserver.setPosition(400, 400);
		bserver.addListener(new ChangeListener() {
			public void changed(ChangeEvent event, Actor actor) {
				// game.setScreen(game.gameScreen);
				System.out.println("server");
				sockets(new String[] { "s" });
			}
		});

		bclient = new TextButton("Client", textButtonStyle);
		bclient.setSize(30, 30);
		bclient.setPosition(400, 300);
		bclient.addListener(new ChangeListener() {
			public void changed(ChangeEvent event, Actor actor) {
				// game.setScreen(game.gameScreen);
				System.out.println("client");
				sockets(new String[] { "c" });
			}
		});

		bturno = new TextButton("Turno", textButtonStyle);
		bturno.setSize(30, 30);
		bturno.setPosition(400, 200);
		bturno.addListener(new ChangeListener() {
			public void changed(ChangeEvent event, Actor actor) {
				System.out.println("turno");
			}
		});

//		stage.addActor(back);
		// g.addActor(back);
//		stage.addActor(g);
		// back.toBack();
//		stage.addActor(bserver);
//		stage.addActor(bclient);
		stage.addActor(bturno);
		stage.addActor(bquit);
	}

	@Override
	public void render() {
		ScreenUtils.clear(1, 1, 0, 1);
//		back.setFillParent(true);
		stage.act();
		stage.draw();
	}

	@Override
	public void dispose() {
		System.out.println("vai dispose");
		// batch.dispose();
		// img.dispose();
		stage.dispose();
	}

	private void sockets(String[] arg) {
		if (arg[0].equals("s")) {
			new ServerListenClientsThread().start();
		} else {
			new ClientReadThread().start();
		}
	}
}
