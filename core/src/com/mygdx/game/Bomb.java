package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public class Bomb {
    //Debug:
    boolean draw_hitbox = false;
    ShapeRenderer renderer;//can render the hitboxes

    GameController controller;

    /** Bomb Entitiers **/
    float bomb_height = 150, bomb_width = 150; //Bigest Possible: 250*250 //Smallest: 150*150
    float y_bomb = 200, x_bomb = 200;
    float drop_loc_y; //y on the ground plate where the Bomb explodes
    float drop_lane_x; // The x lane where the Bomb is dropped.
    float movement_speed = 10; //The downwar falling speedx

    boolean intact = true; //Falls if the Bom already hit the ground.

    Rectangle hitBox;
    float shrink_y = 0.14f;

    public Bomb(GameController controller, float drop_lane_x, float drop_loc_y){
        this.controller = controller;

        this.drop_lane_x = drop_lane_x;
        this.drop_loc_y = drop_loc_y;
        x_bomb = drop_lane_x;
        y_bomb = controller.getGameHeight();

        //Calculate size of Bomb:
        bomb_height = 250 -100*(drop_loc_y/330);
        bomb_width = bomb_height;
        hitBox = new Rectangle((int)(drop_lane_x-bomb_height/2),(int)(drop_loc_y-bomb_width/2),(int)bomb_width,(int)bomb_height);
        hitBox.height = (int)(bomb_height*shrink_y);
        hitBox.y += (int)(bomb_height/2 - (bomb_height*shrink_y)/2);
        renderer = new ShapeRenderer();
        renderer.setProjectionMatrix(controller.main_cam.combined);
        //load the Assets:
        loadAssets();
    }

    TextureAtlas atlas; //Contains Bomb asset.
    TextureRegion bomb_region;
    TextureRegion shadow; //The show, of the bomb
    public void loadAssets(){
        atlas = controller.getAtlas();
        bomb_region = atlas.findRegion("Bomb");
        shadow = atlas.findRegion("shadow");

    }

    public void update(SpriteBatch sb){
        y_bomb -= movement_speed;
        if(y_bomb-bomb_height/2 < drop_loc_y){ // Here the bob is destroyed / The exloson animation will be generated
            explode();
        }
        if(draw_hitbox) {
            renderer.begin(ShapeRenderer.ShapeType.Line);
            renderer.rect(hitBox.x, hitBox.y, hitBox.width, hitBox.height);
            renderer.end();
        }
        draw(sb);
    }

    public void draw(SpriteBatch sb){
        sb.begin();
        sb.draw(bomb_region,x_bomb-bomb_width/2,y_bomb-bomb_height/2,bomb_width,bomb_height);
        sb.draw(shadow,drop_lane_x-bomb_width/2,drop_loc_y-bomb_height/2,bomb_width,bomb_height);
        sb.end();
    }

    public void explode(){ // Will do the Player explosion Damage Calculation.
        intact = false;
        if(controller.player.checkIfBomHit(hitBox)){
            controller.live_manager.LIVES--;
        }
        controller.bomb_controler.destroyMe(this);
    }
}
