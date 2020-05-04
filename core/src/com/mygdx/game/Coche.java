package com.mygdx.game;

public class Coche {
    static int numeroCoches = 3;
    static int diferenciaCocheACarretera = 50;
    static int[] cocheY = new int[Carretera.numeroCarreteras];
    static int[][] cocheX = new int[Carretera.numeroCarreteras][Coche.numeroCoches];
    static int height = Carretera.height - diferenciaCocheACarretera;
    static int width = com.mygdx.game.Carretera.width / 5;
    static float velocidadCoches = 3.5f;
}
