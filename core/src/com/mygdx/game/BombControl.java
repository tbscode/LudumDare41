package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class BombControl {

    GameController controller;

    ArrayList<Bomb> bombs;

    String dropMode = "random";
    public float drop_time = 3f; //Till next Bomb Drops

    float timer = 0;

    public BombControl(GameController controller){
        this.controller = controller;

        bombs = new ArrayList<Bomb>();
        //bombs.add(new Bomb(controller, 200, 0));
    }

    //Updates and drops Bombs if necesarry
    public void update(SpriteBatch sb){
        for(int i = 0; i < bombs.size(); i++){
            bombs.get(i).update(sb);
        }

        timer += controller.getDelta();
        if(dropMode.equals("random")){
            //Gdx.app.log("TIME",""+timer);
            if(timer > drop_time){
                timer = 0;
                bombs.add(new Bomb(controller,(float) Math.random()*(1000f),(float) Math.random()*(330f)));
            }
        }
    }

    public void destroyMe(Bomb b){
        bombs.remove(bombs.indexOf(b));
    }
}
