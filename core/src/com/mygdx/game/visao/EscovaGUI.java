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
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.utils.ScreenUtils;
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
	TextButton bserver;
	TextButton bclient;
	TextButton bturno;
	EstadoJogo estado;
	VerticalGroup gMao1;
	VerticalGroup gMao2;
	// Image back;
	// Stack g = new Stack();//ficam empilhados e stratched ao tamanho do stack
	// WidgetGroup g = new WidgetGroup();//parece na renderizar o WidgetGroup

	private void dragDrop(final Actor actor, Actor t) {
		// Define o comportamento de arrastar e soltar
		DragAndDrop dragAndDrop = new DragAndDrop();
		dragAndDrop.addSource(new DragAndDrop.Source(actor) {
			@Override
			public DragAndDrop.Payload dragStart(InputEvent event, float x, float y, int pointer) {
				DragAndDrop.Payload payload = new DragAndDrop.Payload();
				payload.setDragActor(actor); // Define o ator a ser arrastado
				System.out.println("startdrag");
				return payload;
			}
		});

		dragAndDrop.addTarget(new DragAndDrop.Target(t) {
			@Override
			public boolean drag(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
				// Define o comportamento durante o arraste
				//actor.setPosition(x - actor.getWidth() / 2, y - actor.getHeight() / 2);
				System.out.println("drag...");
				return true;
			}

			@Override
			public void drop(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
				System.out.println("drop...");
				// Define o comportamento ao soltar o elemento arrastado
				//actor.setPosition(x - actor.getWidth() / 2, y - actor.getHeight() / 2);
				// Torna o elemento dropado filho do target
				System.out.println(this.getActor().getName());
				((Group) this.getActor()).addActor(payload.getDragActor());
			}
		});
	}

	void estado2Group() {
		estado = new EstadoJogo();
		estado.add(new Zona("Mão1", null, null));
		estado.add(new Zona("Mão2", null, null));
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
					gMao1.addActor(i);
					i.setName("carta");
					// i.setDebug(true);
					// i.setPosition(0, 0);
					// delta += 15;
					dragDrop(i,gMao2);
				} else if (z instanceof Zona) {
					VerticalGroup g = new VerticalGroup();// fica um abaixo do outro
					if(z.getName().equals("Mão1")) {
						gMao1=g;
					}else {
						gMao2=g;
					}
//					Group r = new Group();
//					r.setColor(Color.BLUE);
//					r.setSize(150, 150);
					stage.addActor(g);
					g.setName("mão");
					g.setDebug(true);
					g.space(-75);// vgroup
					g.pad(5);// vgroup
					g.setPosition(stageX+delta, stageY);
					g.setSize(100, stage.getHeight());
//					g.addListener(new DragListener() {
//						@Override
//						public void drag(InputEvent event, float x, float y, int pointer) {
//							// super.drag(event, x, y, pointer);
//							System.out.println(event.getTarget().getName());
//						}
//					});
					delta += 150;
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
		estado2Group();
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

			}
		});
	}

	@Override
	public void render() {
		ScreenUtils.clear(1, 1, 0, 1);

//		Image back = new Image(new Texture("baralhos/border.gif"));
//		back.setFillParent(true);
//		stage.addActor(back);
		// g.addActor(back);
//		stage.addActor(g);
		// back.toBack();
//		stage.addActor(bserver);
//		stage.addActor(bclient);
//		stage.addActor(bturno);
		stage.draw();
	}

	@Override
	public void dispose() {
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
