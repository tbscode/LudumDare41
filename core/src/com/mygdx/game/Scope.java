package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Scope {
    //The Aim Scope to shoot le opponents

    /** Scopes Entitys: Eg: Width/Height **/
    float width = 200,height = 200;
    public float x_draw = 0, y_draw = 0;

    GameController controller;//Die game control

    public Scope(GameController controller){
        this.controller = controller;
        loadAssets();//loadsTheAssets
    }

    TextureRegion scope_region;
    public void loadAssets(){
        scope_region =  (controller.getAtlas()).findRegion("Scope");
    }

    public void update(SpriteBatch sb){
        draw(sb);
    }

    public void draw(SpriteBatch sb){
        sb.begin();
        sb.draw(scope_region,x_draw-width/2,y_draw-height/2,width,height); //Draws the Scope Centered
        sb.end();
    }
}
