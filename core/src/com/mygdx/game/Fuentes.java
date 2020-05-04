package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class Fuentes {
    BitmapFont font;
    public Fuentes(float scaleX, float scaleY) {
        font = new BitmapFont(Gdx.files.internal("fonts/special_elite.fnt"),Gdx.files.internal("fonts/special_elite.png"),false);
        font.getData().setScale(scaleX, scaleY);
    }
}
