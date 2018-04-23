package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class FrontView {

    GameController controller;

    /** Front View Entities: E.G: Scale co. **/
    public float width_tile, height_tile = 1000;
    float scale;

    public FrontView(GameController controller){
        this.controller = controller;

        //loadFrontBackgroundView
        loadAssets();

    }

    TextureAtlas atlas; //The atlas also contains the Background
    TextureRegion Background_left, Background_right;
    public void loadAssets(){
        atlas = controller.getAtlas();
        Background_left = atlas.findRegion("3DB_left");
        Background_right = atlas.findRegion("3DB_right");

        scale = ((float)Background_left.getRegionWidth())/((float)Background_left.getRegionHeight());
        width_tile = height_tile*scale;
        //Gdx.app.log("WIDTH",""+Background_right.getRegionWidth()+Background_left.getRegionWidth());
    }

    public void update(SpriteBatch sb){ //Update The Complete Front View
        draw(sb);
    }

    public void draw(SpriteBatch sb){ //Draw the Front View
        sb.begin();
        sb.draw(Background_right,controller.getGameWidth()-width_tile,0,width_tile,height_tile);
        sb.draw(Background_left,controller.getGameWidth()-width_tile*2,0,width_tile,height_tile);
        sb.end();
    }

    float y_new, x_new;// Now x and y for actual game plain.
    float x_hori; //The Maximum x posion.
    float height_pane = 143; //The height in which the bebth will be converted
    float real_debth = 1120;
    float real_width = 1410, half_width = 705;
    float x_constant = 160;
    float x_increase = 545;
    public Vector2 Project_to_game_space(float x, float y) {
        y_new = height_pane - height_pane * (y / real_debth);
        x_hori = ((height_pane - y_new) / height_pane) * x_increase + x_constant;
        //Gdx.app.log("HORI", " " + x_hori);
        if ((x - half_width) > 0) { //On the right side
            x_new = half_width + ((x - half_width) / half_width) * x_hori;
        } else if ((x - half_width) < 0) {
            x_new = half_width - ((half_width - x) / half_width) * x_hori;
        } else if (x - half_width == 0) {
            x_new = half_width;
        }
        return new Vector2(x_new, y_new);
    }
}
