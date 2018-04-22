package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class BasicEnemy {
    //Will at first use the same Animation, as the main Player.

    //The Right screen debt will be 1080
    GameController control;

    /** Enemys Entetys **/
    float x_pos = 710, y_pos = 0; //Between:
    float width = 400, height = 400; //Smallest 80*80 Biggest: 400*400 fiff:320
    float size_decrease_max = 320;
    float y_move_speed;

    float height_pane = 143;


    float animation_speed= 0.2f;

    Rectangle hitBox; //The Enemys Hitbox

    public BasicEnemy(GameController controller, float x_start, float y_speed){
        this.control = controller;

        this.x_pos = x_start;
        this.y_move_speed = y_speed;

        //load Enemy Assets:
        loadAssets();
    }

    TextureAtlas player_atlas;
    TextureRegion frame_1_A,frame_2_A,frame_3_A,frame_4_A,frame_5_A;
    TextureRegion[]frames;
    Animation walk;
    float animationTimer = 0;
    TextureRegion currenFrame;
    public void loadAssets(){
        player_atlas = control.getAtlas();
        // Normal Walk Frames:
        frame_1_A = player_atlas.findRegion("frame_1_A");
        frame_2_A = player_atlas.findRegion("frame_2_A");
        frame_3_A = player_atlas.findRegion("frame_3_A");//Idle frame
        frame_4_A = player_atlas.findRegion("frame_4_A");
        frame_5_A = player_atlas.findRegion("frame_5_A");

        currenFrame = frame_3_A;

        frames = new TextureRegion[]{frame_1_A,frame_2_A,frame_3_A,frame_4_A,frame_5_A};
        walk = new Animation(animation_speed,frames);
        walk.setPlayMode(Animation.PlayMode.LOOP);
        hitBox = new Rectangle();
    }

    public void update(SpriteBatch sb){
        animationTimer+= control.getDelta();
        currenFrame = (TextureRegion) walk.getKeyFrame(animationTimer);
        if(x_pos-705 +width/2< 0){
            if(!currenFrame.isFlipX()) {
                currenFrame.flip(true, false);
            }
        }else if(x_pos-705 +width/2 > 0){
            if(currenFrame.isFlipX())
                currenFrame.flip(true,false);
        }
        randomMovement();
        draw(sb);
    }

    public void draw(SpriteBatch sb){

        sb.begin();
        Vector2 new_pos = control.frontView.Project_to_game_space(x_pos,y_pos);
        Gdx.app.log("POS"," X_N: "+new_pos.x+"Y_N: "+new_pos.y);//Re renders the position and moves the view

        hitBox.set(new_pos.x+control.getGameWidth()-control.frontView.width_tile*2,new_pos.y,
                width-size_decrease_max*sizeMultiply(new_pos.y),height-size_decrease_max*sizeMultiply(new_pos.y));
        //Its beeing rendert in the midde of the sprite square:
        sb.draw(currenFrame,new_pos.x+control.getGameWidth()-control.frontView.width_tile*2,new_pos.y,

                width-size_decrease_max*sizeMultiply(new_pos.y),height-size_decrease_max*sizeMultiply(new_pos.y));
        sb.end();
    }

    public void randomMovement(){
        //Always moves forward and randomly, to left and right
        y_pos += y_move_speed; // Not so random ;) for the forward movement speed
        x_pos = (float) (2-Math.random()*4)+x_pos;

    }

    public boolean isInScope(float x_scope,float y_scope){
        if(hitBox.contains(new Vector2(x_scope,y_scope))){
            return true;
        } else{
            return false;
        }
    }




    public float sizeMultiply(float y){
        return y/height_pane;
    }

    public void gotShot(){ //When Player aimed and shot at this Enemy
        control.score_manager.increase(control.score_manager.normal_kill);
        control.enemy_control.requestRemove(this);
    }
}
