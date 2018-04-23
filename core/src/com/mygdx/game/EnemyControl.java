package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class EnemyControl {

    GameController control; //Controlls the Game

    ArrayList<BasicEnemy> enemies; //List with al the currently active enemys.

    public float spawn_speed = 3f; // pause beween spawns.
    public float spawn_y_speed = 1f;
    float timer = 0;

    public EnemyControl(GameController control){
        this.control = control;

        enemies =  new ArrayList<BasicEnemy>();
    }

    public void update(SpriteBatch sb){
        timer += control.getDelta();
        if(timer > spawn_speed){
            timer = 0;
            enemies.add(new BasicEnemy(control,(float) (1410*Math.random()), spawn_y_speed));
        }
        for(int i = 0; i < enemies.size();i++){
            enemies.get(i).update(sb);
        }
    }

    boolean aiming_at_enemy = false;
    boolean one_killed = false;
    public void checkIfEnemyInScope(float x_touch, float y_touch, boolean touched){
        for(int i = 0; i < enemies.size(); i++){
            if(enemies.get(i).isInScope(x_touch,y_touch)){
                aiming_at_enemy = true;
                control.player.gun_drawn = true;
                if(touched&&!one_killed){
                    enemies.get(i).gotShot();//Enemy is killed
                    one_killed = true;
                }
            }
        }

        for (int i = 0; i < enemies.size(); i++){
            if(enemies.get(i).inLeftView){
                if(control.player.checkIfBomHit(enemies.get(i).second_hitbox))
                {
                    control.live_manager.LIVES--;
                    enemies.get(i).gotShot();
                }
            }
        }

        if(!aiming_at_enemy)
            control.player.gun_drawn = false;
        aiming_at_enemy = false;
        one_killed = false;
    }

    public void requestRemove(BasicEnemy enemy){
        enemies.remove(enemies.indexOf(enemy));
    }

}
