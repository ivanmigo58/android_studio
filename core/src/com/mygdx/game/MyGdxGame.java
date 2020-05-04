package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.Random;

public class MyGdxGame extends ApplicationAdapter {
	Random random;
	SpriteBatch batch;
	Texture carretera, pelota, coche, circuloBandera, banderin, trofeo;
	static FitViewport fitViewport;
	static boolean haGanado;
	
	static int pantallaWidth = 1080;
	static int pantallaHeight = 1920;

	static Stage stage;
    static Skin skin;
	static TextButton buttonNextLevel;
	static boolean click, primeravez;
	static Botones botonNextLevel;
	Golfista golfista;
	float tiempoClickGolfista, tiempo;
	int nivel = 1;
	Fuentes fuenteLevel;
	static Sprites spriteTrofeo;

    @Override
	public void create () {
		batch = new SpriteBatch();
		fitViewport = new FitViewport(pantallaWidth,pantallaHeight);
		carretera = new Texture("carretera.png");
		coche = new Texture("coche.png");
		pelota = new Texture("pelota.png");
		circuloBandera = new Texture("circulo_bandera.png");
		banderin = new Texture("banderin.png");
		trofeo = new Texture("trofeo.png");
		// Creo la clase del boton
		botonNextLevel = new Botones();
		stage = new Stage(fitViewport);
		skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
		// Creo el boton de nextLevel
		buttonNextLevel = botonNextLevel.crearBotones("Next Level", 400,200, 3f, stage, skin);
		// Creo la clase de golfista
		golfista = new Golfista(Golfista.golfistaX, Golfista.golfistaY);
		// Creo la clase de la fuente de nivel
		fuenteLevel = new Fuentes(1.5f,1.5f);
		// Creo el sprite del trofeo
		spriteTrofeo = new Sprites(trofeo, 0 , 0, 11, 1, 2f);
		initGame();
	}

	@Override
	public void render() {
		// Mientras el jugador no haya llevado la pelota al hoyo
		// Tiempo
		tiempo += Gdx.graphics.getDeltaTime();
		Gdx.gl.glClearColor(31 / 255f, 122 / 255f, 31 / 255f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();

		// Siempre que no haya ganado
		if (!haGanado) {
			if (Gdx.input.isTouched()) {
				Pelota.pelotaY += 10;
			}

			// Mover coche y comprobar que sale de la pantalla
			for (int i = 0; i < Carretera.numeroCarreteras; i++) {
				for (int j = 0; j < Coche.numeroCoches; j++) {
					Coche.cocheX[i][j] += Coche.velocidadCoches;
					if (Coche.cocheX[i][j] > pantallaWidth) {
						Coche.cocheX[i][j] = -Coche.width;
					}
				}
			}
		}

		// Cuando se le da click por primera vez ponemos un contador para la animacion
		if (Gdx.input.justTouched() && primeravez) {
			click = true;
			primeravez = false;
			tiempoClickGolfista = tiempo;
			System.out.println("Tiempo: " + tiempo + "onclick: " + tiempoClickGolfista);
		}


		// Comprobamos si el coche choca con la tortuga
		for (int i = 0; i < Carretera.numeroCarreteras; i++) {
			// Si la tortuga esta en la carretera
			if (Carretera.carreterasY[i] <= Pelota.pelotaY && Carretera.carreterasY[i] + Carretera.height >= Pelota.pelotaY) {
				for (int j = 0; j < Coche.numeroCoches; j++) {
					// Si la tortuga se choca
					if (Pelota.pelotaX >= Coche.cocheX[i][j] && Pelota.pelotaX <= Coche.cocheX[i][j] + Coche.width || Pelota.pelotaX  + Pelota.width >= Coche.cocheX[i][j] && Pelota.pelotaX + Pelota.width <= Coche.cocheX[i][j] + Coche.width) {
						Pelota.pelotaY = 100;
					}
				}
			}
			// Si alguna parte de la pelota entra a la carretera
			else if (Pelota.pelotaY + Pelota.height > Carretera.carreterasY[i] && Pelota.pelotaY < Carretera.carreterasY[i]) {
				for (int j = 0; j < Coche.numeroCoches; j++) {
					// Si la tortuga se choca
					if (Pelota.pelotaX >= Coche.cocheX[i][j] && Pelota.pelotaX <= Coche.cocheX[i][j] + Coche.width || Pelota.pelotaX  + Pelota.width >= Coche.cocheX[i][j] && Pelota.pelotaX + Pelota.width <= Coche.cocheX[i][j] + Coche.width) {
						Pelota.pelotaY = 100;
					}
				}
			}
		}

		// Si la pelota ha llegado al abujero
		if (Pelota.pelotaY >= CirculoBandera.CirculoBanderaY && Pelota.pelotaY < CirculoBandera.CirculoBanderaY + CirculoBandera.height) {
			if (!haGanado) nivel++;
			haGanado = true;
			// Cuando se le da click al boton
			buttonNextLevel.addListener(new ChangeListener() {
				@Override
				public void changed(ChangeEvent event, Actor actor) {
					Gdx.input.setInputProcessor(null);
					initGame();
				}
			});
			// Llamo a la funcion que pinta los objetos
			draw();
			Gdx.input.setInputProcessor(stage);
			stage.draw();
		}
		// Si no ha ganado aun, sigo pintando las imagenes
		else {
			draw();
		}
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		fitViewport.update(width, height, true);
		batch.setProjectionMatrix(fitViewport.getCamera().combined);
	}

	@Override
	public void dispose() {
		batch.dispose();
		carretera.dispose();
		coche.dispose();
		pelota.dispose();
	}

	// Funcion que inicia el juego
	void initGame() {
		// Creo la posicon de la pelota
		Pelota.pelotaY = 100;
		Pelota.pelotaX = MyGdxGame.pantallaWidth / 2 - Pelota.width / 2;
		// Creo las posiciones Y de carretera
		for (int i = 0; i < Carretera.numeroCarreteras; i++) {
			// La primera carretera
			if (i == 0) {
				Carretera.carreterasY[i] = 300;
			} else {
				int numRandomCarretera = (int) Math.floor(Math.random()*(Carretera.carreterasY[i - 1] + Carretera.height + 120 -(Carretera.carreterasY[i - 1] + Carretera.height + 200 +1))+(Carretera.carreterasY[i - 1] + Carretera.height + 200));
				Carretera.carreterasY[i] = numRandomCarretera;

			}
			// Le pongo a coche Y, la posicion de la carretera
			Coche.cocheY[i] = Carretera.carreterasY[i] + Carretera.height / 2 - Coche.height / 2;
			// Por cada carretera, pongo la posicion de X de coche
			for (int j = 0; j < Coche.numeroCoches; j++) {
				// La distancia entre coches
				int numeroRandom = (int) Math.floor(Math.random()*(Coche.width + Pelota.width -(Coche.width + (Pelota.width * 3) +1))+(Coche.width + Pelota.width + (Pelota.width * 3) ));
				// Si es el primer coche
				if (j == 0) {
					Coche.cocheX[i][j] = 0;
				} else {
					Coche.cocheX[i][j]= Coche.cocheX[i][j-1] + numeroRandom;
				}
			}
		}
		primeravez = true;
		click = false;
		haGanado = false;
	}

	// Funcion que dibuja los objetos en la pantalla
	public void draw() {
		// Dibujo las imagenes
		// Por cada una de las carreteras
		for (int i = 0; i < Carretera.numeroCarreteras; i++) {
			batch.draw(carretera, Carretera.carreteraX, Carretera.carreterasY[i], Carretera.width, Carretera.height);
			// Por cada carretera, dibujamos x coches
			for (int j = 0; j < Coche.numeroCoches; j++) {
				batch.draw(coche, Coche.cocheX[i][j], Coche.cocheY[i], Coche.width, Coche.height);
			}
		}

		// Cuando se le ha dado click por primera vez, muestro la animacion durante 3 segundos
		if (click && tiempo - tiempoClickGolfista < Golfista.tiempoAnimacion) {
			golfista.render(batch);
		} else if (!haGanado) {
			// Muñeco en la posicion de inicio
			if (primeravez)	{
				batch.draw(Golfista.regionInicio, Golfista.golfistaX, Golfista.golfistaY);
			}
			// Muñeco en la posicion final
			else {
				batch.draw(Golfista.regionFinal, Golfista.golfistaX, Golfista.golfistaY);
			}
		}
		batch.draw(circuloBandera, CirculoBandera.circuloBanderaX, CirculoBandera.CirculoBanderaY, CirculoBandera.width, CirculoBandera.height);
		batch.draw(banderin, pantallaWidth / 2 - (Pelota.width / 2) - (CirculoBandera.diferenciaConPelota / 2) - 10, pantallaHeight - 220);
		// Dibujo la label del nivel
        fuenteLevel.font.draw(batch, "Level: " + nivel, pantallaWidth - 230,pantallaHeight - 10);
		// La muestro siempre que haya acabado el tiempo de la animacion
		if (tiempo - tiempoClickGolfista > Golfista.tiempoAnimacion && click) {
			batch.draw(pelota, Pelota.pelotaX, Pelota.pelotaY, Pelota.width, Pelota.height);
		}
		// Siempre que no haya dado el primer click, muestro la pelota en la posicion 0
		else {
			batch.draw(pelota, Pelota.pelotaX, 0, Pelota.width, Pelota.height);
			Pelota.pelotaY = 150;
		}
		// Si ha ganado, pongo el sprite del trofeo
		if (haGanado) {
			spriteTrofeo.render(batch);
		}
		batch.end();
	}

}

/*

RANDOM:
int random = (int) Math.floor(Math.random()*(Nubes.nubesWidth-(Nubes.espacioEntreNubes+1))+(Nubes.espacioEntreNubes));

 */