package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Golfista {
    static int golfistaX = MyGdxGame.pantallaWidth / 2 - (Pelota.width * 4);
    static int golfistaY = Pelota.pelotaY;

    int x, y;
    // Por cada movimiento que hay dentro de la imagen
    int movimientos, filas;
    static float tiempoAnimacion = 0.8f;
    Animation animation;
    float tiempo;
    TextureRegion[] regionsMovimientos;
    static TextureRegion regionInicio;
    static TextureRegion regionFinal;
    Texture imagen;
    TextureRegion frameActual;

    public Golfista(int x, int y) {
        this.x = golfistaX;
        this.y = golfistaY;
        this.movimientos = 5;

        // Cargar la imagen
        imagen = new Texture(Gdx.files.internal("golfista.png"));
        TextureRegion[][] tmp = TextureRegion.split(imagen, imagen.getWidth()/movimientos, imagen.getHeight() / 3);
        regionsMovimientos = new TextureRegion[movimientos];

        // Pongo en un array todas las imagenes de la segunda fila
        for (int i = 0; i < movimientos; i++) {
            regionsMovimientos[i] = tmp[1][i];
        }

        regionInicio = tmp[0][0];
        regionFinal = tmp[1][movimientos - 1];


        animation = new Animation(0.2f,regionsMovimientos);
        tiempo = 0f;
    }

    public void render(final SpriteBatch batch) {
        tiempo += Gdx.graphics.getDeltaTime(); // es el tiempo que pasa desde el ultimo render
        frameActual = (TextureRegion) animation.getKeyFrame(tiempo, true);
        batch.draw(frameActual, x, y);
    }
}
