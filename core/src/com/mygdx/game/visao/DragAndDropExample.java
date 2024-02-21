package com.mygdx.game.visao;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;

public class DragAndDropExample extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture texture;
    private Stage stage;
    private DragAndDrop dragAndDrop;

    @Override
    public void create() {
        batch = new SpriteBatch();
        texture = new Texture(Gdx.files.internal("badlogic.jpg"));
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        // Cria a imagem que pode ser arrastada
        final Image image = new Image(texture);
        image.setPosition(100, 100);

        // Define o comportamento de arrastar e soltar
        dragAndDrop = new DragAndDrop();
        dragAndDrop.addSource(new DragAndDrop.Source(image) {
            @Override
            public DragAndDrop.Payload dragStart(InputEvent event, float x, float y, int pointer) {
                DragAndDrop.Payload payload = new DragAndDrop.Payload();
                payload.setDragActor(image); // Define o ator a ser arrastado
                return payload;
            }
        });

        dragAndDrop.addTarget(new DragAndDrop.Target(image) {
            @Override
            public boolean drag(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
                // Define o comportamento durante o arraste
                image.setPosition(x - image.getWidth() / 2, y - image.getHeight() / 2);
                return true;
            }

            @Override
            public void drop(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
                // Define o comportamento ao soltar o elemento arrastado
                image.setPosition(x - image.getWidth() / 2, y - image.getHeight() / 2);
             // Torna o elemento dropado filho do target
               // this.getActor().addActor(payload.getDragActor());
            }
        });

        stage.addActor(image);
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        texture.dispose();
        stage.dispose();
    }
}