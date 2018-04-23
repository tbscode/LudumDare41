package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

class GameScreen implements Screen {

    float height, width, ratio, size;

    SpriteBatch batch;

    OrthographicCamera cam;

    GameController controller;

    Player player;
    BitmapFont font;

    boolean in_game = false;

    SpriteBatch sb;
    int Previus_score = 0;

    @Override
    public void show() {
        font = new BitmapFont();
        font.getData().setScale(2,2);
        sb = new SpriteBatch();

        //controller = new GameController();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(in_game) {

            controller.render_main();//Renders Everything and handels the Game
            if (controller.GAME_OVER) {
                in_game = false;Previus_score = controller.score_manager.SCORE;
                controller = null;

            }
        }else{
            sb.begin();
            font.draw(sb,"temporary start screen",0,Gdx.graphics.getHeight());
            font.draw(sb,"Press SPACE to start",0,Gdx.graphics.getHeight()-font.getXHeight()*2);
            font.draw(sb,"How to play: Move back and forward with the Scope.",0,Gdx.graphics.getHeight()-font.getXHeight()*4);
            font.draw(sb,"Move left and right with W and D",0,Gdx.graphics.getHeight()-font.getXHeight()*6);
            font.draw(sb,"Doge Bombs and shoot enemys",0,Gdx.graphics.getHeight()-font.getXHeight()*8);
            font.draw(sb,"BestPlayed with mouse",0,Gdx.graphics.getHeight()-font.getXHeight()*10);
            font.draw(sb,"Score in Last Game: "+Previus_score,0,Gdx.graphics.getHeight()-font.getXHeight()*12);
            if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
                in_game = true;
                controller = new GameController();
            }
            sb.end();
        }


    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
