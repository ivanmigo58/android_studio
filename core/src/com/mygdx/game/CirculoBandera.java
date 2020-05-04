package com.mygdx.game;

public class CirculoBandera {
    static int diferenciaConPelota = 20;
    static int height = Pelota.width + diferenciaConPelota;
    static int width = Pelota.height + diferenciaConPelota;
    static int circuloBanderaX = MyGdxGame.pantallaWidth / 2 - (Pelota.width / 2) - (diferenciaConPelota / 2);
    static int CirculoBanderaY = MyGdxGame.pantallaHeight - 230 ;
}
