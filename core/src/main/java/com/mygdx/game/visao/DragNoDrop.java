package com.mygdx.game.visao;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class DragNoDrop extends ApplicationAdapter {
	Stage stage;
	Image back;// = new Image(new Texture("baralhos/c1.gif"));

	@Override
	public void create() {
		Gdx.input.setInputProcessor(stage);
		stage = new Stage(new ScreenViewport());
		back = new Image(new Texture("baralhos/c1.gif"));
		Gdx.input.setInputProcessor(stage);
		Gdx.graphics.setWindowedMode(800, 600);
		back.addListener(new DragListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				System.out.println("down..");
				return super.touchDown(event, x, y, pointer, button);//true;
			}

			@Override
			public void touchDragged(InputEvent event, float x, float y, int pointer) {
				System.out.println("draging.. x=" + (x - back.getWidth() / 2) + ", y=" + (y - back.getHeight() / 2));
				// back.setPosition(x - back.getWidth()/2, y - back.getHeight()/2);
				//back.moveBy(x - back.getWidth() / 2, y - back.getHeight() / 2);
				super.touchDragged(event, x, y, pointer);
			}
		});
		stage.addActor(back);
		// super.create();
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
		// ScreenUtils.clear(1, 1, 0, 1);

		back.setPosition(100, 100);
		// back.setSize(60, 30);
		// back.setFillParent(true);
		stage.act();
		stage.draw();
	}

	@Override
	public void dispose() {
		stage.dispose();
	}
}