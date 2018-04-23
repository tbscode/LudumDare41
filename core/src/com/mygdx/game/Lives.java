package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Lives {
    //This class will render and calculate Player lives.
    GameController control;

    public int LIVES = 3;//Max = 3
    public float width = 100;

    public Lives(GameController control){
        this.control = control;

        //load heart Asset:
        loadAssets();
    }

    TextureRegion hearth;
    public void loadAssets(){
        hearth = control.getAtlas().findRegion("herz");
    }

    public void update(SpriteBatch sb){
        if(LIVES <= 0){
            control.GAME_OVER = true;
        }
        draw(sb);
    }

    public void draw(SpriteBatch sb){
        sb.begin();
        for(int i = 0; i < LIVES; i++){
            sb.draw(hearth,(control.getGameWidth()/2-(width*1.5f)+width*i),0,width,width);
        }
        sb.end();
    }
}
