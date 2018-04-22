package com.mygdx.game;

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

    }

    public void update(SpriteBatch sb) {
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
