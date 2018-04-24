package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ScoreManager {

    GameController control;
    BitmapFont font;
    GlyphLayout layout;//Glyph layout to get the Text Width

    /**Score Entitys: **/
    int SCORE = 0;
    int time_bonus = 1;
    public int normal_kill = 100; //Enemy killed score bonus
    float timer = 0;

    public ScoreManager(GameController control) {
        this.control = control;
        font = new BitmapFont();
        font.getData().setScale(4,6);

       layout = new GlyphLayout(font,"0");

        //Sets the beginning difficulty
        control.enemy_control.spawn_speed = 2f;
        control.enemy_control.spawn_y_speed = 1.6f;
        control.bomb_controler.drop_time = 1.9f;

    }

    float dificulty_inrease = 35;
    float max_difficulty;
    float difficulty_timer = 0;
    int difficulty = 1;

    public void increaseDifficulty(){
        difficulty++;
        Gdx.app.log("WOW","DIfficultiy Increased");

        switch (difficulty){
            case 1:
                control.enemy_control.spawn_speed = 1.8f;
                control.enemy_control.spawn_y_speed = 1.7f;
                control.bomb_controler.drop_time = 1.8f;
                time_bonus = 2;
                break;
            case 2:
                control.enemy_control.spawn_speed = 1.4f;
                control.enemy_control.spawn_y_speed = 2.2f;
                control.bomb_controler.drop_time = 1.2f;
                time_bonus = 4;
                break;
            case 3:
                //Sets the beginning difficulty
                control.enemy_control.spawn_speed = 1.4f;
                control.enemy_control.spawn_y_speed = 2.4f;
                control.bomb_controler.drop_time = 0.8f;
                time_bonus = 7;
                break;
            case 4:
                control.enemy_control.spawn_speed = 1.2f;
                control.enemy_control.spawn_y_speed = 2.5f;
                control.bomb_controler.drop_time = 0.8f;
                time_bonus = 11;
                break;
            case 5:
                control.enemy_control.spawn_speed = 1.0f;
                control.enemy_control.spawn_y_speed = 2.5f;
                control.bomb_controler.drop_time = 0.8f;
                time_bonus = 16;
                break;
            case 6:
                control.enemy_control.spawn_speed = 0.9f;
                control.enemy_control.spawn_y_speed = 2.7f;
                control.bomb_controler.drop_time = 0.7f;
                time_bonus = 25;
        }
    }

    public void update(SpriteBatch sb) {
        difficulty_timer +=control.getDelta();
        if(difficulty_timer > dificulty_inrease){
            difficulty_timer = 0;
            increaseDifficulty();
        }
        timer += control.getDelta();
        if(timer > 1f ){
            timer = 0;
            SCORE += time_bonus;
        }
        layout.setText(font,""+SCORE);
        draw(sb);
    }

    public void draw(SpriteBatch sb) {
        sb.begin();

        font.draw(sb, ""+SCORE, (control.getGameWidth()/2)-layout.width/2, control.getGameHeight());

        sb.end();
    }

    public void increase(int i){
        SCORE += i;
    }


}
