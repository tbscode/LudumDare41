package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Background {

    GameController controller;

    //Handels Placing the background Tiles.
    public Background(GameController controller){
        this.controller = controller;
        loadAssets();
    }

    String[] background_tiles_names = {"house_one.png","house_two.png"}; //Contains all Diffrerent Background Grafics
    TextureRegion house_1, house_2;
    TextureAtlas atlas;

    //The Levels Background Tiles set Individually:
    TextureRegion[] background_tiles;
    public void loadAssets(){
        atlas = controller.getAtlas();
        house_1 = atlas.findRegion("house_one");
        house_2 = atlas.findRegion("house_two");
        background_tiles = new TextureRegion[]{house_1,house_2,house_1,house_2,house_1
                ,house_2,house_1,house_1,house_1,house_2};
    }


    int pixel_width = 188; //Thr Pixel width of the house tiles

    public void update(SpriteBatch sb){
        draw(sb);
    }

    public void draw(SpriteBatch sb){
        sb.begin();
        for (int t = 0; t < background_tiles.length; t++)
            sb.draw(background_tiles[t],188*t,0);
        //Drawing the diffen houses;
        sb.end();
    }
}
