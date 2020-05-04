package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Sprites {
    int posicionX, posicionY;
    int movimientos, filas;
    float tiempoAnimacion;
    Animation animation;
    float tiempo;
    TextureRegion[] regionsMovimientos;
    static TextureRegion regionInicio;
    static TextureRegion regionFinal;
    Texture imagen;
    TextureRegion frameActual;

    public Sprites(Texture imagenURL, int posicionSpriteX, int posicionSpriteY, int movimientos, int filas, float tiempoAnimacion) {
        // Actualizo las variables de la clase con las variables que le paso a esta funcion
        this.posicionX = posicionSpriteX;
        this.posicionY = posicionSpriteY;
        this.tiempoAnimacion = tiempoAnimacion;
        this.movimientos = movimientos;
        this.filas = filas;
        this.imagen = imagenURL;

        TextureRegion[][] tmp = TextureRegion.split(imagen, imagen.getWidth()/movimientos, imagen.getHeight() / filas);
        regionsMovimientos = new TextureRegion[movimientos];

        // Pongo en un array todas las imagenes de la segunda fila
        for (int i = 0; i < movimientos; i++) {
            regionsMovimientos[i] = tmp[0][i];
        }

        regionInicio = tmp[0][0];
        regionFinal = tmp[filas - 1][movimientos - 1];


        animation = new Animation(0.2f,regionsMovimientos);
        tiempo = 0f;
    }

    public void render(final SpriteBatch batch) {
        tiempo += Gdx.graphics.getDeltaTime(); // es el tiempo que pasa desde el ultimo render
        frameActual = (TextureRegion) animation.getKeyFrame(tiempo, true);
        batch.draw(frameActual, posicionX, posicionY);
    }
}
