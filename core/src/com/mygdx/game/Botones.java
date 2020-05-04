package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class Botones {
    TextButton crearBotones(String textButton, int widthButton, int heightButton, float scaleButton, Stage stage, Skin skin) {
        TextButton button = new TextButton(textButton , skin);
        button.setPosition(MyGdxGame.pantallaWidth / 2 - button.getWidth() - button.getWidth(), MyGdxGame.pantallaHeight / 2);
        button.setWidth(widthButton);
        button.setHeight(heightButton);
        // Pongo el texto mas grande
        button.getLabel().setFontScale(scaleButton);
        stage.addActor(button);
        return button;
    }
}
