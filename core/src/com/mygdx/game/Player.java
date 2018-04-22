package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Player {
    /** The Main Player **/

    GameController control;

    public Player(GameController control){
        this.control = control;
        loadAnimation();//Loads the Player Walking Animation
    }

    /** Player Eigenschaften: **/
    boolean gun_drawn = false;


    private float animation_duration = 0.1f;
    private float animationTimer = 0;
    TextureRegion frame_1_A,frame_2_A, frame_3_A,frame_4_A,frame_5_A; //The normal walk Frames
    TextureRegion frame_1_B, frame_2_B, frame_3_B, frame_4_B, frame_5_B; //The "Gun Drawn" Frames
    TextureAtlas player_atlas;

    TextureRegion[] animation_regions_A, animation_regions_B;
    Animation walk_A,walk_B;

    Sprite player_container; //Will contail the player animation for easy scaling

    TextureRegion currentFrame; //The current animation frame

    /** Player Coordinates **/
    float x_hori = 400,y_hori = 300;
    float width_flat = 350, height_flat = 350; //Current Default size of the Player

    float default_width = 350, default_height = 350;
    //Smallest size: 200 x 200 also 150 bereich
    float y_min = 0, y_max = 330;
    float x_min = 0, x_max = 0;
    float scale = 1; //The Ratio the Player has to its default size

    public void loadAnimation(){
        player_atlas = control.getAtlas();
        // Normal Walk Frames:
        frame_1_A = player_atlas.findRegion("frame_1_A");
        frame_2_A = player_atlas.findRegion("frame_2_A");
        frame_3_A = player_atlas.findRegion("frame_3_A");//Idle frame
        frame_4_A = player_atlas.findRegion("frame_4_A");
        frame_5_A = player_atlas.findRegion("frame_5_A");
        // Gun Walk Frames:
        frame_1_B = player_atlas.findRegion("frame_1_B");
        frame_2_B = player_atlas.findRegion("frame_2_B");
        frame_3_B = player_atlas.findRegion("frame_3_B");//Idle frame
        frame_4_B = player_atlas.findRegion("frame_4_B");
        frame_5_B = player_atlas.findRegion("frame_5_B");


        animation_regions_A = new TextureRegion[]{frame_1_A,frame_2_A,frame_3_A,frame_4_A,frame_5_A};

        animation_regions_B = new TextureRegion[]{frame_1_B,frame_2_B,frame_3_B,frame_4_B,frame_5_B};

        walk_A = new Animation(animation_duration,animation_regions_A);
        walk_A.setPlayMode(Animation.PlayMode.LOOP);

        walk_B = new Animation(animation_duration,animation_regions_B);
        walk_B.setPlayMode(Animation.PlayMode.LOOP);

        currentFrame = (TextureRegion) walk_A.getKeyFrame(0f);
        player_container = new Sprite(currentFrame);

        x_max = control.getGameWidth()-2*control.frontView.width_tile;
    }

    TextureRegion currentFrame_A,currentFrame_B;
    Texture Frame_A, Frame_B;
    TextureRegion curren_player_view;

    public void update(SpriteBatch sb){
        animationTimer += control.getDelta();
        //Gdx.app.log("TIME",animationTimer+" ");
        //Decides which Player Animation will be drawn:

            currentFrame_A = ((TextureRegion) walk_A.getKeyFrame(animationTimer, true));
            currentFrame_B = ((TextureRegion) walk_B.getKeyFrame(animationTimer, true));

        //Frame_A = new Texture(currentFrame_A.getTexture());

        update_movement(); //Makes the Player Move, when controlled
        update_dimensions();
        draw(sb);


    }


    public void draw(SpriteBatch sb){

        sb.begin();
        if(gun_drawn) {
            sb.draw(currentFrame_B, x_hori, y_hori, width_flat, height_flat);
        }else {

            sb.draw(currentFrame_A, x_hori, y_hori, width_flat, height_flat);
        }

        sb.end();
    }

    public void draw_weapon(boolean draw){
        gun_drawn = draw;
    }

    //==============================Moving-Environment-of-the-Player==========
    float move_speed = 8f;
    public int dir_x = 0, dir_y = 0;
    public void move(){ // 1 is down 2 is up
        if(dir_x==1){
            if(x_hori-move_speed > x_min)
            x_hori -= move_speed;
        }else if(dir_x==2){
            if(x_hori+move_speed <x_max)
            x_hori += move_speed;
        }

        if(dir_y==1){
            if(y_hori-2 > y_min)
            y_hori -= move_speed;
        }else if(dir_y==2){
            if(y_hori+2 < y_max)
            y_hori += move_speed;
        }
    }

    public void update_movement(){
        if(dir_x == 0 && dir_y == 0){
            currentFrame_A = frame_3_A;
            currentFrame_B = frame_3_B;
        }

        if(dir_x==1){
            if(!currentFrame_A.isFlipX()){
                currentFrame_A.flip(true,false);
            }

            if(!currentFrame_B.isFlipX()){
                currentFrame_B.flip(true,false);
            }
        }

        if(dir_x==2){
            if(currentFrame_A.isFlipX()){
                currentFrame_A.flip(true,false);
            }

            if(currentFrame_B.isFlipX()){
                currentFrame_B.flip(true,false);
            }
        }

        move();
    }

    public void update_dimensions(){
        scale = y_hori/350;

        height_flat = default_height - 150*scale;
        width_flat = default_width -150*scale;

    }
}
