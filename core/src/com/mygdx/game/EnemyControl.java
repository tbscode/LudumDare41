package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class EnemyControl {

    GameController control; //Controlls the Game

    ArrayList<BasicEnemy> enemies; //List with al the currently active enemys.

    public float spawn_speed = 1.5f; // pause beween spawns.
    float timer = 0;

    public EnemyControl(GameController control){
        this.control = control;

        enemies =  new ArrayList<BasicEnemy>();
    }

    public void update(SpriteBatch sb){
        timer += control.getDelta();
        if(timer > spawn_speed){
            timer = 0;
            enemies.add(new BasicEnemy(control,(float) (1410*Math.random()), 2));
        }
        for(int i = 0; i < enemies.size();i++){
            enemies.get(i).update(sb);
        }
    }

    boolean aiming_at_enemy = false;
    public void checkIfEnemyInScope(float x_touch, float y_touch, boolean touched){
        for(int i = 0; i < enemies.size(); i++){
            if(enemies.get(i).isInScope(x_touch,y_touch)){
                aiming_at_enemy = true;
                control.player.gun_drawn = true;
                if(touched){
                    enemies.get(i).gotShot();//Enemy is killed
                }
            }
        }
        if(!aiming_at_enemy)
            control.player.gun_drawn = false;
        aiming_at_enemy = false;
    }

    public void requestRemove(BasicEnemy enemy){
        enemies.remove(enemies.indexOf(enemy));
    }

}
